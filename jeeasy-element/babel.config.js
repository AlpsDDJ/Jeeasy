module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset',
    ['@vue/babel-preset-jsx', {
      'injectH': false
    }]
  ],
  // plugins: ['@babel/plugin-syntax-jsx']
  plugins: ['@babel/plugin-syntax-jsx', 'transform-vue-jsx']
}
