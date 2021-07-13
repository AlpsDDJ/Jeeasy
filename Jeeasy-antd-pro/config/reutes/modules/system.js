export default {
  path: '/sys',
  name: '系统管理',
  icon: '',
  routes: [
    {
      path: './user',
      name: '用户管理',
      component: './modules/system/user/SysUserList'
    },
    {
      path: './role',
      name: '角色管理',
      component: './modules/system/role'
    },
    {
      path: './perms',
      name: '菜单权限',
      component: './modules/system/permission'
    },
    {
      path: './gen',
      name: '代码生成',
      component: './modules/generate/genTable/GenTableList'
    },
  ]
}