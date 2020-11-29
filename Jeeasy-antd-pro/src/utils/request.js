/**
 * request 网络请求工具
 * 更详细的 api 文档: https://github.com/umijs/umi-request
 */
import { extend } from 'umi-request';
import { notification } from 'antd';
// import { TOKEN_KEY } from "@/utils/Const";
import { path2url } from "@/utils/utils";
import {getToken, TOKEN_KEY} from "@/utils/JeeasyUtil";

// const codeMessage = {
//   200: '服务器成功返回请求的数据。',
//   201: '新建或修改数据成功。',
//   202: '一个请求已经进入后台排队（异步任务）。',
//   204: '删除数据成功。',
//   400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
//   401: '用户没有权限（令牌、用户名、密码错误）。',
//   403: '用户得到授权，但是访问是被禁止的。',
//   404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
//   406: '请求的格式不可得。',
//   410: '请求的资源被永久删除，且不会再得到的。',
//   422: '当创建一个对象时，发生一个验证错误。',
//   500: '服务器发生错误，请检查服务器。',
//   502: '网关错误。',
//   503: '服务不可用，服务器暂时过载或维护。',
//   504: '网关超时。',
// };
/**
 * 异常处理程序
 */

const errorHandler = (error) => {
  const { data, response } = error

  if (data && data.message) {
    const errorText = data.message
    const { status, url } = response
    notification.error({
      message: `${status}: ${url}`,
      description: errorText,
    })
  } else if (!response) {
    notification.error({
      description: '您的网络发生异常，无法连接服务器',
      message: '网络异常',
    })
  }

  return response
};
/**
 * 配置request请求时的默认参数
 */

const request = extend({
  errorHandler,
  prefix: '/api',
  // 默认错误处理
  credentials: 'include', // 默认请求是否带上cookie
});


const contentTypeMap = {
  'json': 'application/json',
  'form': 'multipart/form-data',
}

/**
 * 拼接URL规则
 * url = path [|method][|contentType]
 * 默认: path|GET|json
 * 例:  /api/getOne|GET|json
 */
request.interceptors.request.use((api, { payload, ...options }) => {
  const token = getToken()
  const { url, method, type } = path2url(api)

  const headers = {
    [TOKEN_KEY]: token,
    'Content-Type': contentTypeMap[type]
  }
  return {
    url,
    options: {
      ...options,
      method,
      params: method === 'GET' ? payload : undefined,
      data: method !== 'GET' ? payload : undefined,
      headers,
    }
  }
})

request.interceptors.response.use(((response) => {
  return response
}))

export default request

/**
 * 查询方法
 * @param url
 * @param params
 * @returns {Promise<any>}
 */
export const query = (url, params) => {
  return request(url, {
    method: 'GET',
    params
  })
}


/**
 * 更新方法
 * @param url
 * @param data
 * @returns {Promise<any>}
 */
export const update = (url, data) => {
  return request(url, {
    method: 'PUT',
    data
  })
}

/**
 * 新增方法
 * @param url
 * @param data
 * @returns {Promise<any>}
 */
export const insert = (url, data) => {
  return request(url, {
    method: 'POST',
    data
  })
}

/**
 * 删除方法
 * @param url
 * @param params
 * @returns {Promise<any>}
 */
export const del = (url, params) => {
  return request(url, {
    method: 'DELETE',
    params
  })
}