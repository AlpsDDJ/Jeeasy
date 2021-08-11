<template>
  <div class="main-conent main-conent-minheight">
    <je-search-form v-model="query.form" :search-params="searchParams" @submit="loadData"/>
    <je-table :columns="columns" :data="list" tableTitle="用户列表" v-model="query.page" @pageChange="loadData" :loading="loading[api.list]"/>
  </div>
</template>

<script type="text/jsx">
import JeTable from '@/components/jeeasy/JeTable'
import JeSearchForm from '@/components/jeeasy/JeSearthForm'
import { parseApi } from '@/service/lib/request'
import { mapGetters } from 'vuex'

export default {
  name: 'SysUserList',
  data() {
    return {
      list: [],
      columns: [
        {
          label: '用户名',
          key: 'username',
          slot: 'username',
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
          search: () => {
            return <el-select/>
          }
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
      ],
      query: {
        page: {
          current: 1,
          size: 3
        },
        form: {}
      },
      api: parseApi('/sys/user')
    }
  },
  components: {
    JeSearchForm,
    JeTable
  },
  mounted() {
    this.loadData()
  },
  computed: {
    ...mapGetters([
      'loading'
    ]),
    searchParams(){
      return this.columns.filter(({ search = false }) => search)
    }
  },
  methods: {
    loadData() {
      const { current, size } = this.query.page
      const params = {
        current,
        size,
        ...this.query.form
      }
      this.$ajax(this.api.list, params).then(({ result }) => {
        const { records, ...page } = result
        this.query.page = page
        this.list = records
      })
    }
  }
}
</script>

<style lang="scss">
</style>
