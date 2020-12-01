import { parse } from 'querystring'
import _ from 'lodash'
/* eslint no-useless-escape:0 import/prefer-default-export:0 */

const reg = /(((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+(?::\d+)?|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$/;
export const isUrl = (path) => reg.test(path);
export const isAntDesignPro = () => {
  if (ANT_DESIGN_PRO_ONLY_DO_NOT_USE_IN_YOUR_PRODUCTION === 'site') {
    return true
  }

  return window.location.hostname === 'preview.pro.ant.design'
} // 给官方演示站点用，用于关闭真实开发环境不需要使用的特性

export const isAntDesignProOrDev = () => {
  const { NODE_ENV } = process.env

  if (NODE_ENV === 'development') {
    return true
  }

  return isAntDesignPro()
}
export const getPageQuery = () => parse(window.location.href.split('?')[1])


/**
 * 把 url.yml 里的路径，根据接口映射规则转换成完整请求路径
 * @param {String} path
 */
export const path2url = (path = '', params = {}) => {
  const [url, method = 'GET', type = 'json'] = path.split('|')
  const pathReg = /{(\w+)}/g
  if (pathReg.test(url)) {
    return {
      method, type,
      url: url.replace(pathReg, (match, $1) => {
        return params[$1]
      })
    }
  }
  return { url, method, type }
}

/**
 * 动态引入文件夹下多个文件
 * @context {Context} Webpack Require Context
 * @normalize {Function} 把context的key转换为想要的格式,返回falsy值则表示忽略该文件
 * @return {Object} 包含多个文件的引用的对象
 */
export const requireAll = (
  context,
  normalize = v => v.replace(/\.\/([\w./]+)\.\w+$/, '$1').replace(/\//g, '.'),
) => context.keys().reduce((obj, key) => {
  const normalizedKey = normalize(key)
  return _.has(obj, normalizedKey) ? obj : _.set(obj, normalizedKey, context(key).default || context(key))
}, {})