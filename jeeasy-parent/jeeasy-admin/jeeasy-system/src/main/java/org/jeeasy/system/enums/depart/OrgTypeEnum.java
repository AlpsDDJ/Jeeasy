package org.jeeasy.system.enums.depart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jeeasy.common.core.enums.DictEnum;

/**
 * 机构类型
 * 1: 一级部门 2: 子部门
 *
 * @author AlpsDDJ
 * @date 2020/11/23 16:27
 */
@Getter
@AllArgsConstructor
public enum OrgTypeEnum implements DictEnum<Integer> {
    // 机构类型
    TOP(1, "一级部门"),
    SUB(2, "子部门"),
    ;

    private final Integer value;
    private final String text;
}