<script>
// import service from '@/service/lib/request'

export default {
  name: 'DictSelect',
  data() {
    return {
      val: '',
      options: [],
      url: '/common/dicts/{dictCode}'
    }
  },
  mounted() {
    if(this.type !== 'tree') {
      this.loadOptions()
    }
  },
  methods: {
    loadOptions(parentId = 0, resolve) {
      const params = { dictCode: this.dictCode }
      if (this.type === 'tree') {
        params.parentId = parentId
      }
      console.log('========================== ', params)
      this.$ajax(this.url, params).then(({ result }) => {
        this.options = result
        if(this.type === 'tree') {
          resolve(result)
        }
      })
      // this.load(params).then(({ result }) => {
      //   this.options = result
      //   resolve(result)
      // })
    }
  },
  props: {
    value: {
      type: [String, Number, Array]
    },
    dictCode: {
      type: String
    },
    type: {
      type: String,
      default: 'select'
    },
    load: {
      type: Function,
      default: function (params) {
        return this.$ajax(this.url, params)
      }
    }
  },
  watch: {
    value: {
      handler(val) {
        this.val = val
      },
      immediate: true
    },
    val: {
      handler(val) {
        this.$emit('input', val)
      }
    }
  },
  computed: {
    attrs() {
      const defaultArrts = {
        clearable: true,
        loading: this.$store.getters.loading[this.url]
      }
      return Object.assign(defaultArrts, this.$attrs)
    },
    treeAttrs() {
      return {
        lazy: true,
        checkStrictly: true,
        value: 'dictCode',
        label: 'dictName',
        lazyLoad: ({ value, level }, resolve) => {
          const params = { dictCode: this.dictCode }
          const parentId = level === 0 ? 0 : value
          if (this.type === 'tree') {
            params.parentId = parentId
          }
          console.log('========================== ', params)
          this.$ajax(this.url, params).then(({ result }) => {
            if(this.type === 'tree') {
              resolve(result)
            }
          })
        }
      }
    }
  },
  render() {
    const type = this.type
    switch (type) {
      case 'select':
        return (
            <el-select v-model={this.val} props={this.attrs}>
          {
            this.options.map(option => <el-option value={option.dictCode} label={option.dictName} />)
          }
          </el-select>
        )
      case 'radio':
      case 'radio-button':
        return (
            <el-radio-group v-model={this.val} props={this.attrs}>
              {
                this.options.map(option => (
                    type === 'radio-button'
                        ?
                        <el-radio-button label={option.dictCode}>{option.dictName}</el-radio-button>
                        :
                        <el-radio label={option.dictCode}>{option.dictName}</el-radio>))
              }
            </el-radio-group>
        )
      case 'tree':
        return (
            <el-cascader props={{ props: this.treeAttrs }} />
        )
      default:
        return <span />
    }
  }
}
</script>

<style lang="scss">
</style>
