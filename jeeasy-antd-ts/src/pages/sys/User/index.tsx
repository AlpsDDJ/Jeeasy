import React from 'react'
import { message } from 'antd'
import { PageContainer } from '@ant-design/pro-layout'
import ProTable from '@ant-design/pro-table'
import { useEasyTable } from '@/utils/EasyTable'
import { useApis } from '@/services'
import { BetaSchemaForm } from '@ant-design/pro-form'
import { SysUser } from '@/pages/sys'

const { labels, fields } = SysUser.prototype.$$

const UserList: React.FC = () => {

  const apis = useApis<SysUser>('/api/sys/user')

  const { columns, formOptions, tableRef, tableOptions, showForm } = useEasyTable<SysUser>({
    title: '系统用户',
    fl: labels,
    apis,
    formatFormData: ({ roles, depts, ...user }) => {
      return {
        user,
        roles: roles.join(','),
        depts: depts.join(',')
      }
    },
    columns: [
      {
        dataIndex: fields.username
      },
      {
        dataIndex: fields.userNo,
        sorter: true
      },
      {
        dataIndex: fields.realName
      },
      {
        dataIndex: fields.phone
      },
      {
        dataIndex: fields.sex,
        dict: 'sex'
      },
      {
        dataIndex: fields.status,
        dict: 'sys_user_status'
      },
      {
        operate: ['add', {
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
    <PageContainer>
      <ProTable<SysUser>
        { ...tableOptions }
        columns={ columns }
      />
      <BetaSchemaForm<SysUser>
        { ...formOptions }
        columns={ columns }
      />
    </PageContainer>
  )
}

export default UserList
