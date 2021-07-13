import { get, post } from '_u/service'

export const getToken = params => get('/getToken', params)

export const navlist = data => post('/user/navlist', data)
