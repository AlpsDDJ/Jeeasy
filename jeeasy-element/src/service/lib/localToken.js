// import Cookies from 'js-cookie'
import { siteTokenKey } from '@/settings'
import Cookies, { setCookie, getCookie } from '@/utlis/cookieUtil'

/**
 * 获取本地Token
 * @author LiQingSong
 */
export function getToken() {
  return getCookie(siteTokenKey)
}

/**
 * 设置存储Token
 * @author LiQingSong
 */
export function setToken(token) {
  return setCookie(siteTokenKey, token, 1)
}

/**
 * 移除本地Token
 * @author LiQingSong
 */
export function removeToken() {
  return Cookies.remove(siteTokenKey)
}
