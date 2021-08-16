package org.jeeasy.system.modules;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiModelProperty;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.modules.depart.domain.SysDepart;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Field[] declaredFields = ClassUtil.getDeclaredFields(SysDepart.class);
        Map<String, String> map = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            map.put(fieldName, fieldName);
            String fieldText = "";
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
            if(annotation != null && Tools.isNotEmpty(annotation.value())){
                fieldText = annotation.value();
            }
            map2.put(fieldName, fieldText);
        }
        System.out.println(JSONUtil.toJsonStr(map));
        System.out.println(JSONUtil.toJsonStr(map2));
    }
}
