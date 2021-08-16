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
      columnOptions: {},
      formTypes,
      query: {
        page: {
          current: 1,
          size: 10
        },
        form: {}
      },
      queryForm: {},
      baseApi: ''
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
    api () {
      return parseApi(this.baseApi)
    },
    queryRequestParams () {
      const {current, size} = this.query.page
      return {
        current,
        size,
        ...this.queryForm
      }
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
    loadData () {
      this.beforeLoad(this.queryRequestParams).then(params => {
        // console.log('loadData')
        this.$ajax(this.api.list, params).then(({result: {records, ...page}}) => {
          // this.query.page = page
          // this.list = records
          this.afterLoad(records, page)
        })
      })
    },
    afterLoad(list, page){
      this.query.page = page
      this.list = list
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
            this.loadData()
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
