// import React from 'react'

const fields = {
  id: 'id',
  name: 'name',
  description: 'description',
  dataSource: 'dataSource',
  izSync: 'izSync',
  izSimpleQuery: 'izSimpleQuery',
  izPage: 'izPage',
  izTree: 'izTree',
  treePid: 'treePid',
  treeNameField: 'treeNameField',
  formType: 'formType',
  idSequence: 'idSequence',
  idType: 'idType',
  tableType: 'tableType',
  mainTable: 'mainTable',
  slaveTable: 'slaveTable',
  mainColumn: 'mainColumn',
  mainColumnCode: 'mainColumnCode',
  slaveColumn: 'slaveColumn',
  slaveColumnCode: 'slaveColumnCode',
  formStyle: 'formStyle',
  relationType: 'relationType',
  subTableStr: 'subTableStr',
  tabOrderNum: 'tabOrderNum',
  // createBy: 'createBy',
  // createTime: 'createTime',
  // updateBy: 'updateBy',
  // updateTime: 'updateTime',
  // sysOrgCode: 'sysOrgCode',
}

const labels = {
  [fields.id]: '编号',
  [fields.name]: '表名',
  [fields.description]: '表描述',
  [fields.dataSource]: '数据源',
  [fields.izSync]: '同步数据库状态',
  [fields.izSimpleQuery]: '是否简单查询',
  [fields.izPage]: '是否分页',
  [fields.izTree]: '是否是树',
  [fields.treePid]: '树PID',
  [fields.treeNameField]: '树名称字段',
  [fields.formType]: '表单风格',
  [fields.idSequence]: '主键生成序列',
  [fields.idType]: '主键类型',
  [fields.tableType]: '表类型',
  [fields.mainTable]: '主表',
  [fields.slaveTable]: '从表',
  [fields.mainColumn]: '主表字段',
  [fields.mainColumnCode]: '主表字段Code',
  [fields.slaveColumn]: '从表字段',
  [fields.slaveColumnCode]: '从表字段Code',
  [fields.formStyle]: '显示样式',
  [fields.relationType]: '映射关系',
  [fields.subTableStr]: '子表',
  [fields.tabOrderNum]: '附表排序序号',
  // [fields.createBy]: '创建人',
  // [fields.createTime]: '创建日期',
  // [fields.updateBy]: '更新人',
  // [fields.updateTime]: '更新日期',
  // [fields.sysOrgCode]: '所属部门',
}

// const listColumnsConf = {
//   // [fields.username]: {
//   //   render: (text, record, index, action) => {
//   //     console.log(action)
//   //     return (<a>{text}</a>)
//   //   }
//   // }
// }
//
// const listHidden = [fields.izSimpleQuery, fields.izPage, fields.izTree, fields.treePid, fields.treeNameField, fields.formType, fields.idSequence, fields.idType, fields.tableType, fields.mainTable, fields.slaveTable, fields.mainColumn, fields.mainColumnCode, fields.slaveColumn, fields.slaveColumnCode, fields.formStyle, fields.relationType, fields.subTableStr, fields.tabOrderNum]
// const searchColumns = []
// const listColumns = _this => {
//   console.log(_this)
//   return [...Object.keys(fields).filter(key => listHidden.indexOf(key) === -1).map(key => ({
//     renderText: (text, record) => (record[`${key}_dict`] || text),
//     title: labels[key] || '',
//     dataIndex: fields[key],
//     search: searchColumns.indexOf(key) !== -1,
//     ...(listColumnsConf[key] || {})
//   })), {
//     title: '操作',
//     dataIndex: fields.id,
//     valueType: 'option',
//     render: () => {
//       return (
//         <a >修改</a>
//       )
//     }
//   }]
// }

export default {
  fields,
  labels,
  // listColumns,
}