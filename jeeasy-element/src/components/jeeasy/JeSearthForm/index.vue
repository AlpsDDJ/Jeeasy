<template>
  <el-card shadow="never" class="list-search border-none">
    <el-form v-model="formData" label-width="100px">
      <el-row :justify="justify" type="flex" class="flex-wrap-wrap">
        <slot></slot>
        <template v-for="({slot, ...item}, index) in searchParams">
          <el-col :key="item.key" v-bind="searchLayout" v-if="index < 3 || searchOpen">
            <el-form-item :label="item.label" :prop="item.key">
              <slot v-if="slot && $slots[slot]" :name="slot"></slot>
              <ex-slot v-else-if="item.search && typeof item.search === 'function'" :render="item.search" :index="index" :column="item"></ex-slot>
              <el-input v-else v-model="formData[item.key]"/>
            </el-form-item>
          </el-col>
        </template>
        <el-col v-bind="searchLayout" :offset="offset" class="search-btns text-right">
          <el-form-item label-width="0">
            <el-button type="primary" @click="$emit('submit')">查询</el-button>
            <el-button @click="() => {formData = {}}">重置</el-button>
            <slot name="ex-operate"/>
            <el-button type="text" v-if="!searchOpen" @click="toggleSearch">展开<i class="el-icon-arrow-down el-icon--right"></i></el-button>
            <el-button type="text" v-else @click="toggleSearch">收起<i class="el-icon-arrow-up el-icon--right"></i></el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-card>
</template>

<script>
import ExSlot from '@/components/jeeasy/JeTable/ExSlot'
export default {
  name: 'JeSearchForm',
  components: { ExSlot },
  data () {
    return {
      searchLayout: {
        xs: 24,
        sm: 24,
        md: 12,
        lg: 6,
        xl: 6
      },
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
    justify(){
      return this.searchOpen ? 'end': 'start'
    },
    offset(){
      return (4 -this.searchParams.length) * 6
    }
  },
  methods: {
    toggleSearch(){
      this.searchOpen = !this.searchOpen
    }
  }
}
</script>

<style lang="scss">

.list-search {
  margin-bottom: 15px;
  .el-card__body{
    padding: 8px 20px;
  }
  .el-form-item{
    margin: 9px auto;
  }
  .search-btns{
  //padding-left: 50px;
  }
  .el-select{
    width: 100%;
  }
}
</style>
