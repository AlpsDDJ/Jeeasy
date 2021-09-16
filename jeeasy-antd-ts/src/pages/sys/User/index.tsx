import React, { useRef } from 'react'
import { PageContainer } from '@ant-design/pro-layout'
import type { ActionType, ProTableProps } from '@ant-design/pro-table'
import ProTable from '@ant-design/pro-table'
import type { ExtendProColumns } from '@/utils/proTableUtil'
import { columnsExtend, useFl } from '@/utils/proTableUtil'
import { useApis } from '@/services'
import type { PageParams } from '@/utils/common'
import type { User } from './fl';
import { UserLabels } from './fl'

const UserList: React.FC = () => {

  const actionRef = useRef<ActionType>()

  const { fields, labels } = useFl<User>(UserLabels)

  const columns: ExtendProColumns<User>[] = [
    {
      dataIndex: fields.username
    },
    {
      dataIndex: fields.userNo
    },
    {
      dataIndex: fields.realName
    },
    {
      dataIndex: fields.phone
    },
    {
      dataIndex: fields.sex
    },
    {
      dataIndex: fields.status,
      dict: 'sys_user_status'
    }
  ]

  // const apis: ApiMap = parseApi('/sys/user')
  const { list } = useApis<User>('/api/sys/user')

  const tableOpt: ProTableProps<User, PageParams> = {
    actionRef,
    request: list,
    rowKey: 'id',
    columns: columnsExtend(columns, labels)
  }

  return (
    <PageContainer>
      <ProTable<User, PageParams>
        { ...tableOpt }
      />
    </PageContainer>
  )
}

export default UserList
