import React  from 'react'
// import { PageContainer } from '@ant-design/pro-layout'
// import ProTable from '@ant-design/pro-table'
import container from '@/utils/container'
import ListPage from '@/components/Jeeasy/JListPage'
import ACTIONS, { namespace } from './models/list/actions'
import SysUser from './components/SysUser'

const { fields } = SysUser

// @container(false, namespace)
const SysUserList = (props) => {

  const {
    dispatch,
    result = {},
  } = props

  // const loadDataList = async (search = {}) => (
  //   props.dispatch(ACTIONS.LIST, search).then(({ result }) => (
  //     Promise.resolve({
  //       data: result.records,
  //       success: true,
  //     })
  //   )))

  const columnsConf = {
    [fields.username]: {
      valueType: 'view',
      // render: (text, record, index, action) => {
      //   console.log(action)
      //   return (<a>{text}</a>)
      // }
    }
  }


  const listOpt = {
    dispatch,
    ACTIONS,
    listHidden: [fields.id, fields.password, fields.salt, fields.remark],
    searchColumns: [fields.username, fields.phone, fields.realName, fields.status],
    columnsConf,
    ...SysUser,
    ...result,
  }

  return (
    <ListPage
      {...listOpt}
    />
  )
}

export default container(false, namespace)(SysUserList)