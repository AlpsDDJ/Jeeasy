<template>
  <div id="app" :class="this.$store.state.app.theme">
    <router-view/>
  </div>
</template>
<script>
export default {
  name: 'App'
}
</script>
