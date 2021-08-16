<template>
  <div class="main-content main-content-minheight">
    <je-search-form v-model="queryForm" :search-params="searchFields" @submit="loadData" />
    <je-table :columns="columns" :data="list" tableTitle="用户列表" v-model="query.page" @pageChange="loadData" :loading="loading[api.list]" show-selection>
      <template slot="username" slot-scope="{username}">{{ username }}</template>
      <template slot="operate" slot-scope="record">
        <el-button type="text" @click="() => { handleEdit(record) }">编辑</el-button>
      </template>
      <template slot="top-tools">
        <el-button @click="handleAdd" title="111">新增</el-button>
      </template>
    </je-table>
    <el-dialog :visible.sync="formVisible" width="600px" :title="formTitle" :close-on-click-modal="false">
      <je-form v-model="formData" :fileds="formFields" :colspan="24" @submit="handleFormSubmit" @cancel="handleFormCancel"></je-form>
    </el-dialog>
  </div>
</template>

<script type="text/jsx">
import ListViewMixin from '@/mixin/ListViewMixin'
import JeForm from '@/components/jeeasy/JeForm/index'
import fields, { labels } from './user'

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
      labels
    }
  },
  mounted() {
    this.loadData()
  },
  computed: {
    columnOptions() {
      return {
        [fields.roles]: {
          customRender: ({ record }) => record.roles?.map(roles => roles?.roleName).join(),
          form: () => <dict-select v-model={this.formData.roles} dict-code={'@sys_role'} multiple />,
          search: () => <dict-select v-model={this.queryForm.roleId} dict-code={'@sys_role'} />
        },
        [fields.departs]: {
          customRender: ({ record }) => record.departs?.map(depart => depart?.departName).join(),
          form: () => <dict-select v-model={this.formData.departs} dict-code="@sys_depart" multiple />,
          search: () => <dict-select v-model={this.queryForm.departs} dict-code="@sys_depart" />
        },
        [fields.status]: {
          form: () => <dict-select v-model={this.formData.status} dict-code={'sys_user_status'} />,
          search: () => <dict-select v-model={this.queryForm.status} dict-code={'sys_user_status'} />
        },
        [fields.sex]: {
          form: () => (<dict-select v-model={this.formData.sex} dict-code={'sex'} type="radio-button" />)
        }
      }

    }
  },
  methods: {
    beforeSubmit({ roles = [], departs = [], ...user }, api) {
      const params = {
        user,
        roles: roles.join(','),
        departs: departs.join(',')
      }
      return Promise.resolve({ params, api })
    },
    beforeEdit({ departs = [], roles = [], ...params }) {
      return Promise.resolve({
        roles: roles.map(({ id }) => id),
        departs: departs.map(({ id }) => id),
        ...params
      })
    }
  }
}
</script>

<style lang="scss">

</style>
