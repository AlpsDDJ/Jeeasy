package org.jeeasy.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author AlpsDDJ
 * @date 2020/11/23 11:04
 */
@Getter
@AllArgsConstructor
public enum DelFlagEnum implements IDictEnum<Integer> {

    // 已删除
    YES(1, "已删除"),
    // 未删除
    NO(0, "未删除"),
    ;

    private final Integer value;
    private final String text;
}
