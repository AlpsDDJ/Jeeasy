// import Cookies from 'js-cookie'
// // import axios from '@/util/service'
// import Auth from '@/util/auth'
// import { getToken, navlist } from '_a/comm'
// import { login } from '_a/user'
//
// const state = {
//   token: '',
//   navList: []
// }
//
// const mutations = {
//   setNavList: (state, data) => {
//     state.navList = data
//   },
//
//   setToken: (state, data) => {
//     if (data) {
//       Auth.setToken(data)
//       Auth.setLoginStatus()
//     } else {
//       Auth.removeToken()
//       Auth.removeLoginStatus()
//     }
//     state.token = data
//   }
// }
//
// const actions = {
//   // 邮箱登录
//   loginByEmail ({commit}, userInfo) {
//     return new Promise((resolve) => {
//       login(userInfo).then(res => {
//         if (res.success) {
//           commit('setToken', res.token)
//           commit('user/setName', res.name, {root: true})
//         }
//         resolve(res)
//       })
//     })
//   },
//
//   // 登出
//   logout ({commit}) {
//     return new Promise((resolve) => {
//       commit('setToken', '')
//       commit('user/setName', '', {root: true})
//       commit('tagNav/removeTagNav', '', {root: true})
//       resolve()
//     })
//   },
//
//   // 重新获取用户信息及Token
//   // TODO: 这里不需要提供用户名和密码，实际中请根据接口自行修改
//   relogin ({dispatch, commit, state}) {
//     return new Promise((resolve) => {
//       // 根据Token进行重新登录
//       const token = Cookies.get('token')
//       const userName = Cookies.get('userName')
//
//       // 重新登录时校验Token是否存在，若不存在则获取
//       if (!token) {
//         dispatch('getNewToken').then(() => {
//           commit('setToken', state.token)
//         })
//       } else {
//         commit('setToken', token)
//       }
//       // 刷新/关闭浏览器再进入时获取用户名
//       commit('user/setName', decodeURIComponent(userName), {root: true})
//       resolve()
//     })
//   },
//
//   // 获取新Token
//   getNewToken ({commit, state}) {
//     return new Promise((resolve) => {
//       getToken({token: state.token}).then(data => {
//         commit('setToken', data)
//         resolve()
//       })
//     })
//   },
//
//   // 获取该用户的菜单列表
//   getNavList ({commit}) {
//     return new Promise((resolve) => {
//       navlist().then((res) => {
//         commit('setNavList', res)
//         resolve(res)
//       })
//     })
//   },
//
//   // 将菜单列表扁平化形成权限列表
//   getPermissionList ({state}) {
//     return new Promise((resolve) => {
//       let permissionList = []
//
//       // 将菜单数据扁平化为一级
//       function flatNavList (arr) {
//         for (let v of arr) {
//           if (v.child && v.child.length) {
//             flatNavList(v.child)
//           } else {
//             permissionList.push(v)
//           }
//         }
//       }
//
//       flatNavList(state.navList)
//       resolve(permissionList)
//     })
//   }
// }
//
// export default {
//   namespaced: true,
//   state,
//   mutations,
//   actions
// }
