import { request } from 'umi'
import { RequestData } from '@ant-design/pro-table/lib/typing'

type RequestMethod = 'GET' | 'POST' | 'PUT' | 'DELETE'

export const apiType: Record<string, RequestMethod> = {
  query: 'GET',
  add: 'POST',
  del: 'DELETE',
  edit: 'PUT'
}

type Api = string | Record<string, string>

export type ApiPathMap = Record<string, string> & {
  list?: string,
  info?: string,
  del?: string,
  delAll?: string,
  add?: string,
  edit?: string,
}

export const parseApi = (api: Api): ApiPathMap => {
  if (typeof api === 'string') {
    return {
      list: `${ api } ${ apiType.query }`,
      info: `${ api }/{id} ${ apiType.query }`,
      del: `${ api }/{id} ${ apiType.del }`,
      delAll: `${ api } ${ apiType.del }`,
      add: `${ api } ${ apiType.add }`,
      edit: `${ api } ${ apiType.edit }`
    }
  }
  const { base, ...others } = api
  return {
    ...others,
    ...parseApi(base)
  }

}


type SendOptions = {
  url: string,
  data: any,
  method: RequestMethod
}

export function getSendOpt(url: string, dataObj: any): SendOptions {
  const [_url, method = apiType.query] = url.split(' ')

  const data = { ...dataObj }
  const realUrl = _url.replace(/{\s*(.*?)\s*}/g, (context, objKey) => {
    const val = dataObj[objKey] || ''
    // 删除URL中匹配的参数
    delete data[objKey]
    return val
  })
  // @ts-ignore
  return { url: realUrl, data, method }
}

export const send = async (url: string, params?: any, options?: any) => {

  const { url: realUrl, data, method } = getSendOpt(url, params)

  // const finalOptions = {
  //   data,
  //   params: method === apiType.query ? data: undefined,
  //   method,
  //   ...options
  // }

  let finalOptions = {}
  if (method === apiType.query) {
    finalOptions = {
      params: data,
      method,
      ...options
    }
  } else {
    finalOptions = {
      data,
      method,
      ...options
    }
  }

  return await request(realUrl, finalOptions)
}

type SendRequest<T> = () => Promise<T>

export type ApiMap<T> = Record<string, SendRequest<T>> & {
  list?: SendRequest<Partial<RequestData<T>>>,
  info?: SendRequest<T>,
  del?: SendRequest<any>,
  delAll?: SendRequest<any>,
  add?: SendRequest<any>,
  edit?: SendRequest<any>,
}

export const useApis = <T>(api: Api): ApiMap<T> => {
  const apiPathMap = parseApi(api)
  const apiMap: ApiMap<T> = {}
  Object.keys(apiPathMap).forEach(key => {
    apiMap[key] = async (data?: any, options?: any) => {
      const path = apiPathMap[key]
      if (key === 'list') {
        const resp = await send(path, data, options)
        const { success, data: result, message } = resp
        return new Promise<Partial<RequestData<T>>>((resolve, reject) => {
          if (success) {
            const { records, total, size: pageSize } = result
            resolve({
              data: records,
              success,
              pageSize,
              total
            })
          } else {
            reject(new Error(message))
          }
        })
      }

      return send(path, data, options)
    }
  })
  return apiMap
}
