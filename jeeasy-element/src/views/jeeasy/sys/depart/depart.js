import { initColumns } from '@/common/utlis/JeeasyUtils'

const fields = {
  id: 'id',
  orgType: 'orgType',
  parentId: 'parentId',
  departName: 'departName',
  departNameEn: 'departNameEn',
  departNameAbbr: 'departNameAbbr',
  orgCategory: 'orgCategory',
  orgCode: 'orgCode',
  address: 'address',
  mobile: 'mobile',
  description: 'description',
  remark: 'remark',
  fax: 'fax',
  sortNo: 'sortNo',
  enableFlag: 'enableFlag'
  // createBy: 'createBy',
  // createTime: 'createTime',
  // updateBy: 'updateBy',
  // updateTime: 'updateTime',
  // delFlag: 'delFlag'
}

export const labels = {
  [fields.id]: 'ID',
  [fields.departName]: '名称',
  [fields.departNameAbbr]: '缩写',
  [fields.orgCategory]: '类别',
  [fields.departNameEn]: '英文名',
  [fields.address]: '地址',
  [fields.mobile]: '手机号',
  [fields.description]: '描述',
  [fields.remark]: '备注',
  [fields.parentId]: '上级部门',
  [fields.orgType]: '类型',
  [fields.orgCode]: '编码',
  [fields.fax]: '传真',
  [fields.sortNo]: '排序',
  [fields.enableFlag]: '启用标记'
  // [fields.updateTime]: '更新日期',
  // [fields.delFlag]: '删除状态',
  // [fields.createBy]: '创建人',
  // [fields.createTime]: '创建日期',
  // [fields.updateBy]: '更新人'
}


const options = {
  listHidden: [fields.id, fields.parentId, fields.departNameAbbr, fields.fax, fields.remark, fields.departNameEn, fields.address, fields.mobile, fields.description, fields.orgType],
  formHidden: [fields.id],
  showQuery: [fields.departName, fields.orgCategory, fields.parentId]
  // showQuery: []
}

export default {
  ...fields,
  columns: () => {
    return initColumns(fields, labels, options)
  }
}
