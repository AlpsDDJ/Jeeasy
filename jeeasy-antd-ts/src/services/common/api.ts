// @ts-ignore
/* eslint-disable */
import { request } from 'umi';
import { DataNode } from 'rc-tree-select/lib/interface'
import { refreshTokenUrl } from '@/common/setting'
import { getRefreshToken, saveToken } from '@/common/utils/tokenUtil'
import { RequestOptionsInit } from 'umi-request'

/** 获取当前的用户 GET /api/currentUser */
export async function currentUser(options?: { [key: string]: any }) {
  return request<{
    data: API.CurrentUser;
  }>('/api/currentUser', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 退出登录接口 POST /api/login/outLogin */
export async function outLogin(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/auth/logout', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 登录接口 POST /api/login/account */
export async function login(body: API.LoginParams, options?: { [key: string]: any }): Promise<R<LoginRespData>> {
  return request<R<LoginRespData>>('/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/notices */
export async function getNotices(options?: { [key: string]: any }) {
  return request<API.NoticeIconList>('/api/notices', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取规则列表 GET /api/rule */
export async function rule(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.RuleList>('/api/sys/user', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 新建规则 PUT /api/rule */
export async function updateRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'PUT',
    ...(options || {}),
  });
}

/** 新建规则 POST /api/rule */
export async function addRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 删除规则 DELETE /api/rule */
export async function removeRule(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/rule', {
    method: 'DELETE',
    ...(options || {}),
  });
}

export async function getDictItems(code: string, options?: { [key: string]: any }): Promise<any[]> {
  const resp = await request<R<any[]>>('/api/common/dicts/' + code, {
    method: 'GET',
    ...(options || {}),
  })
  if(resp.success){
    const {data = []} = resp
    return data.map(({dictName, dictCode}) => ({value: dictCode, label: dictName}))
  } else {
    return []
  }
}

export async function getTreeDictItems(code: string, parentId: string | number = 0, loadAll: boolean = true, options?: { [key: string]: any }): Promise<DataNode[]> {
  const params = {
    parentId,
    'async': loadAll,
    ...options?.params
  }
  const opt = { ...({ ...options, params } || {}) }
  // console.log('opt = ', opt)
  const resp = await request<R<TreeDict[]>>('/api/common/dicts/' + code, {
    method: 'GET',
    ...opt
  })
  if(resp.success){
    const {data = []} = resp
    return data.map(({isLeaf, dictCode, dictName, parentId: pId}) => ({id: dictCode, value: dictCode, title: dictName, pId: pId, isLeaf}))
  }else{
    return []
  }
}

type LoginRespData = {refreshToken: string, token: string}

export async function refreshToken(reqOptions?: RequestOptionsInit){
  const refreshToken = getRefreshToken()
  // console.log('refreshToken   ----->>> ', refreshToken)
  const resp = await request<R<LoginRespData>>(refreshTokenUrl, {
    method: 'POST',
    data: {
      refreshToken,
    }
  })
  if(resp.success) {
    const {refreshToken, token} = resp.data
    saveToken(token, refreshToken)
    if(reqOptions) {
      return request(reqOptions.url, reqOptions)
    }
    return Promise.reject(true)
  }
}
