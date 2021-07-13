const staticRoute = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    component: () => import('_v/layout'),
    children: [
      {
        path: '/',
        component: () => import(/* webpackChunkName: 'home' */ '_v/home')
      }
    ]
  },
  {
    path: '/login',
    component: () => import(/* webpackChunkName: 'index' */ '_v/login')
  },
  {
    path: '/error',
    component: () => import(/* webpackChunkName: 'error' */ '_v/error'),
    children: [
      {
        path: '401',
        component: () => import(/* webpackChunkName: 'error' */ '_v/error/401')
      },
      {
        path: '403',
        component: () => import(/* webpackChunkName: 'error' */ '_v/error/403')
      },
      {
        path: '404',
        component: () => import(/* webpackChunkName: 'error' */ '_v/error/404')
      },
      {
        path: '500',
        component: () => import(/* webpackChunkName: 'error' */ '_v/error/500')
      }
    ]
  },
  {
    path: '*',
    redirect: '/error/404'
  }
]

export default staticRoute
