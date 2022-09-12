package org.jeeasy.system.modules.dict.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.dict.Dict;
import org.jeeasy.common.core.enums.BooleanEnum;
import org.jeeasy.common.core.enums.DelFlagEnum;
import org.jeeasy.common.core.enums.EnableFlagEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author mobie
 * @TableName sys_table_dict
 */
@Data
@Accessors(chain = true)
@Schema(description = "表类型字典")
@TableName(value = "sys_table_dict")
public class SysTableDict implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private String id;

    /**
     * 字典编码
     */
    @Schema(description = "字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;

    /**
     * 查询表名
     */
    @Schema(description = "查询表名")
    private String tableName;

    /**
     * 数据源名称
     */
    @Schema(description = "数据源名称")
    private String databaseName;

    /**
     * 值字段
     */
    @Schema(description = "值字段")
    private String valueColumn;

    /**
     * 名称字段
     */
    @Schema(description = "名称字段")
    private String nameColumn;

    /**
     * 是否为树表
     */
    @Schema(description = "是否为树表")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer isTree;

    /**
     * 父级字段名称
     */
    @Schema(description = "父级字段名称")
    private String parentId;

    /**
     * 是否为叶子节点
     */
    @Schema(description = "叶子节点字段名称")
    private String leafColumn;

    /**
     * 父级字段名称
     */
    @Schema(hidden = true)
    @JsonIgnore
    private String parentValue;

    /**
     * 启用标记 0: 未启用 1： 启用
     */
    @Schema(description = "启用标记")
    @Dict(dictEnum = EnableFlagEnum.class)
    private Integer enableFlag;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除标记 0: 未删除 1: 已删除
     */
    @Schema(description = "删除标记")
    @Dict(dictEnum = DelFlagEnum.class)
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}