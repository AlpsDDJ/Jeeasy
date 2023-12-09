package org.jeeasy.generate.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jeeasy.common.core.enums.IDictEnum;

/**
 * 表类型
 *
 * @author AlpsDDJ
 * @date 2021/1/14 13:30
 */
@Getter
@AllArgsConstructor
public enum TableStyleEnum implements IDictEnum<Integer> {
    AUTO(0, "自动"),
    ONE(1, "一列"),
    two(2, "两列"),
    three(3, "三列"),
    four(4, "四列"),
    five(5, "五列"),
    six(6, "六列"),
    ;

    private final Integer value;
    private final String text;
}
