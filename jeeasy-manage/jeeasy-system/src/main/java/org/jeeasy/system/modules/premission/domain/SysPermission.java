package org.jeeasy.system.modules.premission.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.Dict;
import org.jeeasy.common.core.domain.vo.BaseTree;
import org.jeeasy.common.core.enums.BooleanEnum;
import org.jeeasy.system.enums.permission.LinkOpenTypeEnum;
import org.jeeasy.system.enums.permission.MenuTypeEnum;
import org.jeeasy.system.enums.permission.PermsTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单权限表(sys_permission)实体类
 *
 * @author AlpsDDJ
 * @description 菜单权限
 * @since 2020-11-21 13:52:05
 */
@Data
@NoArgsConstructor
@Schema(description = "系统权限")
@Accessors(chain = true)
@TableName("sys_permission")
@EqualsAndHashCode(callSuper = true)
public class SysPermission extends Model<SysPermission> implements BaseTree<SysPermission>, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private String id;

    /**
     * 父id
     */
    @Schema(description = "父id")
    private String parentId;

    /**
     * 菜单标题
     */
    @Schema(description = "菜单标题")
    private String name;

    /**
     * 路径
     */
    @Schema(description = "路径")
    private String path;

    /**
     * 组件
     */
    @Schema(description = "组件")
    private String component;

    /**
     * 组件名字
     */
    @Schema(description = "组件名字")
    private String componentName;

    /**
     * 一级菜单跳转地址
     */
    @Schema(description = "一级菜单跳转地址")
    private String redirect;

    /**
     * 菜单类型(0:一级菜单 1:子菜单 2:按钮权限)
     */
    @Schema(description = "菜单类型", example = "0:一级菜单 1:子菜单 2:按钮权限")
    @Dict(dictEnum = MenuTypeEnum.class)
    private Integer menuType;

    /**
     * 菜单权限编码
     */
    @Schema(description = "菜单权限编码")
    private String perms;

    /**
     * 权限策略     1显示2禁用
     */
    @Schema(description = "权限策略", example = "1:显示 2:禁用")
    @Dict(dictEnum = PermsTypeEnum.class)
    private Integer permsType;

    /**
     * 菜单排序
     */
    @Schema(description = "菜单排序")
    private Double sortNo;

    /**
     * 聚合子路由: 1是0否
     */
    @Schema(description = "聚合子路由", example = "1:是 0:不是")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer alwaysShow;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 是否路由菜单: 0:不是  1:是（默认值1）
     */
    @Schema(description = "是否路由菜单", example = "1:是 0:不是（默认值1）")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer isRoute;

    /**
     * 是否叶子节点:    1:是   0:不是
     */
    @Schema(description = "是否叶子节点", example = "1:是 0:不是")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer isLeaf;

    /**
     * 是否缓存该页面:    1:是   0:不是
     */
    @Schema(description = "是否缓存该页面", example = "1:是 0:不是")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer keepAlive;

    /**
     * 是否隐藏路由: 0否,1是
     */
    @Schema(description = "是否隐藏路由", example = "0:否 1:是")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer hidden;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

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
    @TableField(fill = FieldFill.UPDATE, update = "now()")
    private LocalDateTime updateTime;

    /**
     * 删除状态 0正常 1已删除
     */
    @Schema(description = "删除状态", example = "0:正常 1:已删除")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer delFlag;

    /**
     * 是否添加数据权限1是0否
     */
    @Schema(description = "是否添加数据权限", example = "1:是 0:否")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer ruleFlag;

    /**
     * 按钮权限状态(0无效1有效)
     */
    @Schema(description = "启用标记", example = "0:未启用 1:启用")
    private Integer enableFlag;

    /**
     * 外链菜单打开方式 0/内部打开 1/外部打开
     */
    @Schema(description = "外链菜单打开方式", example = "0:内部打开 1:外部打开")
    @Dict(dictEnum = LinkOpenTypeEnum.class)
    private Integer internalOrExternal;

//    @JsonGetter
//    public boolean hasChildren() {
//        return !BooleanEnum.yes(this.isLeaf);
//    }

    @TableField(exist = false)
    private List<SysPermission> children;

}