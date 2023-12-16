package org.jeeasy.generate.generator;

import cn.hutool.core.text.NamingCase;
import lombok.Data;

/**
 * 用于转换字符串为其他类型的案例风格的类。
 */
@Data
public class CaseName {

    private String underline;  // 下划线
    private String pascal;  // 大驼峰
    private String camel;  // 小驼峰
    private String kebab;  // 短横线

    /**
     * 构造函数，根据给定的字符串base初始化各种案例风格的字符串。
     *
     * @param base 需要转换的字符串
     */
    public CaseName(String base) {
        this.pascal = NamingCase.toPascalCase(base);
        this.underline = NamingCase.toUnderlineCase(base);
        this.camel = NamingCase.toCamelCase(base);
        this.kebab = NamingCase.toKebabCase(base);
    }
}

