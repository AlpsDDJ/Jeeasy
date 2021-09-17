import { request } from 'umi'
import type { RequestData } from '@ant-design/pro-table/lib/typing'
import type { R } from '@/utils/common'

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

export const send = async <T = R>(url: string, params?: any, options?: any) => {

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

  return await request<T>(realUrl, finalOptions)
}

type SendRequest<T> = (data?: any, options?: any) => Promise<T>

export type ApiMap<T> = Record<string, SendRequest<T>> & {
  list: SendRequest<Partial<RequestData<T>>>,
  info: (id: string, data?: any) => Promise<T>,
  del: (id: string, data?: any) => Promise<R>,
  delAll: (ids: string[], data?: any) => Promise<R>,
  add: (entity: T & any) => Promise<R>,
  edit: (entity: T & any) => Promise<R>,
}

export const useApis = <T>(api: Api): ApiMap<T> => {
  const apiPathMap = parseApi(api)
  const apiMap: any = {}
  Object.keys(apiPathMap).forEach(key => {
    let _
    const path = apiPathMap[key]


    if (key === 'list') {
      apiMap[key] = async (params: T & {pageSize: number, current: number}, sort: any, filter: any) => {
        const data = {
          ...params,
          sort,
          filter
        }
        console.log('data --------------------------------- ', data)
        const resp = await send(path, data)
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
    } else {
      apiMap[key] = async (data?: any, options?: any) => {

        _ = data

        if (key === 'info' || key === 'del' && typeof data === 'string') {
          _ = {id: data}
        }
        return send(path, _, options)
    }



    }
  })
  return apiMap
}
