package org.jeeasy.system.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jeeasy.common.core.enums.DictEnum;

/**
 * SysUserStatusEnum: 系统用户状态
 *
 * @author AlpsDDJ
 * @version v1.0
 * @date 2020/11/22 2:10
 */
@Getter
@AllArgsConstructor
public enum SysUserStatusEnum implements DictEnum<Integer> {

    // 正常状态
    NORMAL(1, "正常"),
    // 冻结状态
    FREEZE(0, "冻结"),
    ;

    private final Integer value;
    private final String text;

}
