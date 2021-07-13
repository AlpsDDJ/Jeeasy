const hasPermission = (perms, route) => {
  if (route.meta && route.meta.perms) {
    return perms.some(p => route.meta.perms.includes(p))
  } else {
    return true
  }
}

export const filterAsyncPerms = (routes, perms) => {
  const res = []
  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(perms, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncPerms(tmp.children, perms)
      }
      res.push(tmp)
    }
  })
  return res
}
