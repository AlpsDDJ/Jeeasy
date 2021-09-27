import React, { useCallback, useEffect, useLayoutEffect, useState } from 'react'
import './index.less'
// import styles from './index.less';
import type { TagProps } from 'antd'
import { Row, Tag } from 'antd'
import { history } from 'umi'
import type { HeaderViewProps } from '@ant-design/pro-layout/lib/Header'
import type { MenuDataItem } from '@ant-design/pro-layout'
import { remove } from 'lodash'

export type MenuTabsItem = {
  key: string,
  name: string,
  path: string
}

const defaultMenu: MenuTabsItem = {
  name: '欢迎页',
  key: '/welcome',
  path: '/welcome'
}


const getMenuList = (menus: MenuDataItem[]): MenuTabsItem[] => {
  console.log('getMenuList')
  let tabMenus: MenuTabsItem[] = []
  menus.forEach(({ key, name, children: child, path }) => {
    if (key && name && path) {
      tabMenus.push({ key, name, path })
    }
    if (child && child.length > 0) {
      tabMenus = [...tabMenus, ...getMenuList(child)]
    }
  })
  return tabMenus
}

const TabsLayout: React.FC<HeaderViewProps & { breadcrumb: any }> = ({ children, menuData = [], breadcrumb }) => {
  const [tabs, setTabs] = useState<MenuTabsItem[]>([defaultMenu])
  const [allTabs, setAllTabs] = useState<MenuTabsItem[]>([])
  // const [currPath, setCurrPath] = useState<string>('')
  const [historyPath, setHistoryPath] = useState<string[]>([defaultMenu.path])

  // let allTabs: MenuTabsItem[] = []

  // if (!allTabs || allTabs.length === 0) {
  //   setAllTabs(getMenuList(menuData))
  //   // allTabs = getMenuList(menuData)
  // }
  // console.log('historyPath -- ', historyPath)

  const closeTab = useCallback((key: string) => {
    const copyMeuns = [...tabs]
    const copyHistoryPath = [...historyPath]
    remove(copyMeuns, (menu: { key: string }) => menu.key === key)
    remove(copyHistoryPath, (path: string) => path === key)
    setTabs(copyMeuns)
    setHistoryPath(copyHistoryPath)
    // console.log('historyPath -  2222  - ', historyPath)
    if (key === history.location.pathname) {
      history.push(copyHistoryPath[copyHistoryPath.length - 1])
    }
    // history.go(-1)
  }, [history.location.pathname])

  const openTab = useCallback((menu: MenuTabsItem) => {
    // const path = history.location.pathname
    // setCurrPath(menu.path)
    // console.log('menus -  2222  - ', menus)
    if (!tabs.some(m => m.key === menu.key)) {
      setTabs([...tabs, menu])
    }
    // console.log('historyPath -    - ', historyPath)
    setHistoryPath([...historyPath.filter(p => p !== menu.key), menu.path])
  }, [history.location.pathname])

  // const closeAll = useCallback(() => {
  //   setMenus([defaultMenu])
  // }, [])


  const getTabMenuByPath = useCallback((path: string): MenuTabsItem | null => {
    // const allTabs = getMenuList(menuData)
    console.log('getTabMenuByPath')

    return breadcrumb[path]

    // let tab = null
    // allTabs.forEach((t) => {
    //   if (path === t.key) {
    //     tab = t
    //   }
    // })
    // return tab
  }, [history.location.pathname])

  useLayoutEffect(() => {
    setAllTabs(getMenuList(menuData))
  }, [])

  useEffect(() => {
    // console.log(history)
    const path = history.location.pathname
    // setCurrPath(path)
    console.log('allTabs ---->', allTabs)
    const tab = getTabMenuByPath(path)
    if (tab) {
      openTab(tab)
    }
  }, [history.location.pathname])


  const closeHandle = useCallback((key) => {
    closeTab(key)
  }, [history.location.pathname])

  return (
    <>
      { children }
      <Row className="tabs">
        {
          tabs.map(({ name, key, path }) => {
            const isCurrent = history.location.pathname === key

            // const curr = isCurrent(key)
            const tagProps: TagProps = {
              closable: key !== '/welcome',
              // color: curr ? 'success' : '',
              className: isCurrent ? 'tab active-tab' : 'tab',
              onClick: () => {
                if (history.location.pathname !== key) {
                  history.push(path)
                }
              },
              onClose: () => {
                closeHandle(key)
              }
            }
            return <Tag key={ key } { ...tagProps }>{ name }</Tag>
          })
        }
      </Row>
    </>
  )

}

export default TabsLayout
