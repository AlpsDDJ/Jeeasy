import { Field, BaseVo, Data } from '@/common/dataDecorator'
import { dictCode } from '@/common/dict'
import type SysRole from '@/pages/sys/role/vo'

@Data('系统用户', '/api/sys/user', 'sys:user')
export default class SysUser extends BaseVo<SysUser> {
  @Field('ID') id?: string
  @Field('用户名', true) username?: string
  @Field('姓名', true) realName?: string
  @Field('用户编号', { sorter: true, hideInForm: true }) userNo?: number
  @Field('手机号', true) phone?: string
  @Field('性别', { hideInSearch: true, dict: dictCode.sex }) sex?: Sex
  @Field('出生日期', { hideInSearch: true, valueType: 'date' }) birthday?: string
  @Field('密码') password?: string
  @Field('状态', { dict: dictCode.sysUserStatus }) status?: 1 | 0
  @Field('email', { hideInSearch: true }) email?: string
  @Field('头像') avatar?: string
  @Field('创建时间') createTime?: Date
  @Field('创建人') createBy?: string
  @Field('更新时间') updateTime?: Date
  @Field('更新人') updateBy?: string
  @Field('备注') remark?: string
  @Field('删除标记') delFlag?: DelFalg
  @Field('角色', { dict: dictCode.sysRole, fieldProps: { mode: 'multiple' } }) roles?: SysRole[]
  @Field('部门', { dict: dictCode.sysDept }) depts?: any[] | string[]
}

export const { labels, fields, baseApi, name, access, columnMap } = SysUser.prototype.$$
