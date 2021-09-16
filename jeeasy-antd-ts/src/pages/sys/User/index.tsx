import React, { useRef } from 'react'
import { PageContainer } from '@ant-design/pro-layout'
import type { ActionType, ProTableProps } from '@ant-design/pro-table'
import ProTable from '@ant-design/pro-table'
import type { ExtendProColumns } from '@/utils/proTableUtil'
import { columnsExtend } from '@/utils/proTableUtil'
import { useApis } from '@/services'

const UserList: React.FC = () => {


  const actionRef = useRef<ActionType>()
  const columns: ExtendProColumns<SYS.User>[] = [
    {
      title: '用户名',
      dataIndex: 'username'
    },
    {
      title: '姓名',
      dataIndex: 'realName'
    },
    {
      title: '状态',
      dataIndex: 'status',
      dict: 'sys_user_status'
    }
  ]

  // const apis: ApiMap = parseApi('/sys/user')
  const { list } = useApis<SYS.User>('/api/sys/user')

  const tableOpt: ProTableProps<SYS.User, PUB.PageParams> = {
    actionRef,
    request: list,
    rowKey: 'id',
    columns: columnsExtend(columns)
  }

  return (
    <PageContainer>
      <ProTable<SYS.User, PUB.PageParams>
        { ...tableOpt }
      />
    </PageContainer>
  )
}

export default UserList
