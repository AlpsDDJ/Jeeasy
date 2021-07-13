<template>
  <el-row class="menus">
    <el-menu>
      <nav-bar-item v-for="m in menus" :menu="m" :key="m.path"/>
    </el-menu>
  </el-row>
</template>

<script>
import NavBarItem from './NavBarItem'
export default {
  name: 'Nav',
  components: {NavBarItem},
  data () {
    const menus = [
      {
        title: '首页',
        path: '/home'
      },
      {
        title: '系统管理',
        path: '/sys',
        children: [
          {
            title: '用户管理',
            path: '/sys/user'
          },
          {
            title: '角色管理',
            path: '/sys/role'
          },
          {
            title: '权限管理',
            path: '/sys/permission',
            children: [
              {
                title: '新增权限',
                path: '/sys/permission/add'
              }
            ]
          }
        ]
      }
    ]

    return {
      menus
    }
  }
}
</script>

<style>
  .menus{
    text-align: left;
  }
</style>
