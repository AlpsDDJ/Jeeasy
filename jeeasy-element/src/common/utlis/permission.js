import store from '@/store'

/**
 * 权限、显示工具
 * @author LiQingSong
 */

/**
 * 获取当前路由对应的顶部菜单path
 * @param  route 当前路由
 * @returns String
 * @author LiQingSong
 */
export function getBelongTopMenuPath(route) {
  // const route = this.$route;
  const { meta, path } = route
  if (meta.belongTopMenu) {
    return meta.belongTopMenu
  }
  return '/' + path.split('/')[1]
}

/**
 * 获取当前路由选中的侧边栏菜单path
 * @param  route 当前路由
 * @returns String
 * @author LiQingSong
 */
export function getActiveSidebarMenuPath(route) {
  // const route = this.$route;
  const { meta, path } = route
  if (meta.activeMenu) {
    return meta.activeMenu
  }
  return path
}

function showOneChild(children = []) {
  let oneChild = {}
  const showChildren = children.filter(item => {
    if (item.hidden) {
      return false
    } else {
      if (item.meta.showOne) {
        oneChild = { ...item }
        return true
      }
      return false
    }
  })
  if (showChildren.length === 1) {
    return oneChild
  }
  return false
}

export function getMenus() {
  const menus = []
  const routes = [...store.state.permission.routes]
  routes.forEach(route => {
    const item = {...route}
    if (item.children && item.children.length > 0) {
      const oneChild = showOneChild(item.children)
      if (oneChild) {
        menus.push({ ...oneChild, path: `${item.path}/${oneChild.path}`.replace('//', '/') })
        item.hidden = true
      }
    }
    menus.push(item)
  })
  return menus
}
