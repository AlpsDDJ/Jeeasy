<template>
  <div class="main-conent main-conent-minheight">
    <je-search-form v-model="query.form" :search-params="searchParams" @submit="loadData"/>
    <je-table :columns="columns" :data="list" tableTitle="用户列表" v-model="query.page" @pageChange="loadData"/>
  </div>
</template>

<script type="text/jsx">
import JeTable from '@/components/jeeasy/JeTable'
import JeSearchForm from '@/components/jeeasy/JeSearthForm'
import { parseApi } from '@/service/lib/request'

export default {
  name: 'SysUserList',
  data() {
    return {
      // :xs="24" :sm="24" :md="12" :lg="8" :xl="8"
      /*searchLayout: {
        xs: 24,
        sm: 24,
        md: 12,
        lg: 6,
        xl: 6
      },
      searchOpen: false,*/
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
          search: (opt) => {
            console.log(opt)
            return <el-select/>
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
