package org.jeeasy.system.modules.user.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeeasy.system.modules.user.domain.SysUser;

/**
 * UserInfoModel: 保存用户 数据模型
 *
 * @author AlpsDDJ
 * @version v1.0
 * @date 2020/11/21 22:11
 */
@ApiModel("保存用户 数据模型")
@Data
public class UserInfoModel {

    @ApiModelProperty("用户信息")
    private SysUser user;

    @ApiModelProperty("用户角色")
    private String roles;

    @ApiModelProperty("用户部门")
    private String departs;
}
