<template>
  <div id="indexlayout-left" :class="{'narrow': isCollapse}">
    <div class="indexlayout-left-logo" v-if="siteSidebarLogo">
      <router-link to="/" class="logo-url">
<!--        <h3 v-if="!isCollapse" class="logo-title">Jeeasy Admin</h3>-->
        <img alt="Vue logo" :src="Logo" :width="isCollapse ? 30: 100">
      </router-link>
    </div>
    <div class="indexlayout-left-menu">
      <el-scrollbar class="flex-scrollbar" wrap-class="default-scrollbar__wrap">
        <el-menu
            :background-color="variables.menuBg"
            :text-color="variables.menuText"
            :default-active="getSidebarMenuActive"
            :collapse="isCollapse"
            :collapse-transition="false">

          <template v-if="!siteTopNavEnable">
            <sidebar-menu-item v-for="route in menus" :key="route.path" :routes="route"
                               :base-path="route.path" :resolve-path="route.path"
                               :active-top-menu="getTopMenuActive"/>
          </template>
          <template v-else>
            <template v-for="route in menus">
              <template v-if="!route.hidden">

                <sidebar-menu-item v-for="route2 in route.children" :key="route.path + '/' + route2.path"
                                   :routes="route2" :base-path="route.path" :resolve-path="route.path"
                                   :active-top-menu="getTopMenuActive"/>

              </template>
            </template>
          </template>

        </el-menu>

      </el-scrollbar>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import variables from '@/assets/css/variables.scss'
import SidebarMenuItem from '@/components/layout/components/SidebarMenuItem'
import { getBelongTopMenuPath, getActiveSidebarMenuPath, getMenus } from '@/common/utlis/permission'
import Logo from '@/assets/images/logo.png'

export default {
  name: 'LayoutIndexLeft',
  components: {
    SidebarMenuItem
  },
  data () {
    return {
      Logo
    }
  },
  computed: {
    ...mapGetters([
      'siteTopNavEnable',
      'siteSidebarLogo',
      'sidebarOpened'
    ]),
    menus () {
      return getMenus()
      // const menus = []
      // this.$store.state.permission.routes.forEach(item => {
      //   if (item.children && item.children.length > 0) {
      //     const oneChild = this.showOneChild(item.children)
      //     if (oneChild) {
      //       menus.push({ ...oneChild, path: `${item.path}/${oneChild.path}`.replace('//', '/') })
      //       item.hidden = true
      //     }
      //   }
      //   menus.push(item)
      // })
      // return menus
    },
    variables () {
      return variables
    },
    isCollapse () {
      return !this.sidebarOpened
    },
    getSidebarMenuActive: function () {
      const route = this.$route
      return getActiveSidebarMenuPath(route)
    },
    getTopMenuActive () {
      let route = this.$route
      return getBelongTopMenuPath(route)
    }
  },
  methods: {
    // showOneChild(children = []) {
    //   let oneChild = {}
    //   const showChildren = children.filter(item => {
    //     if (item.hidden) {
    //       return false
    //     } else {
    //       if (item.meta.showOne) {
    //         oneChild = { ...item }
    //         return true
    //       }
    //       return false
    //     }
    //   })
    //   if (showChildren.length === 1) {
    //     return oneChild
    //   }
    //   return false
    // }
  }
}
</script>
<style lang="scss" scoped>
@import "~@/assets/css/variables.scss";

#indexlayout-left {
  display: flex;
  flex-direction: column;
  width: $leftSideBarWidth;
  height: 100vh;
  background-color: $leftBgColor;
  color: $topMenuFontColor;
  transition-duration: 0.1s;

  .indexlayout-left-logo {
    width: 100%;
    height: $headerHeight;
    line-height: $headerHeight;
    text-align: center;
    vertical-align: middle;
    /* background-color: $subMenuBg; */
    .logo-url {
      display: inline-block;
      width: 100%;
      height: 100%;
      overflow: hidden;

      .logo-title {
        display: inline-block;
        margin: 0;
        font-size: 16px;
        font-family: Roboto, sans-serif;
        color: $topMenuFontColor;
      }
    }

    img {
      vertical-align: middle;
    }

  }

  .indexlayout-left-menu {
    flex: 1;
    display: flex;
    overflow: auto;
    // height: calc(100vh);
    /* .el-scrollbar {
        flex:1;
    } */
  }

  &.narrow {
    width: $leftSideBarMinWidth;
  }
}
</style>
