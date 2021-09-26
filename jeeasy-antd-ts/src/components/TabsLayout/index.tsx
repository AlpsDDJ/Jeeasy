import React, { useCallback, useEffect, useState } from 'react'
import './index.less'
import { Row, Tag, TagProps } from 'antd'
import { history, useModel } from 'umi'
import type { HeaderViewProps } from '@ant-design/pro-layout/lib/Header'
import type { MenuDataItem } from '@ant-design/pro-layout'

// type Menus = any[]
export type MenuTabsItem = {
  key: string,
  name: string,
  path: string
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
  const { menus, current, openTab, closeTab } = useModel('menu-tabs')
  const [currPath, setCurrPath] = useState<string>('')
  const tabs = getMenuList(menuData)

  const getTabMenuByPath = useCallback((path: string): MenuTabsItem | null => {
    let tab = null
    tabs.forEach((t) => {
      if (path === t.key) {
        tab = t
      }
    })
    return tab
  }, [tabs])


  // useEffect(() => {
  //   history.listen((location) => {
  //     const tab = getTabMenuByPath(location.pathname)
  //     if (tab) {
  //       openTab(tab)
  //     }
  //   })
  // }, [getTabMenuByPath, menus, openTab])


  useEffect(() => {
    history.listen((location) => {
      setCurrPath(location.pathname)
      const tab = getTabMenuByPath(location.pathname)
      if (tab) {
        openTab(tab)
      }
    })
    if(!currPath) {
      setCurrPath(history.location.pathname)
    }
    const currMenu = getTabMenuByPath(history.location.pathname)
    if (currMenu && currMenu.key !== '/welcome') {
      openTab(currMenu)
    }
  }, [currPath])

  const isCurrent = useCallback((key): boolean => {
    return current?.key === key
  }, [current?.key])

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
