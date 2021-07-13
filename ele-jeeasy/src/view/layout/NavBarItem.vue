<template>
  <div>
    <el-submenu :index="menu.path" v-if="menu.children && menu.children.length > 0">
      <template slot="title">{{ menu.title }}</template>
      <nav-bar-item v-for="sub in menu.children" :menu="sub" :key="sub.path"/>
    </el-submenu>
    <el-menu-item v-else @click="linkTo(menu.path)">
      <span>{{ menu.title }}</span>
    </el-menu-item>
  </div>
</template>

<script>
export default {
  name: 'NavBarItem',
  props: {
    menu: {
      type: Object
    }
  },
  methods: {
    linkTo (path) {
      this.$router.push(path)
    }
  }
}
</script>

<style scoped>

</style>
