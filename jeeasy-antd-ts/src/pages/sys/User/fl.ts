import type { DelFalg, Sex, FL } from '@/utils/common'


class ModelField<T = undefined> implements Record<string, any>{
  public labels?: FL<T> = {}
  public fields?: FL<T> = {}
  constructor() {
    this.labels = {}
    this.fields = {}
  }

  public get getLabels(){
    return this.labels
  }

  public setLabel?(k: string, v: any, _this: ModelField<T>){
    // if(!_this.fields){
    //   _this.fields = {}
    // }
    // @ts-ignore
    _this.labels[k] = v
  }
  public setField?(k: string, v: any, _this: ModelField<T>){
    // if(!_this.fields){
    //   _this.fields = {}
    // }
    // @ts-ignore
    _this.fields[k] = v
  }
}

@Data
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


function Data(target: any) {
  // save a reference to the original constructor
  // const original = target;

  // a utility function to generate instances of a class
  // function construct(constructor: any, args: any[]) {
  //   const c: any = function () {
  //     return constructor.apply(this, args);
  //   }
  //   c.prototype = constructor.prototype;
  //   return new c();
  // }

  // the new constructor behaviour
  const f: any = function (...args: any[]) {
    console.log("New: ", target.name);
    // @ts-ignore
    // eslint-disable-next-line @typescript-eslint/no-invalid-this
    const o = target.apply(this, args)
    const d = Reflect.getOwnPropertyDescriptor(o, 'id')
    console.log('=============>>> ', d)
    return o;
  }

  // copy prototype so intanceof operator still works
  f.prototype = target.prototype;

  // return new constructor (will override original)
  return f;
}

// type Target = ModelField<any>

export function Label(label: string) {
  return (target: ModelField, attr: string) => {
    // let { labels, fields } = target
    // Reflect.getOwnPropertyDescriptor("design:type", target, key);
    const d = Reflect.getOwnPropertyDescriptor(target, attr)
    console.log(d)

    const {labels, fields} = target

    console.log(labels, fields)

    target.setLabel?.(attr, label, target)
    target.setField?.(attr, attr, target)
    // labels = {...labels, ...{[attr]: label}}
    // // eslint-disable-next-line no-param-reassign,@typescript-eslint/no-unused-vars
    // fields = {...fields, ...{[attr]: attr}}
  }
}

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
