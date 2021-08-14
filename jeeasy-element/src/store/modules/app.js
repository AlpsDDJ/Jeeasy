/**
 * 项目 Store
 * @author LiQingSong
 */
import { siteFiexdHeader, siteTopNavEnable,siteSidebarLogo } from '@/settings'
import { setCookie, getCookie } from '@/common/utlis/cookieUtil'

function getLocalTheme(){
    const theme = getCookie('jeeasy:theme')
    if(theme && theme !== 'default'){
        import(`@/assets/theme/${theme}/index.scss`)
        return theme
    }
    return 'default'
}

const state = {
    siteFiexdHeader: siteFiexdHeader,// 是否固定右侧头部
    siteTopNavEnable: siteTopNavEnable, // 是否启用顶部导航
    siteSidebarLogo: siteSidebarLogo, // 是否显示侧边栏LOGO
    sidebarOpened: true, // 侧边栏是否展开
    theme: getLocalTheme()
}
const mutations = {
    // 设置是否固定右侧头部 Bval(true|false)
    SET_SITE_FIEXD_HEADER: (state, Bval) => {
        state.siteFiexdHeader = Bval
    },
    // 设置是否启用顶部导航 Bval(true|false)
    SET_SITE_TOPNAV_ENABLE: (state, Bval) => {
        state.siteTopNavEnable = Bval
    },
    // 设置是否显示侧边栏LOGO Bval(true|false)
    SET_SITE_SIDEBAR_LOGO: (state, Bval) => {
        state.siteSidebarLogo = Bval
    },
    // 设置是否显示侧边栏LOGO Bval(true|false)
    SET_THEME: (state, Bval) => {
        state.theme = Bval
    },
    // 侧边栏开关
    TOGGLE_SIDEBAR: state => {
        state.sidebarOpened = !state.sidebarOpened
    }
}
const actions = {
    toggleSideBar({ commit }) {
        commit('TOGGLE_SIDEBAR')
    },
    setTheme({commit}, theme){
        commit('SET_THEME', theme)
        setCookie('jeeasy:theme', theme, 30)
        // window.location.reload()
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
