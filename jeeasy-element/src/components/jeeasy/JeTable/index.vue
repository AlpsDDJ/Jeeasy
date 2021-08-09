<template>
  <div class="list-panel">
<!--    <el-card class="operate-container" shadow="never">
      <el-row>
        <el-col :span="6">
          <i class="el-icon-tickets"></i>
          <span>{{ tableTitle }}</span>
        </el-col>
        <el-col :span="18" style="text-align: right">
          <el-popover
            placement="left-start"
            width="150">
            <div v-for="(col) in columns" :key="col.key">
              <el-checkbox @change="val => colFilterChange(val, col)"  :value="!col.hidden">{{ col.label }}</el-checkbox>
            </div>
            <i slot="reference" style="color: #3788DE; font-size: 18px; line-height: 18px; margin-bottom: -10px" class="el-icon-setting"/>
          </el-popover>
        </el-col>
      </el-row>
    </el-card>-->
    <el-table v-bind="$attrs" :data="$attrs.data">
      <el-table-column v-if="showIndex" width="50" align="center" type="index" :index="indexMethod"></el-table-column>
      <template v-for="col in columns">
        <el-table-column :key="col.key" v-if="!col.hidden" :align="col.align || defaultColumnTextAlign" v-bind="col">
        <template slot-scope="scope">
          <!-- 插槽 -->
          <slot v-if="col.slot" :name="col.slot" :row="scope.row"></slot>
          <!-- 自定义渲染方法 -->
          <ex-slot v-else-if="col.customRender" :render="col.customRender" :row="scope.row" :index="scope.$index" :column="col"></ex-slot>
          <!-- 默认文本渲染 -->
          <span v-else v-html="showDefaultText(col.key, scope.row)">

          </span>
        </template>
      </el-table-column>
      </template>
    </el-table>
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
      defaultColumnTextAlign: 'center'
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
    showDefaultText(key, record){
      const dictText = record[`${key}_dict`]
      if(dictText){
        return dictText
      }
      return record[key]
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
    }
  }
}
</script>

<style scoped>

</style>
