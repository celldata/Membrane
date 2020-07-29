<template>
  <div id="application-manage" class="common-root" :style="{minHeight: minHeight + 'px'}">
    <!-- 条件筛选查询 -->
    <div class="select-container">
      <el-col :span="21">
        应用名称
        <el-select placeholder="请选择" v-model="filter.appId" size="mini">
          <el-option
           v-for="(item,index) in appList"
           :key="index"
           :value="item.clientId"
           :label="item.clientName"
          >
          </el-option>
        </el-select>
      </el-col>
    </div>
    <!-- 列表区 -->
    <div class="table-container">
      <template>
        <el-table ref="table" :data="tabData" highlight-current-row>
          <el-table-column min-width="150" label="提供接口应用" prop="clientId" width="100">
          </el-table-column>
          <el-table-column label="调用接口应用">
            <template slot-scope="scope">
            <el-tooltip :content="scope.row.clientName" placement="top" effect="dark">
               <span>{{scope.row.clientName.length > 18 ? '...'.padStart(18,scope.row.clientName) : scope.row.clientName}}</span>
            </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column label="Access ID" prop="createTime">
            <template slot-scope="scope">
              {{scope.row.createTime }}
            </template>
          </el-table-column>
          <el-table-column label="Token" prop="authentication">
            <template slot-scope="scope">
              {{scope.row.authentication == null ? '--' :scope.row.authentication == 0 ? '内部认证中心' : authCode(scope.row.authentication)}}
            </template>
          </el-table-column>
          <el-table-column label="描述" prop="tokenCheckType">
            <template slot-scope="scope">
              {{scope.row.tokenCheckType == null ? '--' : scope.row.tokenCheckType == "0" ? '强安全模式' : '高性能模式'}}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <el-button size="mini" type="text" class="operate">
                详情<i :class="!isExpand ? 'el-icon-caret-bottom' : 'el-icon-caret-top'"></i>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </template>
      <div class="paging fr">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="page.pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="page.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="page.total"
          v-if="page.total"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>
<script>

import {
  applicationList,
} from "../../../api/api";
export default {
  data(){
    return{
      minHeight: null,
      selectedApp:null,
      tabData:[],
      page: {
        pageIndex:1,
        pageSize: 10,
        total:0
      },
      filter:{
        appId:null,
        status:[null],
        validMode:null
      },//筛选条件
      selectedAppId:null,
    }
  },
  methods:{
    getAppList(){
      /**
       * @description 获取应用列表
         * @param {Number} pageIndex
         * @param {Number} pageSize
         * @param {Number} authentication
         * @param {Number} tokenCheckType
         * @param {Number} verification
         * @param {Number} clientId
       */
      let self = this;
      let param = {
          sign:1,
          clientId:null,
          authentication:null,
          tokenCheckType:null,
          verification:null,
          pageIndex:self.page.pageIndex,
          pageSize:self.page.pageSize
        }
      self.appList = [];
      applicationList(param).then(res=>{
          if(res.err_CODE === 0){
            let appList = [
              { clientId : null,clientName : '全部' },
              ...res.data.list
            ];
            self.appList = appList;
            let list;
            list = res.data.list.map(item =>{
              // 转换时间格式
              // item.createTime = item.createTime == null ? '--' : getDate(new Date(item.createTime).getTime()/1000);
              return item;
            })
            self.tabData = list;
            self.page.total = res.data.total;
          }else{
            self.$message.error(res.err_MESSAGE);
          }
        })
    },
  },
  mounted(){
    this.minHeight = window.innerHeight - 95;
  },
}
</script>

<style lang="scss">
  @import "../../../styles/application-manage/application-management.scss";
</style>


