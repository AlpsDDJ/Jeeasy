<script>
export default {
  name: 'JeForm',
  data() {
    return {
      formData: {}
    }
  },
  props: {
    value: {
      type: Object,
      required: true
    },
    role: {
      type: Array,
      required: true
    },
    colspan: {
      type: Number,
      default: null
    }
  },
  computed: {
    formOption() {
      return Object.assign({
        'label-width': '100px'
      }, this.$attrs)
    },
    formColLayout() {
      return (layout = {}) => {
        if (this.colspan) {
          return {
            span: this.colspan
          }
        } else {
          return Object.assign({
            xs: 24,
            sm: 24,
            md: 12,
            lg: 12,
            xl: 12
          }, layout)
        }
      }
    },
    component() {
      return (component, key) => {
        console.log(key, component)
        return component
      }
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
      }
    }
  },
  methods: {
    handleSubmit(){
      this.$emit('submit')
    },
    handleCancel(){
      this.$emit('cancel')
    }
  },
  render() {
    return (
      <div class="je-form">
        <el-form v-model={this.formData} props={this.formOption}>
          <el-row type="flex" class="flex-wrap-wrap">
            {
              this.role.map(({ slot, form = {}, ...item }, index) => (
                  form && <el-col key={item.key || index} props={this.formColLayout(form.colLayout)}>
                  <el-form-item label={item.label} prop={item.key}>
                    {
                      () => {
                        if (slot && this.$scopedSlots[slot]) {
                          return this.$scopedSlots[slot]()
                        } else if (typeof form === 'function') {
                          return this.component(form(), item.key)
                        } else {
                          return <el-input v-model={this.formData[item.key]} />
                        }
                      }
                    }
                  </el-form-item>
                </el-col>
              ))
            }
          </el-row>
          <el-row>
            <el-col class="text-right">
              <el-button type="primary" on-click={this.handleSubmit}>保存</el-button>
              <el-button on-click={this.handleCancel}>取消</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
    )
  }
}
</script>

<style lang="scss">
</style>
