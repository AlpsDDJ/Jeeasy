<template>
  <div class="main-conent main-conent-minheight">
    <je-search-form v-model="query.form" :search-params="searchParams" @submit="loadData" />
    <je-table :columns="column" :data="list" tableTitle="用户列表" v-model="query.page" @pageChange="loadData" :loading="loading[api.list]" selection>
      <template slot="username" slot-scope="{username}">{{ username }}</template>
      <template slot="operate" slot-scope="record">
        <el-button type="text" @click="() => { handleEdit(record) }">编辑</el-button>
      </template>
      <template slot="top-tools">
        <el-button @click="handleAdd" title="111">新增</el-button>
      </template>
    </je-table>
    <el-dialog :visible.sync="formVisible" width="600" :title="formTitle" :close-on-click-modal="false">
      <je-form v-model="formData" :fields="formFields" @submit="handleFormSubmit" @cancel="handleFormCancel"></je-form>
    </el-dialog>
  </div>
</template>

<script type="text/jsx">
import ListViewMixin from '@/mixin/ListViewMixin'
import JeForm from '@/components/jeeasy/JeForm/index'
import fields, { labels } from './form/fields'
// import DictSelect from '@/components/jeeasy/DictSelect'

const formTypes = {
  ADD: '新增',
  EDIT: '编辑',
  VIEW: '查看'
}

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
      columns: [
        {
          label: labels[fields.username],
          key: fields.username,
          slot: fields.username,
          search: true
        },
        {
          label: '姓名',
          key: 'realName',
          search: true
        },
        {
          label: '性别',
          key: 'sex',
          form: () => (<dict-select v-model={this.formData.sex} dict-code={'sex'} type="radio-button"/>)
        },
        {
          label: '电话',
          key: 'phone',
          search: true
        },
        {
          label: '状态',
          key: 'status',
          form: () => <dict-select v-model={this.formData.status} dict-code={'sys_user_status'}/>,
          search: () => <dict-select v-model={this.query.form.status} dict-code={'sys_user_status'} />
        }
        /*{
         label: '操作',
         key: 'operate',
         customRender: ({record}) => {
         return (
         <el-button type="text" onClick={() => { record }}>编辑</el-button>
         )
         }
         }*/
      ]
    }
  },
  computed: {
    formTitle() {
      const title = this.$router.currentRoute.meta['title']
      return `${this.formType}${title.replace(/(管理)|(列表)/g, '')}`
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    handleFormSubmit() {
      let api = ''
      switch (this.formType) {
        case formTypes.ADD:
          api = this.api.add
          break
        case formTypes.EDIT:
          api = this.api.edit
      }
      if (api) {

        const params = {
          user: this.formData,
          roles: '',
          departs: ''
        }

        this.$ajax(api, params).then(({result: {message}}) => {
          this.$message.success(message)
          this.formVisible = false
          this.loadData()
        })
      }
    },
    handleFormCancel() {
      this.formVisible = false
    },
    handleAdd() {
      this.formData = {}
      this.formType = formTypes.ADD
      this.formVisible = true
    },
    handleEdit(record) {
      console.log(record)
      this.formType = formTypes.EDIT
      this.formData = { ...record }
      this.formVisible = true
    }
  }
}
</script>

<style lang="scss">

</style>
