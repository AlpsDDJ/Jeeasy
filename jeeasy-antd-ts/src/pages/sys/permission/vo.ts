import { Field, BaseVo, Data } from '@/common/dataDecorator'
import { dictCode } from '@/common/dict'

const { menuType, permsType, bool, linkOpenType, enableFlag } = dictCode

@Data('权限菜单', '/api/sys/permission', 'sys:permission')
export default class SysPremission extends BaseVo<SysPremission> {
  @Field('ID') id?: string
  @Field('父id') parentId?: string
  @Field('菜单标题', true) name?: string
  @Field('路径', true) path?: string
  @Field('组件', true) component?: string
  @Field('组件名字') componentName?: string
  @Field('一级菜单跳转地址') redirect?: string
  @Field('菜单类型', { dict: menuType }) menuType?: number
  @Field('菜单权限编码') perms?: string
  @Field('权限策略', { dict: permsType }) permsType?: number
  @Field('菜单排序') sortNo?: number
  @Field('聚合子路由', { dict: bool }) alwaysShow?: number
  @Field('菜单图标') icon?: string
  @Field('路由菜单', { dict: bool }) isRoute?: number
  @Field('叶子节点', { dict: bool }) isLeaf?: number
  @Field('缓存该页面', { dict: bool }) keepAlive?: number
  @Field('隐藏路由', { dict: bool }) hidden?: number
  @Field('描述') description?: string
  @Field('创建人') createBy?: string
  @Field('创建时间') createTime?: Date
  @Field('更新人') updateBy?: string
  @Field('更新时间') updateTime?: Date
  // @Field('删除状态') delFlag?: number
  @Field('添加数据权限') ruleFlag?: number
  @Field('启用标记', { dict: enableFlag }) enableFlag?: number
  @Field('外链菜单打开方式', { dict: linkOpenType }) internalOrExternal?: number
}

export const { labels, fields, baseApi, name, access, columnMap } = SysPremission.prototype.$$
