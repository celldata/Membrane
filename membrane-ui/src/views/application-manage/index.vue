<template>
  <div id="application-manage" class="common-root" :style="{minHeight: minHeight + 'px'}">
    <!-- 条件筛选查询 -->
    <div class="select-container">
      <el-col :span="21">
        应用名称
        <el-select placeholder="请选择" v-model="filter.appId" @change="selectAppChange" size="mini">
          <el-option
           v-for="(item,index) in appList"
           :key="index"
           :value="item.clientId"
           :label="item.clientName"
          >
          </el-option>
        </el-select>
        认证方式
        <el-cascader
          v-model="filter.status"
          size="mini"
          :options="authStatus"
          @change="authChange"
        >
        </el-cascader>
        校验模式
        <el-select placeholder="请选择" v-model="filter.validMode" @change="validChange" size="mini">
          <el-option
          v-for="(item,index) in validates"
          :key="index"
          :value="item.modeId"
          :label="item.modeName"
          >
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="3" class="fr" style="">
        <el-button type="primary" size="mini" style="border:none" icon="el-icon-plus" @click="addAppDialogVisible = true">新增应用</el-button>
      </el-col>
    </div>
    <!-- 列表区 -->
    <div class="table-container">
      <template>
        <el-table ref="table" :data="tabData" highlight-current-row>
          <el-table-column min-width="150" label="应用ID" prop="clientId" width="100">
          </el-table-column>
          <el-table-column label="应用名称">
            <template slot-scope="scope">
            <el-tooltip :content="scope.row.clientName" placement="top" effect="dark">
               <span>{{scope.row.clientName.length > 18 ? '...'.padStart(18,scope.row.clientName) : scope.row.clientName}}</span>
            </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" prop="createTime">
            <template slot-scope="scope">
              {{scope.row.createTime }}
            </template>
          </el-table-column>
          <el-table-column label="认证方式" prop="authentication">
            <template slot-scope="scope">
              {{scope.row.authentication == null ? '--' :scope.row.authentication == 0 ? '内部认证中心' : authCode(scope.row.verification)}}
            </template>
          </el-table-column>
          <el-table-column label="校验模式" prop="tokenCheckType">
            <template slot-scope="scope">
              {{scope.row.tokenCheckType == null ? '--' : scope.row.tokenCheckType == "0" ? '强安全模式' : '高性能模式'}}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <el-button size="mini" type="text" class="operate" @click="toggleExpand(scope.row)">
                详情<i :class="selectedAppId == scope.row.clientId ? 'el-icon-caret-bottom' : 'el-icon-caret-top'"></i>
              </el-button>
            </template>
          </el-table-column>
          <!-- 表格中详情折叠布局 -->
          <el-table-column type="expand" width="1">
            <template slot-scope="props">
              <el-row>
                <el-col class="col-card">
                  <!-- 应用基本信息管理 -->
                  <app-information class="app-child" :appDetail="toChild" @refreshTable="childByValue"></app-information>
                  <!-- 应用认证管理 -->
                  <authentication-config class="app-child" :appDetail="toChild"  @refreshTable="childByValue"></authentication-config>
                  <!-- 应用权限录入管理 -->
                  <permission-manage class="app-child" :appDetail="toChild"></permission-manage>
                  <!-- 开发者中心 -->
                  <developer-center class="app-child"></developer-center>
                </el-col>
              </el-row>
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
    <!-- 新增应用 -->
    <el-dialog
      title="新增应用"
      :visible.sync="addAppDialogVisible"
      width="746px"
      top="20vh"
      :close-on-click-modal="false"
      class="appDialog"
      @close="resetForm('addAppForm')">
      <div class="app-content">
        <template>
          <el-form :model="addAppForm" :rules="addAppRules" ref="addAppForm" label-width="91px">
            <el-form-item label="应用logo" size="mini" label-width="81px" class="mg10">
              <!-- 应用logo上传 -->
              <div class="uploadLogo">
                <img alt="logo" src="../../assets/image/login-bg.png" />
                <el-upload
                  class="upload-demo"
                  ref="upload"
                  name="image_file"
                  action=""
                  :auto-upload="true"
                  :http-request="uploadImage"
                  :show-file-list="false"
                  :before-upload="beforeAvatarUpload"
                  :on-change="FileChange"
                  :on-success="handleAvatarSuccess"
                  :on-error="uploadError"
                  :with-credentials="true"
                  size="mini"
                >
                  <el-button size="small" type="primary">上传</el-button>
                </el-upload>
              </div>
            </el-form-item>
            <el-form-item label="应用名称" size="mini" prop="clientName">
              <el-input placeholder="请输入应用名称" v-model.trim="addAppForm.clientName"></el-input>
            </el-form-item>
            <el-form-item label="跳转Url" size="mini"  class="mg10"  label-width="81px"  prop="targetUrl">
              <el-input disabled placeholder="请输入跳转到业务平台的Url" v-model="addAppForm.targetUrl"></el-input>
            </el-form-item>
            <!-- <el-form-item label="App Secret" size="mini"  label-width="81px" class="mg10">
              <el-button type="text" :disabled="true">重置</el-button>
            </el-form-item> -->
            <el-form-item label="描述" size="mini" class="mg10"  label-width="81px" prop="appDesc">
              <el-input type="textarea" :rows="2" placeholder="请输入内容" show-word-limit v-model.trim="addAppForm.appDesc" maxlength="30"></el-input>
            </el-form-item>
          </el-form>
        </template>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addAppDialogVisible = false; resetForm('addAppForm')">取消</el-button>
        <el-button type="primary" @click="addApplication('addAppForm')">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {
  applicationList,
  addAppInfo,
} from "../../api/api.js";
import constant from "../../labs/constant"
import appInformation from "./components/information-manage";
import authenticationConfig from "./components/authentication-config";
import permissionManage from "./permission-manage/permission-manage";
import developerCenter from "./components/developer-center"
import { getDate } from '../../labs/tools';
import ossUpload from '../../labs/upload.js'


export default {
  data(){
    return{
      minHeight: null,
      selectedApp:null,
      appList:null,//应用列表
      authStatus:null,//认证状态列表
      validates:null,//验证方式
      tabData:[],
      toChild:null,//传值给组件
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
      activeName:'1',
      isExpand:false,//用于控制列表中详情是否展开
      addAppDialogVisible:false,
      addAppForm:{
        clientName:null,
        appDesc:null,
      },//新增应用form
      addAppRules:{
        clientName:[
          { required : true, message : "请输入应用名称!", trigger : "blur" },
          { min: 2, max: 20, message: "请输入2到20个字符", trigger : "blur"}
        ],
        appDesc:[
          { required : false}
        ]
      },//新增应用表单验证
      selectedAppId:null,
      uploadForm: {
          productModelId: null,
          version: null,
          description: null,
          procedureUrl: null
      },
      upStatus: 0, // 上传图片的状态
      fileName: null, // 文件名称
      uploadFormRule: {
          version: [
            { required: true,pattern: /^[\d+(\.\d+)*]{1,20}$/, message: "1-20个字符(支持数字和.)",  trigger: "blur" }
          ],
          description: [
            { message: "0-100个字符",trigger: "blur"}
          ],
          procedureUrl: [
            { required: true, message: "请浏览并上传文件！"}
          ]
      },
    }
  },
  components:{
    appInformation,
    authenticationConfig,
    permissionManage,
    developerCenter
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
          clientId:self.filter.appId,
          authentication:self.filter.status[0],
          tokenCheckType:self.filter.validMode,
          verification:self.filter.status.length > 1 ? self.filter.status[1] : null,
          pageIndex:self.page.pageIndex,
          pageSize:self.page.pageSize
        }
      applicationList(param).then(res=>{
          if(res.err_CODE === 0){
            let list;
            list = res.data.list.map(item =>{
              // 转换时间格式
              item.createTime = item.createTime == null ? '--' : getDate(new Date(item.createTime).getTime()/1000);
              return item;
            })
            self.tabData = list;
            self.page.total = res.data.total;
          }else{
            self.$message.error(res.err_MESSAGE);
          }
        })
    },
    authCode(code){
      switch(code){
        case 0:
           code = "CAS";
           break;
        case 1 :
            code = "OAUTH";
            break;
        case 2:
            code = "SAML";
            break;
      }
      return code;
    },
    handleSizeChange(val) {
      /**
       * @description 分页方法
       */
      this.page.pageSize = val;
      this.getAppList();
    },
    handleCurrentChange(val) {
      /**
       * @description 当前页面查询
       */
      this.page.pageIndex = val;
      this.getAppList();
    },
    toggleExpand(row) {
      /**
       * 详情展开交互事件
       */
      this.toChild = null;
      this.selectedAppId = null;
      let $table = this.$refs.table;
      // 父组件传值给子组件
      this.tabData.forEach((item) => {
        if (row.clientId != item.clientId) {
          $table.toggleRowExpansion(item, false)
        }else{
          this.selectedAppId = item.clientId;
          this.toChild = JSON.parse(JSON.stringify(row));
        }
      })
      this.isExpand = !this.isExpand;
      // 对应行展开交互 箭头指向渲染
      !this.isExpand ? this.selectedAppId = null : this.selectedAppId = row.clientId;
      $table.toggleRowExpansion(row);
    },
    addApplication(formName){
      /**
       * @description 新增应用
       * @param {String} appID 应用ID
       * @param {String} secret
       * @param {String} clientName 应用名称
       * @param {String} imgUrl logo Url
       * @param {String} appDesc 应用描述
       * @param {Number} accessValidity app Secret有效期单位（分钟）
       * @param {Number} authentication 认证状态  0:内部认证,1外部认证，默认 0
       * @param {Number} tokenCheckType 验证方式 0:安全优先，1性能优先 默认 0
       * @param {Number} verification 认证方式 0: "CAS" ,1: "OAUTH" , 2 :"SAML"
       */
      let self = this;
      self.$refs[formName].validate(valid =>{
        if(valid){
          // 使用MD5加密算法对输入的APPID进行加密后生成对应的APP Secret
          let param = {
            clientName:self.addAppForm.clientName,
            imgUrl:'',
            tokenCheckType:0,
            authentication:0,
            verification:null,
            appDesc:self.addAppForm.appDesc
          };
          addAppInfo(param).then(res=>{
            if(res.err_CODE === 0){
              self.addAppDialogVisible = false;
              self.getAppList();
              self.selectApp();
              self.$message.success(res.err_MESSAGE);
            }else{
              self.$message.error(res.err_MESSAGE);
            }
          })
        }else{
          return false;
        }
      })
    },
    resetForm(formName){
      /**
       * @description 弹出框关闭事件
       * @param {String} formName 表单名称
       */
      this.$refs[formName].resetFields();
    },
    childByValue(value){
      /**
       * @description 接收子组件传值给父组件
       * @param {Boolean} value 是否刷新表格
       */
      value ? this.getAppList() : '';
    },
    authChange(val){
      /**
       * @description 认证状态改变事件
       */
      this.filter.status = val;
      this.getAppList();
    },
    selectAppChange(){
      /**
       * @description 根据选择的应用查询应用列表
       */
      this.getAppList();
    },
    validChange(val){
      /**
       * @description 根据验证方式查询应用列表
       */
      this.filter.validMode = val;
      this.getAppList();
    },
    selectApp(){
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
          clientId:self.filter.appId,
          authentication:self.filter.status[0],
          tokenCheckType:self.filter.validMode,
          verification:self.filter.status.length > 1 ? self.filter.status[1] : null,
          pageIndex:null,
          pageSize:null
        }
      // self.appList = [];
      applicationList(param).then(res=>{
          if(res.err_CODE === 0){
            if(param.clientId == null){
              let appList = [
                { clientId : null,clientName : '全部' },
                ...res.data.list
              ];
              self.appList = appList;
            }
          }else{
            self.$message.error(res.err_MESSAGE);
          }
        })
    },
    // 手动上传
    uploadFile(){
        // this.$refs.upload.submit();
    },
    FileChange(file,fileList){
      console.log(file);
        // if(file.response){
        //   this.uploadForm.procedureUrl = file.response.res.requestUrls[0];
        // }else{
        //   this.uploadForm.procedureUrl = " ";
        // }
        // this.fileName = fileList[fileList.length - 1].name;
    },
    uploadImage(option) {
        console.log(option);
        ossUpload.ossUploadFile(option);
    },
    beforeAvatarUpload(file) {
        // this.upStatus = 0;
        // const isLt200M = file.size / 1024 / 1024 < 200; // 文件大小不超过200M
        // if(!isLt200M) {
        //   this.upStatus = 2;
        //   return false;
        // }
    },
    // 获取上传成功文件的地址
    handleAvatarSuccess(res, file) {
        // this.uploadForm.procedureUrl = res.res.requestUrls[0];
        // this.upStatus = 1;
    },
    uploadError() {
        this.$message.error("上传失败，请重新上传");
    },
  },
  mounted(){
    this.minHeight = window.innerHeight - 95;
    this.getAppList();
    this.selectApp();
  },
  created(){
    // 筛选下拉框数据渲染
    this.validates = constant.validMode;
    this.authStatus = constant.authStatus;
  }
}
</script>

<style lang="scss">
  @import "../../styles/application-manage/index.scss";
</style>


