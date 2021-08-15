<template>
  <div class="main-conent main-conent-minheight">
    <je-search-form v-model="query.form" :search-params="searchFields" @submit="loadData" />
    <je-table :columns="columns" :data="list" tableTitle="用户列表" v-model="query.page" @pageChange="loadData" :loading="loading[api.list]" selection>
      <template slot="username" slot-scope="{username}">{{ username }}</template>
      <template slot="operate" slot-scope="record">
        <el-button type="text" @click="() => { handleEdit(record) }">编辑</el-button>
      </template>
      <template slot="top-tools">
        <el-button @click="handleAdd" title="111">新增</el-button>
      </template>
    </je-table>
    <el-dialog :visible.sync="formVisible" width="600" :title="formTitle" :close-on-click-modal="false">
      <je-form v-model="formData" :role="formFields" @submit="handleFormSubmit" @cancel="handleFormCancel"></je-form>
    </el-dialog>
  </div>
</template>

<script type="text/jsx">
import ListViewMixin from '@/mixin/ListViewMixin'
import JeForm from '@/components/jeeasy/JeForm/index'
import fields, { labels } from './user'
// import DictSelect from '@/components/jeeasy/DictSelect'


export default {
  name: 'SysUserList',
  components: { JeForm },
  mixins: [ListViewMixin],
  data() {
    return {
      baseApi: '/sys/user',
      formVisible: false,
      formData: {},
      formType: '',
      fields,
      labels,
      columnOptions: {
        [fields.status]: {
          form: () => <dict-select v-model={this.formData.status} dict-code={'sys_user_status'}/>,
          search: () => <dict-select v-model={this.query.form.status} dict-code={'sys_user_status'} />
        },
        [fields.sex]: {
          form: () => (<dict-select v-model={this.formData.sex} dict-code={'sex'} type="radio-button"/>)
        }
      }
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    beforeSubmit ({roles, departs, ...user}, api) {
      const params = {
        user,
        roles,
        departs
      }
      return Promise.resolve({params, api})
    }
  }
}
</script>

<style lang="scss">

</style>
