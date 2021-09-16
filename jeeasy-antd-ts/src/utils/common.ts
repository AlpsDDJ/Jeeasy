
export type DelFalg = 1 | 0

export type EnableFlag = 1 | 0

export type Sex = 1 | 0

export type PageParams = {
  current?: number;
  pageSize?: number;
}

/**
 * 属性&名称
 */
export type FL<T> = {
  [P in keyof T]?: P | string
}

export type R = {
  code?: number,
  success?: boolean,
  message?: string,
  timestamp?: number,
  data?: any
}
