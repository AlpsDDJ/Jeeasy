package org.jeeasy.system.modules.login.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author AlpsDDJ
 * @date 2020/11/11
 */
@ApiModel("登录用户")
@Data
public class LoginUserModel {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码")
    private String captcha;

    @ApiModelProperty("验证码key")
    private String captchaKey;

}
