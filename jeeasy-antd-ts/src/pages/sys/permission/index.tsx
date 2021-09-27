import React from 'react'
import { message } from 'antd'
import ProTable from '@ant-design/pro-table'
import { useEasyTable } from '@/common/EasyTable'
import { useApis } from '@/services'
import { BetaSchemaForm } from '@ant-design/pro-form'
import type SysPremission from './vo'
import { baseApi, columnMap, labels, name } from './vo'

const PremissionList: React.FC = () => {

  const apis = useApis<SysPremission>(baseApi)

  const { columns, formOptions, tableRef, tableOptions, showForm } = useEasyTable<SysPremission>({
    title: name,
    fl: labels,
    apis,
    columnMap,
    columns: [
      {
        dataIndex: 'operate',
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
      <ProTable<SysPremission>
        columns={ columns }
        { ...tableOptions }
      />
      <BetaSchemaForm<SysPremission>
        { ...formOptions }
        columns={ columns }
      />
    </>
  )
}

export default PremissionList
