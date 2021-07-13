// import Cookies from 'js-cookie'
import { loginByUsername } from '_a/user'

const state = {
  userInfo: {},
  token: ''
}

const mutations = {
  setUserInfo: (state, data) => {
    state.name = data
  },
  setToken: (state, token) => {
    state.token = token
  }
}

const actions = {
  login ({ commit }, loginForm) {
    loginByUsername(loginForm).then(res => {
      commit('setToken', res)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
