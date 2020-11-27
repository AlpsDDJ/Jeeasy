import _ from 'lodash'
import { path2url } from "@/utils/utils";

export const reducerHandler = reducers => _.mapKeys(reducers, (v, k) => k.replace(/.+\//, ''))

/**
 * 将 action 转换成带参数的 action
 * @param {*} actionSet
 * @param {*} prefix
 */
export const mapAction = (actionSet, prefix = []) => {
  _.mapValues(actionSet, (v, k, obj) => {
    const action = prefix.concat(k)
    if (typeof v === 'object') {
      mapAction(v, action)
    } else {
      const fn = () => {
      }
      fn.toString = () => action.join('/')
      fn.KEY = k
      fn.VAL = v
      fn.URL = v ? path2url(v).url : ''
      fn.OK = action.concat('OK').join('_')
      fn.NOK = action.concat('NOK').join('_')
      // eslint-disable-next-line no-param-reassign
      obj[k] = fn
    }
  })
}