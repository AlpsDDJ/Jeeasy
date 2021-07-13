<template>
  <el-container class="main-box">
    <el-header>
      <header-bar/>
    </el-header>
    <el-container>
      <el-aside width="260px">
        <nav-bar/>
      </el-aside>
      <el-container>
        <el-main>
          <tab-bar></tab-bar>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import HeaderBar from './HeaderBar'
import NavBar from './NavBar'
import TabBar from './TabBar'
export default {
  name: 'Layout',
  components: { TabBar, NavBar, HeaderBar },
  mounted () {
    console.log('--------------------------')
  }
}
</script>

<style>
  .main-box{
    min-height: 100vh;
  }
 .el-header, .el-footer {
   background-color: #B3C0D1;
   color: #333;
   text-align: center;
   line-height: 60px;
 }

 .el-aside {
   background-color: #D3DCE6;
   color: #333;
   text-align: center;
   line-height: 200px;
 }

 .el-main {
   background-color: #E9EEF3;
   color: #333;
   text-align: center;
   line-height: 160px;
 }

 body > .el-container {
   margin-bottom: 40px;
 }

 .el-container:nth-child(5) .el-aside,
 .el-container:nth-child(6) .el-aside {
   line-height: 260px;
 }

 .el-container:nth-child(7) .el-aside {
   line-height: 320px;
 }
</style>
