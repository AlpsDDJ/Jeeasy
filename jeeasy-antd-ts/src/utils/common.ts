
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
    const {labels = {}, fields = {}} = target
    // eslint-disable-next-line no-param-reassign
    target.labels = {...labels, [key]: label}
    // eslint-disable-next-line no-param-reassign
    target.fields = {...fields, [key]: key}
  }
}

export class ModelField<T = any> implements Record<string, any>{
  public labels: FL<T> = {}
  public fields: FL<T> = {}
}
