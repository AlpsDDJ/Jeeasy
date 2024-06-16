package org.jeeasy.generate.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tangzc.mpe.autotable.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.dict.Dict;
import org.jeeasy.common.core.enums.BooleanEnum;
import org.jeeasy.generate.emuns.JavaTypeEnum;
import org.jeeasy.generate.emuns.JdbcTypeEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * 表字段
 *
 * @author mobie
 * @date 2021/11/14
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@Table(value = "gen_table_field", comment = "表字段配置")
@Schema(description = "代码生成表字段信息")
public class GenTableField implements Serializable {
    private static final long serialVersionUID = 1L;

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
    @Dict(dictEnum = JdbcTypeEnum.class)
    @TableField(value = "jdbc_type")
    private String jdbcType;

    /**
     * java类型
     */
    @Schema(description = "java类型")
    @Dict(dictEnum = JavaTypeEnum.class)
    @TableField(value = "java_type")
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

    /* 页面配置 */

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
     * 新增显示
     */
    @Schema(description = "新增显示")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer showAdd;

    /**
     * 修改显示
     */
    @Schema(description = "修改显示")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer showEdit;

    /**
     * 列表展示
     */
    @Schema(description = "列表展示")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer showTable;

    /**
     * 搜索展示
     */
    @Schema(description = "搜索展示")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer showSearch;

    /**
     * 修改时禁用
     */
    @Schema(description = "修改时禁用")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer disableOnEdit;

    /**
     * 新增时禁用
     */
    @Schema(description = "新增时禁用")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer disableOnAdd;

    /**
     * 行内编辑时禁用
     */
    @Schema(description = "行内编辑时禁用")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer disableOnTableEdit;

    /* 校验路径 */
    @Schema(description = "校验路径")
    private String fieldPath;

    /**
     * 必填
     */
    @Schema(description = "必填")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer required;

    /**
     * 校验规则
     */
    @Schema(description = "校验规则")
    private String rule;

    /* 外键关联 */

    /**
     * 主表
     */
    @Schema(description = "主表")
    private String mainTable;

    /**
     * 主表字段
     */
    @Schema(description = "主表字段")
    private String mainTableField;


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
}