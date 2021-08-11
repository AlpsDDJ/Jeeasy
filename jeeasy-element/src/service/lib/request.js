/**
 * 重置 axios
 * @author LiQingSong
 */
import store from '@/store'
import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import { ajaxHeadersTokenKey, serverLoginUrl, ajaxResponseNoVerifyUrl } from '@/settings'
import { isExternal } from '@/utlis/validate'
// import Qs from 'qs'

// 创建一个axios实例
const service = axios.create({
  baseURL: process.env.VUE_APP_APIHOST, // url = api url + request url
  withCredentials: true, // 当跨域请求时发送cookie
  timeout: 0 // 请求超时时间,5000(单位毫秒) / 0 不做限制
})

// 全局设置 - post请求头
// service.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';

// 请求拦截器 - 在发送请求之前
service.interceptors.request.use(
  config => {

    // 如果设置了cType 说明是自定义 添加 Content-Type类型 为自定义post 做铺垫
    if (config.cType) {
      config.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
    }

    // 请根据实际情况修改
    if (store.getters.token) {
      // store.getters.token 加载时已在 [store/user] 用 getToken()获取Token
      // 让每个请求携带令牌
      // ajaxHeadersTokenKey -> ['X-Token'] 是自定义头key
      config.headers[ajaxHeadersTokenKey] = store.getters.token
    }
    return config
  },
  error => {
    // 处理请求错误
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  /**
   * 通过自定义代码确定请求状态
   * 这只是一个例子
   * 您还可以通过HTTP状态码来判断状态
   */
  response => {
    const res = response.data
    const { code, success } = res

    // 如果自定义代码不是200，则判断为错误。
    if (!success) {
      // 获取替换后的字符串
      const reqUrl = response.config.url.split('?')[0].replace(response.config.baseURL, '')
      const noVerifyBool = ajaxResponseNoVerifyUrl.includes(reqUrl)

      switch (code) {
        case 401: // 未登陆

          if (!noVerifyBool) {
            MessageBox({
              title: '提示',
              showClose: false,
              closeOnClickModal: false,
              closeOnPressEscape: false,
              message: '当前用户登入信息已失效，请重新登入再操作',
              beforeClose: (action, instance, done) => {
                if (isExternal(serverLoginUrl)) {
                  window.location.href = serverLoginUrl
                } else {
                  window.location.reload()
                }
                console.log(action, instance, done)
              }
            })
          }

          break
        case 600:
          MessageBox({
            title: '提示',
            showClose: false,
            closeOnClickModal: false,
            closeOnPressEscape: false,
            message: '当前用户登入信息已失效，请重新登入再操作',
            beforeClose: (action, instance, done) => {
              if (isExternal(serverLoginUrl)) {
                window.location.href = serverLoginUrl
              } else {
                window.location.reload()
              }
              console.log(action, instance, done)
            }
          })
          break

        default:
          if (!noVerifyBool) {
            Message({
              message: res.msg || 'Error',
              type: 'error',
              duration: 5 * 1000
            })
          }
          break
      }

      // 返回错误 走 catch
      return Promise.reject(res)
      // return Promise.reject(new Error(res.msg || 'Error'));
      // return res;
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error) // for debug
    //console.log('err' + error.response.headers); // for debug
    //console.log('err' + error.response.data); // for debug
    //console.log('err' + error.response.status); // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

/**
 * 原 Axios 返回
 *
 * Method: get
 *     Request Headers
 *         无 - Content-Type
 *     Query String Parameters
 *         name: name
 *         age: age
 *
 * Method: post
 *     Request Headers
 *         Content-Type:application/json;charset=UTF-8
 *     Request Payload
 *         { name: name, age: age }
 *         Custom parameters
 *             { cType: true }  Mandatory Settings Content-Type:application/json;charset=UTF-8
 * ......
 */
export default service


export const apiType = {
  query: 'GET',
  add: 'POST',
  del: 'DELETE',
  edit: 'PUT'
}

export const parseApi = api => {
  if (typeof api === 'string') {
    return {
      list: `${api} ${apiType.query}`,
      info: `${api}/{id} ${apiType.query}`,
      del: `${api}/{id} ${apiType.del}`,
      delAll: `${api} ${apiType.del}`,
      edit: `${api} ${apiType.edit}`
    }
  } else {
    const { base, ...others } = api
    return {
      ...others,
      ...parseApi(base)
    }
  }
}

export function urlRender(tpl, dataObj) {
  return tpl.replace(/{\s*(.*?)\s*}/g, (context, objKey) => {
    console.log('objKey ===>>> ', objKey)
    const val = dataObj[objKey] || ''
    // 删除URL中匹配的参数
    delete dataObj[objKey]
    return val
  })
}

export const ajax = (url, params, config = {}) => {
  if (url.indexOf(' ') !== -1) {
    const [_url, method = apiType.query] = url.split(' ')
    const realUrl = urlRender(_url, params)




    store.dispatch('global/setLoading', { [url]: true })

    let finalConfig = {}

    if (method === apiType.query) {
      finalConfig = {
        params,
        method,
        ...config
      }
    } else {
      finalConfig = {
        data: params,
        method,
        ...config
      }
    }

    return new Promise((resolve, reject) => {
      service(realUrl, finalConfig).then(res => {
        resolve(res)
        store.dispatch('global/setLoading', { [url]: false })
      }).catch(err => {
        reject(err)
        store.dispatch('global/setLoading', { [url]: false })
      })
    })

  }

}
