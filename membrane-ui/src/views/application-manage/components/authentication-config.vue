<template>
<!-- 认证管理 -->
  <div class="appComponent">
    <el-card>
      <div slot="header" class="clearfix">
        <span>认证管理</span>
      </div>
      <div class="text item">
        <div class="content">
          应用认证配置中心
        </div>
        <div class="bottom clearfix">
          <!-- <el-button type="text" class="button detail" @click="detailVisible=true">查看详情</el-button> -->
          <el-button type="text" class="button edit" @click="configVisible = true">认证配置</el-button>
        </div>
      </div>
    </el-card>
    <!-- 认证配置 -->
    <el-dialog
      title="认证管理"
      :visible.sync="configVisible"
      width="746px"
      height="400px"
      @close="cancelClose('authConfigForm')"
      top="20vh"
      class="appDialog">
      <div class="app-content">
        <template>
          <el-form :model="authConfigForm" ref="authConfigForm" label-width="121px" hide-required-asterisk :rules="formRule">
            <!-- <el-form-item label="启用认证" size="mini" class="appId">
              <el-switch active-value="1" inactive-value="0" v-model="authConfigForm.authentication"  @change="isEnable = !isEnable;"></el-switch>
              <el-radio-group v-model="authConfigForm.verification" size="mini" :disabled="authConfigForm.authentication == '0'">
                <el-radio v-for="(item,index) in authStatus[0].children" :label="item.value" :key="index" border>{{item.label}}</el-radio>
              </el-radio-group>
            </el-form-item> -->
            <el-form-item label="认证方式" size="mini" class="appId" prop="authentication">
              <el-radio @change="radioHandle" v-for="(i,index) in ways" v-model="authConfigForm.authentication" :key="index" :label="i.value">{{i.label}}</el-radio>
            </el-form-item>
            <el-form-item label="外部认证类型" size="mini" class="appId" prop="verification">
              <el-radio-group v-model="authConfigForm.verification" :disabled="authConfigForm.authentication == 0" @change="handleChange">
                <el-radio  v-for="(item,index) in authStatus[0].children" :label="item.value" :key="index">{{item.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="外部认证源" size="mini" class="appId" prop="externalAuthId"  v-if="authConfigForm.authentication != 0">
              <el-select v-model="authConfigForm.externalAuthId" >
                <template v-if="externalAuthList.length == 0">
                  <el-option label="暂无数据" value="暂无数据"></el-option>
                </template>
                <template v-else>
                  <el-option v-for="item in externalAuthList" :key="item.id" :value="item.id" :label="item.name"></el-option>
                </template>

              </el-select>
            </el-form-item>
            <el-form-item label="校验模式" size="mini" class="appId mgl" prop="tokenCheckType">
              <el-radio-group v-model="authConfigForm.tokenCheckType" size="mini">
                <el-radio v-for="(item,index) in validMode" :key="index" :label="item.modeId"  border>{{item.modeName}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="Access Token(min)" size="mini" prop="accessValidity">
              <el-input-number size="mini" v-model="authConfigForm.accessValidity" :step="5"></el-input-number>
            </el-form-item>
          </el-form>
        </template>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelClose('authConfigForm')">取消</el-button>
        <el-button type="primary" @click="authConfig('authConfigForm')">确定</el-button>
      </span>
    </el-dialog>
    <!-- 认证管理详情[因目前内容较少，暂时屏蔽此详情展示功能] -->
    <!-- <el-dialog
      title="认证管理详情"
      :visible.sync="detailVisible"
      width="746px"
      top="20vh"
      class="appDialog detail-dialog">
      <div class="app-content dialog-content">
        <ul class="certificate-detail">
          <li><span class="label-name">认证状态：</span><span class="label-value"></span></li>
          <li><span class="label-name">验证方式：</span><span class="label-value"></span></li>
          <li><span class="label-name">Access Token(m)：</span><span class="label-value">{{appDetails.accessValidity}}分钟</span></li>
        </ul>
      </div>
    </el-dialog> -->
  </div>
</template>

<script>
import constant from "../../../labs/constant";
import {
  authConfig,
  externalAuthSourceList
} from "../../../api/api";
export default {
  props:['appDetail'],
  data(){
      let validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        }else if(value < 15 || value > 11520){
          callback(new Error('请输入在15分钟和11520分钟范围内的有效时间'))
        }else{
          callback();
        }
      };//accessToken 校验
    return{
      error:false,
      errorTips:'',
      configVisible:false,
      detailVisible:false,
      switchStatus:false,//认证状态未启用
      authType:null,//认证类型
      validate:null,
      tokenDate:1,
      isEnable:true,//是否启用
      appDetails:null,
      authStatus:null,
      validMode:null,
      ways:[],
      externalAuthList:[],
      authConfigForm:{
        clientId:null,
        accessValidity:15,
        authentication:null,
        tokenCheckType:null,
        verification:null,
        externalAuthId:null
      },
      formRule:{
        accessValidity:[
          { required :true, validator: validatePass, trigger: 'blur'}
        ],
        authentication:[
          { required : false}
        ],
        tokenCheckType:[
          { required : false}
        ],
        verification:[
          { required : false,}
        ],
        externalAuthId:[
          { required : true, message:'请选择认证源', trigger: 'blur'}
        ]
      }
    }
  },
  mounted(){
    this.getExternalList();
  },
  created(){
    this.authConfigForm = JSON.parse(JSON.stringify(this.appDetail));
    // 将认证方式为null的数据赋予默认值0(主要是针对之前接口中在新增应用时未提供认证方式的默认值而增加的逻辑操作)
    this.authConfigForm.authentication = this.authConfigForm.authentication == null ?  0 : this.authConfigForm.authentication;
    this.authConfigForm.tokenCheckType = this.authConfigForm.tokenCheckType == null ? 0 : this.authConfigForm.tokenCheckType;
    // 认证select 数据绑定
    this.authStatus = constant.authStatus.filter(item => item.value == 1);
    // 过滤"全部"选择，为radio进行数据渲染
    this.ways = constant.authStatus.filter(item => item.value != null);
    // 验证select 数据绑定
    this.validMode = constant.validMode.filter(item => item.modeId != null);
  },
  methods:{
    authConfig(formName){
      /**
       * @description 认证配置
       * @param {Number} clientId
       * @param {Number} accessValidity
       * @param {Number} authentication
       * @param {Number} tokenCheckType
       * @param {Number} verification
       * @param {Number} externalAuthId
       */
      let param = {
        clientId:this.authConfigForm.clientId,
        accessValidity:this.authConfigForm.accessValidity,
        authentication:this.authConfigForm.authentication,
        tokenCheckType:this.authConfigForm.tokenCheckType,
        verification:this.authConfigForm.verification,
        externalAuthId:this.authConfigForm.externalAuthId
      };
      this.$refs[formName].validate(valid =>{
        if(valid){
          authConfig(param).then(res=>{
            if(res.err_CODE === 0){
              this.configVisible = false;
              this.$emit("refreshTable",true)
              this.$message.success(res.err_MESSAGE);
            }else{
              this.$message.error(res.err_MESSAGE);
            }
          })
        }else{
          return false;
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
    radioHandle(val){
      /**
       * @description 内外部认证方式选择事件
       */
      this.authConfigForm.authentication == 1 ? this.authConfigForm.verification = 0 : this.authConfigForm.verification = null;
    },
    cancelClose(formName){
      /**
       * @description 取消弹出框关闭
       */
      this.configVisible = false;
      this.$refs[formName].resetFields();
      this.getExternalList();
    },
    getExternalList(){
      /**
       * @description 获取外部认证源列表
      /**
       * @description 获取认证源列表数据
       * @param {Number} pageSize 分页大小
       * @param {Number} pageIndex 当前页
       * @param {Number} type 外部接入类型：1代表ldap
       * @param {Number} id 外部认证源id
       * @param {String} name 认证源名称
       */
      let param = {
        name: null,
        id: null,
        type: this.authConfigForm.verification,
        pageSize: null,
        pageIndex:null
      }
      externalAuthSourceList(param).then(res=>{
        console.log(res);
        res.err_CODE === 0 ? this.externalAuthList = res.data.list : this.$message.error(res.err_MESSAGE);
        if(res.data.list.length > 0){
          if(this.authConfigForm.externalAuthId == null){
            this.authConfigForm.externalAuthId = this.externalAuthList[0].id;
          }
        }
      })
    },
    handleChange(val){
      /**
       * @description 认证类型改变事件
       */
      this.authConfigForm.externalAuthId = null;
      this.authConfigForm.verification = val;
      this.getExternalList();
    }
  }
}
</script>
<style lang="scss">
  @import "../../../styles/application-manage/application-component.scss";
</style>
