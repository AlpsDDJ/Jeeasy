import React from 'react'
import { message } from 'antd'
import { PageContainer } from '@ant-design/pro-layout'
import ProTable from '@ant-design/pro-table'
import { useEasyTable } from '@/common/EasyTable'
import { useApis } from '@/services'
import { BetaSchemaForm } from '@ant-design/pro-form'
import type SysUser from './vo'
import { baseApi, columnMap, labels, name } from './vo'
import TreeDict from '@/components/EasyForm/TreeDict'

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
        renderText: (text) => {
          return text.join(', ')
        }
      },
      {
        dataIndex: 'depts',
        renderText: (text) => {
          return text.join(', ')
        },
        valueType: 'text',
        renderFormItem: ({ originProps: { dict = '' } }) => (<TreeDict dictCode={ dict } multiple={true} />)
      },
      {
        dataIndex: 'operate',
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

  // useEffect(() => {
  //   getTreeDictItems(dictCode.sysDept).then((data) => {
  //     setDeptOptions(data)
  //   })
  // }, [])


  return (
    <PageContainer>
      <ProTable<SysUser>
        { ...tableOptions }
        postData={ records => {
          return [...records].map(({ roles, depts, ...user }) => {
            return {
              ...user,
              roles: roles?.map((role: any) => role?.roleName),
              depts: depts?.map((dept: any) => dept?.deptName)
            }
          })

        } }
        beforeSearchSubmit={({roles, depts, ...params}) => {
          console.log(params)
          return {
            ...params,
            roleId: roles?.join(),
            deptId: depts?.join()
          }
        }}
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
