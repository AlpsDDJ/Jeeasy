import { reducerHandler, pageState } from "@/utils/modelUtil";
import ACTIONS from './actions'

export const initState = {
  ...pageState
}

export default reducerHandler({
  // --------------- list ---------------
  [ACTIONS.LIST.OK]: (state, { payload: { result } }) => ({
    ...state,
    result,
  }),
  [ACTIONS.LIST.NOK]: (state) => ({
    ...state,
    result: initState.list,
  }),
  // --------------- info ---------------
  // --------------- add ---------------
  // --------------- edit ---------------
  // --------------- delete ---------------
  [ACTIONS.SET_STATE]: (state, { payload }) => ({
    ...state,
    ...payload,
  }),
  [ACTIONS.RESET_STATE]: () => initState,
})