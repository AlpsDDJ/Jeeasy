import ProTable from '@ant-design/pro-table'
import React, { useRef } from 'react'
import { PageContainer } from '@ant-design/pro-layout'
import { Popconfirm } from 'antd'

const ListPage = props => {

  const {dispatch, ACTIONS, fields, labels, rowKey, current = 1, pageSize = 10, total = 0, beforeSearch = search => search,
    rowAction = ['edit', 'delete'], searchColumns = [], columnsConf = {}, listHidden = [], ...options} = props

  const dataKey = rowKey || fields.id

  const actionMap = {
    edit: {
      title: '修改',
      fn: (id, record) =>{
        return (<a onClick={() => {console.log('修改', id)}}> 修改 </a>)
      }
    },
    'delete': {
      title: '删除',
      fn: id => (
        <Popconfirm title="确定删除？" okText="确定" cancelText="取消"
          onConfirm={() => {console.log('删除', id)}}
        >
          <a> 删除 </a>
        </Popconfirm>
      )
    }
  }

  const actionCol = () => {
    return rowAction? [{
      title: '操作',
      dataIndex: dataKey,
      render: (text, record) => rowAction.map(actionKey => (actionMap[actionKey].fn(text, record)))
    }] : []
  }

  const listColumns = () => {
    return [...Object.keys(fields).filter(key => listHidden.indexOf(key) === -1).map(key => ({
      renderText: (text, record) => (record[`${key}_dict`] || text),
      title: labels[key] || '',
      dataIndex: fields[key],
      search: searchColumns.indexOf(key) !== -1,
      ...(columnsConf[key] || {})
    })), ...actionCol()]
  }

  const loadDataList = async (search = {}) => (
    dispatch(ACTIONS.LIST, beforeSearch(search)).then(({ result }) => (
      Promise.resolve({
        data: result.records,
        success: true,
      })
    )))

  const tableOptions = {
    rowKey: dataKey,
    actionRef: useRef(),
    columns: listColumns(),
    request: loadDataList,
    pagination: {
      current,
      pageSize,
      total,
    },
    ...options
  }

  return (
    <PageContainer>
      <ProTable
        {...tableOptions}
      />
    </PageContainer>
  )

}

export default ListPage