const state = {
  loading: {},
  navTabs: {}
}

const mutations = {
  SET_LOADING: (state, Bval) => {
    state.loading = {
      ...state.loading,
      ...Bval
    }
  }
}

const actions = {
  setLoading({ commit }, payload) {
    commit('SET_LOADING', payload)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
