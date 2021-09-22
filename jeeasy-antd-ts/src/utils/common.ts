/* eslint-disable no-param-reassign */
export declare type DelFalg = 1 | 0

export declare type EnableFlag = 1 | 0

export declare type Sex = 1 | 0

export declare type PageParams = {
  current?: number;
  pageSize?: number;
}

/**
 * 属性&名称
 */
export declare type FL<T = Record<string, any>> = {
  [P in keyof T | string]?: P | string
}

export declare type R<T = any> = {
  code?: number,
  success?: boolean,
  message?: string,
  timestamp?: number,
  data?: T
}


export function Label(label: string) {
  return function (target: any, key: string) {
    if(!target.$$) {
      target.$$ = {}
    }
    const { labels = {}, fields = {} } = target.$$

    target.$$.labels = { ...labels, [key]: label }
    target.$$.fields = { ...fields, [key]: key }
    // 替换属性，先删除原先的属性，再重新定义属性
    // @ts-ignore
    // eslint-disable-next-line @typescript-eslint/no-invalid-this
    // delete this['labels']
    // @ts-ignore
    // eslint-disable-next-line @typescript-eslint/no-invalid-this
    // delete this['fields']
  }
}

export function Data(name: string, baseApi: string, access: string) {
  return function (target: any) {
    target.prototype.$$.name = name
    target.prototype.$$.baseApi = baseApi
    target.prototype.$$.access = access
  }
}

export class BaseModel<T = any> {
  readonly $$: {
    labels: FL<T>,
    fields: FL<T>,
    name: string,
    baseApi: string,
    access: string,
  } = {
    access: '', baseApi: '', fields: {}, labels: {}, name: ''
  }
  // constructor() {
  //   this.$$ = {}
  //   console.log(this)
  // }

  // labels: FL<T> = {}
  // fields: FL<T> = {}
  // name: string = ''
  // baseApi: string = ''
  // access: string = ''

}
