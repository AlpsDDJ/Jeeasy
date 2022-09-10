package org.jeeasy.system.modules.user.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeeasy.system.modules.user.domain.SysUser;

/**
 * UserInfoModel: 保存用户 数据模型
 *
 * @author AlpsDDJ
 * @version v1.0
 * @date 2020/11/21 22:11
 */
@Schema(description = "保存用户 数据模型")
@Data
public class UserInfoModel {

    @Schema(description = "用户信息")
    private SysUser user;

    @Schema(description = "用户角色")
    private String roles;

    @Schema(description = "用户部门")
    private String depts;
}
