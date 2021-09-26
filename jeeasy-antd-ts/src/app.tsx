import { Modal } from 'antd'
import type { Settings as LayoutSettings } from '@ant-design/pro-layout'
import { PageContainer, PageLoading } from '@ant-design/pro-layout'
import type { RequestConfig, RunTimeLayoutConfig } from 'umi'
import { ErrorShowType, history, Link } from 'umi'
import RightContent from '@/components/RightContent'
import Footer from '@/components/Footer'
import { currentUser as queryCurrentUser, refreshToken } from './services/common/api'
import { BookOutlined, LinkOutlined } from '@ant-design/icons'
import { getToken } from '@/common/utils/tokenUtil'
import type { RequestOptionsInit } from 'umi-request'
import { stringify } from 'querystring'
// import { ErrorShowType } from '@@/plugin-request/request'
import TabsLayout from '@/components/TabsLayout'
import { authHeaderKey, loginPath, refreshTokenUrl } from '@/common/setting'
// import { useModel } from '@@/plugin-model/useModel'

const isDev = process.env.NODE_ENV === 'development'

/** 获取用户信息比较慢的时候会展示一个 loading */
export const initialStateConfig = {
  loading: <PageLoading />
}

/**
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */
export async function getInitialState(): Promise<{
  settings?: Partial<LayoutSettings>;
  currentUser?: API.CurrentUser;
  fetchUserInfo?: () => Promise<API.CurrentUser | undefined>;
}> {
  const fetchUserInfo = async () => {
    try {
      const msg = await queryCurrentUser()
      return msg.data
    } catch (error) {
      history.push(loginPath)
    }
    return undefined
  }
  // 如果是登录页面，不执行
  if (history.location.pathname !== loginPath) {
    const currentUser = await fetchUserInfo()
    return {
      fetchUserInfo,
      currentUser,
      settings: {}
    }
  }
  return {
    fetchUserInfo,
    settings: {}
  }
}

const loginRedirect = (): Promise<string> => {
  return new Promise(resolve => {

    const { query = {}, pathname } = history.location
    const { redirect } = query
    // Note: There may be security issues, please note
    if (!redirect) {
      history.replace({
        pathname: loginPath,
        search: stringify({
          redirect: pathname
        })
      })
      resolve(pathname)
    }
  })
}


// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({ initialState }) => {
  // useModel('menu-tabs')
  return {
    rightContentRender: () => <RightContent />,
    disableContentMargin: false,
    waterMarkProps: {
      content: initialState?.currentUser?.username
    },
    childrenRender: dom => <PageContainer title={false} children={dom} />,
    headerRender: (props, dom) => {
      return <TabsLayout {...props} children={dom} />
    },
    footerRender: () => <Footer />,
    // itemRender: (route, params, routes, paths) => {
    //   console.log('paths --- ', paths)
    //   return ('')
    // },
    // pageTitleRender: (props, defaultPageTitle, info) => {
    //   console.log(info)
    //   return '123123123'
    // },
    // menuDataRender: menuData => {
    //   // console.log('menuData ====== ',menuData)
    //   return menuData.map(({name, ...menu}) => ({
    //     name: `${name}`,
    //     ...menu
    //   }))
    // },
    onPageChange: async () => {
      const { location } = history
      // 如果没有登录，重定向到 login
      if (!initialState?.currentUser && location.pathname !== loginPath) {
        // history.push(loginPath)
        // await loginRedirect()
      }
    },
    links: isDev
      ? [
        <Link to="/umi/plugin/openapi" target="_blank">
            <LinkOutlined />
            <span>OpenAPI 文档</span>
          </Link>,
        <Link to="/~docs">
            <BookOutlined />
            <span>业务组件文档</span>
          </Link>
      ]
      : [],
    menuHeaderRender: undefined,
    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,
    ...initialState?.settings
  }
}


const requestInterceptor = (url: string, options: RequestOptionsInit) => {
  const token = getToken()
  const authHeader = { [authHeaderKey]: token }
  return {
    url,
    options: {
      ...options,
      headers: authHeader
    }
  }
}

const responseInterceptor = (response: Response, options: RequestOptionsInit): Response | Promise<Response> => {
  const {status} = response
  const { url } = options
  if(status === 401) {
    if(url !== refreshTokenUrl) {
      return refreshToken(options)
    }
  }
  return response
}

export const request: RequestConfig = {
  timedout: 10000,
  errorConfig: {
    adaptor: (resp) => {
      const {code} = resp
      let showType = null

      switch (code) {
        case 200:
          showType = ErrorShowType.SILENT
          break
        case 401:
          showType = ErrorShowType.SILENT
          loginRedirect().then(() => {
            Modal.warning({
              title: '提示',
              content: '登录失效，请重新登录!'
            })
          })
          break
        default:
          showType = ErrorShowType.ERROR_MESSAGE
          break
      }

      return {
        ...resp,
        showType,
        // success: resData.success,
        errorMessage: resp.success ? '': resp.message
      }
    }
  },
  requestInterceptors: [requestInterceptor],
  responseInterceptors: [responseInterceptor]
}
