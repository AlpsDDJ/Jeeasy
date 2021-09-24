import { Field, BaseVo, Data } from '@/common/dataDecorator'
import { dictCode } from '@/common/dict'

@Data('系统角色', '/api/sys/role', 'sys:role')
export default class SysRole extends BaseVo<SysRole> {
  @Field('ID') id?: string
  @Field('角色名称', true) roleName?: string
  @Field('角色标识', true) roleCode?: string
  @Field('启用标记', { dict: dictCode.enableFlag }) enableFlag?: number
  @Field('创建时间') createTime?: Date
  @Field('创建人') createBy?: string
  @Field('更新时间') updateTime?: Date
  @Field('更信人') updateBy?: string
  @Field('备注') remark?: string
  @Field('描述') description?: string
  @Field('排序', true) sortNo?: number
}

export const { labels, fields, baseApi, name, access, columnMap } = SysRole.prototype.$$
