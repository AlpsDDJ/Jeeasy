package org.jeeasy.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别选项
 *
 * @author AlpsDDJ
 * @date 2020/11/23 11:04
 */
@Getter
@AllArgsConstructor
public enum SexEnum implements DictEnum<Integer> {

    // 性别选项
    MAN(1, "男"),
    WOMAN(0, "女"),
    ;

    private final Integer value;
    private final String text;
}
