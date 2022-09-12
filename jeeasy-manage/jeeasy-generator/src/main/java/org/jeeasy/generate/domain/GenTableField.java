package org.jeeasy.generate.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.dict.Dict;
import org.jeeasy.common.core.enums.BooleanEnum;

import java.io.Serializable;
import java.util.Date;

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
@Schema(description = "代码生成表字段信息")
public class GenTableField implements Serializable {
    /**
     * ID
     */
    @TableId
    @Schema(description = "ID")
    private String id;

    /**
     * 表ID
     */
    @Schema(description = "表ID")
    private String tableId;

    /**
     * 列名
     */
    @Schema(description = "列名")
    private String columnName;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer primaryKey;

    /**
     * 字段名
     */
    @Schema(description = "字段名")
    private String fieldName;

    /**
     * 字段描述
     */
    @Schema(description = "字段描述")
    private String description;

    /**
     * 数据类型
     */
    @Schema(description = "数据类型")
    private String jdbcType;

    /**
     * java类型
     */
    @Schema(description = "java类型")
    private String javaType;

    /**
     * 字段长度
     */
    @Schema(description = "字段长度")
    private Integer length;

    /**
     * 小数位
     */
    @Schema(description = "小数位")
    private Integer decimalPlaces;

    /**
     * 允许空值
     */
    @Schema(description = "允许空值")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer allowNull;

    /**
     * 默认值
     */
    @Schema(description = "默认值")
    private String defaultValue;

    /**
     * 字典
     */
    @Schema(description = "字典")
    private String dictCode;

    /**
     * 表单类型
     */
    @Schema(description = "表单类型")
    private String formType;

    /**
     * 页面配置（json）
     */
    @Schema(description = "页面配置（json）")
    private String viewOptionsJson;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人")
    private String createBy;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建日期")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    @Schema(description = "更新人")
    private String updateBy;

    /**
     * 更新日期
     */
    @TableField(fill = FieldFill.UPDATE)
    @Schema(description = "更新日期")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}