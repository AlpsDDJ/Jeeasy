import Storage from '@/common/utils/storageUtil'
import { localRefreshTokenKey, localTokenKey } from '@/common/setting'


export function saveToken(token: string, refreshToken: string) {
  Storage.save(localTokenKey, token)
  Storage.save(localRefreshTokenKey, refreshToken)
}

export function getToken(key?: string | undefined): string {
  return Storage.load(key || localTokenKey) || ''
}


export function getRefreshToken(): string {
  return Storage.load(localRefreshTokenKey) || ''
}

export function removeToken(key?: string): void {
  if (key) {
    Storage.remove(key)
  } else {
    Storage.remove(localTokenKey)
    Storage.remove(localRefreshTokenKey)
  }
}
