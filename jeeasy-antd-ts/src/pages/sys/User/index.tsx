import React, { useEffect, useState } from 'react'
import { Cascader, message } from 'antd'
import { PageContainer } from '@ant-design/pro-layout'
import ProTable from '@ant-design/pro-table'
import { useEasyTable } from '@/common/EasyTable'
import { useApis } from '@/services'
import { BetaSchemaForm } from '@ant-design/pro-form'
import type SysUser from './vo'
import { baseApi, columnMap, labels, name } from './vo'
import { getTreeDictItems } from '@/services/ant-design-pro/api'
import { dictCode } from '@/common/dict'
import { CascaderOptionType } from 'antd/lib/cascader'

const UserList: React.FC = () => {

  const [deptOptions, setDeptOptions] = useState<CascaderOptionType[]>([])
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
        renderFormItem: ({ originProps: {dict = ''} }) => {
          return <Cascader loadData={(selectedOptions ) => {
            const targetOption = selectedOptions?.[selectedOptions.length - 1] || {};
            targetOption.loading = true
            getTreeDictItems(dict, targetOption?.value).then(data => {
              targetOption.loading = false
              targetOption.children = data
              setDeptOptions([...deptOptions])
            })
          }} options={deptOptions} />
        }
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

  useEffect(() => {
    getTreeDictItems(dictCode.sysDept).then((data) => {
      setDeptOptions(data)
    })
  }, [])


  return (
    <PageContainer>
      <ProTable<SysUser>
        { ...tableOptions }
        postData={records => {
          return [...records].map(({roles, depts, ...user}) => {
            return {
              ...user,
              roles: roles?.map((role: any) => role?.roleName),
              depts: depts?.map((dept: any) => dept?.deptName),
            }
          })

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
