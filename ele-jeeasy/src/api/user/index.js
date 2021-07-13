import { get, post } from '_u/service'

export const getCurrLoginUser = () => get('/user/curr')

export const loginByUsername = (data) => post('/login', data)
