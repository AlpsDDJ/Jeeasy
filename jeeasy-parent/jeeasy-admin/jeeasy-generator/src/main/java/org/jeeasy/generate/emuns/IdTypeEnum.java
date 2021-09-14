package org.jeeasy.generate.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jeeasy.common.core.enums.IDictEnum;

/**
 * @author AlpsDDJ
 * @date 2021/1/14 13:30
 */
@Getter
@AllArgsConstructor
public enum IdTypeEnum implements IDictEnum<Integer> {
    AUTO(0, "自动"),
    NONE(1, ""),
    INPUT(2, ""),
    ASSIGN_ID(3, ""),
    ASSIGN_UUID(4, ""),
    ;
    private final Integer value;
    private final String text;
}
