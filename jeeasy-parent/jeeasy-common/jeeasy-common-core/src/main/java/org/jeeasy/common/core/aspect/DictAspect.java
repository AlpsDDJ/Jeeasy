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
import org.jeeasy.common.core.config.property.DictEnumProperty;
import org.jeeasy.common.core.service.DictTranslationService;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.vo.R;
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
            if (Tools.isEmpty(ignoreAnnotation)) {
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
     * 根据Dict注释参数 翻译字典文本
     *
     * @param key
     * @return
     */
    private String translateDictValue(Dict dictAnnotation, Object key) {
        String code = dictAnnotation.dictCode();
//        Dict.DictType dictType = dictAnnotation.dictType();
        Class<? extends Enum> enumClass = dictAnnotation.dictEnum();
        if (Tools.isEmpty(key)) {
            return null;
        }
        StringBuilder textValue = new StringBuilder();
        // 含有 "," 则按拼接值处理
        if (StrUtil.contains(key.toString(), ",")) {
            String[] keys = key.toString().split(",");
            for (String k : keys) {
                String tmpValue = null;
                log.debug(" 字典 key : " + k);
                if (k.trim().length() == 0) {
                    continue; //跳过循环
                }
//                tmpValue = translate(code, dictType, enumClass, k.trim());
                tmpValue = translate(code, enumClass, k.trim());
                if (tmpValue != null) {
                    if (!"".equals(textValue.toString())) {
                        textValue.append(",");
                    }
                    textValue.append(tmpValue);
                }
            }
        } else {
//            textValue.append(translate(code, dictType, enumClass, key));
            textValue.append(translate(code, enumClass, key));
        }

        return textValue.toString();
    }

    /**
     * 根据code首字符是够为 # 判断字典值获取方法
     * enumClass != null --> 根据enum获取
     * code首字符 == # --> 根据table获取
     * code首字符 != # --> 根据sysDict获取
     *
     * @param code
     * @param enumClass
     * @param key
     * @return
     */
    private String translate(String code, Class<? extends Enum> enumClass, Object key) {
        String tmpValue = null;
        if (Tools.isNotEmpty(enumClass) && !enumClass.equals(Enum.class)) {
            tmpValue = dictTranslationService.translateDictFromEnum(enumClass, key);
        }
        if (Tools.isNotEmpty(code)) {
            String codeFlag = String.valueOf(code.charAt(0));
            if (ArrayUtil.contains(dictEnumProperty.getDictTableFlag(), codeFlag)) {
                tmpValue = dictTranslationService.translateDictFromTable(code, key);
            } else {
                tmpValue = dictTranslationService.translateDict(code, key);
            }
        }
        return tmpValue;
    }

}
