import React from 'react'
import { message } from 'antd'
import ProTable from '@ant-design/pro-table'
import { useEasyTable } from '@/common/EasyTable'
import { useApis } from '@/services'
import { BetaSchemaForm } from '@ant-design/pro-form'
import type SysUser from './vo'
import { baseApi, columnMap, labels, name } from './vo'
import TreeDictSelect from '@/components/EasyForm/dict/TreeDictSelect'
// import DictSelect from '@/components/EasyForm/dict/DictSelect'

const UserList: React.FC = () => {

  const apis = useApis<SysUser>(baseApi)

  const { columns, formOptions, tableRef, tableOptions, showForm } = useEasyTable<SysUser>({
    title: name,
    fl: labels,
    apis,
    columnMap,
    formatFormData: ({ roles, depts, ...user }) => {
      return {
        user,
        roles: roles?.join(','),
        depts: depts?.join(',')
      }
    },
    columns: [
      {
        dataIndex: 'roles',
        renderText: (text, { rolesText }) => rolesText.join(', '),
        // renderFormItem: ({ originProps: { dict = '' } }) => <DictSelect dictCode={ dict } multiple={ true } />
      },
      {
        dataIndex: 'depts',
        renderText: (text, { deptsText }) => deptsText.join(', '),
        valueType: 'text',
        renderFormItem: ({ originProps: { dict = '' } }) => <TreeDictSelect dict={ dict } multiple={ true } />
      },
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
      <ProTable<SysUser>
        columns={ columns }
        { ...tableOptions }
        postData={ records => {
          return [...records].map(({ roles, depts, ...user }) => {
            return {
              ...user,
              roles: roles?.map((role: any) => role?.id),
              rolesText: roles?.map((role: any) => role?.roleName),
              depts: depts?.map((dept: any) => dept?.id),
              deptsText: depts?.map((dept: any) => dept?.deptName)
            }
          })
        } }
        beforeSearchSubmit={ ({ roles, depts, ...params }) => {
          return {
            ...params,
            roleId: roles?.join(),
            deptId: depts?.join()
          }
        } }
      />
      <BetaSchemaForm<SysUser>
        { ...formOptions }
        columns={ columns }
      />
    </>
  )
}

export default UserList
