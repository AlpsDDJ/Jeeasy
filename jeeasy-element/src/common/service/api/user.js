import request from '@/common/utlis/request'

/**
 * 用户 - 登录
 * @param {String} url 请求地址
 * @param {Object} data 请求参数
 * @author LiQingSong
 */
export function login(url, data) {
    return request({
      url: url,
      method: 'post',
      data
    })
}

/**
 * 用户 - 获取用户信息和权限
 * @author LiQingSong
 */
export function getInfo(url) {
    return request({
      url,
      method: 'get'
    })
}

/**
 * 用户 - 退出
 * @author LiQingSong
 */
export function logout(url) {
    return request({
      url: url,
      method: 'post'
    })
}
