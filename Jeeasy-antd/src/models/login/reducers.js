// import { setAuthority } from "@/utils/authority"
// import { REFRESH_TOKEN_KEY, TOKEN_KEY } from "@/utils/Const"
import { reducerHandler } from "@/utils/modelUtil";
import action from './actions'

export const initState = {
  status: false,
  type: undefined,
}

export default reducerHandler({
  [action.LOGIN.OK]: (state, { payload }) => {
    return { ...state, status: payload.success, type: 'account' };
  },
  [action.SET_STATE]: (state, { payload }) => ({
    ...state,
    ...payload,
  }),
  [action.RESET_STATE]: () => initState,
})