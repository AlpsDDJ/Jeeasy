package org.jeeasy.system.modules.dict.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author mobie
 * @TableName sys_table_dict
 */
@Data
@Accessors(chain = true)
@ApiModel("表类型字典")
@TableName(value = "sys_table_dict")
public class SysTableDict implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id", notes = "ID")
    private String id;

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "dictCode", notes = "字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "dictName", notes = "字典名称")
    private String dictName;

    /**
     * 查询表名
     */
    @ApiModelProperty(value = "tableName", notes = "查询表名")
    private String tableName;

    /**
     * 数据源名称
     */
    @ApiModelProperty(value = "databaseName", notes = "数据源名称")
    private String databaseName;

    /**
     * 值字段
     */
    @ApiModelProperty(value = "valueColumn", notes = "值字段")
    private String valueColumn;

    /**
     * 名称字段
     */
    @ApiModelProperty(value = "nameColumn", notes = "名称字段")
    private String nameColumn;

    /**
     * 启用标记 0: 未启用 1： 启用
     */
    @ApiModelProperty(value = "enableFlag", notes = "启用标记")
    private Boolean enableFlag;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "createBy", notes = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "createTime", notes = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "updateBy", notes = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "updateTime", notes = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 删除标记 0: 未删除 1: 已删除
     */
    @ApiModelProperty(value = "delFlag", notes = "删除标记")
    private Boolean delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}