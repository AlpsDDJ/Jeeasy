<script>
export default {
  name: 'JeTable',
  data() {
    return {
      defaultColumnTextAlign: 'center',
      tableIndex: 0,
      selection: [],
      currentRow: {},
      page: {}
    }
  },
  methods: {
    pageChange(current, size) {
      if (current) {
        this.page.current = current
      }
      if (size) {
        this.page.size = size
      }
      this.$emit('pageChange')
    },
    indexMethod(index) {
      return index + 1
    },
    colFilterChange(val, col) {
      col.hidden = !val
      this.tableIndex++
      this.$forceUpdate()
    },
    showDefaultText(key, record) {
      const dictText = record[`${key}_dict`]
      if (dictText) {
        return dictText
      }
      return record[key]
    },
    handleSelectionChange(val) {
      this.selection = val
    },
    handleCurrentChange(val) {
      this.currentRow = val
    },
    update(){
      this.$refs['main-table'].doLayout()
    }
  },
  computed: {
    pageSizes() {
      const sizes = [10, 20, 50, 100]
      if (sizes.includes(this.value.size)) {
        return sizes
      } else {
        return [this.value.size, ...sizes]
      }
    },
    toolsParams(){
      return {
        currentRow: this.currentRow,
        selection: this.selection
      }
    }
  },
  watch: {
    value: {
      handler(val) {
        this.page = val
      },
      immediate: true
    },
    page: {
      handler(val) {
        this.$emit('input', val)
      },
      deep: true
    }
  },
  props: {
    columns: {
      required: true
    },
    showIndex: {
      type: Boolean,
      default: true
    },
    isTree: {
      type: Boolean,
      default: false
    },
    showPage: {
      type: Boolean,
      default: true
    },
    value: {
      type: Object,
      default: () => ({})
    },
    loading: {
      type: Boolean,
      default: false
    },
    showSelection: {
      type: Boolean,
      default: false
    }
  },
  render() {
    return (
      <div class="list-panel">
        <el-card shadow="never" class="list-table border-none">
          <div slot="header">
            <el-row>
              <el-col span={6}>
                {
                  this.$scopedSlots['top-tools'] && this.$scopedSlots['top-tools'](this.toolsParams)
                }
              </el-col>
              <el-col span={18} style="text-align: right">
                <el-popover placement="left-start" width="150">
                  {
                    this.columns.map(col => (
                      <div key={col.key}>
                        <el-checkbox on-change={val => this.colFilterChange(val, col)} value={!col.hidden} label={col.label} />
                      </div>
                    ))
                  }
                  <i slot="reference" style="font-size: 18px; line-height: 18px; margin-bottom: -10px" class="el-icon-setting" />
                </el-popover>
              </el-col>
            </el-row>
            </div>
            <el-table ref="main-table" {...{ props: this.$attrs }}
                      data={this.$attrs.data}
                      key={this.tableIndex}
                      v-loading={this.loading}
                      on-selection-change={this.handleSelectionChange}
                      on-current-change={this.handleCurrentChange}
                      highlight-current-row={true}
                      element-loading-background="rgba(0, 0, 0, 0.2)">
              {
                this.showSelection
                &&
                <el-table-column type="selection" width="50" align="center" />
              }
              {
                this.showIndex
                &&
                <el-table-column label="#" width="50" align="center" type="index" index={this.indexMethod} />
              }

              {
                this.columns.map(({ slot, ...item }) => (
                  !item.hidden
                  &&
                  <el-table-column
                    key={item.key}
                    align={item.align || 'center'}
                    props={item}
                    scopedSlots={{
                      default: scope => {
                        if (slot && this.$scopedSlots[slot]) { // 插槽
                          return this.$scopedSlots[slot] && this.$scopedSlots[slot](scope.row)
                          // return <slot name={slot} row={scope.row} />
                        } else if (item.customRender) { // 自定义渲染方法
                          return item.customRender({
                            text: scope.row[item.key],
                            record: scope.row,
                            index: scope.$index
                          })
                        } else { // 默认文本渲染
                          return <span>{this.showDefaultText(item.key, scope.row)}</span>
                        }
                      }
                    }}>
                  </el-table-column>
                ))
              }
              {
                this.$scopedSlots.operate
                &&
                <el-table-column
                  key={'operate'}
                  align={'center'}
                  label={'操作'}
                  scopedSlots={{
                    default: scope => {
                      return this.$scopedSlots.operate(scope.row)
                    }
                  }}>
                </el-table-column>
              }
            </el-table>
          {
            this.showPage
            &&
            <el-row class="list-foot">
              <el-col span={12} offset={12} class="text-right">
                <el-pagination background={true} small={true} layout="total, sizes, prev, pager, next"
                               on-size-change={size => {
                                 this.pageChange(null, size)
                               }}
                               on-current-change={current => {
                                 this.pageChange(current)
                               }}
                               page-sizes={this.pageSizes}
                               current-page={this.page.current}
                               total={this.page.total} />
              </el-col>
            </el-row>
          }

        </el-card>
      </div>
    )
  }
}
</script>

<style lang="scss">
.list-panel {
  .el-card__body {
    padding: 0;
  }

  .list-foot {
    margin: 10px;

    .el-input__inner {
      height: 22px;
      line-height: 22px;
    }

    .el-input__icon {
      height: 22px;
      line-height: 22px;
    }
  }
}
</style>
