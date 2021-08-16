import { initColumns } from '@/common/utlis/JeeasyUtils'

const fields = {
  id: 'id',
  roleName: 'roleName',
  roleCode: 'roleCode',
  remark: 'remark',
  description: 'description',
  sortNo: 'sortNo',
  enableFlag: 'enableFlag'
  // createTime: 'createTime',
  // createBy: 'createBy',
  // updateTime: 'updateTime',
  // updateBy: 'updateBy'
  // id: 'id'
}

export const labels = {
  [fields.id]: 'ID',
  [fields.roleName]: '角色名称',
  [fields.roleCode]: '角色标识',
  [fields.sortNo]: '排序',
  [fields.remark]: '备注',
  [fields.description]: '描述',
  [fields.enableFlag]: '启用标记'
  // [fields.createTime]: '创建时间',
  // [fields.createBy]: '创建人',
  // [fields.updateTime]: '修改时间',
  // [fields.updateBy]: '修改人'
  // [fields.id]: 'ID'
}

const options = {
  listHidden: [fields.id, fields.remark],
  formHidden: [fields.id],
  showQuery: [fields.roleName, fields.roleCode, fields.enableFlag]
}

export default {
  ...fields,
  columns: () => {
    return initColumns(fields, labels, options)
  }
}













