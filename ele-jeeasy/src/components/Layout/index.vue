<template>
    <div class="wrapper">
        <template v-if="layout ==='left'">
            <header v-once>
                <p slot="logo">VueJS模板系统</p>
            </header>
            <nav :layout="layout" />
        </template>
        <template v-if="layout === 'top'">
            <header>
                <p slot="logo">VueJS模板系统</p>
                <template slot="topnav">
                    <nav :layout="layout" />
                </template>
            </header>
        </template>
        <div class="sys-content" :class="layout">
            <tag-nav />
            <keep-alive :include="tagNavList">
                <router-view/>
            </keep-alive>
        </div>
    </div>
</template>

<script>
import Header from './Header'
import Nav from './Nav'
import TagNav from './TagNav'

export default {
  name: 'Layout',
  components: { Header, Nav, TagNav },
  computed: {
    layout () {
      return this.$store.state.navbarPosition
    },
    tagNavList () {
      return this.$store.state.tagNav.cachedPageName
    }
  }
}
</script>

<style scoped>

</style>
