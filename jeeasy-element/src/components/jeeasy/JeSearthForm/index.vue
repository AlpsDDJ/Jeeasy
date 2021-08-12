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
        props: {
          xs: 24,
          sm: 24,
          md: 12,
          lg: 6,
          xl: 6
        }
      }
    },
    justify() {
      return this.searchOpen ? 'end' : 'start'
    },
    offset() {
      return (4 - this.searchParams.length) * 6
    },
    component() {
      return (component, key) => {
        console.log(key, component)
        const tag = component.componentOptions.tag
        const _this = this
        // component.data.model = { value: this.formData[key] }
        // component.componentInstance.$listeners = val => {
        //   _this.formData[key] = val
        // }
        component.$attrs.value = this.formData[key]
        component.$listeners.input = value => {_this.formData[key] = value}
        return key !== 'status1' ? component : <tag v-model={this.formData[key]} {...component.child}/>
        // return [<tag v-model={this.formData[key]} {...component.child}/>]
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
    handleInput(value) {
      console.log('value ---> ', value)
    },
    renderItem() {
      return this.searchParams.map(({ slot, ...item }, index) => {
        if (index < 3 || this.searchOpen) {
          return (
            <el-col key={item.key}  {...this.searchColLayout}>
                <el-form-item label={item.label} prop={item.key}>
                  {
                    () => {
                      if (slot && this.$scopedSlots[slot]) {
                        return this.$scopedSlots[slot]()
                      } else if (item.search && typeof item.search === 'function') {
                        const formItem = item.search()
                        return this.component(formItem, item.key)
                        /*return Object.assign(
                          {
                            props: {
                              value: this.formData[item.key]
                            },
                            on: {
                              'input': this.handleInput
                            }
                          }, formItem)*/
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
    }
  },
  render() {
    return (
      <el-card shadow="never" class="list-search border-none">
          <el-form v-model={this.formData} label-width="100px" nativeOnKeyup={({ code }) => {
            if (code === 'Enter') this.submit()
          }}>
            <el-row justify={this.justify} type="flex" class="flex-wrap-wrap">
              <slot />
              {
                this.renderItem(this)
              }
              <el-col {...this.searchColLayout} offset={this.offset} class="search-btns text-right">
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
  }

  .el-select {
    width: 100%;
  }
}
</style>
