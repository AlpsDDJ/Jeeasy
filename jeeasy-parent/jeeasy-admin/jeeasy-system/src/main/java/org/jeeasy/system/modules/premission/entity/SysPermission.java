package org.jeeasy.system.modules.premission.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.Dict;
import org.jeeasy.common.core.enums.BooleanEnum;
import org.jeeasy.system.enums.LinkOpenTypeEnum;
import org.jeeasy.system.enums.MenuTypeEnum;
import org.jeeasy.system.enums.PermsTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单权限表(sys_permission)实体类
 *
 * @author AlpsDDJ
 * @description 菜单权限
 * @since 2020-11-21 13:52:05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_permission")
@ApiModel("系统权限")
public class SysPermission extends Model<SysPermission> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("ID")
    private String id;

    /**
     * 父id
     */
    @ApiModelProperty("父id")
    private String parentId;

    /**
     * 菜单标题
     */
    @ApiModelProperty("菜单标题")
    private String name;

    /**
     * 路径
     */
    @ApiModelProperty("路径")
    private String url;

    /**
     * 组件
     */
    @ApiModelProperty("组件")
    private String component;

    /**
     * 组件名字
     */
    @ApiModelProperty("组件名字")
    private String componentName;

    /**
     * 一级菜单跳转地址
     */
    @ApiModelProperty("一级菜单跳转地址")
    private String redirect;

    /**
     * 菜单类型(0:一级菜单 1:子菜单 2:按钮权限)
     */
    @ApiModelProperty(value = "菜单类型", notes = "0:一级菜单 1:子菜单 2:按钮权限")
    @Dict(dictEnum = MenuTypeEnum.class)
    private Integer menuType;

    /**
     * 菜单权限编码
     */
    @ApiModelProperty("菜单权限编码")
    private String perms;

    /**
     * 权限策略     1显示2禁用
     */
    @ApiModelProperty(value = "权限策略", notes = "1:显示 2:禁用")
    @Dict(dictEnum = PermsTypeEnum.class)
    private Integer permsType;

    /**
     * 菜单排序
     */
    @ApiModelProperty("菜单排序")
    private Double sortNo;

    /**
     * 聚合子路由: 1是0否
     */
    @ApiModelProperty(value = "聚合子路由", notes = "1:是 0:不是")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer alwaysShow;

    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    private String icon;

    /**
     * 是否路由菜单: 0:不是  1:是（默认值1）
     */
    @ApiModelProperty(value = "是否路由菜单", notes = "1:是 0:不是（默认值1）")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer isRoute;

    /**
     * 是否叶子节点:    1:是   0:不是
     */
    @ApiModelProperty(value = "是否叶子节点", notes = "1:是 0:不是")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer isLeaf;

    /**
     * 是否缓存该页面:    1:是   0:不是
     */
    @ApiModelProperty(value = "是否缓存该页面", notes = "1:是 0:不是")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer keepAlive;

    /**
     * 是否隐藏路由: 0否,1是
     */
    @ApiModelProperty(value = "是否隐藏路由", notes = "0:否 1:是")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer hidden;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE, update = "now()")
    private LocalDateTime updateTime;

    /**
     * 删除状态 0正常 1已删除
     */
    @ApiModelProperty(value = "删除状态", notes = "0:正常 1:已删除")
//    @Dict(dictEnum = DelFlagEnum.class)
    @TableLogic
    private Integer delFlag;

    /**
     * 是否添加数据权限1是0否
     */
    @ApiModelProperty(value = "是否添加数据权限", notes = "1:是 0:否")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer ruleFlag;

    /**
     * 按钮权限状态(0无效1有效)
     */
    @ApiModelProperty(value = "启用标记", notes = "0:未启用 1:启用")
//    @Dict(dictEnum = EnableFlagEnum.class)
    private Integer enableFlag;

    /**
     * 外链菜单打开方式 0/内部打开 1/外部打开
     */
    @ApiModelProperty(value = "外链菜单打开方式", notes = "0:内部打开 1:外部打开")
    @Dict(dictEnum = LinkOpenTypeEnum.class)
    private Integer internalOrExternal;

}