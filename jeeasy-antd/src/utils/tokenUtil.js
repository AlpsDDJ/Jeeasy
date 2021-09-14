import cookie from 'react-cookies'
import { localRefreshTokenKey, localTokenKey } from '@/utils/commotSense'

export function saveToken(token = '', refreshToken = ''){
  cookie.save(localTokenKey, token)
  cookie.save(localRefreshTokenKey, refreshToken)
}

export function getToken(key = localTokenKey){
  return cookie.load(key) || ''
}


export function getRefreshToken(){
  return cookie.load(localRefreshTokenKey) || ''
}

export function removeToken(key){
  if(key){
    cookie.remove(key)
  } else {
    cookie.remove(localTokenKey)
    cookie.remove(localRefreshTokenKey)
  }
}
