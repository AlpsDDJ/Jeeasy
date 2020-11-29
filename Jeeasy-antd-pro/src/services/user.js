import request from '@/utils/request';

export async function query() {
  return request('/api/users');
}
export async function queryCurrent() {
  return request('/currentUser');
}
export async function queryNotices() {
  return request('/api/notices');
}
export async function getUserAuth(){
  return request('/getUserAuth');
}