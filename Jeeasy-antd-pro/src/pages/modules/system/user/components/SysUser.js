import React from 'react'

const fields = {
  id: 'id',
  username: 'username',
  realName: 'realName',
  phone: 'phone',
  userNo: 'userNo',
  sex: 'sex',
  birthday: 'birthday',
  password: 'password',
  salt: 'salt',
  status: 'status',
  email: 'email',
  avatar: 'avatar',
  remark: 'remark',
}

const labels = {
  [fields.id]: 'ID',
  [fields.username]: '用户名',
  [fields.userNo]: '编号',
  [fields.phone]: '手机号',
  [fields.realName]: '姓名',
  [fields.sex]: '性别',
  [fields.birthday]: '生日',
  [fields.password]: '密码',
  [fields.salt]: '--',
  [fields.status]: '状态',
  [fields.email]: 'Email',
  [fields.avatar]: '头像',
  [fields.remark]: '备注',
}

const listColumnsConf = {
  [fields.username]: {
    render: (text, record, index, action) => {
      console.log(action)
      return (<a>{text}</a>)
    }
  }
}

const listHidden = [fields.id, fields.password, fields.salt, fields.remark]
const listColumns = Object.keys(fields).filter(key => listHidden.indexOf(key) === -1).map(key => ({
  renderText: (text, record) => (record[`${key}_dict`] || text),
  title: labels[key] || '',
  dataIndex: fields[key],
  ...(listColumnsConf[key] || {})
}))

export default {
  fields,
  labels,
  listColumns,
}