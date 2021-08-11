import { parseApi } from '@/service/lib/request'
import JeSearchForm from '@/components/jeeasy/JeSearthForm'
import JeTable from '@/components/jeeasy/JeTable'
import { mapGetters } from 'vuex'


export default {
  data () {
    return {
      list: [],
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
    searchParams(){
      return this.columns.filter(({ search = false }) => search)
    }
  },
  methods: {
    beforeLoad (params) {
      return Promise.resolve(params)
    },
    async loadData() {
      await this.beforeLoad(this.queryRequestParams).then(params => {
        console.log('loadData')
        this.$ajax(this.api.list, params).then(({ result }) => {
          const { records, ...page } = result
          this.query.page = page
          this.list = records
        })
      })
    }
  }
}