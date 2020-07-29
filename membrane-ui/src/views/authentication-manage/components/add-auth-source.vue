<template>
<!-- 添加认证源 -->
    <el-dialog
      title="新增认证源"
      :visible.sync="addVisible"
      width="846px"
      :close-on-click-modal="false"
      class="appDialog add-source"
      @close="cancelHandle('addForm')"
     >
      <div class="app-content">
        <template>
          <el-tabs v-model="activeName" type="card">
            <!-- 认证源配置 -->
            <el-tab-pane label="认证源配置" name="first" style="">
              <el-scrollbar id="scrollbar">
                <div class="tab-box">
                  <el-form ref="addForm" label-width="110px" :model="addForm" :rules="addRule">
                    <!-- 基本信息 -->
                    <el-divider>基本信息</el-divider>
                    <el-form-item label="认证类型" prop="type">
                      <el-select placeholder="请选择活动区域" size="mini" v-model="addForm.type">
                        <el-option v-for="item in authType" :key="item.value" :label="item.label" :value="item.value"></el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="认证源名称" prop="name">
                      <el-input placeholder="请输入认证源名称" size="mini" v-model="addForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="描述" prop="desc" label-width="100px" class="mg10 top10">
                      <el-input type="textarea" size="mini" :rows="2" show-word-limit maxlength="30" placeholder="请输入内容" v-model="addForm.desc"></el-input>
                    </el-form-item>
                    <!-- 服务器配置信息 -->
                    <el-divider>服务器配置</el-divider>
                    <el-form-item label="LDAP URL" prop="url">
                      <el-input placeholder="ldap://" size="mini" v-model="addForm.url"></el-input>
                    </el-form-item>
                    <el-form-item label="LDAP Base" prop="base">
                      <el-input placeholder="dc=xxx,dc=com" size="mini" v-model="addForm.base"></el-input>
                    </el-form-item>
                    <el-form-item label="LDAP UserDn" prop="userDn">
                      <el-input placeholder="cn=Manager,dc=xxx,dc=com" size="mini" v-model="addForm.userDn"></el-input>
                    </el-form-item>
                    <el-form-item label="LDAP 密码" prop="pwd">
                      <el-input placeholder="LDAP 密码" size="mini" v-model="addForm.pwd"></el-input>
                    </el-form-item>
                    <el-form-item label="过滤条件" label-width="100px" class="mg10">
                      <el-input placeholder="查询用户的过滤条件" size="mini" v-model="addForm.filter"></el-input>
                    </el-form-item>
                    <!-- 状态 -->
                    <el-divider>LDAP数据同步状态</el-divider>
                    <el-form-item label="LDAP同步权限系统" label-width="130px" prop="sycUser">
                      <el-radio-group size="mini" v-model="addForm.sycUser" @change="statusHandle">
                        <el-radio :label="0" border>禁用</el-radio>
                        <el-radio :label="1" border>启用</el-radio>
                      </el-radio-group>
                      <span class="tips">禁用状态下：只做用户登录认证处理，启用状态下：可以自动从LDAP同步数据到权限系统</span>
                    </el-form-item>
                    <template v-if="addForm.sycUser != 0">
                      <el-form-item label="定时任务同步数据" label-width="130px" prop="sycSche">
                        <el-radio-group size="mini" v-model="addForm.sycSche">
                          <el-radio :label="0" border>禁用</el-radio>
                          <el-radio :label="1" border>启用</el-radio>
                        </el-radio-group>
                        <span class="tips">禁用状态下：定时任务不触发，用户初次登录时从LDAP上进行该用户同步数据，启用状态下：定时从LDAP上进行用户数据同步</span>
                      </el-form-item>
                      <el-form-item label="间隔时间(hour)" label-width="130px" prop="sycFixed">
                        <el-input-number :step="1" :disabled="addForm.sycSche == 0" size="mini" v-model="addForm.sycFixed" :min="1" :max="24"></el-input-number>
                      </el-form-item>
                    </template>

                  </el-form>
                </div>
              </el-scrollbar>
            </el-tab-pane>
            <!-- 字段配置 -->
            <!-- <el-tab-pane label="字段配置" name="second">
              <el-scrollbar id="scrollbar1">
                <div class="tab-box">
                  待开发
                </div>
              </el-scrollbar>
            </el-tab-pane> -->
          </el-tabs>
        </template>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelHandle('addForm')">取消</el-button>
        <el-button type="primary" @click="addHandle('addForm')">确定</el-button>
      </span>
    </el-dialog>
</template>

<script>
import {addExternalAuthInfo} from '../../../api/api'
import selectObj from '../../../labs/constant'
export default {
  props:['addAuthSource'],
  data(){
    return{
      activeName:'first',
      addVisible:false,
      num7:1,
      addForm:{
        type:1,
        name:null,
        url:null,
        desc:null,
        pwd:null,
        base:null,
        userDn:null,
        filter:null,
        sycUser:0,
        sycSche:0,
        sycFixed:1,
      },
      addRule:{
        type:[
          { required: true, message: '请选择活动区域', trigger: 'change' }
        ],
        name:[
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        url:[
          { required: true, message: '请输入服务器URL', trigger: 'blur' },
          {
            pattern: /^(ldap?:\/\/)([0-9a-z.]+)(:[0-9]+)?([/0-9a-z.]+)?(\?[0-9a-z&=]+)?(#[0-9-a-z]+)?/,
            message: '请输入符合规范的服务器URL',
            trigger: 'blur'
          },{
            min:10,
            max:30,
            message:'请输入长度为10到30个字符内的服务器Url',
            trigger: 'blur'
          }
        ],
        pwd:[
          { required: true, message: '请输入LDAP 密码', trigger: 'blur' },
          { min: 6, max: 30, message: '请输入长度为6-30位任意字符组成的密码',trigger: 'blur'}
        ],
        base:[
          { required: true, message: '请输入LDAP base', trigger: 'blur' },
          { pattern: /^([a-z])+[=]([a-zA-Z0-9]){1,19}$/, message: '请输入3-20位由小写字母开头，中间以等号隔开的字母数字构成的符合规范的字符', trigger: 'blur'},
        ],
        userDn:[
          { required: true, message: '请输入LDAP userDn', trigger: 'blur' },
          { pattern: /(^[a-z]{2})[=]([a-z0-9])[,]?/, message: '请输入由长度为2的小写字母组成，中间以等号隔开的长度为2-8的字母数字构成的符合规范的字符', trigger: 'blur'},
        ],
        desc:[
          { required: false }
        ],
        filter:[
          { required: false }
        ],
        sycUser:[
          { required: true, message: '请选择数据同步状态', trigger: 'change'}
        ],
        sycSche:[
          { required: true, message: '请选择是否进行定时任务', trigger: 'change'}
        ],
        sycFixed:[
          { required: true, message: '请填写间隔时间(小时)', trigger: 'blur'}
        ]
      },
      authType:[]
    }
  },
  methods:{
    cancelHandle(formName){
      /**
       * @description 新增认证源弹出框取消
       */
      this.$refs[formName].resetFields();
      this.addVisible = false;
    },
    addHandle(formName){
      /**
       * @description 新增认证源
       * @param {Number} type 外部接入类型
       * @param {Object} parameters 参数列表
       * @param {String} parameters.url 服务器URL Ldap url(如：ldap://127.0.0.1:389)
       * @param {String} parameters.pwd 服务器连接密码
       * @param {String} parameters.base Ldap Base 如：dc = xxxx,dc=com
       * @param {String} parameters.userDn Ldap UserDn,如：cn = job,dc=xxx,dc=com
       * @param {String} parameters.filter 过滤条件中使用$userName$替换用户名（uid=$userName$）
       * @param {String} desc 认证源描述
       * @param {String} name 认证源名称
       * @param {Number} sycUser 是否同步用户数据  0：否 1：是
       * @param {Number} sycSche 是否触发定时任务  0：否 1：是
       * @param {Number} sycFixed 定时任务间隔时间(Hours)  min:1 max:24
       */
      this.$refs[formName].validate(valid =>{
        if(valid){
          // 验证通过，进行参数赋值
          let param = {
            type:this.addForm.type,
            name:this.addForm.name,
            desc:this.addForm.desc,
            parameters:{
              url: this.addForm.url,
              pwd: this.addForm.pwd,
              base: this.addForm.base,
              userDn: this.addForm.userDn,
              filters: this.addForm.filter,
              sycUser: this.addForm.sycUser,
              sycSche: this.addForm.sycSche,
              sycFixed: this.addForm.sycFixed
            }
          };
          addExternalAuthInfo(param).then(res=>{
            if(res.err_CODE === 0){
              this.addVisible = false;
              //调用父组件重新刷新列表页
              this.$parent.getAllAuthenticationSources();
            }
          })

        }else{
          return false;
        }
      })
    },
    statusHandle(val){
      /**
       * @description 状态切换
       */

    }
  },
  mounted(){
  },
}
</script>


<style lang="scss">
  @import "../../../styles/authentication-manage/auth-component.scss";
</style>
