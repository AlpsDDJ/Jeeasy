import Cookies from 'js-cookie'

export function setCookie(key, value, expiresOrConfig) {
  if (typeof expiresOrConfig === 'object') {
    Cookies.set(key, value, expiresOrConfig)
  } else if (typeof expiresOrConfig === 'number') {
    Cookies.set(key, value, { expires: expiresOrConfig })
  } else {
    Cookies.set(key, value)
  }
}

export function getCookie(key, getJson = false) {
  if (getJson) {
    return Cookies.getJSON(key)
  } else {
    return Cookies.get(key)
  }
}


export default Cookies
