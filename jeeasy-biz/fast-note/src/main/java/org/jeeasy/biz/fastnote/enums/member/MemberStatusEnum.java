package org.jeeasy.biz.fastnote.enums.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jeeasy.common.core.enums.IDictEnum;

/**
 * 用户状态
 *
 * @author wei.yang
 * @date 2022-09-11 18:37
 */
@Getter
@AllArgsConstructor
public enum MemberStatusEnum implements IDictEnum<Integer> {
    NORMAL(1, "正常"),
    FREEZE(0, "冻结"),
    ;



    private final Integer value;
    private final String text;

}
