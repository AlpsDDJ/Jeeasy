// TODO 批量导入？
import system from "./modules/system";

// 将模块路由组合到路由数组
const moduleRoutes = [system]

export default [
  {
    path: '/login',
    component: '../layouts/UserLayout',
    routes: [
      {
        name: 'login',
        path: './',
        component: './base/login',
      },
    ],
  },
  {
    path: '/',
    component: '../layouts/SecurityLayout',
    routes: [
      {
        path: '/',
        component: '../layouts/BasicLayout',
        // authority: ['admin', 'user'],
        routes: [
          // 加载模块路由
          ...moduleRoutes
        ],
      },
      {
        component: './base/error/404',
      },
    ],
  },
  {
    component: './base/error/404',
  },
];
