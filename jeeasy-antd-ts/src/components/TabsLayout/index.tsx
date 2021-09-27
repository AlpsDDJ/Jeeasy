import React, { useCallback, useEffect, useState } from 'react'
import './index.less'
import type { TagProps } from 'antd'
import { Row, Tag } from 'antd'
import { history } from 'umi'
import type { HeaderViewProps } from '@ant-design/pro-layout/lib/Header'
import type { MenuDataItem } from '@ant-design/pro-layout'
import { remove } from 'lodash'
// import { defaultMenu } from '@/models/menu-tabs'

// type Menus = any[]
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

function getMenuList(menus: MenuDataItem[] = []): MenuTabsItem[] {
  let tabMenus: MenuTabsItem[] = []
  menus.forEach(({ key, name, children, path }) => {
    if (key && name && path) {
      tabMenus.push({ key, name, path })
    }
    if (children && children.length > 0) {
      tabMenus = [...tabMenus, ...getMenuList(children)]
    }
  })
  return tabMenus
}

const TabsLayout: React.FC<HeaderViewProps> = ({ children, menuData }) => {
  // const { menus, current, openTab, closeTab } = useModel('menu-tabs')
  const [menus, setMenus] = useState<MenuTabsItem[]>([defaultMenu])
  const [currPath, setCurrPath] = useState<string>('')
  const [historyPath, setHistoryPath] = useState<string[]>([defaultMenu.path])
  console.log('historyPath -- ', historyPath)

  const closeTab = useCallback((key: string) => {
    const copyMeuns = [...menus]
    const copyHistoryPath = [...historyPath]
    remove(copyMeuns, (menu: { key: string }) => menu.key === key)
    remove(copyHistoryPath, (path: string) => path === key)
    setMenus(copyMeuns)
    setHistoryPath(copyHistoryPath)
    console.log('historyPath -  2222  - ', historyPath)
    if(key === currPath) {
      history.push(copyHistoryPath[copyHistoryPath.length - 1])
    }
    // history.go(-1)
  }, [currPath])

  const openTab = useCallback((menu: MenuTabsItem) => {
    // const path = history.location.pathname
    setCurrPath(menu.path)
    if(!menus.some(m => m.key === menu.key)) {
      setMenus([...menus, menu])
    }
    console.log('historyPath -    - ', historyPath)
    setHistoryPath([...historyPath.filter(p => p !== menu.key), menu.path])
  }, [currPath])

  // const closeAll = useCallback(() => {
  //   setMenus([defaultMenu])
  // }, [])

  const tabs = getMenuList(menuData)
  const getTabMenuByPath = useCallback((path: string): MenuTabsItem | null => {
    let tab = null
    tabs.forEach((t) => {
      if (path === t.key) {
        tab = t
      }
    })
    return tab
  }, [menuData])

  useEffect(() => {
    // setCurrPath(history.location.pathname)
    // const tab = getTabMenuByPath(history.location.pathname)
    // if (tab) {
    //   openTab(tab)
    // }
    history.listen((location) => {
      setCurrPath(location.pathname)
      const tab = getTabMenuByPath(location.pathname)
      if (tab) {
        openTab(tab)
      }
    })
  }, [getTabMenuByPath, openTab, closeTab])

  // useEffect(() => {
  //   // if(!currPath) {
  //   //   setCurrPath(history.location.pathname)
  //   // }
  //   const currMenu = getTabMenuByPath(history.location.pathname)
  //   if (currMenu) {
  //     openTab(currMenu)
  //   }
  // }, [openTab])

  const isCurrent = useCallback((key): boolean => {
    return currPath === key
  }, [currPath])

  const closeHandle = useCallback((key) => {
    closeTab(key)
  }, [closeTab])

  return (
    <>
      { children }
      <Row className="tabs">
        {
          menus.map(({ name, key, path }) => {
            const curr = isCurrent(key)
            const tagProps: TagProps = {
              closable: key !== '/welcome',
              color: curr ? 'success' : '',
              className: curr ? 'tab active-tab' : 'tab',
              onClick: () => {
                history.push(path)
              },
              onClose: () => {
                closeHandle(key)
              }
            }
            return <Tag key={key} { ...tagProps }>{ name }</Tag>
          })
        }
      </Row>
    </>
  )

}

export default TabsLayout
