package org.jeeasy.system.enums.depart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jeeasy.common.core.enums.IDictEnum;

/**
 * 机构类别
 * 1: 组织机构 2: 岗位
 *
 * @author AlpsDDJ
 * @date 2020/11/23 16:27
 */
@Getter
@AllArgsConstructor
public enum OrgCategoryEnum implements IDictEnum<Integer> {
    // 机构类别
    ORG(1, "组织机构"),
    POST(2, "岗位"),
    ;

    private final Integer value;
    private final String text;
}
