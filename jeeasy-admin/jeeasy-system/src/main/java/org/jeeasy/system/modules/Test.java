package org.jeeasy.system.modules;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.modules.premission.domain.SysPermission;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Field[] declaredFields = ClassUtil.getDeclaredFields(SysPermission.class);
        Map<String, String> map = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
//        System.out.println(declaredFields.length);
        for (Field field : declaredFields) {
            String fieldName = field.getName();
//            System.out.println(fieldName);
            map.put(fieldName, fieldName);
            String fieldText = "";
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
            if(annotation != null && Tools.isNotEmpty(annotation.value())){
                fieldText = annotation.value();
            }
            String simpleName = field.getType().getSimpleName();
            map2.put(fieldName, fieldText);
//            System.out.println(fieldName);

            String s = StrUtil.format("@Field('{}') {}?: {};", fieldText, fieldName, simpleName);
            System.out.println(s);

        }
//        map2.values().forEach(System.out::println);

//        System.out.println(JSONUtil.toJsonStr(map));
//        System.out.println(JSONUtil.toJsonStr(map2));
    }
}
