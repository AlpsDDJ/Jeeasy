/**
 * Jeeasy Admin 路由
 * @author AlpsDDJ
 */
import LayoutIndex from '@/layout/Index'
// import AppMainLayout from '@/layout/components/AppMain'

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
      // redirect: '/component/editor/one',
      name: 'sys-user',
      meta: {
        title: '用户管理',
        icon: 'editor',
        roles: ['sys-user']
      }
    }
  ]
}

export default jeeasyAdminRouter
