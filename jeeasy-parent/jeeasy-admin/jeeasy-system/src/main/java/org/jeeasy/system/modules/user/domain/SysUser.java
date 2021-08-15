package org.jeeasy.system.modules.user.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.common.db.annotation.Dict;
import org.jeeasy.system.enums.user.SysUserStatusEnum;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 系统用户
 * @author Alps
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
@ApiModel("系统用户")
@Accessors(chain = true)
public class SysUser extends Model<SysUser> implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户名编号")
    private Long userNo;

    @ApiModelProperty("电话号码")
    private String phone;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty(value = "性别", notes = "1：男，0：女")
//    @Dict(dictEnum = SexEnum.class)
    private Integer sex;

    @ApiModelProperty("生日")
    private LocalDate birthday;

    @JsonIgnore
    @ApiModelProperty("密码")
    private String password;

    @JsonIgnore
    @ApiModelProperty("--")
    private String salt;

    @ApiModelProperty("状态")
    @Dict(dictEnum = SysUserStatusEnum.class)
    @TableField(fill = FieldFill.INSERT)
    private Integer status;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("修改人")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @ApiModelProperty("备注")
    private String remark;

    @TableLogic
    @ApiModelProperty("删除标记")
    @TableField(fill = FieldFill.UPDATE)
//    @Dict(dictEnum = DelFlagEnum.class)
    private Integer delFlag;


//    @TableField(exist = false)
//    @Dict("@sys_user")
//    private Collection<SysRole> roles;
//    @TableField(exist = false)
//    @Dict("@sys_depart")
//    private Collection<SysDepart> departs;



//    public static void main(String[] args) {
//        SysUser user = new SysUser();
//        user.setId("111");
//
//        Field username = ReflectUtil.getField(user.getClass(), "username1");
//        ReflectUtil.hasField()
//        System.out.println(username);
//
//    }

}
