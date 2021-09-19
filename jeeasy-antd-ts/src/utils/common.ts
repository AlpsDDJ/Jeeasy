
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
export declare type FL<T> = {
  [P in keyof T | string]?: P | string
}

export declare type R<T = any> = {
  code?: number,
  success?: boolean,
  message?: string,
  timestamp?: number,
  data?: T
}
