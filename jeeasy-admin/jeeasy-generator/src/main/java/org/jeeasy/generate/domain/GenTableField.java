package org.jeeasy.generate.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.Dict;
import org.jeeasy.common.core.enums.BooleanEnum;

/**
 * 表字段
 *
 * @author mobie
 * @TableName gen_table_field
 * @date 2021/11/14
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@TableName(value = "gen_table_field")
@ApiModel(value = "代码生成表字段信息", description = "代码生成表字段信息")
public class GenTableField implements Serializable {
    /**
     * ID
     */
    @TableId
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 表ID
     */
    @ApiModelProperty(value = "表ID")
    private String tableId;

    /**
     * 列名
     */
    @ApiModelProperty(value = "列名")
    private String columnName;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer primaryKey;

    /**
     * 字段名
     */
    @ApiModelProperty(value = "字段名")
    private String fieldName;

    /**
     * 字段描述
     */
    @ApiModelProperty(value = "字段描述")
    private String description;

    /**
     * 数据类型
     */
    @ApiModelProperty(value = "数据类型")
    private String jdbcType;

    /**
     * java类型
     */
    @ApiModelProperty(value = "java类型")
    private String javaType;

    /**
     * 字段长度
     */
    @ApiModelProperty(value = "字段长度")
    private Integer length;

    /**
     * 小数位
     */
    @ApiModelProperty(value = "小数位")
    private Integer decimalPlaces;

    /**
     * 允许空值
     */
    @ApiModelProperty(value = "允许空值")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer allowNull;

    /**
     * 默认值
     */
    @ApiModelProperty(value = "默认值")
    private String defaultValue;

    /**
     * 字典
     */
    @ApiModelProperty(value = "字典")
    private String dictCode;

    /**
     * 表单类型
     */
    @ApiModelProperty(value = "表单类型")
    private String formType;

    /**
     * 页面配置（json）
     */
    @ApiModelProperty(value = "页面配置（json）")
    private String viewOptionsJson;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    /**
     * 更新日期
     */
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}