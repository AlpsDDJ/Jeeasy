package org.jeeasy.biz.fastnote.modules.member.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeeasy.biz.fastnote.enums.member.MemberStatusEnum;
import org.jeeasy.common.core.annotation.dict.Dict;
import org.jeeasy.common.core.handler.userpwd.IUserPassword;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * FastNote用户
 *
 * @author wei.yang
 * @date 2022-09-11 18:44
 */
@Data
@TableName("fn_member")
@Accessors(chain = true)
@Schema(description = "FastNote用户")
public class FnMember extends Model<FnMember> implements IUserPassword, Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID", example = "ID")
    private String id;

    @JsonIgnore
    @Schema(description = "密码")
    private String password;

    @JsonIgnore
    @Schema(description = "密码盐")
    private String salt;

    @Schema(description = "注册时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "微信openid")
    private String wxOpenid;

    /**
     * @see MemberStatusEnum
     */
    @Schema(description = "状态")
    @Dict(dictEnum = MemberStatusEnum.class)
    private Integer status = MemberStatusEnum.NORMAL.getValue();

    @TableLogic
    @Schema(description = "删除标记")
    @TableField(fill = FieldFill.UPDATE)
    private String delFlag;


    @Override
    public IUserPassword setUsername(String username) {
        this.phone = username;
        return this;
    }

    @Override
    public String getUsername() {
        return this.phone;
    }
}
