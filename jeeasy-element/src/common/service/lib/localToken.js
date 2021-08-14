// import Cookies from 'js-cookie'
import { siteTokenKey } from '@/settings'
import Cookies, { setCookie, getCookie } from '@/common/utlis/cookieUtil'

const refreshSiteTokenKey = `refresh_${siteTokenKey}`

/**
 * 获取本地Token
 * @author LiQingSong
 */
export function getToken() {
  return getCookie(siteTokenKey)
}

/**
 * 获取本地Token
 * @author LiQingSong
 */
export function getRefreshToken() {
  return getCookie(refreshSiteTokenKey)
}

/**
 * 设置存储Token
 * @author LiQingSong
 */
export function setToken(token) {
  setCookie(siteTokenKey, token)
}

/**
 * 设置存储Token
 * @author LiQingSong
 */
export function setRefreshToken(refreshToken) {
  setCookie(refreshSiteTokenKey, refreshToken, 1)
}

/**
 * 移除本地Token
 * @author LiQingSong
 */
export function removeToken() {
  Cookies.remove(refreshSiteTokenKey)
  Cookies.remove(siteTokenKey)
}
