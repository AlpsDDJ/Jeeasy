import React, { useRef } from 'react'
import { Button, message } from 'antd'
import { PageContainer } from '@ant-design/pro-layout'
import type { ActionType, ProTableProps } from '@ant-design/pro-table'
import ProTable from '@ant-design/pro-table'
// import type { ExtendProColumns } from '@/utils/proTableUtil'
import { columnsExtend, ExtendProColumns, useFl } from '@/utils/proTableUtil'
import { useApis } from '@/services'
import type { PageParams } from '@/utils/common'
import type { SysUser } from './fl';
import { UserLabels } from './fl'
import { BetaSchemaForm } from '@ant-design/pro-form'


const UserList: React.FC = () => {

  const tableRef = useRef<ActionType>()

  const { fields, labels } = useFl<SysUser>(UserLabels)
  const { list, del } = useApis<SysUser>('/api/sys/user')

  const columns: ExtendProColumns<SysUser>[] = columnsExtend([
    {
      dataIndex: fields.username,
    },
    {
      dataIndex: fields.userNo,
      sorter: true,
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
      option: [(user) => (
        <BetaSchemaForm<SysUser>
          key={user.id}
          trigger={<Button  type="link">编辑</Button>}
          layoutType="ModalForm"
          onFinish={async (values) => {
            console.log(values);
          }}
          initialValues={user}
          columns={columnsExtend(columns, labels)}
        />
      ),{
        key: 'del',
        name: '删除',
        handle: async ({ id }) => {
          if (id) {
            const resp = await del(id)
            if(resp.success){
              message.success(resp.message)
              tableRef?.current?.reload()
            }
          }
        }
      }]
    }
  ], labels)

  // const apis: ApiMap = parseApi('/sys/user')

  const tableOpt: ProTableProps<SysUser, PageParams> = {
    actionRef: tableRef,
    request: list,
    rowKey: 'id',
    columns
  }


  return (
    <PageContainer>
      <ProTable<SysUser, PageParams>
        { ...tableOpt }
        toolbar={{
          actions: [
            <BetaSchemaForm<SysUser>
              trigger={<Button type="primary">新增</Button>}
              layoutType="ModalForm"
              onFinish={async (values) => {
                console.log(values);
              }}
              columns={columnsExtend(columns, labels)}
            />
          ]
        }}
      />

    </PageContainer>
  )
}

export default UserList
