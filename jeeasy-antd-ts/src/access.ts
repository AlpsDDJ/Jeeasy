import allRouter from '../config/routes'

/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: { currentUser?: API.CurrentUser | undefined }) {
  const { currentUser = {} } = initialState || {}
  const { roleSet = [], permissionSet = [] } = currentUser
  const roles = {}
  const permissions = {}
  roleSet.forEach(role => {
    roles[`ROLE:${ role }`] = true
  })
  permissionSet.forEach(permission => {
    permissions[permission] = true
  })
  const base = {}

  function getBase(data: any[]) {
    data.forEach((ele: { access: any; routes: any }) => {
      if (ele.access) {
        base[ele.access] = false
        // Object.assign(base, { [ele.access]: false })
      }
      if (ele.routes) {
        getBase(ele.routes)
      }
    })
  }

  getBase(allRouter)
  return {
    ...base,
    ...roles,
    ...permissions
  }
}
