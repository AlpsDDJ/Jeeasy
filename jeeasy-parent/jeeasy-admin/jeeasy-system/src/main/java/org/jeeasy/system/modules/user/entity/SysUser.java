package org.jeeasy.system.modules.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.Dict;
import org.jeeasy.common.core.enums.DelFlagEnum;
import org.jeeasy.system.enums.SysUserStatusEnum;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 系统用户
 * @author Alps
 */
@EqualsAndHashCode(callSuper = true)
@Data
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
    @Dict(dictType = Dict.DictType.ENUM, dictEnum = SysUserStatusEnum.class)
    private Integer status;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("备注")
    private String remark;

    @TableLogic
    @ApiModelProperty("删除标记")
    @Dict(dictType = Dict.DictType.ENUM, dictEnum = DelFlagEnum.class)
    private Integer delFlag;

}
