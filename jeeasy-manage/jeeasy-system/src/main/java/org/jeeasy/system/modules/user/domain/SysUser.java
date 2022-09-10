package org.jeeasy.system.modules.user.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.Dict;
import org.jeeasy.system.enums.user.SysUserStatusEnum;
import org.jeeasy.system.modules.dept.domain.SysDept;
import org.jeeasy.system.modules.role.domian.SysRole;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户
 * @author Alps
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
@Schema(description = "系统用户")
@Accessors(chain = true)
public class SysUser extends Model<SysUser> implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private String id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户编号")
    private Long userNo;

    @Schema(description = "电话号码")
    private String phone;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "性别", example = "1：男，0：女")
//    @Dict(dictEnum = SexEnum.class)
    private Integer sex;

    @Schema(description = "生日")
    private LocalDate birthday;

    @JsonIgnore
    @Schema(description = "密码")
    private String password;

    @JsonIgnore
    @Schema(description = "--")
    private String salt;

    @Schema(description = "状态")
    @Dict(dictEnum = SysUserStatusEnum.class)
    @TableField(fill = FieldFill.INSERT)
    private Integer status;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @Schema(description = "修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "修改人")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @Schema(description = "备注")
    private String remark;

    @TableLogic
    @Schema(description = "删除标记")
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;


    @TableField(exist = false)
    private List<SysRole> roles;

    @TableField(exist = false)
    private List<SysDept> depts;

}
