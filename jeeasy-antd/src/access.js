/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState) {
  const { currentUser = {} } = initialState || {}
  const { roleSet = [], permissionSet = [] } = currentUser
  const roles = {}
  const permissions = {}
  roleSet.forEach(role => {
    roles[`ROLE:${role}`] = true
  })
  permissionSet.forEach(permission => {
    permissions[permission] = true
  })
  return {
    ...roles,
    ...permissions,
    // canAdmin: roleSet.includes('admin'),
  }
}
