package org.jeeasy.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jeeasy.common.core.enums.DictEnum;

/**
 * 菜单类型
 * 1:一级菜单 2:子菜单 3:按钮权限
 *
 * @author AlpsDDJ
 * @date 2020/11/23 16:27
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum implements DictEnum<Integer> {
    // 菜单类型
    MENU_TYPE_1(1, "一级菜单"),
    MENU_TYPE_2(2, "子菜单"),
    MENU_TYPE_3(3, "按钮权限"),
    ;

    private final Integer value;
    private final String text;
}
