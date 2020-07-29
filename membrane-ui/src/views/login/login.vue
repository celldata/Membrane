<template>
	<div class="login" id="login">
		<div class="title-name"></div>
		<div class="login-container">
			<div class="logo-img">{{platform}}</div>
			<div class="login-box">
				<div id="error-tip">
					<div v-show="fail" class="error msg-e"><i class="el-icon-remove"></i>{{errMessage}}</div>
				</div>
				<el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="80px" hide-required-asterisk>
					<el-form-item label="用户名" prop="userName" >
						<el-input v-model.trim="loginForm.userName" placeholder="输入用户名" size="mini"></el-input>
					</el-form-item>
					<el-form-item label="密码" prop="password">
						<el-input type="password" v-model.trim="loginForm.password" placeholder="输入密码" size="mini"></el-input>
					</el-form-item>
          <!-- <el-form-item label="认证类型">
            <el-radio-group v-model="loginForm.authType" size="mini">
              <el-radio label="1" border>内部认证</el-radio>
              <el-radio label="2" border>外部认证</el-radio>
            </el-radio-group>
          </el-form-item> -->
          <p class="forget" ><span @click="dialogVisible = true">忘记密码？</span></p>
          <el-button size="mini" @click="userLogin()">登录</el-button>
        </el-form>
			</div>
		</div>
    <!-- 找回密码弹出框-->
    <el-dialog
      title="找回密码"
      :visible.sync="dialogVisible"
      width="500px"
      hight="171px"
      top="25vh"
      class="forgetPassWordDialog"
      :before-close="cancelHandle">
      <el-form :model="ruleForm" :rules="pwdRules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label='用户名' size="mini" prop="userName">
          <el-input placeholder="请输入用户名" v-model.trim="ruleForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="找回方式" size="mini" label-width="90px" style="margin-left:10px">
          <el-select v-model="ruleForm.mode" placeholder="发送方式">
            <el-option v-for="(item,index) in sendMode" :key="index" :value="item.value" :label="item.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item size="mini" label="验证码" prop="verifyCode">
          <el-input placeholder="请输入验证码" v-model="ruleForm.verifyCode">
            <template slot="append"><span @click="getCode" class="getCode" style="cursor:pointer">获取验证码</span></template>
          </el-input>
          <span class="sendMsg" v-show="msgShow">验证码已发送至{{sendMsg}}，请根据发送的验证码填写至验证码输入框，验证码将在30分钟后过期</span>
        </el-form-item>
        <el-form-item label="密码" prop="pass" size="mini">
          <el-input type="password" placeholder="请输入以字母开头，由字母数字组成的6-12位有效密码" v-model="ruleForm.pass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass" size="mini">
          <el-input type="password" placeholder="请再次输入密码" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        <div size="mini" class="btn">
          <el-button @click="cancelHandle">取消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
        </div>
      </el-form>
    </el-dialog>
	</div>
</template>
<script>
  import {validateToken,login,appId,sendCode,forgetPassword} from "../../api/api.js";
  import md5 from 'js-md5'
	export default {
		name:'login',
		data(){
      let validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {

          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };//密码校验
      let validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };//密码两次输入一致性校验
			return {
        platform:'权限管理平台',
				loginForm:{
					userName:null,
          password:null,
        },
        rules:{
          userName:[
            {required:true, message:"请输入用户名！", trigger:'blur'}
          ],
          password:[
            {required:true,message:"请输入密码！",trigger:'blur'},
            {pattern:/^[a-zA-Z][a-zA-Z0-9]{5,11}$/, message:"请输入以字母开头，由字母数字组成的6-12位有效密码", trigger:'blur'}
          ]
        },
				errMessage:null, //后台返回登录失败错误提示信息
				fail:false,//登录表单验证提示语是否展示
				redirect_url : null,
        appId:null, //应用平台唯一标识
        certificationType:null,//应用平台认证类型
        dialogVisible:false,//密码找回弹出框是否显示
        sendMode:[
          {name:'短信验证', value:1},
          {name:'邮箱验证', value:0}
        ],//验证方式
        mode:2,//默认选择邮箱发送
        ruleForm: {//找回密码form
          pass: '',
          checkPass: '',
          userName:'',
          mode:0,
          verifyCode:''
        },
        pwdRules: {//找回密码formRule
          userName:[
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          verifyCode:[
            { required: true, message: '请输入验证码', trigger: 'blur'}
          ],
          pass: [
            { required: true, validator: validatePass, trigger: 'blur' },
            { pattern:/^[a-zA-Z][a-zA-Z0-9]{5,11}$/, message:"请输入以字母开头，由字母数字组成的6-12位有效密码", trigger:'blur'}
          ],
          checkPass: [
            { required: true, validator: validatePass2, trigger: 'blur' },
            { pattern:/^[a-zA-Z][a-zA-Z0-9]{5,11}$/, message:"请输入以字母开头，由字母数字组成的6-12位有效密码", trigger:'blur'}
          ]
        },
        isShow:false,//修改密码Input是否要展示
        isDisabled:true,//‘下一步’button是否可点击
        msgShow:false,//验证码发送提示语
        sendMsg:"",//验证码发送成功提示语
			}
		},
		mounted(){
      //1. 用户是否由其它系统跳转，保存跳转url
      let self = this;
      self.redirect_url = self.getUrlKey("url");
      self.appId = self.getUrlKey("appId");
      self.certificationType = self.getUrlKey("certificationType");
      if (!self.appId) {
        self.appId = appId;
      }
      window.localStorage.setItem("appId", self.appId);
      //2. 用户是否已经登录: 登录首要条件为有sso_token且有效
      let sso_token = window.localStorage.getItem("sso_token");
      if (sso_token) {
        //调用后台接口判断token是否依然有效并获得目标系统的tokenKey
        validateToken({ authorization: sso_token, appId: self.appId }).then(res => {
          if (res.err_CODE && res.err_CODE !== 0) {
            //token已经失效，重新登录，清除sso token
            window.localStorage.removeItem("sso_token");
          } else {
            //token有效，用户已经登录，进行跳转
            var tokenData = res.data;
            var token = tokenData.tokenKey;
            let haveAccess = tokenData.haveAccess;
            window.localStorage.setItem("sso_token", token);
            //跳转到原系统
            if (self.appId && self.redirect_url && haveAccess ) {
              var appTokenKey = tokenData.tokenKey;
              window.location.href =
                self.redirect_url + "?tokenKey=" + appTokenKey;
            } else if (self.appId && !self.redirect_url && haveAccess ) {
              self.$router.push({ path: "/userManagement" });
            }
          }
        });
      } else {
        //用户尚未登录
      }
		},
		methods:{
			getUrlKey: function (name) {
        return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null
			},
			userLogin(){
        /**
         * @description 用户登录
         * @param {String} userName
         * @param {String} password
         * @param {String} appId 应用ID
         * @param {String} url 跳转地址
         * @param {Number} certificationType 应用认证类型:0-内部 1-外部
         */
        let self = this;
        self.$refs['loginForm'].validate(valid =>{
          if(valid){
            let params = {
              userName:self.loginForm.userName,
              password:self.certificationType == 0 || self.certificationType == null ? md5(self.loginForm.password) : self.loginForm.password,
              appId:self.appId,
              url:self.redirect_url
            };

            if(!self.appId){
              // 如果应用ID没有，意味着登录的是SSO平台，则将前端常量appId 赋值；
              params.appId = appId;
            }

            login(params).then(res=>{
              if(res.err_CODE === 0){
                //登录成功
                window.localStorage.setItem("sso_token",res.data.token)
                window.localStorage.setItem("tokenKey",res.data.tokenKey);
                // haveAuthority =1 表示该用户有该平台权限， 0表示该用户没有该平台权限
                window.localStorage.setItem("haveAuthority",res.data.isHavaAuthority)
                // haveSSOAuthority = 1 表示该用户有权限管理平台的权限， 0 表示无权限管理平台的权限
                window.localStorage.setItem("haveSSOAuthority",res.data.isHavaSSOAuthority)
                window.localStorage.setItem("userId",res.data.userInfo.id)
                window.localStorage.setItem("userName",res.data.userInfo.username)

                //如果是从别的系统跳转过来，则跳转回原系统
                if(self.appId && self.redirect_url){
                  var appTokenKey = res.data.tokenKey;
                  window.location.href = self.redirect_url + "?tokenKey="+appTokenKey;
                }else{
                  //TODO跳转到sso的默认已登录页面
                  self.$router.push({path:'/application'});
                }
                self.fail = false;
              }else{
                self.fail = true;
                self.errMessage = res.err_MESSAGE;
              }
            });
          }
        })
      },
      cancelHandle(){
        /**
         * @description 找回密码弹出框消失,form表单reset
         */
        this.dialogVisible = false;
        this.isShow = false;
        this.msgShow = false;
        this.$refs['ruleForm'].resetFields();
      },
      getCode(){
        /**
         * @description 根据选择的找回方式，向邮箱或手机号发送验证码
         * @param {String} token 令牌
         * @param {String} userName 用户名
         * @param {Number} flag 找回方式：1-手机号 0-邮箱
         */
        let param = {userName:this.ruleForm.userName,flag:this.ruleForm.mode};
        sendCode(param).then(res=>{
          if(res.err_CODE === 0){
            this.msgShow = true;
            this.sendMsg = res.data;
          }else{
             this.$message.error(res.err_MESSAGE);
          }
        })
      },
      submitForm(formName) {
        /**
         * @description 找回密码
         * @param {String} passWord 输入密码
         * @param {String} rePassword 密码确认
         * @param {String} userName 用户名
         * @param {String} verification 验证码
         * @param {Number} flretrieveFormag 找回方式：1-手机号 0-邮箱
         */
        let self = this;
        let param = {
          passWord:md5(self.ruleForm.pass),
          userName:self.ruleForm.userName,
          verification:self.ruleForm.verifyCode,
          flag:self.ruleForm.mode
        };
        this.$refs[formName].validate((valid) =>{
          if(valid){
            forgetPassword(param).then(res=>{
              if(res.err_CODE === 0){
                self.dialogVisible = false;
                self.$message.success(res.err_MESSAGE);
                self.$refs[formName].resetFields();
                self.msgShow = false;
              }else{
                self.dialogVisible = true;
                self.$message.error(res.err_MESSAGE);
              }
            })
          }else{
            return false;
          }
        })
      }
		},
	}
</script>
<style lang="scss">
	@import '../../styles/login.scss';
</style>
