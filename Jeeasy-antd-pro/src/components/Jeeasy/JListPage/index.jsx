import ProTable from '@ant-design/pro-table'
import React, { useRef, useState } from 'react'
import { PageContainer } from '@ant-design/pro-layout'
import { Button, Popconfirm } from 'antd'
import JFormModal from '@/components/Jeeasy/JFormModal'

const ListPage = props => {
  const [modalVisible, handleModalVisible] = useState(false);

  const {
    dispatch, ACTIONS, fields, labels, rowKey, current = 1, pageSize = 10, total = 0, beforeSearch = search => search,
    rowAction = ['edit', 'delete'], searchColumns = [], columnsConf = {}, listHidden = [], toolBar = ['add'], ...options
  } = props

  const dataKey = rowKey || fields.id

  const actionMap = {
    edit: {
      title: '修改',
      fn: (id, record) => {
        return (<a key="edit" onClick={() => {
          console.log('修改', id)
        }}> 修改 </a>)
      }
    },
    'delete': {
      title: '删除',
      fn: id => (
        <Popconfirm
          title="确定删除？"
          okText="确定"
          cancelText="取消"
          key="delete"
          onConfirm={() => {
            console.log('删除', id)
          }}
        >
          <a> 删除 </a>
        </Popconfirm>
      )
    }
  }

  const actionCol = () => {
    return rowAction ? [{
      title: '操作',
      dataIndex: dataKey,
      render: (text, record) => rowAction.map(actionKey => (actionMap[actionKey].fn(text, record)))
    }] : []
  }

  const valueTypeRender = {
    'view': (text, record) => (<a onClick={() => {
      console.log(record[dataKey])
    }}>{text}</a>)
  }

  const listColumns = () => {
    return [...Object.keys(fields).filter(key => listHidden.indexOf(key) === -1).map(key => ({
      renderText: (text, record) => (record[`${key}_dict`] || text),
      title: labels[key] || '',
      dataIndex: fields[key],
      search: searchColumns.indexOf(key) !== -1,
      ...({
        ...columnsConf[key],
        render: valueTypeRender[(columnsConf[key] || {}).valueType || undefined] || undefined
      } || {})
    })), ...actionCol()]
  }

  const loadDataList = async (search = {}) => (
    dispatch(ACTIONS.LIST, beforeSearch(search)).then(({ result }) => (
      Promise.resolve({
        data: result.records,
        success: true,
      })
    )))

  const handleAdd = () => {
    console.log('添加')
  }

  const handleExport = () => {
    console.log('导出')
  }

  const handleImport = () => {
    console.log('导入')
  }

  const toolBarButtons = {
    add: <Button type="primary" onClick={() => handleModalVisible(true)} key="add">添加</Button>,
    'export': <Button type="primary" onClick={handleExport} key="export">导出</Button>,
    'import': <Button type="primary" onClick={handleImport} key="import">导入</Button>,
  }

  const toolBarRender = () => {
    const btns = toolBar.map(act => toolBarButtons[act] || undefined).filter(btn => !!btn)

    return ([
      ...options.toolBarRender || [],
      ...btns
    ])
  }
  const actionRef = useRef();

  const columns = listColumns()

  const tableOptions = {
    rowKey: dataKey,
    toolBarRender,
    actionRef,
    columns,
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
      <JFormModal onClose={() => handleModalVisible(false)} visible={modalVisible} columns={columns} columnsConf={columnsConf}/>
    </PageContainer>
  )
}

export default ListPage