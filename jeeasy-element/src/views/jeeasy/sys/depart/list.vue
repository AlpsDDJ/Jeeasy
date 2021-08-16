<template>
  <div class="main-content main-content-minheight">
    <je-search-form v-model="queryForm" :search-params="searchFields" @submit="loadData"/>
    <je-table :columns="columns" :data="list" tableTitle="用户列表" v-model="query.page" @pageChange="loadData" :loading="loading[api.list]" show-selection>
      <template slot="operate" slot-scope="record">
        <el-button type="text" @click="() => { handleEdit(record) }">编辑</el-button>
      </template>
      <template slot="top-tools">
        <el-button @click="handleAdd">新增</el-button>
      </template>
    </je-table>
    <el-dialog :visible.sync="formVisible" width="800px" :title="formTitle" :close-on-click-modal="false">
      <je-form v-model="formData" :fileds="formFields" :colspan="12" @submit="handleFormSubmit" @cancel="handleFormCancel"></je-form>
    </el-dialog>
  </div>
</template>

<script type="text/jsx">
import ListViewMixin from '@/mixin/ListViewMixin'
import JeForm from '@/components/jeeasy/JeForm/index'
import fields, { labels } from './depart'

export default {
  name: 'SysUserList',
  components: {JeForm},
  mixins: [ListViewMixin],
  data () {
    return {
      baseApi: '/sys/depart',
      formVisible: false,
      formData: {},
      formType: '',
      fields,
      labels
    }
  },
  mounted () {
    this.loadData()
  },
  computed: {
    columnOptions () {
      return {
        [fields.orgCategory]: {
          type: 'dict:org_category'
        },
        [fields.parentId]: {
          type: 'dict:@sys_depart',
          formHidden: (this.formData || {})[fields.orgType] === 1
        },
        [fields.orgType]: {
          type: 'dict:org_type'
        },
        [fields.enableFlag]: {
          type: 'dict:enable_flag'
        }
      }
    }
  },
  methods: {
    // beforeSubmit({ roles = [], departs = [], ...user }, api) {
    //   const params = {
    //     user,
    //     roles: roles.join(','),
    //     departs: departs.join(',')
    //   }
    //   return Promise.resolve({ params, api })
    // },
    // beforeEdit({ departs = [], roles = [], ...params }) {
    //   return Promise.resolve({
    //     roles: roles.map(({ roleName }) => roleName),
    //     departs: departs.map(({ departName }) => departName),
    //     ...params
    //   })
    // }
  }
}
</script>

<style lang="scss">

</style>
