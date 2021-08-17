import { parseApi } from '@/common/utlis/request'
import JeSearchForm from '@/components/jeeasy/JeSearthForm'
import JeTable from '@/components/jeeasy/JeTable'
import { mapGetters } from 'vuex'

const formTypes = {
  ADD: '新增',
  EDIT: '编辑',
  VIEW: '查看'
}

export default {
  data () {
    return {
      list: [],
      formVisible: false,
      formData: {},
      formType: '',
      fields: [],
      labels: [],
      formTypes,
      showPage: true,
      showSelection: true,
      queryPage: {
        current: 1,
        size: 10
      },
      queryForm: {},
      baseApi: '',
      isTree: false,
      rowKey: 'id',
      childrenKey: 'children',
      hasChildrenKey: 'hasChildren',
      tableChildMap: new Map()
    }
  },
  components: {
    JeSearchForm,
    JeTable
  },
  computed: {
    ...mapGetters([
      'loading'
    ]),
    tableConfig(){
      const defaultConfig = {
        columns: this.columns,
        data: this.list,
        showPage: this.showPage,
        loading: this.loading[this.api.list],
        showSelection: this.showSelection
      }
      return this.isTree ? Object.assign(defaultConfig, {
        columns: this.columns,
        data: this.list,
        loading: this.loading[this.api.list],
        showSelection: this.showSelection,
        rowKey: this.rowKey,
        lazy: true,
        load: this.loadTreeData
      }) : defaultConfig
    },
    columnOptions() {
      return {}
    },
    api () {
      return parseApi(this.baseApi)
    },
    columns () {
      return this.fields.columns().map(field => {
        const key = field.key
        return Object.assign(field, this.columnOptions[key] || {})
      })
    },
    searchFields () {
      return this.columns.filter(({search = false}) => search)
    },
    formFields () {
      return this.columns.filter(({form = {}}) => form)
    },
    formTitle () {
      const title = this.$router.currentRoute.meta['title']
      return `${this.formType}${title.replace(/(管理)|(列表)/g, '')}`
    }
  },
  methods: {
    beforeLoad (params) {
      return Promise.resolve(params)
    },
    beforeEdit (params) {
      return Promise.resolve(params)
    },
    beforeSubmit (params, api) {
      return Promise.resolve({params, api})
    },
    queryRequestParams (exParams = {}) {
      const {current, size} = this.queryPage
      if(this.showPage){
        return {
          current,
          size,
          ...this.queryForm,
          ...exParams
        }
      } else {
        return {
          ...this.queryForm,
          ...exParams
        }
      }
    },
    init(exParams){
      this.loadData(exParams).then((data) => {
        this.afterLoad(data)
      })
    },
    loadData (exParams = {}) {
      return new Promise((resolve, reject) => {
        this.beforeLoad(this.queryRequestParams(exParams)).then(params => {
          return this.$ajax(this.api.list, params).then(data => {
            resolve(data)
          }).catch(err => {
            reject(err)
          })
        })
      })
    },
    loadTreeData(tree = 0, treeNode, resolve) {
      if (typeof tree === 'object') {
        this.tableChildMap.set(tree.id, {tree, treeNode, resolve})
        const { id } = tree
        this.loadData({ parentId: id }).then(({ result }) => {
          console.log(result)
          resolve(result.records)
        })
      } else {
        this.init({ parentId: tree })
      }
    },
    refreshChild(parentId){
      const {tree, treeNode, resplve} = this.tableChildMap.get(parentId)
      this.$set(this.$refs['main-table'].store.states.lazyTreeNodeMap, parentId, [])
      if(tree){
        this.loadData(tree, treeNode, resplve)
      }
    },
    afterLoad(data){
      const {records, ...page} = data.result
      this.queryPage = page
      this.list = records
      if(this.showPage){
        this.queryPage = page
      } else {
        this.queryPage = {}
      }
      // this.$refs['main-table'].update()
      // console.log(data)
      // if(this.showPage){
      //   const {records, ...page} = data.result
      //   this.queryPage = page
      //   this.list = records
      // } else {
      //   this.list = data.result
      // }
    },
    async handleFormSubmit () {
      let submitApi = ''
      switch (this.formType) {
        case formTypes.ADD:
          submitApi = this.api.add
          break
        case formTypes.EDIT:
          submitApi = this.api.edit
      }
      if (submitApi) {
        await this.beforeSubmit(this.formData, submitApi).then(({params, api}) => {
          this.$ajax(api, params).then(({message}) => {
            this.$message.success(message)
            this.formVisible = false
            if(params.parentId === 0){
              this.loadData()
            }else {
              this.refreshChild(params.parentId)
            }
          })
        })
      }
    },
    handleFormCancel () {
      this.formVisible = false
    },
    handleAdd () {
      this.formData = {}
      this.formType = formTypes.ADD
      this.formVisible = true
    },
    handleEdit (record) {
      this.beforeEdit(record).then(params => {
        this.formType = formTypes.EDIT
        this.formData = params
        this.formVisible = true
      })
    }
  }
}
