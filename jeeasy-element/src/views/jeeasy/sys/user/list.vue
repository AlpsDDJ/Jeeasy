<template>
  <div class="main-conent main-conent-minheight">
    <el-card shadow="never" class="border-none">
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
                <el-checkbox @change="val => colFilterChange(val, col)"  :value="!col.hidden">{{ col.label }}</el-checkbox>
              </div>
              <i slot="reference" style="font-size: 18px; line-height: 18px; margin-bottom: -10px" class="el-icon-setting"/>
            </el-popover>
          </el-col>
        </el-row>
      </div>
      <je-table :columns="columns" :data="list" tableTitle="用户列表"></je-table>
    </el-card>
  </div>
</template>

<script>
import JeTable from '@/components/jeeasy/JeTable'
import { parseApi } from '@/service/lib/request'

export default {
  name: 'SysUserList',
  data() {
    return {
      list: [],
      columns: [
        {
          label: '用户名',
          key: 'username'
        },
        {
          label: '姓名',
          key: 'realName'
        },
        {
          label: '性别',
          key: 'sex'
        },
        {
          label: '电话',
          key: 'phone'
        },
        {
          label: '状态',
          key: 'status'
        }
      ],
      query: {
        page: {
          pageNo: 1,
          pageSize: 10
        }
      },
      api: parseApi('/sys/user')
    }
  },
  components: {
    JeTable
  },
  mounted() {
    this.loadData()
  },
  methods: {
    loadData(){
      this.$ajax(this.api.list, {id: '123', id2: 2, id3: 3, name: 'name'}).then(({result}) => {
        const { records, ...page} = result
        this.query.page = page
        this.list = records
      })
    },
    colFilterChange(val, col) {
      col.hidden = !val
      this.$forceUpdate()
    }
  }
}
</script>

<style scoped>

</style>
