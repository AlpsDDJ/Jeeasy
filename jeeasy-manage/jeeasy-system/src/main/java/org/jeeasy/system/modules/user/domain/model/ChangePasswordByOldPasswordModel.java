package org.jeeasy.system.modules.user.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AlpsDDJ
 * @date 2020/11/10
 */
@Getter
@Setter
@Schema(description = "通过旧密码验证修改新密码Model")
public class ChangePasswordByOldPasswordModel {

    @Schema(description = "用户id")
    private String id;

    @Schema(description = "旧密码")
    private String oldPassword;

    @Schema(description = "新密码")
    private String newPassword;

}
