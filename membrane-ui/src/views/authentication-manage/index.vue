<template>
  <div id="auth-manage" class="common-root" :style="{minHeight: minHeight + 'px'}">
    <!-- 条件筛选查询 -->
    <div class="select-container">
      <el-col :span="21">
        认证类型：
        <el-select placeholder="请选择认证类型" size="mini" v-model="page.type" @change="toggleHandle">
          <el-option v-for="item in authTypes" :key="item.label" :value="item.value" :label="item.label"></el-option>
        </el-select>
        认证源名称：
        <el-input class="search" v-model="page.name" placeholder="请输入认证源名称" size="mini" @change="getAllAuthenticationSources"></el-input>
      </el-col>
      <el-col :span="4" class="fr">
        <el-button type="primary" size="mini" style="border:none" icon="el-icon-plus" @click="cmpToggleHandle('add')">添加认证源</el-button>
        <el-button size="mini" style="border:none" :disabled="multipleSelection.length == 0" @click="handleDelete(multipleSelection)">批量删除</el-button>
      </el-col>
    </div>
    <!-- 认证列表 -->
    <div class="table-container">
      <template>
        <el-table ref="table" @selection-change="handleSelectionChange" :data="tableData">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="name" label="认证源名称">
            <template slot-scope="scope">
              <el-tooltip :content="scope.row.name" placement="top" effect="dark">
                <span>{{scope.row.name && scope.row.name.length > 10 ? scope.row.name.substr(0,9)+"..." : scope.row.name}}</span>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column prop="type" label="认证类型">
            <template slot-scope="scope">
              {{getAttributeType(scope.row.type)}}
            </template>
          </el-table-column>
          <el-table-column prop="createName" label="创建人"></el-table-column>
          <el-table-column label="创建时间">
            <template slot-scope="scope">
              {{handleFormat(scope.row.createTime)}}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="180">
            <template slot-scope="scope">
              <span class="spanBtn">
                <el-tooltip class="item" effect="dark" content="详情" placement="top">
                  <em class="el-icon-document" @click="cmpToggleHandle('detail',scope.row)"></em>
                </el-tooltip>
              </span>
              <span class="spanBtn">
                <el-tooltip class="item" effect="dark" content="编辑" placement="top">
                  <em class="el-icon-edit" @click="cmpToggleHandle('edit',scope.row)"></em>
                </el-tooltip>
              </span>
              <span class="spanBtn">
                <el-tooltip class="item" effect="dark" content="删除" placement="top">
                  <em class="el-icon-delete" @click="handleDelete(scope.row)"></em>
                </el-tooltip>
              </span>
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
    <!-- 新增认证源 -->
    <add-auth-source ref="addCmp" @closeHandle="authHandleToClose"></add-auth-source>
    <!-- 查看认证源详情 -->
    <auth-source-detail ref="detailCmp" :auth-detail="toChild" @closeHandle="authHandleToClose"></auth-source-detail>
    <!-- 编辑认证源 -->
    <edit-auth-source ref="editCmp"></edit-auth-source>
  </div>
</template>

<script>
import addAuthSource from '../authentication-manage/components/add-auth-source'
import authSourceDetail from '../authentication-manage/components/auth-source-details'
import editAuthSource from '../authentication-manage/components/edit-auth-source'
import selectObj from '../../labs/constant'
import {externalAuthSourceList,delExternalAuthInfo} from '../../api/api'
import { getDate } from '../../labs/tools'
import qs from 'qs'
export default {
  data(){
    return{
      minHeight:null,
      tableData: [],
      disabled:false,
      authTypes:[],
      addSource:false,
      sourceDetail:false,
      editSource:false,
      page:{
        pageSize:10,
        pageIndex:1,
        type:null,
        id:null,
        name:null,
        total:10,
      },
      toChild:null,
      multipleSelection:[],
    }
  },
  components:{
    addAuthSource,
    authSourceDetail,
    editAuthSource
  },
  methods:{
    authHandleToClose(data){
      /**
       * @description 新增弹出框关闭事件[子传父]
       */
      switch(data.name){
        case 'add':
          this.addSource = false;
          break;
        case 'detail':
          this.sourceDetail = false;
          break;
        case 'edit':
          this.editSource = false;
          break;
      }
    },
    handleSelectionChange(val){
      /**
       * @description 多选事件
       */
      this.multipleSelection = val;
      console.log(val);
    },
    handleSizeChange(){

    },
    handleCurrentChange(){

    },
    handleDelete(data){
      /**
       * @description 删除认证源
       * @param {Array} idList 认证源ID
       */
      this.$confirm("你确定要删除吗？删除后不可恢复",'提示',{
        confirmButtonText:'确定',
        cancelButtonText:'取消',
      }).then(() =>{
        // 确定删除
        let param = { idList : []};
        // 根据参数data判断传入的值类型是数组还是对象进行 删除参数的数值传递
        Array.isArray(data) ? param.idList = data.map((item)=>{return item.id}) : param.idList.push(data.id);
        delExternalAuthInfo(qs.stringify(param,{ arrayFormat: 'repeat' })).then(res=>{
        })
      }).catch(() =>{
        // 取消删除
      })
    },
    getTypesHandle(){
      /**
       * @description 获取认证类型列表
       */
      selectObj.authStatus.forEach(item=>{
        this.authTypes = [];
        if(item.value == 1 ){
          this.authTypes = [ { label: '全部', value:null},...item.children] ;
        }
      })
    },
    cmpToggleHandle(tag,data){
      /**
       * @description 组件切换
       */
      switch(tag){
        case 'add':
          this.$refs['addCmp'].addVisible = true;
          this.$refs['addCmp'].authType = this.authTypes.filter(item=>item.value != null);
          break;
        case 'detail':
          this.$refs['detailCmp'].details = JSON.parse(JSON.stringify(data));
          this.$refs['detailCmp'].detailVisible = true;
          break;
        case 'edit':
          this.$refs['editCmp'].editVisible = true;
          this.$refs['editCmp'].authType = this.authTypes.filter(item=>item.value != null);
          this.$refs['editCmp'].editForm = JSON.parse(JSON.stringify(data));
      }

    },
    getAllAuthenticationSources(){
      /**
       * @description 获取认证源列表数据
       * @param {Number} pageSize 分页大小
       * @param {Number} pageIndex 当前页
       * @param {Number} type 外部接入类型：1代表ldap
       * @param {Number} id 外部认证源id
       * @param {String} name 认证源名称
       */
      let param = {
        name: this.page.name,
        id: this.page.id,
        type: this.page.type,
        pageSize: this.page.pageSize,
        pageIndex: this.page.pageIndex
      }
      externalAuthSourceList(param).then(res=>{
        console.log(res);
        res.err_CODE === 0 ? this.tableData = res.data.list : this.$message.error(res.err_MESSAGE);
        this.page.total = res.data.total;
      })
    },
    handleFormat(date){
      return getDate(new Date(date).getTime()/1000)
    },
    getAttributeType(code){
      switch(code){
        case 1:
          code = "LDAP";
          break;
      }
      return code;
    },
    handleSizeChange(val) {
      /**
       * @description 分页方法
       */
      this.page.pageSize = val;
      this.getAllAuthenticationSources();
    },
    handleCurrentChange(val) {
      /**
       * @description 当前页面查询
       */
      this.page.pageIndex = val;
      this.getAllAuthenticationSources();
    },
    toggleHandle(val){
      /**
       * @description 类型切换
       */
      this.page.type = val;
      this.getAllAuthenticationSources();
    }
  },
  mounted(){
    this.getAllAuthenticationSources();
    this.getTypesHandle();
  },
  created(){
    this.minHeight = window.innerHeight - 95;
    /**
     * 获取外部认证源类型
     */
    // this.getTypesHandle();
  }
}
</script>

<style lang="scss">
  @import "../../styles/authentication-manage/index.scss";
</style>
