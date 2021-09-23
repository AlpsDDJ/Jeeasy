import { ExtendProColumns } from '@/utils/EasyTable'

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

export type R<T = any> = {
  code?: number,
  success?: boolean,
  message?: string,
  timestamp?: number,
  data?: T
}


export function Field(label: string, column: ExtendProColumns | boolean = false) {
  return function (target: any, key: string) {
    if (!Reflect.has(target, '$$')) {
      Reflect.defineProperty(target, '$$', {
        value: {
          labels: {},
          fields: {},
          columnMap: {},
        },
        configurable: false,
        writable: true,
        enumerable: false
      })
    }
    const { $$ } = target
    const { labels, fields, columnMap } = $$

    $$.labels = { ...labels, [key]: label }
    $$.fields = { ...fields, [key]: key }

    if(column){
      const defaultColumn = { dataIndex: key, title: label }
      if(column === true) {
        $$.columnMap = { ...columnMap, [key]: defaultColumn }
      }else{
        $$.columnMap = { ...columnMap, [key]: { ...defaultColumn, ...column } }
      }
    }

    Reflect.set(target, '$$', $$)
  }
}

export function Data(name?: string, baseApi?: string, access?: string) {
  return (target: any) => {
    // save a reference to the original constructor
    const original = target

    // a utility function to generate instances of a class
    function construct(constructor: any, args: any[]) {
      const C: any = function (this: any) {
        return constructor.apply(this, args)
      }
      C.prototype = constructor.prototype
      const obj = new C()
      Reflect.deleteProperty(obj, '$$')
      return obj
    }

    // the new constructor behaviour
    const base: any = (...args: any[]) => construct(original, args)

    // copy prototype so intanceof operator still works
    base.prototype = original.prototype
    base.prototype.$$.name = name
    base.prototype.$$.baseApi = baseApi
    base.prototype.$$.access = access

    // return new constructor (will override original)
    return base
  }
}

type DataOption<T> = {
  labels: FL<T>,
  fields: FL<T>,
  columnMap: Record<string, ExtendProColumns<T>>,
  name: string,
  baseApi: string,
  access: string,
}

export class BaseVo<T = any> {
  $$: DataOption<T> = {
    access: '', baseApi: '', fields: {}, labels: {}, name: '', columnMap: {}
  }
}
