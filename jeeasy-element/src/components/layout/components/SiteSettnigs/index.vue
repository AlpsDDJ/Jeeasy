<template>
<div>
    <div class="site-settings" @click="drawer = true">
        <svg-icon icon-class="set" />
    </div>
    <el-drawer
        custom-class="custom-drawer"
        :visible.sync="drawer"
        size="380px"
        append-to-body
        title="系统布局配置">
        <el-form label-width="200px" label-position="left" size="mini">
            <el-form-item label="启用顶部导航">
              <el-radio-group v-model="setTheme">
                <el-radio-button  label="default">默认</el-radio-button >
                <el-radio-button  label="bg-img">bgImg</el-radio-button >
              </el-radio-group>
            </el-form-item>
            <el-form-item label="启用顶部导航">
               <el-switch v-model="topNavEnable"></el-switch>
            </el-form-item>
            <el-form-item label="固定右侧头部">
               <el-switch v-model="fiexdHeader" :disabled="topNavEnableChildDisabled"></el-switch>
            </el-form-item>
            <el-form-item label="显示侧边栏LOGO">
               <el-switch v-model="sidebarLogo" :disabled="topNavEnableChildDisabled"></el-switch>
            </el-form-item>
        </el-form>
    </el-drawer>
</div>
</template>
<script>
export default {
    name: 'SiteSettings',
    data() {
      return {
        drawer: false
      }
    },
    computed: {
        topNavEnable: {
            get() {
                return this.$store.state.app.siteTopNavEnable
            },
            set(val) {
                // 启用顶部导航如果启用了，(固定右侧头部,显示侧边栏LOGO)必须启用
                if (val) {
                    this.fiexdHeader = val
                    this.sidebarLogo = val
                }
                this.$store.commit('app/SET_SITE_TOPNAV_ENABLE',val)
            }
        },
        fiexdHeader: {
            get() {
                return this.$store.state.app.siteFiexdHeader
            },
            set(val) {
                this.$store.commit('app/SET_SITE_FIEXD_HEADER',val)
            }
        },
        sidebarLogo: {
            get() {
                return this.$store.state.app.siteSidebarLogo
            },
            set(val) {
                this.$store.commit('app/SET_SITE_SIDEBAR_LOGO',val)
            }
        },
        setTheme: {
            get() {
              return this.$store.state.app.theme
            },
            set(val) {
              this.$store.dispatch('app/setTheme',val)
              // const themeNode = document.querySelector('#element-theme')
              // if(val !== 'default') {
              //   import(`@/assets/theme/${val}/index.scss`)
              //   this.$nextTick(() => {
              //     const styles = document.querySelectorAll('head style')
              //     styles.item(styles.length - 1).id = 'element-theme'
              //     console.log('styles ===>>>> ', styles)
              //   })
              //   // const len = styles.length
              //   // console.log('length ===> ', len)
              //   // console.log(styles.item(len - 2))
              //   // const theme = require(`@/assets/theme/${val}/index.scss`)
              //   // themeNode.append(theme)
              //   // import(`@/assets/theme/${val}/index.scss`).then(module => {
              //   //   themeNade.append(module)
              //   //   // module.loadPageInto()
              //   //   // console.log(module)
              //   //   // module.value
              //   //   // module.attribute.id = 'theme'
              //   //   // console.log(module.attribute)
              //   // })
              // } else {
              //   console.log(document.querySelector('#element-theme:after'))
              // }
            }
        },
        // =================
        // 启用顶部导航的值决定着 (固定右侧头部,显示侧边栏LOGO)是否可设置
        topNavEnableChildDisabled: function() {
            return this.topNavEnable
        }

    }
}
</script>
<style lang="scss" scoped>
@import "~@/assets/css/variables.scss";
.site-settings{
    position: fixed;
    display: block;
    width: 45px;
    height: 45px;
    line-height: 45px;
    right: 0;
    top: 30%;
    background-color: $leftBgColor;
    color: $leftFontColor;
    text-align: center;
    font-size: 20px;
    cursor: pointer;
    border-radius: 5px 0 0 5px!important;
    &:hover{
        background-color: $leftMenuActiveBgColor;
        color: $leftMenuActiveFontColor;
    }
}
</style>
