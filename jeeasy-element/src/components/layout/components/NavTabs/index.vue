<template>
  <el-row class="nav-tabs" type="flex">
    <el-tag
      v-for="({path, title}) in pages"
      :key="path"
      :closable="path !== '/home'"
      @click="() => { handleTabClick(path) }"
      @close="() => { handleTabClose(path) }"
      v-bind="activeTabAttr(path)" >
      <span>{{ title }}</span>
    </el-tag>
  </el-row>
</template>

<script>
export default {
  name: 'NavTabs',
  data() {
    return {
      pages: [{
        path: '/home',
        title: '主控台'
      }],
      currentPage: '',
      historyTabs: []
    }
  },
  methods: {
    handleTabClick(path) {
      if (path !== this.currentPage) {
        this.$router.push(path)
      }
    },
    handleTabClose(path) {
      this.historyTabs = this.historyTabs.filter(tab => tab !== path)
      this.pages = this.pages.filter(({ path: _path }) => _path !== path)
      if (path === this.currentPage) {
        this.$router.push(this.historyTabs[0])
      }
    }
  },
  computed: {
    activeTabAttr() {
      const { currentPage } = this
      return path => {
        return path === currentPage ? {
          // effect: 'light',
          // type: 'success',
          class: 'nav-tab active-tab'
        } : {
          effect: 'plain',
          type: 'info',
          class: 'nav-tab'
        }
      }
    }

  },
  watch: {
    $route: {
      handler(to) {
        const { path, meta } = to
        const { title } = meta
        const page = { path, title }
        this.currentPage = path
        this.historyTabs = [path, ...this.historyTabs.filter(tab => tab !== path)]

        if (!this.pages.some(p => p.path === path)) {
          this.pages.push(page)
        }

      },
      immediate: true
    }
  }
}
</script>

<style lang="scss">
@import "~@/assets/css/variables.scss";

</style>
