import React, { useRef } from 'react'
import { PageContainer } from '@ant-design/pro-layout'
import ProTable from '@ant-design/pro-table'
import container from '@/utils/container'
import ACTIONS, { namespace } from './models/list/actions'
import SysUser from './components/SysUser'

const {fields, listColumns} = SysUser

// @container(false, namespace)
const SysUserList = (props) =>{

  const loadDataList = (search = {}) => {
    return props.dispatch(ACTIONS.LIST, search)
  }
  const {
    // $loading,
    result: {
      // records,
      current,
      total,
      pageSize,
    },
  } = props

  // const columns = Object.keys(fields).filter(key => listHidden.indexOf(key) === -1).map(key => ({
  //   title: labels[key] || '',
  //   dataIndex: fields[key]
  // }))

  const actionRef = useRef();

  return (
    <PageContainer>
      <ProTable
        rowKey={fields.id}
        // dataSource={records}
        actionRef={actionRef}
        columns={listColumns}
        request={async (params) => {
          return loadDataList(params).then(({ result = {} }) => {
            return Promise.resolve({
                data: result.records,
                success: true,
            })
          })
        }}
        pagination={{
          current,
          pageSize,
          total,
        }}
      />
    </PageContainer>
  )
}

export default container(false, namespace)(SysUserList);