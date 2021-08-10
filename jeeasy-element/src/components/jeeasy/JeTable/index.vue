<template>
  <div class="list-panel">
    <el-card shadow="never" class="list-table border-none">
      <div slot="header">
        <el-row>
          <el-col :span="6">
            <i class="el-icon-tickets"></i>
            <span>用户列表</span>
          </el-col>
          <el-col :span="18" style="text-align: right">
            <el-popover
              placement="left-start"
              width="150">
              <div v-for="(col) in columns" :key="col.key">
                <el-checkbox @change="val => colFilterChange(val, col)" :value="!col.hidden">{{ col.label }}</el-checkbox>
              </div>
              <i slot="reference" style="font-size: 18px; line-height: 18px; margin-bottom: -10px" class="el-icon-setting" />
            </el-popover>
          </el-col>
        </el-row>
      </div>
      <el-table v-bind="$attrs" :data="$attrs.data">
        <el-table-column v-if="showIndex" width="50" align="center" type="index" :index="indexMethod"></el-table-column>
        <template v-for="({slot, ...item}) in columns">
          <el-table-column :key="item.key" v-if="!item.hidden" :align="item.align || 'center'" v-bind="item">
            <template slot-scope="scope">
<!--              <slot :col="item"></slot>-->
              <!-- 插槽 -->
              <slot v-if="slot && $slots[slot]" :name="slot" :row="scope.row">1</slot>
              <!-- 自定义渲染方法 -->
              <ex-slot v-else-if="item.customRender" :render="item.customRender" :row="scope.row" :index="scope.$index" :column="item"></ex-slot>
              <!-- 默认文本渲染 -->
              <span v-else v-html="showDefaultText(item.key, scope.row)"></span>
            </template>
          </el-table-column>
        </template>
      </el-table>
      <el-row class="list-foot" v-if="showPage">
        <el-col :span="12" :offset="12" class="text-right">
          <el-pagination background small :current-page.sync="page.current" :page-size="page.size" :total="page.total"/>
        </el-col>
      </el-row>
    </el-card>


  </div>
</template>

<script lang="text/jsx">
import ExSlot from './ExSlot'

export default {
  name: 'JeTable',
  components: {
    'ex-slot': ExSlot
  },
  data() {
    return {
      defaultColumnTextAlign: 'center',
      page: {}
    }
  },
  methods: {
    indexMethod(index) {
      return index + 1
    },
    colFilterChange(val, col) {
      col.hidden = !val
      this.$forceUpdate()
    },
    showDefaultText(key, record) {
      const dictText = record[`${key}_dict`]
      if (dictText) {
        return dictText
      }
      return record[key]
    }
  },
  watch: {
    value: {
      handler(val){
        console.log('page ------- >', val)
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
    tableTitle: {
      default: '数据列表'
    },
    columns: {
      required: true
    },
    showIndex: {
      type: Boolean,
      default: true
    },
    showPage: {
      type: Boolean,
      default: true
    },
    value: {
      type: Object,
      default: () => ({})
    }
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
  }
}
</style>
