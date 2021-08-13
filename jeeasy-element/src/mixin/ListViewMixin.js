import { parseApi } from '@/service/lib/request'
import JeSearchForm from '@/components/jeeasy/JeSearthForm'
import JeTable from '@/components/jeeasy/JeTable'
import { mapGetters } from 'vuex'


export default {
  data () {
    return {
      list: [],
      fields: [],
      labels: [],
      columns: [],
      query: {
        page: {
          current: 1,
          size: 10
        },
        form: {}
      },
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
    api() {
      return parseApi(this.baseApi)
    },
    queryRequestParams() {
      const { current, size } = this.query.page
      return  {
        current,
        size,
        ...this.query.form
      }
    },
    column() {
      return Object.values(this.fields).filter(field => field !== 'id').map(field => {
        return {
          key: field,
          label: this.labels[field]
        }
      })
    },
    searchParams(){
      return this.columns.filter(({ search = false }) => search)
    },
    formFields(){
      return this.columns.filter(({ form = {} }) => form)
    }
  },
  methods: {
    beforeLoad (params) {
      return Promise.resolve(params)
    },
    async loadData() {
      await this.beforeLoad(this.queryRequestParams).then(params => {
        console.log('loadData')
        this.$ajax(this.api.list, params).then(({ records, ...page }) => {
          this.query.page = page
          this.list = records
        })
      })
    }
  }
}
