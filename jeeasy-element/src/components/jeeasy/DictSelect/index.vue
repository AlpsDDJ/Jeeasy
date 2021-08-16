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
    this.loadOptions()
  },
  methods: {
    loadOptions() {
      this.load().then(({result}) => {
        this.options = result
      })
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
      default: function () {
        return this.$ajax(this.url, { dictCode: this.dictCode })
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
      default:
        return <span />
    }
  }
}
</script>

<style lang="scss">
</style>
