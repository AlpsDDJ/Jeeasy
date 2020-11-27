// import { getUserAuth } from "@/services/user";
//
// let authRoutes = []
// let authMap = {}
//
// export async function  render(oldRender) {
//   const authRoutesResult = await getUserAuth();  // 拿权限（getUserAuth）是权限接口，里面的参数就是数组包对象的方式
//   window.oldRender = () => {
//     if (authRoutesResult && authRoutesResult.ret.code === 'SUCCESS') {
//       authRoutes = authRoutesResult.data;  // 这个authRoutes放在全局定义下
//     } else {
//       oldRender();
//     }
//     oldRender();
//   }
//   if (window.oldRender) {
//     window.oldRender();
//   }
// }
// export function patchRoutes({routes}) {
//   authMap = authRoutes.reduce((total, next) => {
//     return ({...total, [next.url]: true})  // authMap全局声明
//     // 把权限通过健值对的方式存起来，这个根据个人接口情况去处理
//   }, {'/welcome':true, '/':true, '/login':true})
//   // 这后面这个是默认每个权限都有的路由
//   routes[0].routes = filterRoute(routes[0].routes, authMap)
//   // 我们把这个routes替换跳，这个会给到渲染的组件
//   window.g_routes = routes;
// }
//
// // 对路由进行递归处理，没有权限的过滤掉
// function filterRoute(routes, authMap) {
//   const arr = [];
//   routes.forEach((elem) => {
//     if(authMap[elem.path]) {
//       arr.push({...elem, routes:(elem.routes && elem.routes.length > 0)?filterRoute(elem.routes, authMap):[]})
//     }
//   })
//   return arr;
// }
//
// export async function getInitialState() {
//   return authMap  // 上面说了，全局申请直接传就好
// }