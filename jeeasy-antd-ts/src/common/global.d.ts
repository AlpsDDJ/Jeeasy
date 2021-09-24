declare type DelFalg = 1 | 0

declare type EnableFlag = 1 | 0

declare type Sex = 1 | 0

declare type PageParams = {
  current?: number;
  pageSize?: number;
}

/**
 * 属性&名称
 */
declare type FL<T = Record<string, any>> = {
  [P in keyof T | string]?: P | string
}

declare type R<T = any> = {
  code?: number,
  success?: boolean,
  message?: string,
  timestamp?: number,
  data?: T
}

declare type Dict = {
  dictCode: string,
  dictName: string
}

declare type TreeDict = Dict & {
  leaf: boolean
}

declare type EasyFormInputProps<T = any> = {
  value?: T,
  onChange?: (value: T) => void
}
