<template>
    <div class="main-content main-content-minheight">

        <page-header><strong>基础详情页</strong></page-header>

        <el-card shadow="never" class="border-none margin-t24">
            <div class="main-content-detail">
                <div class="main-content-detail-title">基本信息</div>
                <el-row :gutter="24">
                    <el-col :sm="24" :md="12" :lg="8">
                        <span class="main-content-detail-lable">姓名：</span>
                        <span class="main-content-detail-content">王小二</span>
                    </el-col>
                    <el-col :sm="24" :md="12" :lg="8">
                        <span class="main-content-detail-lable">联系电话：</span>
                        <span class="main-content-detail-content">18888888888</span>
                    </el-col>
                    <el-col :sm="24" :md="12" :lg="8">
                        <span class="main-content-detail-lable">性别：</span>
                        <span class="main-content-detail-content">男</span>
                    </el-col>
                    <el-col :sm="24" :md="12" :lg="8">
                        <span class="main-content-detail-lable">国家：</span>
                        <span class="main-content-detail-content">中国</span>
                    </el-col>
                    <el-col :sm="24" :md="12" :lg="8">
                        <span class="main-content-detail-lable">城市：</span>
                        <span class="main-content-detail-content">某某地</span>
                    </el-col>
                    <el-col :sm="24" :md="12" :lg="8">
                        <span class="main-content-detail-lable">地址：</span>
                        <span class="main-content-detail-content">某某村</span>
                    </el-col>
                </el-row>
            </div>
            <el-divider></el-divider>

            <div class="main-content-detail">
                <div class="main-content-detail-title">退款申请</div>
                <el-row :gutter="24">
                    <el-col :sm="24" :md="12" :lg="8">
                        <span class="main-content-detail-lable">订单号：</span>
                        <span class="main-content-detail-content">dd123456789</span>
                    </el-col>
                    <el-col :sm="24" :md="12" :lg="8">
                        <span class="main-content-detail-lable">状态：</span>
                        <span class="main-content-detail-content">已取货</span>
                    </el-col>
                    <el-col :sm="24" :md="12" :lg="8">
                        <span class="main-content-detail-lable">取货单号：</span>
                        <span class="main-content-detail-content">123456789</span>
                    </el-col>
                    <el-col :sm="24" :md="12" :lg="8">
                        <span class="main-content-detail-lable">金额：</span>
                        <span class="main-content-detail-content">￥ 888,888</span>
                    </el-col>
                    <el-col :sm="24" :md="12" :lg="8">
                        <span class="main-content-detail-lable">城市：</span>
                        <span class="main-content-detail-content">某某地</span>
                    </el-col>
                    <el-col :sm="24" :md="12" :lg="8">
                        <span class="main-content-detail-lable">地址：</span>
                        <span class="main-content-detail-content">某某村</span>
                    </el-col>
                </el-row>
            </div>
            <el-divider></el-divider>

            <div class="main-content-detail">
                <div class="main-content-detail-title">退货商品</div>
                <el-table
                    :data="tableData"
                    style="width: 100%">
                    <el-table-column
                        prop="date"
                        label="日期"
                        width="180">
                    </el-table-column>
                    <el-table-column
                        prop="name"
                        label="姓名"
                        width="180">
                    </el-table-column>
                    <el-table-column
                        prop="address"
                        label="地址"
                        min-width="300">
                    </el-table-column>
                </el-table>

            </div>
            <el-divider></el-divider>

            <div class="main-content-detail">
                <div class="main-content-detail-title">退货进度</div>
                <el-table
                    :data="tableData"
                    style="width: 100%">
                    <el-table-column
                        prop="date"
                        label="日期"
                        width="180">
                    </el-table-column>
                    <el-table-column
                        prop="name"
                        label="姓名"
                        width="180">
                    </el-table-column>
                    <el-table-column
                        prop="address"
                        label="地址"
                        min-width="300">
                    </el-table-column>
                </el-table>

            </div>


        </el-card>
    </div>
</template>
<script>
import PageHeader from '@/components/layout/components/PageHeader'
export default {
    components: {
      PageHeader
    },
    data() {
        return {
          tableData: [{
            date: '2016-05-02',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
          }, {
            date: '2016-05-04',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1517 弄'
          }, {
            date: '2016-05-01',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1519 弄'
          }, {
            date: '2016-05-03',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1516 弄'
          }]
        }
      }
}
</script>
<style lang="scss" scoped>
.main-content-detail {
    box-sizing: border-box;
    .main-content-detail-title {
        margin-bottom: 16px;
        color: #303133;
        font-size: 16px;
        font-weight: 700;
    }
    .main-content-detail-lable,
    .main-content-detail-content {
        display: table-cell;
        padding-bottom: 16px;
        line-height: 20px;
        font-size: 14px;

    }
    .main-content-detail-lable {
        white-space: nowrap;
    }
    .main-content-detail-content {
        width: 100%;
        word-wrap:break-word;
        word-break: break-all;
    }
}
</style>
