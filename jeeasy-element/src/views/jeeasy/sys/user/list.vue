<template>
  <div class="main-conent main-conent-minheight">
    <je-search-form v-model="query.form" :search-params="searchParams" @submit="loadData"/>
    <je-table :columns="columns" :data="list" tableTitle="用户列表" v-model="query.page" @pageChange="loadData" :loading="loading[api.list]">
      <template slot="tools" slot-scope="{currentRow, selection}">
        <el-button @click="() => { console.log(currentRow, selection) }" title="111">222</el-button>
      </template>
    </je-table>
  </div>
</template>

<script type="text/jsx">
import ListViewMixin from '@/mixin/ListViewMixin'

export default {
  name: 'SysUserList',
  mixins: [ListViewMixin],
  data() {
    return {
      baseApi: '/sys/user',
      columns: [
        {
          label: '用户名',
          key: 'username',
          search: true
        },
        {
          label: '姓名',
          key: 'realName',
          search: true
        },
        {
          label: '性别',
          key: 'sex'
        },
        {
          label: '电话',
          key: 'phone',
          search: true
        },
        {
          label: '状态',
          key: 'status',
          search: () => (
            <el-select v-model={this.query.form.status}>
              <el-option label="全部" value=""/>
              <el-option label="正常" value="1"/>
              <el-option label="冻结" value="0"/>
            </el-select>
          )
        },
        {
          label: '操作',
          key: 'operate',
          customRender: ({record}) => {
            return (
              <el-button type="text" onClick={() => { record }}>编辑</el-button>
            )
          }
        }
      ]
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
  }
}
</script>

<style lang="scss">
</style>
