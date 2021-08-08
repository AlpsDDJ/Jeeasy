/**
 * 用户 Store
 * @author LiQingSong
 */
import { login, logout, getInfo } from '@/service/user'
import { getToken, setToken, removeToken } from '@/service/lib/localToken'
import { resetRouter } from '@/router'
import { isExternal } from '@/utlis/validate'
import { serverLoginUrl, serverLogoutUrl, siteLoginRouter } from '@/settings'

const state = {
    token: getToken(),
    username: '',
    name: '',
    avatar: '',
    msgtotal: 0,
    roles: []
}
const mutations = {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_USERNAME: (state, username) => {
      state.username = username
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_MSGTOTAL: (state, msgtotal) => {
      state.msgtotal = msgtotal
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    }
}
const actions = {
    // 用户登录
    login({ commit }, userInfo) {
        const { username, password } = userInfo
        return new Promise((resolve, reject) => {
          login(serverLoginUrl, { username: username, password: password }).then(response => {
            const { result } = response
            const { token } = result
            commit('SET_TOKEN', token)
            setToken(token)
            resolve(result)
          }).catch(error => {
            reject(error)
          })
        })
    },

    // 获取用户信息
    getInfo({ commit, state }) {
        return new Promise((resolve, reject) => {
            getInfo(state.token).then(response => {
                const { result: data } = response

                if (!data) {
                  reject('当前用户登入信息已失效，请重新登入再操作.')
                }

                const { roles = [], username, realName, permissions = [], avatar, msgtotal } = data

                // roles must be a non-empty array
                if (!roles || roles.length <= 0) {
                    reject('用户没有角色权限!')
                }

                // roles.map(role => `ROLE:${role}`)
                data.roles = [...roles.map(role => `ROLE:${role}`), ...permissions]

                commit('SET_ROLES', data.roles)
                commit('SET_NAME', realName)
                commit('SET_USERNAME', username)
                commit('SET_AVATAR', avatar)
                commit('SET_MSGTOTAL', msgtotal)
                resolve(data)
            }).catch(error => {
                reject(error.msg || 'Error')
                // console.log(error);
            })
        })
    },

    // 用户退出
    logout({ commit }) {
        if (isExternal(serverLogoutUrl)) {
            return new Promise((resolve) => {
              commit('SET_TOKEN', '')
              commit('SET_ROLES', [])
              window.location.href = serverLogoutUrl
              resolve({isExternal: true, siteLoginRouter: siteLoginRouter})
            })
        } else {
            return new Promise((resolve, reject) => {
                logout(serverLogoutUrl).then(() => {
                    commit('SET_TOKEN', '')
                    commit('SET_ROLES', [])
                    removeToken()
                    resetRouter()
                    resolve({isExternal: false, siteLoginRouter: siteLoginRouter})
                }).catch(error => {
                    reject(error)
                })
            })
        }
    },
    // 删除Token
    resetToken({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        removeToken()
        resolve()
      })
    }


}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
