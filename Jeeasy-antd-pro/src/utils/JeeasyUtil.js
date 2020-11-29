
export const TOKEN_KEY = 'JEEASY-ACCESS-TOKEN'
export const REFRESH_TOKEN_KEY = 'JEEASY-ACCESS-REFRESH-TOKEN'
export const responseSuccessCode = 200

export const getToken = () => localStorage.getItem(TOKEN_KEY) || ''

export const setToken = (token, refreshToken) => {
  localStorage.setItem(TOKEN_KEY, token)
  if(refreshToken) {
    localStorage.setItem(REFRESH_TOKEN_KEY, refreshToken)
  }
}
