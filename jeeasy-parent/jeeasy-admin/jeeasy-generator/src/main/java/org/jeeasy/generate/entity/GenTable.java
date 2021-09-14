package org.jeeasy.generate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.Dict;
import org.jeeasy.common.core.enums.BooleanEnum;
import org.jeeasy.generate.emuns.IdTypeEnum;
import org.jeeasy.generate.emuns.RelationTypeEnum;
import org.jeeasy.generate.emuns.TableTypeEnum;

import java.time.LocalDateTime;

/**
 * 代码生成
 *
 * @author AlpsDDJ
 * @description 菜单权限
 * @since 2021-01-14 13:52:05
 */
@Data
@TableName("gen_table")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "gen_table对象", description = "代码生成表信息")
public class GenTable {

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 表名
     */
    @ApiModelProperty(value = "表名")
    private String name;

    /**
     * 表描述
     */
    @ApiModelProperty(value = "表描述")
    private String description;

    /**
     * 数据源
     */
    @ApiModelProperty(value = "数据源")
    private String dataSource;

    /**
     * 同步数据库状态
     */
    @ApiModelProperty(value = "同步数据库状态")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer izSync = 1;

    /**
     * 是否简单查询
     */
    @ApiModelProperty(value = "是否简单查询")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer izSimpleQuery = 1;

    /**
     * 是否分页
     */
    @ApiModelProperty(value = "是否分页")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer izPage = 1;

    /**
     * 是否是树
     */
    @ApiModelProperty(value = "是否是树")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer izTree = 0;

    /**
     * 树PID
     */
    @ApiModelProperty(value = "树PID")
    private String treePid = "pid";

    /**
     * 树名称字段
     */
    @ApiModelProperty(value = "树名称字段")
    private String treeNameField = "name";

    /**
     * 表单风格
     * @description 12 一列、6 二列、4 三列、3 四列
     */
    @ApiModelProperty(value = "表单风格", notes = "12 一列、6 二列、4 三列、3 四列")
    private Integer formType = 12;

    /**
     * 主键生成序列
     */
    @ApiModelProperty(value = "主键生成序列")
    private String idSequence;

    /**
     * 主键类型
     */
    @ApiModelProperty(value = "主键类型")
    @Dict(dictEnum = IdTypeEnum.class)
    private Integer idType = 3;

    @ApiModelProperty(value = "表类型", notes = "single单表、main主表、slave附表、catalog分类")
    @Dict(dictEnum = TableTypeEnum.class)
    private String tableType = "single";

    /**
     * 主表
     */
    @ApiModelProperty(value = "主表")
    private String mainTable = "";

    /**
     * 从表
     */
    @ApiModelProperty(value = "从表")
    private String slaveTable = "";

    /**
     * 主表字段
     */
    @ApiModelProperty(value = "主表字段")
    @TableField(exist = false)
    private String mainColumn = "";

    /**
     * 主表字段Code
     */
    @ApiModelProperty(value = "主表字段Code")
    @TableField(exist = false)
    private String mainColumnCode = "";

    /**
     * 从表字段
     */
    @ApiModelProperty(value = "从表字段")
    @TableField(exist = false)
    private String slaveColumn = "";

    /**
     * 从表字段Code
     */
    @ApiModelProperty(value = "从表字段Code")
    @TableField(exist = false)
    private String slaveColumnCode = "";

    /**
     * 显示样式
     */
    @ApiModelProperty(value = "显示样式")
    private String formStyle = "default";

    /**
     * 映射关系
     * @description oneToMany一对多  oneToOne一对一
     */
    @ApiModelProperty(value = "映射关系", notes = "oneToMany一对多  oneToOne一对一")
    @Dict(dictEnum = RelationTypeEnum.class)
    private String relationType = "oneToMany";

    /**
     * 子表
     */
    @ApiModelProperty(value = "子表")
    private String subTableStr;

    /**
     * 附表排序序号
     */
    @ApiModelProperty(value = "附表排序序号")
    private Integer tabOrderNum = 1;

//    /**
//     * 内容
//     */
//    @ApiModelProperty(value = "内容")
//    private String content;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
}
