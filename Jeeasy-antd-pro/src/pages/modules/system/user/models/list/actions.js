import { mapAction } from '@/utils/modelUtil'

export const namespace = 'sysUser'

const actions = {
  LIST: '/sys/user',
  INFO: '/sys/user/{id}',
  ADD: '/sys/user|POST',
  EDIT: '/sys/user|PUT',
  DELETE: '/sys/user|DELETE',
  SET_STATE: false,
  RESET_STATE: false,
}

mapAction(actions, [namespace])

export default actions