import { initColumns } from '@/common/utlis/JeeasyUtils'

const fields = {
  id: 'id',
  username: 'username',
  realName: 'realName',
  roles: 'roles',
  departs: 'departs',
  phone: 'phone',
  sex: 'sex',
  status: 'status'
}

export const labels = {
  [fields.id]: 'ID',
  [fields.username]: '用户名',
  [fields.realName]: '姓名',
  [fields.roles]: '角色',
  [fields.departs]: '部门',
  [fields.phone]: '电话',
  [fields.sex]: '性别',
  [fields.status]: '状态'
}


const options = {
  listHidden: [fields.id],
  formHidden: [fields.id],
  showQuery: [fields.username, fields.realName, fields.phone, fields.status]
  // showQuery: []
}

export default {
  ...fields,
  columns: () => {
    return initColumns(fields, labels, options)
  }
}
