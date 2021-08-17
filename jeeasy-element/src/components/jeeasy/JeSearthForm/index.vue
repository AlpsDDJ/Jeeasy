<script type="text/jsx">
export default {
  name: 'JeSearchForm',
  data() {
    return {
      searchOpen: false,
      formData: {}
    }
  },
  props: {
    value: {
      type: Object,
      default: () => ({})
    },
    searchParams: {
      type: Array,
      default: () => ([])
    }
  },
  watch: {
    value: {
      handler(val) {
        this.formData = val
      },
      immediate: true
    },
    formData: {
      handler(val) {
        this.$emit('input', val)
      },
      deep: true
    }
  },
  computed: {
    searchColLayout() {
      return {
        xs: 24,
        sm: 24,
        md: 12,
        lg: 6,
        xl: 6
      }
    },
    justify() {
      return !this.searchOpen ? 'start' : 'start'
      // return 'space-between'
    },
    // offset() {
    //   return (3 - this.searchParams.length) * 6
    // },
    component() {
      return (component) => {
        return component
      }
    }
  },
  methods: {
    submit() {
      this.$emit('submit')
    },
    reset() {
      this.formData = {}
      this.$nextTick(() => {
        this.submit()
      })
    },
    toggleSearch() {
      this.searchOpen = !this.searchOpen
    },
    renderItem() {
      return this.searchParams.map(({ slot, ...item }, index) => {
        if (index < 3 || this.searchOpen) {
          return (
            <el-col key={item.key}  props={this.searchColLayout}>
                <el-form-item label={item.label} prop={item.key}>
                  {
                    () => {
                      if(item.type) {
                        return this.createItemByType(item.type, item.key)
                      } else if (slot && this.$scopedSlots[slot]) {
                        return this.$scopedSlots[slot]()
                      } else if (item.search && typeof item.search === 'function') {
                        return this.component(item.search())
                      } else {
                        return <el-input v-model={this.formData[item.key]} />
                      }
                    }
                  }
                </el-form-item>
              </el-col>
          )
        }
      })
    },
    createItemByType(_type, key) {
      const [type, param = ''] = _type.split(':')
      switch (type){
        case 'dict':
          return <dict-select v-model={this.formData[key]} dict-code={param} />
        case 'tree':
        case 'radio':
        case 'ratio-button':
          return <dict-select type={type} v-model={this.formData[key]} dict-code={param} />
        case 'text':
          return <span>{this.formData[key]}</span>
        default:
          return ''
      }
    }
  },
  render() {
    return (
      this.searchParams && this.searchParams.length !== 0 &&<el-card shadow="never" class="list-search border-none">
          <el-form v-model={this.formData} label-width="100px" nativeOnKeyup={({ code }) => {
            if (code === 'Enter') this.submit()
          }}>
            <el-row justify={this.justify} >
              <slot />
              {
                this.renderItem(this)
              }
              <el-col props={this.searchColLayout} class="search-btns text-right">
                <el-form-item label-width="0">
                  <el-button type="primary" onClick={this.submit}>查询</el-button>
                  <el-button onClick={this.reset}>重置</el-button>
                  {
                    this.searchOpen ?
                      (<el-button type="text" onClick={this.toggleSearch}>展开<i class="el-icon-arrow-down el-icon--right" /></el-button>)
                      :
                      (<el-button type="text" onClick={this.toggleSearch}>收起<i class="el-icon-arrow-up el-icon--right" /></el-button>)
                  }
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
    )
  }
}
</script>

<style lang="scss">

.list-search {
  margin-bottom: 15px;

  .el-card__body {
    padding: 8px 20px;
  }

  .el-form-item {
    margin: 9px auto;
  }

  .search-btns {
    //padding-left: 50px;
    float: right;
  }

  .el-select {
    width: 100%;
  }
}
</style>
