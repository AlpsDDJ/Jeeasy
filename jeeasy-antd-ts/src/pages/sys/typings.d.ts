// @ts-ignore
/* eslint-disable */



declare namespace SYS {
  import Sex = PUB.Sex
  import DelFalg = PUB.DelFalg
  interface User {
    id?: string,
    username?: string,
    userNo?: number,
    phone?: string,
    realName?: string,
    sex?: Sex,
    birthday?: string,
    password?: string,
    status?: 1 | 0,
    email?: string,
    avatar?: string,
    createTime?: Date,
    createBy?: string,
    updateTime?: Date,
    updateBy?: string,
    remark?: string,
    delFlag?: DelFalg,
    roles?: any,
    departs?: any,
  }
}
