package org.jeeasy.biz.fastnote.modules.member.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 注册用户 数据类
 *
 * @author wei.yang
 * @date 2022-09-11 23:39
 */
@Data
@Accessors(chain = true)
@Schema(description = "注册用户")
public class MemberRegisterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "手机号")
    private String phone;
    @Schema(description = "密码")
    private String password;
    @Schema(description = "验证码")
    private String code;

}
