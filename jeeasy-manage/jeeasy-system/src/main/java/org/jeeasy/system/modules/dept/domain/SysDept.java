package org.jeeasy.system.modules.dept.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.Dict;
import org.jeeasy.common.core.domain.vo.BaseTree;
import org.jeeasy.system.enums.dept.OrgCategoryEnum;
import org.jeeasy.system.enums.dept.OrgTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 组织机构
 * @author AlpsDDJ
 * @date 2020/11/24 11:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dept")
@Schema(description = "组织机构")
public class SysDept extends Model<SysDept> implements BaseTree<SysDept> {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private String id;

    @Schema(description = "父机构ID")
    private String parentId;

    @Schema(description = "机构/部门名称")
    private String deptName;

    @Schema(description = "英文名")
    private String deptNameEn;

    @Schema(description = "缩写")
    private String deptNameAbbr;

    @Schema(description = "排序")
    private Double sortNo;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "机构类别", example = "1: 组织机构 2: 岗位")
    @Dict(dictEnum = OrgCategoryEnum.class)
    private Integer orgCategory;

    @Schema(description = "机构类型", example = "1: 一级部门 2: 子部门")
    @Dict(dictEnum = OrgTypeEnum.class)
    private Integer orgType;

    @Schema(description = "机构编码")
    private String orgCode;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "传真")
    private String fax;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "启用标记", example = "1: 启用 0: 不启用")
    @TableField(fill = FieldFill.INSERT)
    private Integer enableFlag;

    @Schema(description = "删除状态", example = "0: 正常 1: 已删除")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer delFlag;

    @Schema(description = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @Schema(description = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @Schema(description = "更新日期")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @JsonGetter
    public boolean hasChildren() {
        Integer orgType = this.getOrgType();
        return OrgTypeEnum.TOP.getValue().equals(orgType);
    }

    @TableField(exist = false)
    private List<SysDept> children;

}
