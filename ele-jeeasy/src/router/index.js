import Vue from 'vue'
import Router from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
// import store from '@/store'
// import Auth from '@/util/auth'
import staticRoute from './staticRoute'

Vue.use(Router)

// const permissionList = []
// const initRoute = router => {
//   return new Promise(resolve => {
//     if (permissionList.length === 0) {
//       console.log('获取权限数据')
//       store.dispatch('auth/getNavList').then(() => {
//         store.dispatch('auth/getPermissionList').then(res => {
//           console.log('权限列表生成完毕')
//           permissionList.push(res)
//           res.forEach(v => {
//             const routeItem = router.match(v.path)
//             if (routeItem) {
//               routeItem.meta.permission = v.permission || []
//               routeItem.meta.name = v.name
//             }
//           })
//         })
//       })
//     } else {
//       console.log('已有权限数据')
//       resolve()
//     }
//   })
// }

NProgress.configure({ showSpinner: false })

const router = new Router({
  mode: 'hash',
  routes: staticRoute
})

// 免登录白名单页面
// const whiteList = [
//   '/login',
//   '/register',
//   '/notice',
//   '/maintenance'
// ]

// 路由跳转前验证
router.beforeEach((to, from, next) => {
  // 开启进度条
  NProgress.start()
  next()

  // 判断用户登录状态
  // if (Auth.isLogin) {
  //   // 如果当前处于登录状态，并且跳转地址为login，则自动跳回系统首页
  //   // 这种情况出现在手动修改地址栏地址时
  //   if (to.path === '/login') {
  //     next({ path: '/home', replace: true })
  //   } else if (to.path.indexOf('/error') >= 0) {
  //     // 防止因重定向到error页面造成beforeEach死循环
  //     next()
  //   } else {
  //     initRoute(router).then(() => {
  //       let isPermission = false
  //       console.log('进入权限判断')
  //       permissionList.forEach((v) => {
  //         // 判断跳转的页面是否在权限列表中
  //         if (v.path === to.fullPath) {
  //           isPermission = true
  //         }
  //       })
  //       // 没有权限时跳转到401页面
  //       if (!isPermission) {
  //         next({ path: '/error/401', replace: true })
  //       } else {
  //         next()
  //       }
  //     })
  //   }
  // } else {
  //   // 如果是免登陆的页面则直接进入，否则跳转到登录页面
  //   if (whiteList.indexOf(to.path) >= 0) {
  //     console.log('该页面无需登录即可访问')
  //     next()
  //   } else {
  //     console.warn('当前未处于登录状态，请登录')
  //     next({ path: '/login', replace: true })
  //     // 如果store中有token，同时Cookie中没有登录状态
  //     if (store.state.user.token) {
  //       console.info('登录超时，请重新登录')
  //       // Message({
  //       //   message: '登录超时，请重新登录'
  //       // })
  //     }
  //     NProgress.done()
  //   }
  // }
})

router.afterEach(() => {
  // 结束进度条
  NProgress.done()
})

export default router
