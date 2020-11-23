package org.jeeasy.common.core.aspect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jeeasy.common.core.annotation.Dict;
import org.jeeasy.common.core.annotation.DictTranslation;
import org.jeeasy.common.core.service.DictTranslationService;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class DictAspect {
    //表对应字段加上_dictText即可显示出文本
    private static String DICT_TEXT_SUFFIX = "_dictText";

//    private Map<String, >

    @Autowired
    DictTranslationService dictTranslationService;

    @Autowired
    ObjectMapper mapper;

    //定义切点Pointcut拦截所有对服务器的请求
    @Pointcut(value = "@annotation(org.jeeasy.common.core.annotation.DictTranslation)")
    public void dictTranslation() {
        log.info("字典解析");
    }

    @Around(value = "dictTranslation() && @annotation(dictTranslation)", argNames = "dictTranslation")
    public Object doTranslation(final ProceedingJoinPoint pjp, DictTranslation dictTranslation) throws Throwable {
        long time1 = System.currentTimeMillis();
        Object result = pjp.proceed();
        long time2 = System.currentTimeMillis();
        log.debug("获取JSON数据 耗时：" + (time2 - time1) + "ms");
        long start = System.currentTimeMillis();
        this.parseDictText(result);
        long end = System.currentTimeMillis();
        log.debug("解析注入JSON数据  耗时" + (end - start) + "ms");
        return result;
    }

    /**
     * 本方法针对返回对象为Result 的IPage的分页列表数据进行动态字典注入
     * 字典注入实现 通过对实体类添加注解@dict 来标识需要的字典内容,字典分为单字典code即可 ，table字典 code table text配合使用与原来jeecg的用法相同
     * 示例为SysUser   字段为sex 添加了注解@Dict(dicCode = "sex") 会在字典服务立马查出来对应的text 然后在请求list的时候将这个字典text，已字段名称加_dictText形式返回到前端
     * 例输入当前返回值的就会多出一个sex_dictText字段
     * {
     * sex:1,
     * sex_dictText:"男"
     * }
     * 前端直接取值sext_dictText在table里面无需再进行前端的字典转换了
     * customRender:function (text) {
     * if(text==1){
     * return "男";
     * }else if(text==2){
     * return "女";
     * }else{
     * return text;
     * }
     * }
     * 目前vue是这么进行字典渲染到table上的多了就很麻烦了 这个直接在服务端渲染完成前端可以直接用
     *
     * @param result
     */
    private void parseDictText(Object result) {
        if (result instanceof R) {

            R dataResult = (R) result;

            if (dataResult.getResult() instanceof IPage) {
                List<Map<String, Object>> items = new ArrayList<>();
                IPage page = (IPage) dataResult.getResult();
                for (Object record : page.getRecords()) {
//                    Map<String, Object> beanMap = BeanUtil.beanToMap(record);
//                    String json = "{}";
//                    try {
//                        // 解决@JsonFormat注解解析不了的问题详见SysAnnouncement类的@JsonFormat
//                        json = mapper.writeValueAsString(record);
//                    } catch (JsonProcessingException e) {
//                        log.error("json解析失败" + e.getMessage(), e);
//                    }
//
//                    Map<String, Object> objectNode = null;
//                    try {
//                        objectNode = mapper.readValue(json, Map.class);
//                    } catch (JsonProcessingException e) {
//                        e.printStackTrace();
//                    }
//
////                    JSONObject item = JSONUtil.parseObj(json);
//                    //update-begin--Author:scott -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
//                    //for (Field field : record.getClass().getDeclaredFields()) {
//                    for (Field field : ClassUtil.getDeclaredFields(record.getClass())) {
//                        //update-end--Author:scott  -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
//                        Dict annotation = field.getAnnotation(Dict.class);
//                        if (Tools.isNotEmpty(annotation)) {
//                            String code = annotation.dictCode();
//                            Dict.DictType dictType = annotation.dictType();
//                            Class<? extends Enum> enumClass = annotation.dictEnum();
//                            Object key = BeanUtil.getFieldValue(record, field.getName());
//
//                            //翻译字典值对应的txt
//                            String textValue = translateDictValue(code, dictType, enumClass, key);
//
//                            log.debug(" 字典Val : " + textValue);
//                            log.debug(" __翻译字典字段__ " + field.getName() + DICT_TEXT_SUFFIX + "： " + textValue);
//                            objectNode.put(field.getName() + DICT_TEXT_SUFFIX, textValue);
//                        }
//                    }
                    items.add(translate(record));
                }
                page.setRecords(items);
            }else{
//                Object record = dataResult.getResult();
//                String json = "{}";
//                try {
//                    // 解决@JsonFormat注解解析不了的问题详见SysAnnouncement类的@JsonFormat
//                    json = mapper.writeValueAsString(record);
//                } catch (JsonProcessingException e) {
//                    log.error("json解析失败" + e.getMessage(), e);
//                }
////                ObjectNode objectNode = mapper.valueToTree(record);
//
//                Map<String, Object> objectNode = null;
//                try {
//                    objectNode = mapper.readValue(json, Map.class);
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
//                for (Field field : ClassUtil.getDeclaredFields(record.getClass())) {
//                    //update-end--Author:scott  -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
//                    Dict annotation = field.getAnnotation(Dict.class);
//                    if (Tools.isNotEmpty(annotation)) {
//                        String code = annotation.dictCode();
//                        Dict.DictType dictType = annotation.dictType();
//                        Class<? extends Enum> enumClass = annotation.dictEnum();
//                        Object key = BeanUtil.getFieldValue(record, field.getName());
//
//                        //翻译字典值对应的txt
//                        String textValue = translateDictValue(code, dictType, enumClass, key);
//
//                        log.debug(" 字典Val : " + textValue);
//                        log.debug(" __翻译字典字段__ " + field.getName() + DICT_TEXT_SUFFIX + "： " + textValue);
//                        objectNode.put(field.getName() + DICT_TEXT_SUFFIX, textValue);
//                    }
//                }
                dataResult.setResult(translate(dataResult.getResult()));
            }

        }
    }

    private Map<String, Object> translate(Object record){
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
        for (Field field : ClassUtil.getDeclaredFields(record.getClass())) {
            //update-end--Author:scott  -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
            Dict dictAnnotation = field.getAnnotation(Dict.class);
            if (Tools.isNotEmpty(dictAnnotation)) {
                Object key = BeanUtil.getFieldValue(record, field.getName());
                //翻译字典值对应的txt
                String textValue = translateDictValue(dictAnnotation, key);

                log.debug(" 字典Val : " + textValue);
                log.debug(" __翻译字典字段__ " + field.getName() + DICT_TEXT_SUFFIX + "： " + textValue);
                objectNode.put(field.getName() + DICT_TEXT_SUFFIX, textValue);
            }
        }
        return objectNode;
    }

    /**
     * 翻译字典文本
     *
     * @param key
     * @return
     */
    private String translateDictValue(Dict dictAnnotation, Object key) {
        String code = dictAnnotation.dictCode();
        Dict.DictType dictType = dictAnnotation.dictType();
        Class<? extends Enum> enumClass = dictAnnotation.dictEnum();
        if (Tools.isEmpty(key)) {
            return null;
        }
        StringBuffer textValue = new StringBuffer();
        // 含有 "," 则按拼接值处理
        if(StrUtil.contains(key.toString(), ",")){

            String[] keys = key.toString().split(",");
            for (String k : keys) {
                String tmpValue = null;
                log.debug(" 字典 key : " + k);
                if (k.trim().length() == 0) {
                    continue; //跳过循环
                }
                tmpValue = translate(code, dictType, enumClass, k.trim());
                if (tmpValue != null) {
                    if (!"".equals(textValue.toString())) {
                        textValue.append(",");
                    }
                    textValue.append(tmpValue);
                }
            }
        } else {
            textValue.append(translate(code, dictType, enumClass, key));
        }

        return textValue.toString();
    }

    private String translate(String code, Dict.DictType dictType, Class<? extends Enum> enumClass, Object key){

        String tmpValue = null;
        switch (dictType) {
            case DICT:
                tmpValue = dictTranslationService.translateDict(code, key);
                break;
            case ENUM:
                tmpValue = dictTranslationService.translateDictFromEnum(enumClass, key);
                break;
            case TABLE:
                tmpValue = dictTranslationService.translateDictFromTable(code, key);
                break;
            default:
                break;
        }
        return tmpValue;
    }

}
