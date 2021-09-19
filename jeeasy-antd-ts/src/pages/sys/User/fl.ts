import type { DelFalg, Sex, FL } from '@/utils/common'


class ModelField<T = undefined> implements Record<string, any>{
  labels?: FL<T>
  fields?: FL<T>
  // constructor() {
  //   this.labels = {}
  //   this.fields = {}
  // }
  public setLabel(k: string, v: any){
    if(!this.fields){
      this.fields = {}
    }
    // @ts-ignore
    this.fields[k] = v
  }
  public setField(k: string, v: any){
    if(!this.fields){
      this.fields = {}
    }
    // @ts-ignore
    this.fields[k] = v
  }
}


export class SysUser extends ModelField<SysUser> {
  @Label('ID')
  id?: string = ''
  @Label('用户名')
  username?: string
  userNo?: number
  phone?: string
  realName?: string
  sex?: Sex
  birthday?: string
  password?: string
  status?: 1 | 0
  email?: string
  avatar?: string
  createTime?: Date
  createBy?: string
  updateTime?: Date
  updateBy?: string
  remark?: string
  delFlag?: DelFalg
  roles?: any
  departs?: any
}

// type Target = ModelField<any>

function Label(label: string) {
  return (target: ModelField, attr: string) => {
    // let { labels, fields } = target
    target.setLabel(attr, label)
    target.setField(attr, attr)
    // labels = {...labels, ...{[attr]: label}}
    // // eslint-disable-next-line no-param-reassign,@typescript-eslint/no-unused-vars
    // fields = {...fields, ...{[attr]: attr}}
  }
}

console.log(new SysUser())

export const UserLabels: FL<SysUser> = {
  id: 'ID',
  username: '用户名',
  userNo: '用户编号',
  phone: '手机号',
  realName: '姓名',
  sex: '性别',
  birthday: '出生日期',
  password: '密码',
  status: '状态',
  email: 'email',
  avatar: '头像',
  createTime: '创建时间',
  createBy: '创建人',
  updateTime: '更新时间',
  updateBy: '更新人',
  remark: '备注',
  delFlag: '删除标记',
  roles: '角色',
  departs: '部门'
}
