import { PageLoading } from '@ant-design/pro-layout'
import { history, Link } from 'umi'
import RightContent from '@/components/RightContent'
import Footer from '@/components/Footer'
import { currentUser as queryCurrentUser } from './services/ant-design-pro/api'
import { BookOutlined, LinkOutlined } from '@ant-design/icons'
import { getToken } from '@/common/utils/tokenUtil'
import { authHeaderKey, loginPath } from '@/common/setting'

import type { Settings as LayoutSettings } from '@ant-design/pro-layout'
import type { RunTimeLayoutConfig, RequestConfig } from 'umi'
import type { RequestOptionsInit } from 'umi-request'
import { stringify } from 'querystring'
import { ErrorShowType } from '@@/plugin-request/request'
import { Modal } from 'antd'

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

const loginRedirect = (): Promise<string> =>  {
  return new Promise(resolve => {

    const { query = {}, pathname } = history.location;
    const { redirect } = query;
    // Note: There may be security issues, please note
    if (!redirect) {
      history.replace({
        pathname: loginPath,
        search: stringify({
          redirect: pathname,
        }),
      });
      resolve(pathname)
    }
  })
}



// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({ initialState }) => {
  return {
    rightContentRender: () => <RightContent />,
    disableContentMargin: false,
    waterMarkProps: {
      content: initialState?.currentUser?.username
    },
    footerRender: () => <Footer />,
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

export const request: RequestConfig = {
  timedout: 10000,
  errorConfig: {
    adaptor: (resData) => {
      const {code} = resData
      let showType = ErrorShowType.WARN_MESSAGE
      if(code === 401) {
        showType = ErrorShowType.SILENT
        loginRedirect().then(() => {
          Modal.warning({
            title: '提示',
            content: '登录失效，请重新登录!'
          })
        })
      }
      return {
        ...resData,
        showType,
        // success: resData.success,
        errorMessage: resData.success ? '': resData.message
      }
    }
  },
  requestInterceptors: [requestInterceptor]
}
