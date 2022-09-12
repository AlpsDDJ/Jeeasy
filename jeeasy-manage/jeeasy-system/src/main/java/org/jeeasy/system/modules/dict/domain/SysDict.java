package org.jeeasy.system.modules.dict.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.dict.Dict;
import org.jeeasy.common.core.enums.BooleanEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据字典项
 *
 * @author AlpsDDJ
 * @date 2020/11/23 10:50
 */
@Data
@TableName("sys_dict")
@Accessors(chain = true)
@Schema(description = "数据字典项")
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID", example = "ID")
    private String id;

    @Schema(description = "上级ID")
    private String parentId;

    @Schema(description = "字典名称")
    private String dictName;

    @Schema(description = "字典值")
    private String dictCode;

    @Schema(description = "字典类型")
    private Integer dictType;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "排序")
    private Double sortOrder;

//    @Dict(dictEnum = EnableFlagEnum.class)
    @Schema(description = "启用标记")
    private Integer enableFlag;

    @Schema(description = "允许删除")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer allowDelete;

    @Schema(description = "允许修改")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer allowUpdate;

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

    @TableLogic
    @Schema(description = "删除标记")
    @TableField(fill = FieldFill.UPDATE)
//    @Dict(dictEnum = DelFlagEnum.class)
    private Integer delFlag;

}
