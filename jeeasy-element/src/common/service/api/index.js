import { ajax } from '@/common/utlis/request'

const apiUrl = {
  getDictsByCode: ''
}

export function getDictsByCode (code) {
  return ajax(apiUrl.getDictsByCode, {code})
}