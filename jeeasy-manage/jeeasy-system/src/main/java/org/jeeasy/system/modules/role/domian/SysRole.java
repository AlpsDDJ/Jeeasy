package org.jeeasy.system.modules.role.domian;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "系统角色")
@Accessors(chain = true)
public class SysRole {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private String id;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色标识")
    private String roleCode;

    @Schema(description = "启用标记")
    @Dict(dictEnum = EnableFlagEnum.class)
    private Integer enableFlag;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "更信人")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "排序")
    private Double sortNo;

    /**
     * 删除状态 0正常 1已删除
     */
    @Schema(description = "删除状态", example = "0:正常 1:已删除")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer delFlag;


}
