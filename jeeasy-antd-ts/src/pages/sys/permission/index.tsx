import React from 'react'
import { message } from 'antd'
import ProTable from '@ant-design/pro-table'
import { useEasyTable } from '@/common/EasyTable'
import { useApis } from '@/services'
import { BetaSchemaForm } from '@ant-design/pro-form'
import type SysPermission from './vo'
import { baseApi, columnMap, labels, name } from './vo'
import TreeDictSelect from '@/components/EasyForm/dict/TreeDictSelect'

const menuTypeEnum = {
  type1: 1, // 一级菜单
  type2: 2, // 子菜单
  type3: 3, // 权限&按钮

}

const PremissionList: React.FC = () => {

  const isTree = true
  const apis = useApis<SysPermission>(baseApi, isTree)

  const hiddenInButton = (record: SysPermission) => record.menuType === menuTypeEnum.type3

  const { columns, formOptions, tableRef, tableOptions, showForm } = useEasyTable<SysPermission>({
    title: name,
    fl: labels,
    apis,
    isTree,
    columnMap,
    columns: [
      {
        dataIndex: 'parentId',
        renderFormItem: ({ originProps: { dict = '' } }) => <TreeDictSelect dictCode={ dict } />,
        hiddenByData: record => record.menuType === menuTypeEnum.type1,
      },
      {
        dataIndex: 'path',
        hiddenByData: hiddenInButton,
      },
      {
        dataIndex: 'permsType',
        hiddenByData: record => record.menuType !== menuTypeEnum.type3,
      },
      {
        dataIndex: 'component',
        hiddenByData: hiddenInButton,
      },
      {
        dataIndex: 'isRoute',
        hiddenByData: hiddenInButton,
      },
      {
        dataIndex: 'isLeaf',
        hiddenByData: hiddenInButton,
      },
      {
        dataIndex: 'hidden',
        hiddenByData: hiddenInButton,
      },
      {
        dataIndex: 'alwaysShow',
        hiddenByData: hiddenInButton,
      },
      {
        dataIndex: 'keepAlive',
        hiddenByData: hiddenInButton,
      },
      {
        dataIndex: 'operate',
        width: '200',
        operate: [{
          key: 'edit',
          name: '编辑',
          handle: (record) => {
            showForm('edit', record)
          }
        }, {
          key: 'del',
          name: '删除',
          handle: async ({ id }) => {
            if (id) {
              const resp = await apis.del(id)
              if (resp.success) {
                message.success(resp.message)
                tableRef.current?.reload()
              }
            }
          }
        }]
      }
    ]
  })

  return (
    <>
      <ProTable<SysPermission>
        columns={ columns }
        { ...tableOptions }
      />
      <BetaSchemaForm<SysPermission>
        { ...formOptions }
        columns={ columns }
      />
    </>
  )
}

export default PremissionList
