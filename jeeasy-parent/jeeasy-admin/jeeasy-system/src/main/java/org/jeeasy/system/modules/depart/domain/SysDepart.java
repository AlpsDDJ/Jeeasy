package org.jeeasy.system.modules.depart.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.common.db.annotation.Dict;
import org.jeeasy.system.enums.depart.OrgCategoryEnum;
import org.jeeasy.system.enums.depart.OrgTypeEnum;

import java.time.LocalDateTime;

/**
 * 组织机构
 * @author AlpsDDJ
 * @date 2020/11/24 11:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_depart")
@ApiModel(value = "组织机构")
public class SysDepart extends Model<SysDepart> {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "父机构ID")
    private String parentId;

    @ApiModelProperty(value = "机构/部门名称")
    private String departName;

    @ApiModelProperty(value = "英文名")
    private String departNameEn;

    @ApiModelProperty(value = "缩写")
    private String departNameAbbr;

    @ApiModelProperty(value = "排序")
    private Double departOrder;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "机构类别", notes = "1: 组织机构 2: 岗位")
    @Dict(dictEnum = OrgCategoryEnum.class)
    private Integer orgCategory;

    @ApiModelProperty(value = "机构类型", notes = "1: 一级部门 2: 子部门")
    @Dict(dictEnum = OrgTypeEnum.class)
    private Integer orgType;

    @ApiModelProperty(value = "机构编码")
    private String orgCode;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "启用标记", notes = "1: 启用 0: 不启用")
    @TableField(fill = FieldFill.INSERT)
    private Integer enableFlag;

    @ApiModelProperty(value = "删除状态", notes = "0: 正常 1: 已删除")
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;

    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "更新日期")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
