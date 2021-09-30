import React from 'react'
import * as Icon from '@ant-design/icons'
import _ from 'lodash'

// var iconType = 'FastBackwardOutlined';

type IconStyle = 'Outlined' | 'Filled' | 'TwoTone'

const EasyIcon: React.FC<{ icon?: string, iconStyle?: IconStyle } & Record<any, any>> = ({ icon, iconStyle = 'Outlined', ...props }) => {
  if (!icon) {
    return null
  }
  const iconName = _.upperFirst(icon + iconStyle)
  const iconEle = Icon[iconName]
  return (
    iconEle ? React.createElement(
      Icon[iconName],
      {
        ...props
      }
    ) : <>{ icon }</>
  )
}

export default EasyIcon
