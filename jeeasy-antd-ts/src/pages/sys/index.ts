// @ts-ignore
/* eslint-disable */


import type { DelFalg, Sex } from '@/utils/common'
import { Label, BaseModel, Data } from '@/utils/common'

@Data('系统用户', '/api/sys/user', 'sys:user')
export class SysUser extends BaseModel<SysUser> {
  @Label('ID') id?: string
  @Label('用户名') username?: string
  @Label('用户编号') userNo?: number
  @Label('手机号') phone?: string
  @Label('姓名') realName?: string
  @Label('性别') sex?: Sex
  @Label('出生日期') birthday?: string
  @Label('密码') password?: string
  @Label('状态') status?: 1 | 0
  @Label('email') email?: string
  @Label('头像') avatar?: string
  @Label('创建时间') createTime?: Date
  @Label('创建人') createBy?: string
  @Label('更新时间') updateTime?: Date
  @Label('更新人') updateBy?: string
  @Label('备注') remark?: string
  @Label('删除标记') delFlag?: DelFalg
  @Label('角色') roles?: any
  @Label('部门') depts?: any
}

console.log('SysUser -----> ', SysUser)
const user = new SysUser()
console.log('new SysUser() -----> ', user)
