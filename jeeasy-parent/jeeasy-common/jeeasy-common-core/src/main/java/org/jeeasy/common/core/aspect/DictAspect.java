package org.jeeasy.common.core.aspect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
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
import org.jeeasy.common.core.annotation.Dict;
import org.jeeasy.common.core.annotation.DictTranslation;
import org.jeeasy.common.core.domain.vo.TableDictVo;
import org.jeeasy.common.core.service.DictTranslationService;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.config.property.DictEnumProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Resource
    DictTranslationService dictTranslationService;



    @Resource
    ObjectMapper mapper;

    /**
     * 定义切点Pointcut拦截所有对服务器的请求
     */
    @Pointcut(value = "@annotation(org.jeeasy.common.core.annotation.DictTranslation)")
    public void dictTranslation() {
    }

    @Around(value = "@annotation(dictTranslation)")
    public Object doTranslation(final ProceedingJoinPoint pjp, DictTranslation dictTranslation) throws Throwable {
//        long time1 = System.currentTimeMillis();
        TimeInterval timer = DateUtil.timer();
        Object result = pjp.proceed();
//        long time2 = System.currentTimeMillis();
        log.debug("获取JSON数据 耗时：" + timer.interval() + "ms");
        long start = System.currentTimeMillis();
        if (result instanceof R) {
            R dataResult = (R) result;
            if (dataResult.getResult() instanceof IPage) {
                List<Map<String, Object>> items = new ArrayList<>();
                IPage page = (IPage) dataResult.getResult();
                for (Object record : page.getRecords()) {
                    items.add(translate(record));
                }
                page.setRecords(items);
            } else if(dataResult.getResult() instanceof List) {
                List<?> list = (List<?>) dataResult.getResult();
                List<Map<String, Object>> listTemp = new ArrayList<>();
                list.forEach(record -> {
                    listTemp.add(translate(record));
                });
                dataResult.setResult(listTemp);
            } else {
                dataResult.setResult(translate(dataResult.getResult()));
            }
        }
        long end = System.currentTimeMillis();
        log.debug("解析注入JSON数据  耗时" + (end - start) + "ms");
        return result;
    }

    /**
     * 翻译返回的数据对象
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
        if(ObjectUtil.isNull(record)){
            return objectNode;
        }
        for (Field field : ClassUtil.getDeclaredFields(record.getClass())) {
            //update-end--Author:scott  -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
            JsonIgnore ignoreAnnotation = field.getAnnotation(JsonIgnore.class);
            Dict dictAnnotation = field.getAnnotation(Dict.class);
            String fieldName = field.getName();
            Object fieldValue = BeanUtil.getFieldValue(record, fieldName);
            // 排除有 @JsonIgnore 注解的字段
            if (Tools.isEmpty(ignoreAnnotation)) {
                // 对有 @Dict 注解的字段进行翻译
                if (Tools.isNotEmpty(dictAnnotation)) {
                    //翻译字典值对应的txt
                    String textValue = translateDictValue(dictAnnotation, fieldValue);

                    log.debug(" 字典Val : " + textValue);
                    log.debug(" __翻译字典字段__ " + fieldName + dictTextSuffix + "： " + textValue);
                    assert objectNode != null;
                    objectNode.put(fieldName + dictTextSuffix, textValue);
                } else {
                    Class<? extends Enum> aClass = dictEnumProperty.getAutoTranslateEnumClass().get(fieldName);
                    if (Tools.isNotEmpty(aClass)) {
                        objectNode.put(fieldName + dictTextSuffix, dictTranslationService.translateDictFromEnum(aClass, fieldValue));
                    }
                }

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

        if(Tools.isNotEmpty(enumClass) && !enumClass.equals(Enum.class)){
            return translateEnum(enumClass, value);
        }

        if(ArrayUtil.contains(dictEnumProperty.getDictTableFlag(), code.charAt(0))){
            TableDictVo tableDictVo = dictTranslationService.getTableDictByCode(code);
            return translateTableDict(tableDictVo, value);
        }

        return translateDict(code, value);


//        StringBuilder textValue = new StringBuilder();
//        String fieldValue = value.toString();
//        // 含有 "," 则按拼接值处理
//        if (StrUtil.contains(fieldValue, StrUtil.C_COMMA)) {
//            List<String> values = StrUtil.split(fieldValue, StrUtil.C_COMMA, true, true);
//            values.forEach(val -> {
//                log.debug(" 字典 value : " + val);
//                if(Tools.isNotEmpty(val)){
//                    String tmpValue = translate(code, enumClass, val);
//                    if (tmpValue != null) {
//                        if (Tools.isNotEmpty(textValue.toString())) {
//                            textValue.append(StrUtil.C_COMMA);
//                        }
//                        textValue.append(tmpValue);
//                    }
//                }
//            });
//        } else {
//            textValue.append(translate(code, enumClass, value));
//        }
//
//        return textValue.toString();
    }


    private String translateDict(String code, Object value){
        StringBuilder textValue = new StringBuilder();
        String fieldValue = value.toString();
        // 含有 "," 则按拼接值处理
        if (StrUtil.contains(fieldValue, StrUtil.C_COMMA)) {
            List<String> values = StrUtil.split(fieldValue, StrUtil.C_COMMA, true, true);
            values.forEach(val -> {
                log.debug(" 字典 value : " + val);
                if(Tools.isNotEmpty(val)){
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
            String tmpValue = dictTranslationService.translateDict(code, value);
            if(Tools.isNotEmpty(tmpValue)){
                textValue.append(tmpValue);
            }
        }

        return textValue.toString();
    }


    private String translateEnum(Class<? extends Enum> enumClass, Object value){
        StringBuilder textValue = new StringBuilder();
        String fieldValue = value.toString();
        // 含有 "," 则按拼接值处理
        if (StrUtil.contains(fieldValue, StrUtil.C_COMMA)) {
            List<String> values = StrUtil.split(fieldValue, StrUtil.C_COMMA, true, true);
            values.forEach(val -> {
                log.debug(" 字典 value : " + val);
                if(Tools.isNotEmpty(val)){
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
            if(Tools.isNotEmpty(tmpValue)){
                textValue.append(tmpValue);
            }
        }

        return textValue.toString();
    }


    private String translateTableDict(TableDictVo tableDict, Object value){
        StringBuilder textValue = new StringBuilder();
        String fieldValue = value.toString();
        // 含有 "," 则按拼接值处理
        if (StrUtil.contains(fieldValue, StrUtil.C_COMMA)) {
            List<String> values = StrUtil.split(fieldValue, StrUtil.C_COMMA, true, true);
            values.forEach(val -> {
                log.debug(" 字典 value : " + val);
                if(Tools.isNotEmpty(val)){
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
            String tmpValue = dictTranslationService.translateDictFromTable(tableDict, value);
            if(Tools.isNotEmpty(tmpValue)){
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
