import React, { useCallback, useEffect, useState } from 'react'
import './index.less'
import { Row, Tag } from 'antd'
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

const TabsLayout: React.FC<HeaderViewProps> = (props) => {
  // console.log('props =--=---=> ', props)

  const { menus, current, openTab } = useModel('menu-tabs')
  const [tabs, setTabs] = useState<MenuTabsItem[]>([])

  const getTabMenuByPath = useCallback((path: string): MenuTabsItem | null => {
    let tab = null
    tabs.forEach((t) => {
      if (path === t.key) {
        tab = t
      }
    })
    return tab
  }, [tabs])

  useEffect(() => {
    const { menuData } = props
    const tabMenus = getMenuList(menuData)
    setTabs(tabMenus)
    history.listen((location) => {
      const tab = getTabMenuByPath(location.pathname)
      if (tab) {
        openTab(tab)
      }
    })
    const currMenu = getTabMenuByPath(history.location.pathname)
    if (currMenu) {
      openTab(currMenu)
    }
  }, [getTabMenuByPath, openTab, props])

  const isCurrent = useCallback((key): boolean => {
    return current?.key === key
  }, [current?.key])

  return (
    <>
      { props.children }
      <Row className="tabs">
        {
          menus.map(({ name, key, path }) => {
            // const sty = current?.key === key ? 'tab active-tab' : 'tab'
            return <Tag key={ key } color={ isCurrent(key) ? 'success' : '' } onClick={ () => {
              history.push(path)
            } }>{ name }</Tag>
          })
        }
      </Row>
    </>
  )

}

export default TabsLayout
