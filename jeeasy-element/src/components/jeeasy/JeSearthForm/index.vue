<!--<template>
  <el-card shadow="never" class="list-search border-none">
    <el-form v-model="formData" label-width="100px">
      <el-row :justify="justify" type="flex" class="flex-wrap-wrap">
        <slot></slot>
        <template v-for="({slot, ...item}, index) in searchParams">
          <el-col :key="item.key" v-bind="searchLayout" v-if="index < 3 || searchOpen">
            <el-form-item :label="item.label" :prop="item.key">
              <slot v-if="slot && $slots[slot]" :name="slot"></slot>
              <template v-else-if="item.search && typeof item.search === 'function'">
                <component :is="item.search()" />
              </template>
              &lt;!&ndash;              <ex-slot v-else-if="item.search && typeof item.search === 'function'" :customRender="item.search" :index="index" :column="item" v-model="formData[item.key]"></ex-slot>&ndash;&gt;
              <el-input v-else v-model="formData[item.key]" />
            </el-form-item>
          </el-col>
        </template>
        <el-col v-bind="searchLayout" :offset="offset" class="search-btns text-right">
          <el-form-item label-width="0">
            <el-button type="primary" @click="submit">查询</el-button>
            <el-button @click="reset">重置</el-button>
            <slot name="ex-operate" />
            <el-button type="text" v-if="!searchOpen" @click="toggleSearch">展开<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i></el-button>
            <el-button type="text" v-else @click="toggleSearch">收起<i class="el-icon-arrow-up el-icon&#45;&#45;right"></i></el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-card>
</template>-->
<!--<template>
  <je-common-form :form-option="formOption">

  </je-common-form>
</template>-->

<script type="text/jsx">
/* eslint-disable */

// import ExSlot from '@/components/jeeasy/JeTable/ExSlot'
// import JeCommonForm from '@/components/jeeasy/JeCommonForm/index'
const searchLayout = {
  xs: 24,
  sm: 24,
  md: 12,
  lg: 6,
  xl: 6
}

// eslint-disable-next-line no-unused-vars
const renderItem = (_this, h) => {
  const { searchParams, searchOpen, formData } = _this
  return searchParams.map(({ slot, ...item }, index) => {
    if (index < 3 || searchOpen) {
      if (slot && typeof _this.$slots[slot]) {
        return <slot name={slot} />
      } else if (item.search && typeof item.search === 'function') {
        return item.search()
      } else {
        return <el-input model={formData[item.key]} />
      }
    }
  })
}

export default {
  name: 'JeSearchForm',
  // components: { JeCommonForm },
  // components: { ExSlot },
  data() {
    return {
      searchLayout,
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
    // formOption() {
    //   return {
    //     name: 'search-form',
    //     data: {},
    //     items: this.searchParams,
    //     btnList: [{
    //       text: '查询',
    //       onClick: this.submit
    //     }]
    //   }
    // },
    justify() {
      return this.searchOpen ? 'end' : 'start'
    },
    offset() {
      return (4 - this.searchParams.length) * 6
    }
  },
  methods: {
    submit() {
      this.$emit('submit')
    },
    reset() {
      this.formData = {}
    },
    toggleSearch() {
      this.searchOpen = !this.searchOpen
    }
  },
  render(h) {
    const { justify, submit, reset, offset, searchOpen, formData } = this
    return (
      <el-card shadow="never" class="list-search border-none">
        <el-form model={formData} label-width="100px">
          <el-row justify={justify} type="flex" class="flex-wrap-wrap">
            <slot />
            {
              renderItem(this, h)
            }
            <el-col {...searchLayout} offset={offset} class="search-btns text-right">
          <el-form-item label-width="0">
            <el-button type="primary" onClick={submit}>查询</el-button>
            <el-button onClick={reset}>重置</el-button>
            <slot name="ex-operate" />
            {
              searchOpen ?
                (<el-button type="text" onClick={this.toggleSearch}>展开<i className="el-icon-arrow-down el-icon--right" /></el-button>)
                :
                (<el-button type="text" onClick={this.toggleSearch}>收起<i className="el-icon-arrow-up el-icon--right" /></el-button>)
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
