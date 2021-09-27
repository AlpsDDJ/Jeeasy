import React, { useCallback, useEffect, useState } from 'react'
import './index.less'
import type { TagProps } from 'antd'
import { Row, Tag } from 'antd'
import { history } from 'umi'
import type { HeaderViewProps } from '@ant-design/pro-layout/lib/Header'
import { remove } from 'lodash'
import { HomeOutlined } from '@ant-design/icons'

export type MenuTabsItem = {
  key: string,
  name: string,
  path: string
  icon?: string | React.ReactNode
}

const defaultMenu: MenuTabsItem = {
  name: '欢迎页',
  key: '/welcome',
  path: '/welcome',
  icon: <HomeOutlined />
}

const TabsLayout: React.FC<HeaderViewProps & { breadcrumb: any }> = ({ children, breadcrumb }) => {
  const [tabs, setTabs] = useState<MenuTabsItem[]>([defaultMenu])
  const [historyPath, setHistoryPath] = useState<string[]>([defaultMenu.path])

  const closeTab = useCallback((key: string) => {
    const copyMeuns = [...tabs]
    const copyHistoryPath = [...historyPath]
    remove(copyMeuns, (menu: { key: string }) => menu.key === key)
    remove(copyHistoryPath, (path: string) => path === key)
    setTabs(copyMeuns)
    setHistoryPath(copyHistoryPath)
    if (key === history.location.pathname) {
      history.push(copyHistoryPath[copyHistoryPath.length - 1])
    }
  }, [history.location.pathname])

  const openTab = useCallback((menu: MenuTabsItem) => {
    if (!tabs.some(m => m.key === menu.key)) {
      setTabs([...tabs, menu])
    }
    setHistoryPath([...historyPath.filter(p => p !== menu.key), menu.path])
  }, [history.location.pathname])

  useEffect(() => {
    const tab = breadcrumb[history.location.pathname]
    if (tab) {
      openTab(tab)
    }
  }, [history.location.pathname])

  return (
    <>
      { children }
      <Row className="tabs">
        {
          tabs.map(({ name, key, path = '', icon }) => {
            const isCurrent = history.location.pathname === key
            const tagProps: TagProps = {
              icon,
              closable: key !== defaultMenu.key,
              className: isCurrent ? 'tab active-tab' : 'tab',
              onClick: () => {
                if (history.location.pathname !== key) {
                  history.push(path)
                }
              },
              onClose: () => {
                closeTab(key)
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
