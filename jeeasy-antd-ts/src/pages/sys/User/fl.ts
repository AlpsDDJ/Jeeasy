import type { DelFalg, Sex } from '@/utils/common'
import { Label, ModelField } from '@/utils/common'


// @Data
export class SysUser extends ModelField {
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
  @Label('部门') departs?: any
}

// export type SysUser = {
//   [P in keyof SysUserModal]: typeof SysUserModal.prototype[P]
// }


// console.log(' new SysUser() ------- ', SysUserModal.prototype)
// export const fl: ModelField<SysUserModal> = SysUserModal.prototype
export const {labels, fields} = SysUser.prototype

// export const UserLabels: FL<SysUserModal> = {
//   id: 'ID',
//   username: '用户名',
//   userNo: '用户编号',
//   phone: '手机号',
//   realName: '姓名',
//   sex: '性别',
//   birthday: '出生日期',
//   password: '密码',
//   status: '状态',
//   email: 'email',
//   avatar: '头像',
//   createTime: '创建时间',
//   createBy: '创建人',
//   updateTime: '更新时间',
//   updateBy: '更新人',
//   remark: '备注',
//   delFlag: '删除标记',
//   roles: '角色',
//   departs: '部门'
// }
