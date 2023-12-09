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
 * 字典工具类
 *
 * @author AlpsDDJ
 * @date 2021/8/13 11:26
 */
public class DictUtil {
    /**
     * 根据代码获取字典枚举列表
     *
     * @param code 代码
     * @return 字典枚举列表
     */
    public static List<IDictEnum<?>> getDictEnum(String code) {
        // 扫描包路径下的所有继承自IDictEnum的类
        Set<Class<?>> classSet = ClassUtil.scanPackageBySuper("org.jeeasy", IDictEnum.class);
        List<IDictEnum<?>> dictEnumList = new ArrayList<>();
        // 遍历每个类
        classSet.forEach(cls -> {
            String className = cls.getSimpleName();

            // 获取类的代码
            String dictCode = StrUtil.toCamelCase(className.replace("Enum", ""));
            // 判断代码是否与给定的代码相等或转换为下划线形式后相等
            if (code.equalsIgnoreCase(dictCode) || code.equalsIgnoreCase(StrUtil.toUnderlineCase(dictCode))) {
                System.out.println(dictCode);
                // 获取枚举的映射表
                LinkedHashMap<String, Enum> enumMap = EnumUtil.getEnumMap((Class<Enum>) cls);
                // 将枚举添加到字典枚举列表中
                enumMap.forEach((key, value) -> dictEnumList.add((IDictEnum<?>) value));
            }
        });
        // 返回字典枚举列表
        return dictEnumList;
    }
}
