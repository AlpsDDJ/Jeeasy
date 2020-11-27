import { mapAction } from "@/utils/modelUtil";

export const namespace = 'auth'

const actions = {
  LOGIN: '/auth/login|POST',
  LOGOUT: '/logout',
  CURRENT_USER: '/currentUser',
  SET_STATE: false,
  RESET_STATE: false,
}
mapAction(actions, [namespace])

export default actions