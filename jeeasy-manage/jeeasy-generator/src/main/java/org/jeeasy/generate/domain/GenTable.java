package org.jeeasy.generate.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tangzc.mpe.autotable.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.dict.Dict;
import org.jeeasy.common.core.enums.BooleanEnum;
import org.jeeasy.generate.emuns.RelationTypeEnum;
import org.jeeasy.generate.emuns.TableStyleEnum;
import org.jeeasy.generate.emuns.TableTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 代码生成 模块
 *
 * @author wei.yang
 * @date 2023-12-14
 */
@Data
@Table(value = "gen_table", comment = "表配置")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "代码生成表信息")
public class GenTable extends Model<GenTable> {

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private String id;

    /**
     * 表名
     */
    @Schema(description = "表名")
    private String name;

    /**
     * 表描述
     */
    @Schema(description = "表摘要")
    private String summary;

    /**
     * 表描述
     */
    @Schema(description = "表描述")
    private String description;

    /**
     * 数据源
     */
    @Schema(description = "数据源")
    private String dataSource;

    /**
     * 同步数据库状态
     */
    @Schema(description = "同步数据库状态")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer izSync = 0;

    /**
     * 是否简单查询
     */
    @Schema(description = "是否简单查询")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer izSimpleQuery = 1;

    /**
     * 是否分页
     */
    @Schema(description = "是否分页")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer izPage = 1;

    /**
     * 是否是树
     */
    @Schema(description = "是否是树")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer izTree = 0;

    /**
     * 树PID
     */
    @Schema(description = "树PID")
    private String treePid = "pid";

    /**
     * 树名称字段
     */
    @Schema(description = "树名称字段")
    private String treeNameField = "name";

    /**
     * 表单风格
     */
    @Schema(description = "表单风格")
    @Dict(dictEnum = TableStyleEnum.class)
    private Integer formType = 12;

//    /**
//     * 主键生成序列
//     */
//    @Schema(description = "主键生成序列")
//    private String idSequence;

//    /**
//     * 主键类型
//     */
//    @Schema(description = "主键类型")
//    @Dict(dictEnum = IdTypeEnum.class)
//    private Integer idType = 3;

    /**
     * 表类型
     * single单表、main主表、slave附表、catalog分类
     */
    @Schema(description = "表类型")
    @Dict(dictEnum = TableTypeEnum.class)
    private String tableType = "single";

    /**
     * 主表
     */
    @Schema(description = "主表")
    private String mainTable = "";

    /**
     * 从表
     */
    @Schema(description = "从表")
    private String slaveTable = "";

    /**
     * 主表字段
     */
    @Schema(description = "主表字段")
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private String mainColumn = "";

    /**
     * 主表字段Code
     */
    @Schema(description = "主表字段Code")
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private String mainColumnCode = "";

    /**
     * 从表字段
     */
    @Schema(description = "从表字段")
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private String slaveColumn = "";

    /**
     * 从表字段Code
     */
    @Schema(description = "从表字段Code")
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private String slaveColumnCode = "";

    /**
     * 映射关系
     */
    @Schema(description = "映射关系")
    @Dict(dictEnum = RelationTypeEnum.class)
    private String relationType = "oneToMany";

    /**
     * 子表
     */
    @Schema(description = "子表")
    private String subTableStr;

    /**
     * 附表排序序号
     */
    @Schema(description = "附表排序序号")
    private Integer tabOrderNum = 1;

//    /**
//     * 内容
//     */
//    @Schema(description = "内容")
//    private String content;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建日期
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
     * 更新日期
     */
    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "删除状态")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer delFlag;

    /**
     * 所属部门
     */
    @Schema(description = "所属部门")
    @TableField(fill = FieldFill.INSERT)
    private String sysOrgCode;

    @Schema(description = "字段列表")
    @TableField(exist = false)
    private List<GenTableField> tableFields;

    @Schema(description = "索引列表")
    @TableField(exist = false)
    private List<GenTableIndex> tableIndexs;
}
