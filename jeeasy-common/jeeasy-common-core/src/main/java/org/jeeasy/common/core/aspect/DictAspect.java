package org.jeeasy.common.core.aspect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jeeasy.common.core.annotation.dict.Dict;
import org.jeeasy.common.core.annotation.dict.DictTranslation;
import org.jeeasy.common.core.config.property.DictEnumProperty;
import org.jeeasy.common.core.domain.dto.TranslateDictDTO;
import org.jeeasy.common.core.domain.dto.TranslateDictFromTableDTO;
import org.jeeasy.common.core.domain.vo.BaseTree;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.domain.vo.TableDictVo;
import org.jeeasy.common.core.service.IDictTranslationService;
import org.jeeasy.common.core.tools.JStopWatch;
import org.jeeasy.common.core.tools.Tools;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author AlpsDDJ
 * @date 2020/11/23 9:33
 */
@Slf4j
@Aspect
@Component
@EnableConfigurationProperties({DictEnumProperty.class})
public class DictAspect {

    @Resource
    private DictEnumProperty dictEnumProperty;

    @Lazy
    @Resource
    IDictTranslationService dictTranslationService;


    @Resource
    ObjectMapper mapper;

    /**
     * 定义切点Pointcut拦截所有对服务器的请求
     */
    @Pointcut(value = "@annotation(org.jeeasy.common.core.annotation.dict.DictTranslation)")
    public void dictTranslation() {
    }

    @Around(value = "@annotation(dictTranslation)")
    public Object doTranslation(final ProceedingJoinPoint pjp, DictTranslation dictTranslation) throws Throwable {
        JStopWatch stopWatch = JStopWatch.create("字典翻译", "获取JSON数据");
        Object result = pjp.proceed();
        stopWatch.startNew("解析注入JSON数据");
        if (result instanceof R) {
            R dataResult = (R) result;
            if (dataResult.getData() instanceof IPage) {
                List<Map<String, Object>> items = new ArrayList<>();
                IPage<Map<String, Object>> page = (IPage) dataResult.getData();
                for (Object record : page.getRecords()) {
                    items.add(translate(record));
                }
                page.setRecords(items);
            } else if (dataResult.getData() instanceof List) {
                List<?> list = (List<?>) dataResult.getData();
                List<Map<String, Object>> listTemp = new ArrayList<>();
                list.forEach(record -> {
                    listTemp.add(translate(record));
                });
                dataResult.setData(listTemp);
            } else {
                dataResult.setData(translate(dataResult.getData()));
            }
        }
        log.debug("\n{}", stopWatch.stopAndPrettyPrint());
        return result;
    }

    /**
     * 翻译返回的数据对象
     *
     * @param record
     * @return
     */
    private Map<String, Object> translate(Object record) {
        String json = "{}";
        try {
            // 解决@JsonFormat注解解析不了的问题详见SysAnnouncement类的@JsonFormat
            json = mapper.writeValueAsString(record);
        } catch (JsonProcessingException e) {
            log.error("json解析失败" + e.getMessage(), e);
        }
//                ObjectNode objectNode = mapper.valueToTree(record);

        Map<String, Object> objectNode = null;
        try {
            objectNode = mapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String dictTextSuffix = dictEnumProperty.getDictTextSuffix();
        if (ObjectUtil.isNull(record)) {
            return objectNode;
        }
        for (Field field : ClassUtil.getDeclaredFields(record.getClass())) {
            //update-end--Author:scott  -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
            JsonIgnore ignoreAnnotation = AnnotationUtils.getAnnotation(field, JsonIgnore.class);
            //Dict dictAnnotation = field.getAnnotation(Dict.class);
            Dict dictAnnotation = AnnotationUtils.getAnnotation(field, Dict.class);
            String fieldName = field.getName();
            Object fieldValue = BeanUtil.getFieldValue(record, fieldName);
            // 排除有 @JsonIgnore 注解的字段
            if (Tools.isEmpty(ignoreAnnotation)) {
                // 对有 @Dict 注解的字段进行翻译
                if (Tools.isNotEmpty(dictAnnotation)) {
                    //翻译字典值对应的txt
                    String textValue = null;
                    if (dictAnnotation != null) {
                        textValue = translateDictValue(dictAnnotation, fieldValue);
                    }

                    //log.debug(" 字典Val : " + textValue);
                    log.debug("翻译字典字段： fieldName = " + fieldName + dictTextSuffix + ", textValue = " + textValue);
                    assert objectNode != null;
                    objectNode.put(fieldName + dictTextSuffix, textValue);
                } else {
//                    Class<? extends Enum> aClass = dictEnumProperty.getAutoTranslateEnumClass().get(fieldName);
                    AtomicReference<Class<? extends Enum>> aClass = new AtomicReference<>(null);
                    dictEnumProperty.getAutoTranslateEnumClass().forEach(clazz -> {
                        String clazzName = clazz.getSimpleName();
                        if (clazzName.replace("Enum", "").equalsIgnoreCase(fieldName)) {
                            aClass.set(clazz);
                        }
                    });
                    if (null != aClass.get()) {
                        objectNode.put(fieldName + dictTextSuffix, dictTranslationService.translateDictFromEnum(aClass.get(), fieldValue));
                    }
                }

            }
        }

        // 树形结构数据 字典翻译
        if (record instanceof BaseTree) {
            List<Map<String, Object>> childrenMap = new ArrayList<>();
            List<?> children = ((BaseTree<?>) record).getChildren();
            if (BeanUtil.isNotEmpty(children)) {
                children.forEach(r -> {
                    childrenMap.add(translate(r));
                });
                objectNode.put("children", childrenMap);
            }
        }

        return objectNode;
    }

    /**
     * 根据 @Dict 注释参数 翻译字典文本
     *
     * @param value 数据库查询出的值
     * @return 翻译后的值
     */
    private String translateDictValue(Dict dictAnnotation, Object value) {
        String code = dictAnnotation.dictCode();
        Class<? extends Enum> enumClass = dictAnnotation.dictEnum();
        if (Tools.isEmpty(value)) {
            return StrUtil.EMPTY;
        }

        if (null != enumClass && !enumClass.equals(Enum.class)) {
            return translateEnum(enumClass, value);
        }

        if (ArrayUtil.contains(dictEnumProperty.getDictTableFlag(), code.charAt(0))) {
            R<TableDictVo> tableDictByCode = dictTranslationService.getTableDictByCode(code.substring(1));
            TableDictVo tableDictVo = tableDictByCode.getData();
            return translateTableDict(tableDictVo, value);
        }

        return translateDict(code, value);
    }


    private String translateDict(String code, Object value) {
        StringBuilder textValue = new StringBuilder();
        String fieldValue = value.toString();
        // 含有 "," 则按拼接值处理
        if (StrUtil.contains(fieldValue, StrUtil.C_COMMA)) {
            List<String> values = StrUtil.split(fieldValue, StrUtil.C_COMMA, true, true);
            values.forEach(val -> {
                log.debug(" 字典 value : " + val);
                if (Tools.isNotEmpty(val)) {
                    String tmpValue = translateDict(code, val);
                    if (Tools.isNotEmpty(tmpValue)) {
                        if (Tools.isNotEmpty(textValue.toString())) {
                            textValue.append(StrUtil.C_COMMA);
                        }
                        textValue.append(tmpValue);
                    }
                }
            });
        } else {
            String tmpValue = dictTranslationService.translateDict(new TranslateDictDTO(code, value)).getData();
            if (Tools.isNotEmpty(tmpValue)) {
                textValue.append(tmpValue);
            }
        }

        return textValue.toString();
    }


    private String translateEnum(Class<? extends Enum> enumClass, Object value) {
        StringBuilder textValue = new StringBuilder();
        String fieldValue = value.toString();
        // 含有 "," 则按拼接值处理
        if (StrUtil.contains(fieldValue, StrUtil.C_COMMA)) {
            List<String> values = StrUtil.split(fieldValue, StrUtil.C_COMMA, true, true);
            values.forEach(val -> {
                log.debug(" 字典 value : " + val);
                if (Tools.isNotEmpty(val)) {
                    String tmpValue = translateEnum(enumClass, val);
                    if (Tools.isNotEmpty(tmpValue)) {
                        if (Tools.isNotEmpty(textValue.toString())) {
                            textValue.append(StrUtil.C_COMMA);
                        }
                        textValue.append(tmpValue);
                    }
                }
            });
        } else {
            String tmpValue = dictTranslationService.translateDictFromEnum(enumClass, value);
            if (Tools.isNotEmpty(tmpValue)) {
                textValue.append(tmpValue);
            }
        }

        return textValue.toString();
    }


    private String translateTableDict(TableDictVo tableDict, Object value) {
        StringBuilder textValue = new StringBuilder();
        String fieldValue = value.toString();
        // 含有 "," 则按拼接值处理
        if (StrUtil.contains(fieldValue, StrUtil.C_COMMA)) {
            List<String> values = StrUtil.split(fieldValue, StrUtil.C_COMMA, true, true);
            values.forEach(val -> {
                log.debug(" 字典 value : " + val);
                if (Tools.isNotEmpty(val)) {
                    String tmpValue = translateTableDict(tableDict, val);
                    if (Tools.isNotEmpty(tmpValue)) {
                        if (Tools.isNotEmpty(textValue.toString())) {
                            textValue.append(StrUtil.C_COMMA);
                        }
                        textValue.append(tmpValue);
                    }
                }
            });
        } else {
            TranslateDictFromTableDTO tableDTO = new TranslateDictFromTableDTO(tableDict, value);
            String tmpValue = dictTranslationService.translateDictFromTable(tableDTO).getData();
            if (Tools.isNotEmpty(tmpValue)) {
                textValue.append(tmpValue);
            }
        }

        return textValue.toString();
    }

//    /**
//     * 根据code首字符是够为 # 判断字典值获取方法
//     * enumClass != null --> 根据enum获取
//     * code首字符 == # --> 根据table获取
//     * code首字符 != # --> 根据sysDict获取
//     *
//     * @param code
//     * @param enumClass
//     * @param value
//     * @return
//     */
//    private String translate(String code, Class<? extends Enum> enumClass, Object value) {
//        String tmpValue = null;
//        if (Tools.isNotEmpty(enumClass) && !enumClass.equals(Enum.class)) {
//            tmpValue = dictTranslationService.translateDictFromEnum(enumClass, value);
//        }
//        if (Tools.isNotEmpty(code)) {
//            if (ArrayUtil.contains(dictEnumProperty.getDictTableFlag(), code.charAt(0))) {
//
//                TableDictVo tableDictVo = dictTranslationService.getTableDictByCode(code);
//
//                tmpValue = dictTranslationService.translateDictFromTable(code, value);
//            } else {
//                tmpValue = dictTranslationService.translateDict(code, value);
//            }
//        }
//        return tmpValue;
//    }

}
