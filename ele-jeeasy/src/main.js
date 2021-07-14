// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
// 生产环境中注释掉以下语句
import './assets/css/theme-default.scss'

import 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import router from '@/router'
import store from '@/store'
import ElementUI from 'element-ui'
import i18n from '@/util/i18n'
import axios from '@/util/service'
import less from 'less'
import 'element-ui/lib/theme-chalk/index.css'

Vue.config.productionTip = false

Vue.use(less)
Vue.prototype.$axios = axios
Vue.use(ElementUI, {
  i18n: (key, value) => i18n.t(key, value)
})

new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount('#app')
