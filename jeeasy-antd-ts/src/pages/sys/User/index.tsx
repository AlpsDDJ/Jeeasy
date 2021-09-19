import React from 'react'
import { Button, message } from 'antd'
import { PageContainer } from '@ant-design/pro-layout'
import ProTable from '@ant-design/pro-table'
// import type { ExtendProColumns } from '@/utils/proTableUtil'
import { useEasyTable, useFl } from '@/utils/EasyTable'
import { useApis } from '@/services'
import type { PageParams } from '@/utils/common'
import type { SysUser } from './fl'
import { UserLabels } from './fl'
import { BetaSchemaForm } from '@ant-design/pro-form'


const UserList: React.FC = () => {

  const apis = useApis<SysUser>('/api/sys/user')
  const { fields, labels } = useFl<SysUser>(UserLabels)

  const { columns, formOptions, tableOptions, formVisible, setFormVisible, setFormData, tableRef, formRef } = useEasyTable<SysUser>({
    title: '系统用户',
    fl: labels,
    apis,
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
        option: [{
          key: 'edit',
          name: '编辑',
          handle: async (record) => {
            setFormVisible?.(true)
            setFormData?.(record)
          }
        }, {
          key: 'del',
          name: '删除',
          handle: async ({ id }) => {
            if (id) {
              const resp = await apis.del(id)
              if (resp.success) {
                message.success(resp.message)
                tableRef?.current?.reload()
              }
            }
          }
        }]
      }
    ]
  })

  // const columns: ExtendProColumns<SysUser>[] = columnsExtend([
  //   {
  //     dataIndex: fields.username,
  //   },
  //   {
  //     dataIndex: fields.userNo,
  //     sorter: true,
  //   },
  //   {
  //     dataIndex: fields.realName
  //   },
  //   {
  //     dataIndex: fields.phone
  //   },
  //   {
  //     dataIndex: fields.sex,
  //     dict: 'sex'
  //   },
  //   {
  //     dataIndex: fields.status,
  //     dict: 'sys_user_status'
  //   },
  //   {
  //     option: [(user) => (
  //       <BetaSchemaForm<SysUser>
  //         key={user.id}
  //         trigger={<Button  type="link">编辑</Button>}
  //         layoutType="ModalForm"
  //         onFinish={async (values) => {
  //           console.log(values);
  //         }}
  //         initialValues={user}
  //         columns={columnsExtend(columns, labels)}
  //       />
  //     ),{
  //       key: 'del',
  //       name: '删除',
  //       handle: async ({ id }) => {
  //         if (id) {
  //           const resp = await del(id)
  //           if(resp.success){
  //             message.success(resp.message)
  //             tableRef?.current?.reload()
  //           }
  //         }
  //       }
  //     }]
  //   }
  // ], labels)

  // const apis: ApiMap = parseApi('/sys/user')

  // const tableOpt: ProTableProps<SysUser, PageParams> = {
  //   actionRef: tableRef,
  //   request: apis.list,
  //   rowKey: 'id',
  //   columns
  // }


  return (
    <PageContainer>
      <ProTable<SysUser, PageParams>
        { ...tableOptions }
        actionRef={ tableRef }
        columns={ columns }
        toolbar={ {
          actions: [
            <BetaSchemaForm<SysUser>
              { ...formOptions }
              visible={ formVisible }
              formRef={formRef}
              trigger={ <Button type="primary">新增</Button> }
              onFinish={ async (values) => {
                console.log(values)
              } }
              columns={ columns }
            />
          ]
        } }
      />

    </PageContainer>
  )
}

export default UserList
