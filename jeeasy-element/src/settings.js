/**
 * 系统配置
 * @author LiQingSong
 */

/**
 * 站点名称
 */
 export const siteTitle = 'Jeeasy-Element'

 /**
 * 是否固定右侧头部(默认配置)
 */
export const siteFiexdHeader = true

 /**
 * 是否启用顶部导航(默认配置)
 */
export const siteTopNavEnable = false


 /**
 * 是否显示侧边栏LOGO(默认配置)
 */
export const siteSidebarLogo = true

 /**
 * 站点本地存储TokenKey
 */
 export const siteTokenKey = "jeeasy_token"

/**
 * Ajax请求头TokenKey
 */
export const ajaxHeadersTokenKey = "JEEASY-ACCESS-TOKEN"

/**
 * 站点登录路由地址
 */
 export const siteLoginRouter = "/login"

/**
 * 服务端登录,请求ajax地址/跳转地址
 * 外链如单点登录：serverLoginUrl = process.env.VUE_APP_APIHOST + '/cas';
 * 内部：serverLoginUrl = '/login';
 */
export const serverLoginUrl = '/auth/login'

/**
 * 获取用户信息
 * @type {string}
 */
export const getCurrentUserUrl = '/currentUser'

/**
 * ajax请求 - 返回数据 - 不添加前置验证的 URL
 */
export const ajaxResponseNoVerifyUrl = [
    serverLoginUrl, // 用户登录
    getCurrentUserUrl // 获取用户信息
]

/**
 * 服务端退出,请求ajax地址/跳转地址
 * 若是单点登录：serverLogoutUrl = process.env.VUE_APP_APIHOST + '/logout'; 退出方法函数直接 window.location.href = serverLogoutUrl;
 * 若是内部ajax：serverLoginUrl = '/logout';
 */
export const serverLogoutUrl = '/auth/logout'
