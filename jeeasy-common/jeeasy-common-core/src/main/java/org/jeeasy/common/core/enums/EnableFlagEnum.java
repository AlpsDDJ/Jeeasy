package org.jeeasy.common.core.enums;

import cn.hutool.core.util.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author AlpsDDJ
 * @date 2020/11/23 10:57
 */
@Getter
@AllArgsConstructor
public enum EnableFlagEnum implements IDictEnum<Integer> {
    // 启用
    YES(1, "启用"),
    // 未启用
    NO(0, "未启用"),
    ;

    private final Integer value;
    private final String text;

    public static void main(String[] args) {
        Enum enableFlagEnum = EnumUtil.likeValueOf(EnableFlagEnum.class, "1");
        IDictEnum dictEnum = (IDictEnum) enableFlagEnum;
        System.out.println(dictEnum.getText());
    }

}
