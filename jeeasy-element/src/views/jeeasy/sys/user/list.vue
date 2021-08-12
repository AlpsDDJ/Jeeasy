<template>
  <div class="main-conent main-conent-minheight">
    <je-search-form v-model="query.form" :search-params="searchParams" @submit="loadData"/>
    <je-table :columns="column" :data="list" tableTitle="用户列表" v-model="query.page" @pageChange="loadData" :loading="loading[api.list]" selection>
      <template slot="username" slot-scope="{username}">{{ username }}</template>
      <template slot="operate" slot-scope="record">
        <el-button type="text" @click="() => { handleEdit(record) }">编辑</el-button>
      </template>
      <template slot="top-tools">
        <el-button @click="handleAdd" title="111">新增</el-button>
      </template>
    </je-table>
    <el-dialog :visible.sync="formVisible" width="600">
      <je-form v-model="formData" :fields="formFields"></je-form>
    </el-dialog>
  </div>
</template>

<script type="text/jsx">
import ListViewMixin from '@/mixin/ListViewMixin'
import JeForm from '@/components/jeeasy/JeForm/index'
import fields, { labels } from './form/fields'

export default {
  name: 'SysUserList',
  components: {JeForm},
  mixins: [ListViewMixin],
  data () {
    return {
      baseApi: '/sys/user',
      formVisible: false,
      formData: {},
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
          render: {
            form: () => {
            }
          },
          search: () => (
              <el-select v-model={this.query.form.status}>
                <el-option label="全部" value=""/>
                <el-option label="正常" value="1"/>
                <el-option label="冻结" value="0"/>
              </el-select>
          )
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
  },
  mounted () {
    this.loadData()
  },
  methods: {
    handleAdd () {
      this.formData = {}
      this.formVisible = true
    },
    handleEdit (record) {
      console.log(record)
      this.formData = {...record}
      this.formVisible = true
    }
  }
}
</script>

<style lang="scss">

</style>
