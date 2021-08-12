<script>
export default {
  name: 'JeForm',
  data(){
    return {
      formData: {}
    }
  },
  props: {
    value: {
      type: Object,
      required: true
    },
    fields: {
      type: Array,
      required: true
    },
    colspan: {
      type: Number,
      default: null
    }
  },
  computed: {
    formOption(){
      return Object.assign({
        'label-width': '100px'
      }, this.$attrs)
    },
    formColLayout(){
      return (layout = {}) => {
        if(this.colspan){
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
  render() {
    return (
      <div class="je-form">
        <el-form v-model={this.formData} props={this.formOption}>
          <el-row type="flex" class="flex-wrap-wrap">
            {
              this.fields.map(({slot, form = {}, ...item}, index) => (
                form && <el-col key={item.key || index} props={this.formColLayout(form.colLayout)}>
                  <el-form-item label={item.label} prop={item.key}>
                    {
                      () => {
                        console.log(item)
                        if (slot && this.$scopedSlots[slot]) {
                          return this.$scopedSlots[slot]()
                        } else if (item.form) {
                          if(typeof item.form === 'function') {
                            return item.form()
                          }
                        } else {
                          return <el-input v-model={this.formData[item.key]}/>
                        }
                      }
                    }
                  </el-form-item>
                </el-col>
              ))
            }
          </el-row>
        </el-form>
      </div>
    )
  }
}
</script>

<style lang="scss">
.el-dialog{
  background: rgb(60, 60, 60);
}
.el-dialog__wrapper{
  background: rgba(255, 255, 255, .25);
}
</style>
