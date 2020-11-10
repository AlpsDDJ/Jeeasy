package org.jeeasy.system.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AlpsDDJ
 * @date 2020/11/10
 */
@Getter
@Setter
@ApiModel(value = "通过旧密码验证修改新密码Model", description = "通过旧密码验证修改新密码Model")
public class ChangePasswordByOldPasswordModel {

    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("旧密码")
    private String oldPassword;

    @ApiModelProperty("新密码")
    private String newPassword;

}
