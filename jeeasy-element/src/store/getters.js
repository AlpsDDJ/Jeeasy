/**
 * Vuex.Store getters
 * @author LiQingSong
 */
const getters = {
  siteFiexdHeader: state => state.app.siteFiexdHeader,
  siteTopNavEnable: state => state.app.siteTopNavEnable,
  siteSidebarLogo: state => state.app.siteSidebarLogo,
  sidebarOpened: state => state.app.sidebarOpened,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  msgtotal: state => state.user.msgtotal,
  name: state => state.user.name,
  username: state => state.user.username,
  roles: state => state.user.roles,
  permission_routes: state => state.permission.routes,
  loading: state => state.global.loading
}
export default getters
