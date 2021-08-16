<template>
  <el-row class="doc-iframe">
    <iframe id="docIFrame" ref="docIFrame" :src="src" frameborder="0" scrolling="no"/>
  </el-row>
</template>

<script>
import { apiDocUrl } from '@/settings'

export default {
  name: 'index',

  mounted () {
    /**
     * iframe-宽高自适应显示
     */
    const oIframe = document.getElementById('docIFrame')
    // const deviceWidth = document.documentElement.clientWidth
    const deviceHeight = document.documentElement.clientHeight
    // oIframe.style.width = (Number(deviceWidth) - 220) + 'px' //数字是页面布局宽度差值
    oIframe.style.height = (Number(deviceHeight) - 220) + 'px' //数字是页面布局高度差
  },

  computed: {
    src () {
      return apiDocUrl
      // return `${process.env.VUE_APP_APIHOST}/doc.html`
    }
  }
}
</script>

<style lang="scss" scoped>
.doc-iframe {
  width: 100%;
  padding: 5px;

  iframe {
    width: 100%;
    border: none;
    display: block;
  }
}

</style>
