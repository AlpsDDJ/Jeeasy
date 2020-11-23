package org.jeeasy.system.modules.role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.Dict;
import org.jeeasy.common.core.enums.EnableFlagEnum;

import java.time.LocalDateTime;

/**
 * @author AlpsDDJ
 * @date 2020/11/10
 */
@Data
@ApiModel("系统角色")
@Accessors(chain = true)
public class SysRole {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色标识")
    private String roleCode;

    @ApiModelProperty("启用标记")
    @Dict(dictType = Dict.DictType.ENUM, dictEnum = EnableFlagEnum.class)
    private Integer enableFlag;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("更信任")
    private String updateBy;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("排序")
    private Double sortNo;


}
