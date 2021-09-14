package org.jeeasy.system.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jeeasy.common.core.enums.IDictEnum;

/**
 * 权限策略
 * 1:显示 0:禁用
 *
 * @author AlpsDDJ
 * @date 2020/11/23 16:27
 */
@Getter
@AllArgsConstructor
public enum PermsTypeEnum implements IDictEnum<Integer> {
    // 权限策略
    DISABLE(0, "禁用"),
    DISPLAY(1, "显示"),
    ;

    private final Integer value;
    private final String text;
}
