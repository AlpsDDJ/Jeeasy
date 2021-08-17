<script>
import DictSelect from '@/components/jeeasy/DictSelect'
export default {
  name: 'JeForm',
  components: {DictSelect},
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
    fileds: {
      type: Array,
      required: true
    },
    colspan: {
      type: Number,
      default: 24
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
      return (component) => {
        // console.log(key, component)
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
    handleSubmit() {
      this.$emit('submit')
    },
    handleCancel() {
      this.$emit('cancel')
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
        <div class="je-form">
        <el-form v-model={this.formData} props={this.formOption}>
          <el-row type="flex" class="flex-wrap-wrap">
            {
              this.fileds.map(({ slot, form = {}, ...item }, index) => (
                  (form && !item.formHidden) && <el-col key={item.key || index} props={this.formColLayout(form.colLayout)}>
                  <el-form-item label={item.label} prop={item.key}>
                    {
                      () => {
                        if(item.type) {
                          return this.createItemByType(item.type, item.key)
                        } else if (slot && this.$scopedSlots[slot]) {
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
.je-form {
  //.el-select, .el-input, .el-radio-group{
  //  width: 100%;
  //}
  .el-form-item__content > * {
    width: 90%;
  }
}
</style>
