<template>
<!-- 应用基本信息管理 -->
  <div class="appComponent">
    <el-card>
      <div slot="header" class="clearfix">
        <span>应用信息</span>
      </div>
      <div class="text item">
        <div class="content">
          <ul class="app-box">
            <li>
              <span :title="appDetails.clientName">名称：{{appDetails.clientName.length > 8 ? appDetails.clientName.substr(0,7)+"..." : appDetails.clientName}}</span>
              <span :title="appDetails.creatorUserName">创建人：{{appDetails.creatorUserName == null ? "" : appDetails.creatorUserName.length > 8 ? appDetails.creatorUserName.substr(0,7)+"..." : appDetails.creatorUserName}}</span>
            </li>
            <li class="desc"><span :title="appDetails.appDesc">描述：{{appDetails.appDesc == null ? '暂无描述' : appDetails.appDesc.length > 20 ? appDetails.appDesc.substr(0,19)+"..." : appDetail.appDesc}}</span></li>
          </ul>
        </div>
        <div class="bottom clearfix">
          <el-button type="text" class="button detail" @click="checkDetail">查看详情</el-button>
          <el-button type="text" class="button edit" @click="editAppDialogVisible = true">编辑应用</el-button>
          <el-button type="text" class="button delete" @click="delAppDialogVisible = true">删除应用</el-button>
        </div>
      </div>
    </el-card>
    <!-- 编辑应用 -->
    <el-dialog
      title="编辑应用"
      :visible.sync="editAppDialogVisible"
      :close-on-click-modal="false"
      width="746px"
      top="20vh"
      class="appDialog"
      @close="resetForm('editAppForm')">
      <div class="app-content" >
        <template>
          <el-form :model="editAppForm" :rules="editAppRules" ref="editAppForm" label-width="91px">
            <!-- <el-form-item label="应用ID" size="mini" class="appId mg10" label-width="81px">
              <span class="appId">{{editAppForm.clientId}}</span>
            </el-form-item> -->
            <el-form-item label="应用logo" size="mini" class="mg10" label-width="81px">
              <!-- 应用logo上传 -->
              <div class="uploadLogo">
                <img class="fl" :src="editAppForm.url" v-if="editAppForm.url" />
                <el-upload
                  class="upload-demo"
                  ref="upload"
                  name="image_file"
                  action=""
                  :auto-upload="true"
                  :http-request="uploadImage"
                  :show-file-list="false"
                  :before-upload="beforeAvatarUpload"
                  :on-success="handleAvatarSuccess"
                  :on-error="uploadError"
                  :with-credentials="true"
                  size="mini"
                >
                  <el-button size="small" type="text">上传</el-button>
                </el-upload>
              </div>
            </el-form-item>
            <el-form-item label="应用名称" size="mini" prop="clientName">
              <el-input placeholder="请输入应用名称" v-model.trim="editAppForm.clientName"></el-input>
            </el-form-item>
            <el-form-item label="App ID"  class="appId mg10" size="mini">
              <span>{{editAppForm.appId}}</span>
            </el-form-item>
            <el-form-item label="App Secret" size="mini" class="appId mg10"  label-width="81px">
              <el-button type="text" :disabled="true" class="showColor">{{isShow ? '******' : editAppForm.secret}}</el-button>
              <el-button type="text" :disabled="false" @click="isShow = !isShow">{{isShow ? '显示' : '隐藏'}}</el-button>
              <el-button type="text" :disabled="false" @click="resetSecretHandle">重置</el-button>
            </el-form-item>
            <el-form-item label="描述" size="mini" class="appId mg10"  label-width="81px" prop="appDesc">
              <el-input type="textarea" :rows="2" placeholder="请输入描述内容" show-word-limit maxlength="30" v-model.trim="editAppForm.appDesc"></el-input>
            </el-form-item>
          </el-form>
        </template>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editAppDialogVisible = false;resetForm('editAppForm')">取消</el-button>
        <el-button type="primary" @click="editApp('editAppForm')">确定</el-button>
      </span>
    </el-dialog>
    <!-- 应用基本信息查看 -->
    <el-dialog
      title="应用概览"
      :visible.sync="AppDetailDialogVisible"
      width="946px"
      height="600px"
      top="20vh"
      id="app-detail"
      class="appDialog detail-dialog tabMenu"
      :before-close="()=>AppDetailDialogVisible = false">
      <el-scrollbar class="cmp-scroll">
        <el-divider content-position="left">基本信息</el-divider>
        <div class="app-content dialog-content" >
          <template>
            <div class="basic">
              <ul class="app-detail">
                <li class="li-logo">
                  <span><i class="label-name">应用logo：</i><img alt='logo' src="../../../assets/image/login-bg.png" /></span>
                </li>
                <li>
                  <span><i class="label-name">应用ID：</i><i class="label-value">{{appDetails === null ? '--' : appDetails.clientId}}</i></span>
                  <span><i class="label-name">应用名称：</i><i class="label-value" :title="appDetails.clientName">{{appDetails === null ? '--' : appDetails.clientName.length > 10 ? appDetails.clientName.substr(0,9)+"...":appDetails.clientName}}</i></span>
                </li>
                <li>
                  <span><i class="label-name">App Key：</i> <i class="label-value">{{appDetails === null ? '--' : appDetails.appId}}</i></span>
                  <span><i class="label-name">App Secret：</i> <i class="label-value">{{appDetails === null ? '--' : appDetails.secret}}</i></span>
                </li>
                <li>
                  <span><i class="label-name">创建人：</i><i class="label-value">{{appDetails === null ? '--' : appDetails.creatorUserName}}</i></span>
                  <span><i class="label-name">创建时间：</i><i class="label-value">{{appDetails === null ? '--' : appDetails.createTime}}</i></span>
                </li>
              </ul>
            </div>
          </template>
        </div>
        <el-divider content-position="left">应用授权角色详情</el-divider>
        <div class="app-content dialog-content" >
          <div class="roles" style="width:100%;height:auto">
            <template>
              <el-table :data="roleTb">
                <!-- <el-table-column prop="roleId" label="角色ID"></el-table-column> -->
                <el-table-column prop="roleName" label="角色名称"></el-table-column>
                <el-table-column label="挂载用户量">
                  <template slot-scope="scope">
                    <span>{{scope.row.userlist.length > 0 ? scope.row.userlist.length : 0}}</span>
                  </template>
                </el-table-column>
              </el-table>
              <div class="block" v-if="roleTb.length > 5">
                      <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage4"
                        :page-sizes="[100, 200, 300, 400]"
                        :page-size="100"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="400">
                      </el-pagination>
              </div>
            </template>
          </div>
        </div>
      </el-scrollbar>
    </el-dialog>
    <!-- 删除应用 -->
    <el-dialog
      title="删除应用"
      :visible.sync="delAppDialogVisible"
      width="746px"
      top="20vh"
      class="appDialog"
      :before-close="()=>delAppDialogVisible=false">
      <div class="app-content">
        确定要删除应用：<i class="clientName">{{appDetail.clientName}}</i>吗？删除后不可恢复！
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delAppDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="deleteApp">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  getDate,
} from "../../../labs/tools"
import {
  editAppInfo,
  delApp,
  selectAllRole,
  fetchUserInfoList
} from "../../../api/api"
import ossUpload from '../../../labs/upload.js'

export default {
  props:{
    appDetail:Object
  },
  data(){
    return{
      tabPosition:'left',
      activeName:"first",
      input3:null,
      appDetails:null,
      addAppDialogVisible:true,
      AppDetailDialogVisible:false,
      editAppDialogVisible:false,
      delAppDialogVisible:false,
      isShow:true,
      page:{
      		pageIndex: 1,
          pageSize: 10,
          total: 10,
      },
      editAppForm:{
        clientId:null,
        clientName:null,
        appId:null,
        secret:null,
        url:null,
        appDesc:null,
        createTime:null,
        authentication:null,
        verification:null,
        creatorId:null,
        updaterId:null
      },//编辑应用form
      editAppRules:{
        clientName:[
          { required : true, message : "请输入应用名称!", trigger : "blur" },
          { min: 2, max: 20, message: "请输入2到20个字符", trigger : "blur"}
        ],
        appDesc:[
          { required : false}
        ]
      },//编辑应用表单验证
      roleTb:[]
    }
  },
  mounted(){
    this.editAppForm = JSON.parse(JSON.stringify(this.appDetails));
  },
  created(){
    this.appDetail !== undefined ? this.appDetails = JSON.parse(JSON.stringify(this.appDetail)) : this.appDetails = null;
    console.log(this.appDetail);
    this.appDetails.createTime = getDate(new Date(this.appDetails.createTime).getTime()/1000);
  },
  methods:{
    deleteApp(){
      /**
       * @description 删除应用
       * @param {Number} clientId 应用ID
       */
      let param = {clientId: this.appDetails.clientId};
      delApp(param).then(res=>{
        if(res.err_CODE === 0){
          this.delAppDialogVisible = false;
          this.$message.success(res.err_MESSAGE);
          this.$emit("refreshTable",true);
        }else{
          this.$message.error(res.err_MESSAGE);
        }
      })

    },
    resetSecretHandle(){
      /**
       * @description  重置APP Secret
       * @param {Number} clientId 应用ID
       * @param {String} secret
       */
      let param = {
        clientId:this.editAppForm.clientId,
        secret:'reset'
      };
      editAppInfo(param).then(res=>{
        if(res.err_CODE === 0){
          this.$message.success(res.err_MESSAGE);
          this.editAppForm.secret = res.data.secret;
        }else{
          this.$message.error(res.err_MESSAGE);
        }
      })
    },
    editApp(formName){
      let self = this;
      let param = {
        clientId:self.editAppForm.clientId,
        clientName:self.editAppForm.clientName,
        appId:self.editAppForm.appId,
        imgUrl:null,
        appDesc:self.editAppForm.appDesc
      };
      self.$refs[formName].validate((valid) =>{
        if(valid){
          editAppInfo(param).then(res=>{
            if(res.err_CODE === 0){
              self.editAppDialogVisible = false;
              self.$message.success(res.err_MESSAGE);
              self.$emit('refreshTable',true)
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
       * @description form表单重置
       */
      this.$refs[formName].resetFields();
    },
    roleDetails(){
      let param = {
        clientId:this.appDetails.clientId,
        pageIndex:this.page.pageIndex,
        pageSize:this.page.pageSize,
      };
      selectAllRole(param).then(res=>{
        if(res.err_CODE === 0){
          if(res.data.list.length > 0){
            this.roleTb = res.data.list.map(item=>{
              item.userlist = [];
              let param = {
                clientId:this.appDetails.clientId,
                roleId:item.roleId,
                pageIndex:null,
                pageSize:null
              }
              fetchUserInfoList(param).then(res1 =>{
                if(res1.err_CODE === 0){
                  item.userlist = res1.data.list;
                  return item;
                }else{
                  this.$message.error(res1.err_MESSAGE);
                }
              })
            })
            this.roleTb = res.data.list;
            this.page.total = res.data.total;
          }else{
            this.roleTb = [];
            this.page.total = res.data.total;
          }

        }
      })
    },
    checkDetail(){
      this.roleDetails();
      this.AppDetailDialogVisible = true;
    },
		handleSizeChange(val) {
		    this.page.pageSize = val;
        this.page.pageIndex = 1;
        this.getRoleList();
		},
		handleCurrentChange(val) {
        this.page.pageIndex = val;
        this.getRoleList();
		},
    uploadImage(option) {
      ossUpload.ossUploadFile(option);
    },
    //校验图片格式
    beforeAvatarUpload(file) {
      let testmsg = /^image\/(jpeg|png|jpg|gif|bmp)$/.test(file.type);
      let isLt5M = file.size / 1024 / 1024 <= 5; //图片大小不超过5MB
      if (!testmsg) {
        this.$message.error("图片格式必须为：bmp，png，jpeg，jpg，gif!");
        return false;
      }
      if (!isLt5M) {
        this.$message.error("上传图片大小不能超过5M！");
        return false;
      }
    },
    // 获取上传成功的图片地址
    handleAvatarSuccess(res, file) {
      this.editAppForm.url = res.res.requestUrls[0];
      this.$message.success("上传成功");
    },
    //图片错误提示
    uploadError() {
      this.$message.error("上传失败，请重新上传");
    },
  }
}
</script>
<style lang="scss">
  @import "../../../styles/application-manage/application-component.scss";
</style>
