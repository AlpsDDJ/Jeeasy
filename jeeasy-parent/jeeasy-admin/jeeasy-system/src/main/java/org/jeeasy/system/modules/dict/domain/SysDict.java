package org.jeeasy.system.modules.dict.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeeasy.common.db.annotation.Dict;
import org.jeeasy.common.core.enums.BooleanEnum;
import org.jeeasy.common.core.enums.EnableFlagEnum;

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
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id", notes = "ID")
    private String id;

    @ApiModelProperty("上级ID")
    private String parentId;

    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("字典值")
    private String dictCode;

    @ApiModelProperty("字典类型")
    private Integer dictType;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("排序")
    private Double sortOrder;

    @Dict(dictEnum = EnableFlagEnum.class)
    @ApiModelProperty("启用标记")
    private Integer enableFlag;

    @ApiModelProperty("允许删除")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer allowDelete;

    @ApiModelProperty("允许修改")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer allowUpdate;

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

    @TableLogic
    @ApiModelProperty("删除标记")
    @TableField(fill = FieldFill.UPDATE)
//    @Dict(dictEnum = DelFlagEnum.class)
    private Integer delFlag;

}
