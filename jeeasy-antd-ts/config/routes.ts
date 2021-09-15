export default [
  {
    path: '/login',
    name: '登录',
    layout: false,
    component: './Login',
  },
  { path: '/welcome', name: '欢迎', icon: 'smile', component: './Welcome' },
  {
    path: '/sys',
    name: '系统管理',
    icon: 'crown',
    access: 'ROLE:admin',
    routes: [
      { path: '/sys/user', name: '用户管理', icon: 'smile', component: './sys/User' },
      { path: '/sys/role', name: '角色管理', icon: 'smile', component: './TableList' },
      { component: './404' },
    ],
  },
  { name: '查询表格', icon: 'table', path: '/list', component: './TableList' },
  { path: '/', redirect: '/welcome' },
  { component: './404' },
];
