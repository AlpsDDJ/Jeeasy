package org.jeeasy.common.core.tools;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import org.jeeasy.common.core.enums.IDictEnum;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * @author AlpsDDJ
 * @date 2021/8/13 11:26
 */
public class DictUtil {
    public static List<IDictEnum<?>> getDictEnum(String code){
//        AtomicReference<Map<String, Object>> value = new AtomicReference<>();
        Set<Class<?>> classSet = ClassUtil.scanPackageBySuper("org.jeeasy", IDictEnum.class);
        List<IDictEnum<?>> dictEnumList = new ArrayList<>();
        classSet.forEach(cls -> {
            String className = cls.getSimpleName();

            String dictCode = StrUtil.toCamelCase(className.replace("Enum", ""));
            if(code.equalsIgnoreCase(dictCode) || code.equalsIgnoreCase(StrUtil.toUnderlineCase(dictCode))){
                System.out.println(dictCode);
                LinkedHashMap<String, Enum> enumMap = EnumUtil.getEnumMap((Class<Enum>) cls);
                enumMap.entrySet().forEach(e -> {
                    dictEnumList.add((IDictEnum<?>) e.getValue());
                });

//                LinkedHashMap<String, Enum<? extends IDictEnum>> enumMap = EnumUtil.getEnumMap(eCls);
//                enumMap.forEach(entity -> {
//                    System.out.println(entity. + " - - " + entity.getValue());
//                });



//                value.set(EnumUtil.getNameFieldMap((Class<? extends Enum<?>>) cls, "value"));
            }
        });
        return dictEnumList;
    }


    public static void main(String[] args) {

        List<IDictEnum<?>> sysUserStatus = DictUtil.getDictEnum("enable_flag");
        sysUserStatus.forEach(entity -> {
            System.out.println(entity.getValue() + " - - " + entity.getText());
        });

    }
}
