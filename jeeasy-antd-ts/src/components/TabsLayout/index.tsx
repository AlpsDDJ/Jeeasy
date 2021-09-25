import React from 'react'
import './index.less';
import { Row } from 'antd'

type Menus = any[]

const TabsLayout: React.FC = ({children}) => {
  const menus: Menus = [{
    title: '用户管理',
    key: 'sysUser',
    active: true
  }, {
    title: '角色管理',
    key: 'sysRole',
  }]
  return (
    <>
      {children}
      <Row className="tabs">
        {
          menus.map(({ title, key, active }) => {
            const sty = active? 'tab active-tab': 'tab'
            return <div key={key} className={sty}>{`${active}`} {title}</div>
          })
        }
      </Row>
    </>
  )

}

export default TabsLayout
