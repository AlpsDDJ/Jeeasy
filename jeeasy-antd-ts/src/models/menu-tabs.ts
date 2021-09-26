import { useState, useCallback } from 'react'
import { remove } from 'lodash'
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

export const defaultMenu: MenuTabsItem = {
  name: '欢迎页',
  key: '/welcome',
  path: '/welcome'
}

export default function useMenuTabsModel(): MenuTabsState {

  const [menus, setMenus] = useState<MenuTabsItem[]>([defaultMenu])

  const [current, setCurrent] = useState<MenuTabsItem>()

  const openTab = useCallback((menu: MenuTabsItem) => {
    // const newMenus = unionWith([...menus], [menu], (m1, m2) => m1.key === m2.key)
    if(!menus.some(m => m.key === menu.key)){
      setMenus([...menus, menu])
    }
    setCurrent(menu)
  }, [menus])

  const closeTab = useCallback((key: string) => {
    const copyMeuns = [...menus]
    remove(copyMeuns, (menu: { key: string }) => menu.key === key)
    // history.go(-1)
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
