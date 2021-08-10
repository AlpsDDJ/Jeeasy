/**
 * vue-cli 配置文件
 * @author LiQingSong
 */
const path = require('path')

function resolve(dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  publicPath: '/',
  outputDir: 'dist',
  productionSourceMap: false,
  devServer: {
    port: 4396,
    // 配置反向代理
    proxy: {
      '/api': {
        target: 'http://localhost:8888',
        pathRewrite: {
          '^/api': ''
        },
        ws: true,
        changeOrigin: true
      }
    }
  },
  // 修改webpack的配置
  configureWebpack: {
    // 不需要打包的插件
    externals: {
      // 'vue': 'Vue',
      // 'vue-router': 'VueRouter',
      // 'element-ui': 'ELEMENT'
    }
  },
  chainWebpack(config) {
    config.resolve.alias
      .set('vue$', 'vue/dist/vue.esm.js')
      .set('@', path.resolve(__dirname, './src'))
    // set svg-sprite-loader
    config.module
    .rule('svg')
    .exclude.add(resolve('src/assets/icons/svg'))
    .end()
    config.module
    .rule('icons')
    .test(/\.svg$/)
    .include.add(resolve('src/assets/icons/svg'))
    .end()
    .use('svg-sprite-loader')
    .loader('svg-sprite-loader')
    .options({
      symbolId: 'icon-[name]'
    })
    .end()

  }
}
