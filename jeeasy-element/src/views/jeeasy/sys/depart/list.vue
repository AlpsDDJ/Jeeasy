<template>
  <div class="main-content main-content-minheight">
    <je-search-form v-model="queryForm" :search-params="searchFields" @submit="loadTreeData" />
    <je-table v-model="queryPage"
              v-bind="tableConfig"
              ref="main-table"
              >
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
// import TreeViewMixin from '@/mixin/TreeViewMixin'
import JeForm from '@/components/jeeasy/JeForm/index'
import fields, { labels } from './depart'

export default {
  name: 'SysUserList',
  components: { JeForm },
  // mixins: [TreeViewMixin],
  mixins: [ListViewMixin],
  data() {
    return {
      baseApi: '/sys/depart',
      // formVisible: false,
      // formData: {},
      // formType: '',
      showPage: false,
      isTree: true,
      fields,
      labels
    }
  },
  mounted() {
    this.init({ parentId: 0 })
  },
  computed: {
    columnOptions() {
      return {
        [fields.departName]: {
          align: 'left'
        },
        [fields.orgCategory]: {
          type: 'dict:org_category'
        },
        [fields.parentId]: {
          type: 'tree:@sys_depart',
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
    // loadData (exParams = {}) {
    //   this.beforeLoad(this.queryRequestParams(exParams)).then(params => {
    //     console.log('params     ', params)
    //     this.$ajax(this.api.list, params).then((data) => {
    //       this.afterLoad(data)
    //     })
    //   })
    // },
    // loadTreeData(param = 0, treeNode, resolve) {
    //   if (typeof param === 'object') {
    //     const { id } = param
    //     this.loadData({ parentId: id }).then(({ result }) => {
    //       console.log(result)
    //       resolve(result)
    //     })
    //   } else {
    //     this.init({ parentId: param })
    //   }
    // }
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
.el-table__expand-icon{
  color: #d9d9d9;
}

</style>
