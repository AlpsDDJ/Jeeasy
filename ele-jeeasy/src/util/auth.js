// import Cookies from 'js-cookie'

const session = window.sessionStorage

const authToken = {
  // 当Token超时后采取何种策略
  // jumpAuthPage  每次请求时判断Token是否超时，若超时则跳转到授权页面
  // getNewToken  每次请求时判断Token是否超时，若超时则获取新Token (推荐)
  tokenTimeoutMethod: 'getNewToken',

  // 在Cookie中记录登录状态的key
  loginKey: 'isLogin',

  // Token是否超时
  hasToken: () => {
    return session.getItem('token')
  },

  // 当前是否是登录状态
  isLogin: () => {
    return session.getItem(this.loginKey)
  },

  // 设置Token
  setToken: token => {
    // TODO: 设置token，并填写有效期
    const maxAge = new Date(new Date().getTime() + 30 * 1000)
    session.setItem('token', token)
    session.setItem('expires', maxAge.toDateString())
  },

  // 设置登录状态
  setLoginStatus: () => {
    // TODO: 设置超时登录时间，在该时间范围内没有任何请求操作则自动删除
    console.log('登录状态最长时间更新')
    const maxAge = new Date(new Date().getTime() + 30 * 60 * 1000)
    session.setItem(this.loginKey, 'true')
    session.setItem('expires', maxAge.toDateString())
  },

  // 移除Token
  removeToken: () => {
    session.removeItem('token')
  },

  // 移除登录状态
  removeLoginStatus: () => {
    session.removeItem(this.loginKey)
  }
}

export default authToken
