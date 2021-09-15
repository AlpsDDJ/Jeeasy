import { Settings as LayoutSettings } from '@ant-design/pro-layout'

const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
} = {
  // navTheme: 'light',
  // // 拂晓蓝
  // primaryColor: '#1890ff',
  // layout: 'mix',
  // contentWidth: 'Fluid',
  // fixedHeader: false,
  // fixSiderbar: true,
  navTheme: "realDark",
  primaryColor: "#13C2C2",
  fixSiderbar: true,
  fixedHeader: true,
  layout: "side",
  contentWidth: "Fluid",
  splitMenus: false,
  colorWeak: false,
  title: 'Ant Design Pro',
  pwa: false,
  logo: 'https://gw.alipayobjects.com/zos/rmsportal/KDpgvguMpGfqaHPjicRK.svg',
  iconfontUrl: ''
}

export default Settings
