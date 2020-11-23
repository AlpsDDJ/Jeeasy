package org.jeeasy.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jeeasy.common.core.enums.DictEnum;

/**
 * 菜单外链发开方式
 * 0:内部打开 1:外部打开
 *
 * @author AlpsDDJ
 * @date 2020/11/23 16:27
 */
@Getter
@AllArgsConstructor
public enum LinkOpenTypeEnum implements DictEnum<Integer> {
    // 菜单外链发开方式
    INTERNAL(0, "内部打开"),
    EXTERNAL(1, "外部打开"),
    ;

    private final Integer value;
    private final String text;
}
