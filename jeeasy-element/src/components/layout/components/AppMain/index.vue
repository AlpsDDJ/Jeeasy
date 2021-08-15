<template>
  <div>
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive" :key="key">
        <!-- 这里是会被缓存的视图组件，比如 Home！ -->
      </router-view>
    </keep-alive>

    <router-view v-if="!$route.meta.keepAlive" :key="key">
      <!-- 这里是不被缓存的视图组件，比如 Edit！ -->
    </router-view>
  </div>
</template>
<script>
/**
 * 公用 主窗口
 * @author LiQingSong
 */
export default {
  name: 'AppMain',
  computed: {
    key() {
      return this.$route.path
    }
  }
}
</script>
