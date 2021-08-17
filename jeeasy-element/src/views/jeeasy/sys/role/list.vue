<template>
  <div class="main-content main-content-minheight">
    <je-search-form v-model="queryForm" :search-params="searchFields" @submit="loadData"/>
    <je-table
        v-model="queryPage"
        @pageChange="loadData"
        v-bind="tableConfig">
      <template slot="operate" slot-scope="record">
        <el-button type="text" @click="() => { handleEdit(record) }">编辑</el-button>
      </template>
      <template slot="top-tools">
        <el-button @click="handleAdd" title="111">新增</el-button>
      </template>
    </je-table>
    <el-dialog :visible.sync="formVisible" width="600px" :title="formTitle" :close-on-click-modal="false">
      <je-form v-model="formData" :fileds="formFields" @submit="handleFormSubmit" @cancel="handleFormCancel"/>
    </el-dialog>
  </div>
</template>

<script type="text/jsx">
import ListViewMixin from '@/mixin/ListViewMixin'
import JeForm from '@/components/jeeasy/JeForm/index'
import fields, { labels } from './role'

export default {
  name: 'SysRoleList',
  components: {JeForm},
  mixins: [ListViewMixin],
  data () {
    return {
      baseApi: '/sys/role',
      fields,
      labels
    }
  },
  mounted () {
    this.init()
  },
  computed: {

    columnOptions () {
      return {
        [fields.enableFlag]: {
          form: () => <dict-select v-model={this.formData.enableFlag} dict-code={'enable_flag'}/>,
          search: () => <dict-select v-model={this.queryForm.enableFlag} dict-code={'enable_flag'}/>
        }
      }
    }
  },
  methods: {}
}
</script>

<style lang="scss">

</style>
