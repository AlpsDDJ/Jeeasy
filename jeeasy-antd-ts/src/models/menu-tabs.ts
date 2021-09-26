import { useState, useCallback } from 'react'
import { remove, unionWith } from 'lodash'
import type { MenuTabsItem } from '@/components/TabsLayout'

// export type MenuTabsItem = {
//   pathname: string;
//   search?: string;
//   hash?: string;
//   key: string;
//   query?: ParsedQuery;
//   title: string
// }
// type Menus = any[]

export type MenuTabsState = {
  menus: MenuTabsItem[],
  current?: MenuTabsItem,
  openTab: (menu: MenuTabsItem) => void,
  closeTab: (pathname: string) => void,
  closeAll: () => void
}

const defaultMenu: MenuTabsItem = {
  name: '欢迎页',
  key: '/welcome', path: '/welcome'
}

export default function useMenuTabsModel(): MenuTabsState {

  const [menus, setMenus] = useState<MenuTabsItem[]>([defaultMenu])

  const [current, setCurrent] = useState<MenuTabsItem>()

  const openTab = useCallback((menu: MenuTabsItem) => {
    const newMenus = unionWith(menus, [menu], (m1, m2) => m1.key === m2.key)
    setMenus(newMenus)
    setCurrent(menu)
  }, [menus])

  const closeTab = useCallback((key: string) => {
    const copyMeuns = [...menus]
    remove(copyMeuns, (menu: { key: string }) => menu.key === key)
  }, [menus])

  const closeAll = useCallback(() => {
    setMenus([defaultMenu])
  }, [])

  return {
    menus,
    current,
    openTab,
    closeTab,
    closeAll
  }
}
