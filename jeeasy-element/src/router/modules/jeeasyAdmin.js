/**
 * Jeeasy Admin 路由
 * @author AlpsDDJ
 */
import LayoutIndex from '@/components/layout/Index'

const jeeasyAdminRouter = {
  path: '/sys',
  component: LayoutIndex,
  // redirect: '/component/editor/one',
  name: 'sys',
  meta: {
    title: '系统管理',
    icon: 'components',
    roles: ['sys']
  },
  children: [
    {
      path: 'user',
      component: () => import('@/views/jeeasy/sys/user/list'),
      name: 'sys-user',
      meta: {
        // keepAlive: true,
        title: '用户管理',
        icon: 'editor',
        roles: ['sys-user']
      }
    },
    {
      path: 'role',
      component: () => import('@/views/jeeasy/sys/role/list'),
      name: 'sys-role',
      meta: {
        title: '角色管理',
        icon: 'editor',
        roles: ['sys-role']
      }
    },
    {
      path: 'depart',
      component: () => import('@/views/jeeasy/sys/depart/list'),
      name: 'sys-depart',
      meta: {
        title: '部门管理',
        icon: 'editor',
        roles: ['sys-depart']
      }
    },
    {
      path: 'doc',
      component: () => import('@/views/jeeasy/sys/doc'),
      name: 'sys-doc',
      meta: {
        title: '接口文档',
        icon: 'editor',
        roles: ['sys-doc']
      }
    }
  ]
}

export default jeeasyAdminRouter
