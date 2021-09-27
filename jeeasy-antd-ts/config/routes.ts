export default [
  {
    path: 'login',
    layout: false,
    component: './Login',
  },
  { path: 'welcome', name: '欢迎', icon: 'smile', component: './Welcome' },
  // {
  //   path: '/admin',
  //   name: 'Admin',
  //   icon: 'crown',
  //   access: 'admin',
  //   component: './Admin',
  //   routes: [
  //     {path: '/admin/sub', component: './Welcome', name: 'Sub'}
  //   ]
  // },
  {
    path: 'sys',
    name: '系统管理',
    access: 'ROLE:admin',
    icon: 'setting',
    routes: [
      { path: 'user', access: 'ROLE:admin', name: '用户管理', icon: 'user', component: './sys/user' },
      { path: 'role', access: 'ROLE:admin', name: '角色管理', icon: 'team', component: './sys/role' },
      { path: 'permission', access: 'ROLE:admin', name: '权限菜单', icon: 'apartment', component: './sys/permission' },
      { component: './404' },
    ],
  },
  { name: '查询表格', icon: 'table', path: '/list', component: './TableList' },
  { path: '/', redirect: '/welcome' },
  { component: './404' },
];
