import { mapActionToEffect } from '@/utils/actionEffect'
import actions, { namespace } from './actions'
import reducers, { initState } from './reducers'

export default {
  namespace,
  state: initState,
  reducers,
  effects: mapActionToEffect(actions),
}
