// export type DelFalg = 1 | 0
// export type EnableFlag = 1 | 0
// export enum Sex{
//   MAN = 1,
//   WOMAN = 0
// }
declare namespace PUB {
  type DelFalg = 1 | 0

  type EnableFlag = 1 | 0

  type Sex = 1 | 0

  type PageParams = {
    current?: number;
    pageSize?: number;
  }

  type R = {
    code?: number,
    success?: boolean,
    message?: string,
    timestamp?: number,
    data?: any
  }
}
