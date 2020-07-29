<template>
  <div id="user-manage" :style="{minHeight: minHeight + 'px'}">
    <!-- 搜索区 -->
    <div class="select-container">
      <el-col :span="16">
        	选择角色
        <el-cascader
          @change="roleChange"
          :props="{ checkStrictly: true }"
          :options="applicationArr"
          v-model="page.roleId"
          size="mini"
        >
        <template slot-scope="{ node, data }">
          <span :title="data.name">{{ data.label }}</span>
        </template>
        </el-cascader>用户状态
        <el-select v-model="page.statusCode" @change="roleChange" size="mini">
          <el-option
            v-for="(pro, index) in userStatusArr"
            :key="index"
            :label="pro.name"
            :value="pro.code"
          ></el-option>
        </el-select>
        <el-input
          class="search"
          placeholder="用户名/姓名"
          v-model="page.searchKey"
          @keyup.native="roleChange"
          size="mini"
        >
          <em slot="suffix" class="el-input__icon el-icon-search"></em>
        </el-input>
      </el-col>
      <el-col :span="8" id="col_fr">
        <el-button type="primary" icon="el-icon-plus" @click="plusUser">新增用户</el-button>
      </el-col>
    </div>
    <!-- 列表区 -->
    <div class="table-container">
      <template>
        <el-table :data="tab_data">
          <el-table-column min-width="170" label="用户名">
            <template slot-scope="scope" v-if="scope.row.userName">
              <el-tooltip :content="scope.row.userName" placement="top" effect="dark">
                <span>{{scope.row.userName.length > 10 ? scope.row.userName.substr(0,9)+'...' : scope.row.userName}}</span>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column min-width="140" label="姓名">
            <template slot-scope="scope" v-if="scope.row.fullName">
              <el-tooltip :content="scope.row.fullName" placement="top" effect="dark">
                <span>{{scope.row.fullName.length > 10 ? scope.row.fullName.substr(0,9)+'...' : scope.row.fullName}}</span>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column min-width="130" prop="telephone" label="手机号码"></el-table-column>
          <el-table-column label="角色" prop="role" align="center" min-width="260">
            <template slot-scope="scope" v-if="scope.row.role">
              <el-tooltip :content="scope.row.role" placement="top" effect="dark">
                <span>{{scope.row.role.length > 20 ? scope.row.role.substr(0,19)+'...' : scope.row.role}}</span>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column min-width="170" prop="updateTime" label="更新时间"></el-table-column>
          <el-table-column min-width="90" prop="updater" label="更新人"></el-table-column>
          <el-table-column min-width="93" prop="status.name" label="用户状态"></el-table-column>
          <el-table-column min-width="150" label="操作">
            <template slot-scope="scope">
              <span class="spanBtn">
                <el-tooltip class="item" effect="dark" content="详情" placement="top">
                  <em class="iconfont icon-xiangqing" @click="detailsHandle(scope.row)"></em>
                </el-tooltip>
              </span>
              <span class="spanBtn">
                <el-tooltip class="item" effect="dark" content="编辑" placement="top">
                  <em class="el-icon-edit" @click="editDialogHandle(scope.row)"></em>
                </el-tooltip>
              </span>
              <span class="spanBtn">
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="scope.row.status.code==1? '禁用':'启用'"
                  placement="top">
                  <em
                    @click="forbidenStartHandel(scope.row)"
                    :class="scope.row.status['code']==1? 'el-icon-remove-outline':'iconfont icon-qiyong'"
                  ></em>
                </el-tooltip>
              </span>
              <span class="spanBtn2">
                <el-tooltip class="item" effect="dark" content="重置密码" placement="top">
                  <em class="iconfont icon-zhongzhimima" @click="resetHandle(scope.row)"></em>
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
          :total="total"
          v-if="total"
        ></el-pagination>
      </div>
    </div>
    <!-- 新增用户 -->
    <el-dialog
      title="新增用户"
      :visible.sync="addDialogVisible"
      width="746px"
      top="15vh"
      class="plusUserPage"
      :before-close="addCancelHandel">
      <el-scrollbar style="height:540px">
        <el-form :model="addForm" ref="addForm" :rules="userTest">
            <el-form-item label="用户名" prop="userName" label-width="71px">
              <el-input
                maxlength="50"
                v-model="addForm.userName"
                placeholder="支持字母、数字和_,必须以字母开头"
                size="mini"
              ></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="fullName" label-width="71px">
              <el-input
                minlength="2"
                maxlength="50"
                v-model="addForm.fullName"
                placeholder="请输入姓名"
                size="mini"
              ></el-input>
            </el-form-item>
            <el-form-item class="addPhone" label="密码" prop="pwd" label-width="71px">
              <el-input v-model="addForm.pwd" placeholder="请输入以字母开头，由字母数字组成的6-12位有效密码" size="mini" show-password></el-input>
            </el-form-item>
            <el-form-item class="addPhone" label="手机号码" prop="telephone" label-width="71px">
              <el-input v-model="addForm.telephone" placeholder="请输入有效手机号" size="mini"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email" label-width="71px" >
              <el-input type="email" v-model="addForm.email" placeholder="如需使用邮箱相关服务，请填写邮箱邮箱" size="mini"></el-input>
            </el-form-item>
            <el-form-item label="角色" label-width="61px" style="margin-left: 10px;">
              <div
                id="transferDiv"
                v-for="(item,index) in addForm.plusRoleData"
                :key="index"
              >
                <el-checkbox :title="item.clientRoleList.length === 0 ? '该应用下暂无角色':''" :disabled="item.clientRoleList.length === 0" @change="checkChange(item)" v-model="item.checked">{{item.clientName}}</el-checkbox>
                <template v-if="item.checked==true && item.clientRoleList">
                  <el-transfer
                    @change="checkChange(item)"
                    :titles="['未添加角色', '已添加角色']"
                    v-model="item.checkedValue"
                    :data="item.clientRoleList"
                  ></el-transfer>
                </template>
                <div
                  v-if="item.checked==true && item.checkedValue.length==0 && item.transfertTips"
                  class="emptyDate"
                >请至少添加一个角色</div>
              </div>
            </el-form-item>
        </el-form>
      </el-scrollbar>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addCancelHandel">取消</el-button>
        <el-button type="primary" @click="addDifineHandel" :disabled="onlyButton">确定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑用户 -->
    <el-dialog
      title="编辑用户"
      :visible.sync="editDialogVisible"
      width="746px"
      top="15vh"
      class="plusUserPage"
      :before-close="editCancelHandel">
      <el-scrollbar class="editUser" ref="editUser" style="height:540px">
        <el-form :model="editForm" ref="editForm" :rules="userTest">
          <el-form-item label="用户名" prop="userName" label-width="71px">
            <span class="userNameSpan">{{editForm.userName}}</span>
          </el-form-item>
          <el-form-item label="姓名" prop="fullName" label-width="71px">
            <el-input maxlength="50" v-model.trim="editForm.fullName" placeholder="请输入姓名" size="mini"></el-input>
          </el-form-item>
          <el-form-item class="addPhone" label="手机号码" prop="telephone" label-width="71px">
            <el-input v-model="editForm.telephone" placeholder="请输入有效手机号" size="mini"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email" label-width="71px">
             <el-input type="email" v-model="editForm.email" placeholder="如需使用邮箱相关服务，请填写邮箱邮箱" size="mini"></el-input>
          </el-form-item>
          <p id="editUserTip">{{editRole}}</p>
            <el-form-item label="角色" label-width="61px" style="margin-left: 10px;">
              <div
                id="transferDiv"
                v-for="(item,index) in roleEditData"
                :key="index"
              >
                <el-checkbox :title="item.clientRoleList.length === 0 ? '该应用下暂无角色':''" :disabled="item.clientRoleList.length === 0" @change="checkChange(item)" v-model="item.checked">{{item.clientName}}</el-checkbox>
                <template  v-if="item.checked==true && item.clientRoleList">
                  <el-transfer
                    @change="checkChange(item)"
                    :titles="['未添加角色', '已添加角色']"
                    v-model="item.checkedValue"
                    :data="item.clientRoleList"
                  ></el-transfer>
                </template>
                <div
                  v-if="item.checked==true && item.checkedValue.length==0 && item.transfertTips"
                  class="emptyDate"
                >请至少添加一个角色</div>
              </div>
            </el-form-item>
        </el-form>
      </el-scrollbar>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editCancelHandel">取消</el-button>
        <el-button type="primary" @click="editDifineHandel">确定</el-button>
      </span>
    </el-dialog>
    <!-- 重置 -->
    <el-dialog
      title="重置密码"
      :visible.sync="resetDialogVisible"
      width="576px"
      hight="171px"
      top="35vh"
      class="resetUserPage"
      :before-close="resetCancelHandel">
      <p class="resetP">{{resetMessage}}</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetCancelHandel">取消</el-button>
        <el-button type="primary" @click="resetDifineHandel">确定</el-button>
      </span>
    </el-dialog>
    <!-- 禁用启用 -->
    <el-dialog
      v-if="forStartText.status"
      :title="forStartText.status.code==2? '启用':'禁用'"
      :visible.sync="forStartDialogVisible"
      width="576px"
      hight="171px"
      top="35vh"
      class="resetUserPage"
      :before-close="forStartCancelHandel">
      <p
        class="resetP"
      >{{forStartText.status.code==2? '确定启用该用户？':'禁用后用户将无法登录托管到认证中心的所有平台，确定立即禁用？'}}</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="forStartCancelHandel">取消</el-button>
        <el-button type="primary" @click="forStartDifineHandel">确定</el-button>
      </span>
    </el-dialog>
    <!-- 详情 -->
    <el-dialog
      title="用户详情"
      :visible.sync="detailDialogVisible"
      width="746px"
      top="20vh"
      class="resetUserPage"
      :before-close="detailCancelHandel">
      <div class="detailCss">
       	<span><span class="prototypeName">用户名：</span><span class="prototypeValue">{{detailHtml.userName}}</span></span>
        <span><span class="prototypeName">姓名：</span><span  class="prototypeValue">{{detailHtml.fullName}}</span></span>
        <span><span class="prototypeName">手机号码：</span><span  class="prototypeValue">{{detailHtml.telephone}}</span></span>
        <span><span class="prototypeName">邮箱：</span><span  class="prototypeValue">{{detailHtml.email || '--'}}</span></span>
        <span><span class="prototypeName">用户状态：</span><span  class="prototypeValue">{{detailHtml.status['name']}}</span></span>
        <br>
        <span><span class="prototypeName">角色：</span><span  class="prototypeValue">{{detailHtml.role}}</span></span>
        <br>
        <span><span class="prototypeName">更新时间：</span><span  class="prototypeValue">{{detailHtml.updateTime}}</span></span>
        <span><span class="prototypeName">更新人：</span><span  class="prototypeValue">{{detailHtml.updater}}</span></span>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  fetchUserInfoList,
  resetPassword,
  applicationList,
  addUser,
  roleAndUserStatus,
  updateUser,
  appId
} from "../../api/api.js";
import { fullNamePattern } from '../../labs/tools';
export default {
  name: "user-manage",
  data() {
    return {
      roleProp:{
        label:'clientName',
        value:'clientId',
        children:'roles'
      },
      onlyButton: false,//“确定按钮”是否可点击
      applicationArr: [],//角色下拉列表
      minHeight: null,
      resetMessage:' 重置密码后该用户当前密码失效，是否立即重置？',
      editRole:"注意：修改用户的角色后，该用户将退出登录，用户正在进行的操作无法保存，请确认没有影响后进行修改。",
      page: {
        roleId: [null],
        statusCode: null,
        searchKey:null,
        pageSize: 10,
        pageIndex: 1,
        clientId:null,
      },
      userStatusArr: [],
      tab_data: [],
      forStartText: {
        flag:1
      },
      total: 10,
      addDialogVisible: false,
      editDialogVisible: false,
      resetDialogVisible: false,
      forStartDialogVisible: false,
      detailDialogVisible: false,
      addForm: {
        plusRoleData: [],
      },
      appList:[],
      editForm: {},
      userTest: {
        userName: [
          {
            required: true,
            pattern: /^[a-zA-Z][a-zA-Z0-9\_]{0,49}$/,
            message: "1-50个字符(支持字母、数字,必须以字母开头)",
            trigger: "blur"
          }
        ],
        fullName: [
          {
            required: true,
            validator: fullNamePattern,
            trigger: "blur",
            message:"请输入2-50个字符"
          }
        ],
        telephone: [
          {
            required: true,
            pattern: /^1[3456789]\d{9}$/,
            message: "请输入有效手机号",
            trigger: "blur"
          }
        ],
        email: [
          {
            required: true,
            message: "请输入有效邮箱",
            trigger: "blur"
          },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
        ],
        pwd:[
          {
            required:true,
            pattern:/^[a-zA-Z][a-zA-Z0-9]{5,11}$/,
            message:'请输入以字母开头，由字母数字组成的6-12位有效密码',
            trigger:"blur"
          }
        ]
      },
      value1: [1], //穿梭框选中值
      roleData: [],//角色列表数据
      roleEditData: [],//编辑时用户穿梭框数据
      phoneReset: {},//重置时列表数据
      detailHtml: {//详情列表数据
        status: {
          name: "启用",
          code: 1
        }
      },
      modifyMode:0,//修改模式：0-重置密码，1-修改密码
      resetInfo:{},//重置密码信息
      roleList:[],//角色列表
      isCheck:false,
    };
  },
  mounted() {
    this.minHeight = window.innerHeight - 95;
    this.userListAll();
    this.getAllApp();
  },
  created(){
    this.getRolesAndStatus();
  },
  methods: {
    handleItemChange(val){
      /**
       * @description 级联选择父级筛选
       */
      this.page.roleId = val;
      this.page.pageIndex = 1;
      this.userListAll();
    },
    checkChange(val){
      /**
       * @description 应用对应的checkbox Change事件
       */
      val.transfertTips = false;
    },
    roleSelected(val){
      this.isCheck = !this.isCheck
    },
    getAllApp() {
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
          pageSize:null,
          pageIndex:null
        }
      self.appList = [];
      self.addForm.plusRoleData = [];
      self.roleEditData = [];
      applicationList(param).then(res=>{
          if(res.err_CODE === 0){
            self.appList = res.data.list;
          }else{
            self.$message.error(res.err_MESSAGE);
          }

          // 拼成页面渲染的数据结构[{clientId:'',clientName:'',clientRoleList:[{roleId:'',roleName:'',key:'',label:''}]}]
          if(self.appList.length > 0){
            self.appList.map(item =>{
              let clientObj = {
                clientId: item.clientId,
                clientName: item.clientName,
                clientRoleList:item.roleList,
                checked:false,
                transfertTips:false,
                checkedValue:[]
              };

              if(clientObj.clientRoleList.length > 0){
                clientObj.clientRoleList.map(jtem =>{
                  jtem.key = jtem.roleId;
                  jtem.label = jtem.roleName;
                })
              }
              self.addForm.plusRoleData.push(clientObj);
              self.roleEditData.push(clientObj);
            })
          }
          // if(self.appList.length > 0){
          //   self.appList.map(item =>{
          //     let clientObj = {
          //       clientId: item.clientId,
          //       clientName: item.clientName,
          //       clientRoleList:[],
          //       checked:false,
          //       transfertTips:false,
          //       checkedValue:[]
          //     };
          //     /**
          //      * @description 根据应用ID获取对应的角色列表
          //      * @param {Number} clientId 应用ID
          //      */
          //     getSelectedRole({clientId : item.clientId}).then(res=>{
          //       if(res.err_CODE === 0){
          //         if(res.data.length > 0){
          //           res.data.map(jtem=>{
          //             let roleObj = {key: jtem.roleId,label:jtem.roleName};
          //             clientObj.clientRoleList.push(roleObj);
          //           })
          //         }
          //       }else{
          //         self.$message.error(res.err_MESSAGE);
          //       }
          //     })
          //    self.addForm.plusRoleData.push(clientObj);
          //    self.roleEditData.push(clientObj);
          //   })
          // }
        })
    },
    roleChange() {
      /**
       * @description 根据选择的角色、用户状态或用户名、姓名查询用户列表
       */
      this.page.pageIndex = 1;
      this.userListAll();
    },
    userListAll() {
      /**
       * @deprecated 用户列表
       * @param {Object} page
       * @param {Array} page.roleId 选中的角色ID
       * @param {String} page.statusCode 选中的用户状态
       * @param {Number} page.pageSize
       * @param {Number} page.pageIndex
       * @param {Number} page.clientId 应用ID
       */
      let pages = JSON.parse(JSON.stringify(this.page));
      if (pages.roleId.length > 1) {
      	pages.clientId = pages.roleId[0];
        pages.roleId = pages.roleId[pages.roleId.length - 1];
      } else {
      	pages.clientId = pages.roleId[0];
        pages.roleId = null;
      }
      fetchUserInfoList(pages).then(res => {
        if(res.err_CODE === 0){
          this.total = res.data.total;
          this.page.pageSize = res.data.pageSize;
          this.page.pageIndex = res.data.pageNum;
          res.data.list.forEach(item => {
            for (const key in item) {
              if (key == "role") {
                if (!item[key]) {
                  item[key] = "暂未分配角色";
                }
              } else {
                if (!item[key]) {
                  item[key] = "--";
                }
              }
            }
          });
          this.tab_data = res.data.list;
        }else{
          this.$message.error(res.err_MESSAGE);
        }

      });
    },
    plusUser() {
      /**
       * @description “新增”弹出框显示方法
       */
      this.addDialogVisible = true;
      this.getAllApp();
    },
    detailsHandle(val) {
      /**
       * @description 查看详情按钮
       */
      this.detailHtml = val;
      this.detailDialogVisible = true;
    },
    addCancelHandel() {
      /**
       * @description 新增弹框取消
       */
      this.$confirm("是否放弃编辑?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        showClose: false
      })
        .then(() => {
          this.addDialogVisible = false;
          this.addForm.plusRoleData.forEach(item => {
            item.transfertTips=false
          });
          this.addForm = {};
          setTimeout(() => {
            this.$refs.addForm.resetFields();
            this.addForm = {
              plusRoleData: JSON.parse(JSON.stringify(this.roleData))
            };
          }, 600);
        })
        .catch(() => {});
    },
    addDifineHandel() {
      /**
       * @description 新增用户
       * @param {object} param 传给后台的参数对象
       * @param {string} userName 用户名
       * @param {string} fullName 姓名
       * @param {number} telephone 电话号码
       * @param {string} email 邮箱
       * @param {string} passWord 密码
       * @param {Array} roles 选中的角色列表数据
       */
      let isValidate = true;//是否有角色选中
      this.addForm.plusRoleData.forEach(item => {
        if (item.checked == true && item.checkedValue.length == 0) {
          // 应用被选中，应用下包含的角色并未选中时，穿梭框提示语显示，isValidate为false
          item.transfertTips=true
          isValidate = false;
        }
      });

      this.$refs["addForm"].validate(valid => {
        if (valid && isValidate) {
          this.onlyButton = false;
          let obj = {};
          obj.userName = this.addForm.userName;
          obj.fullName = this.addForm.fullName;
          obj.telephone = this.addForm.telephone;
          obj.email = this.addForm.email;
          obj.passWord = this.addForm.pwd;
          obj.roles = [];

          // 根据选中的角色列表数据，拼成规定的传参结构:roles;[{"clientId":0,"roleIds":[]}]
          this.addForm.plusRoleData.forEach(item => {
            let roleObj = {};
            roleObj.clientId = item.clientId;
            roleObj.roleIds = item.checkedValue;
            if (item.checked == true) {
              obj.roles.push(roleObj);
            }
          });
          addUser(obj).then(res => {
            if (res.err_CODE == 0) {
              this.$message.success(res.err_MESSAGE);
              this.addDialogVisible = false;
              this.addForm.plusRoleData.forEach(item => {
                item.transfertTips=false
              });
              this.page.pageIndex = 1;
              this.userListAll();
              setTimeout(() => {
                this.$refs.addForm.resetFields();
                this.onlyButton = false;
              }, 600);
            } else {
              this.$message.error(res.err_MESSAGE);
              this.onlyButton = false;
            }
          });
        } else {
          return false;
        }
      });
    },
    editDialogHandle(val){
      /**
       * @description 点击“编辑”用户按钮，
       */
      this.editDialogVisible = true;
      this.editForm = JSON.parse(JSON.stringify(val));
      // 遍历所有应用下的角色与选中的应用角色匹配，并拼接成需要的数据结构，为编辑用户时，角色列表穿梭框进行数据渲染
      this.roleEditData.forEach((item, index) => {
        item.checkedValue = [];
        this.editForm.selectedRole.forEach((val, ind) => {
          if (item.clientId == val.clientId) {
              item.checked = true;
              item.checkedValue = val.roleIds;
              return item;
          }
        });
      });
      // setTimeout(() => {
      //   this.$set(this.$refs["editUser"], "moveY", 0);
      //   this.$set(this.$refs["editUser"]["wrap"], "scrollTop", 0);
      // });
    },
    editCancelHandel() {
      /**
       * @description 取消编辑用户信息
       */
      this.$confirm("是否放弃编辑?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        showClose: false
      })
        .then(() => {
          this.editDialogVisible = false;
          // 角色数据还原
          this.roleEditData.forEach(item => {
            item.transfertTips=false;
            item.checked = false;
            item.checkedValue = [];
          });
          this.$refs['editForm'].resetFields();
        })
        .catch(() => {});
    },
    editDifineHandel() {
      /**
       * @description 编辑用户信息
       * @param {String} email
       * @param {Number} flag  0 代表编辑用户，1代表改变用户状态
       * @param {String} fullName 姓名
       * @param {Number} id 用户ID
       * @param {Array} Roles:[{clientId:0,roleIds:[]}]
       * @param {Number} Roles[0].clientId 应用ID
       * @param {Array} Roles[0].roleIds 选中的角色ID
       * @param {Number} status 用户状态： 1-启用; 2-禁用
       * @param {String} telephone 手机号
       * @param {String} userName 用户名
       */
      let validTips = true;
      this.roleEditData.forEach(item => {
        if (item.checked == true && item.checkedValue.length == 0) {
          item.transfertTips=true
          validTips = false;
        }
      });
      this.$refs["editForm"].validate(valid => {
        if (valid && validTips) {
          let obj = {};
          obj.id = this.editForm.id;
          obj.userName = this.editForm.userName;
          obj.fullName = this.editForm.fullName;
          obj.telephone = this.editForm.telephone;
          obj.email = this.editForm.email;
          obj.flag = 0;//0 代表编辑用户，1代表改变用户状态
          obj.roles = [];
          this.roleEditData.forEach(item => {
            let roleObj = {};
            roleObj.clientId = item.clientId;
            roleObj.roleIds = item.checkedValue;
            if (roleObj.roleIds.length > 0 && item.checked == true) {
              obj.roles.push(roleObj);
            }
          });

          updateUser(obj).then(res => {
            if (res.err_CODE == 0) {
              this.$message.success(res.err_MESSAGE);
              this.roleEditData.forEach(item => {
                item.transfertTips=false;
                item.checked = false;
                item.checkedValue = [];
              });
              this.editDialogVisible = false;
              this.userListAll();
              setTimeout(() => {
                this.$refs.editForm.resetFields();
              }, 600);
            } else {
              this.$message.error(res.err_MESSAGE);
            }
          });
        }
      });
    },
    resetHandle(val) {
      /**
       * @description 点击重置按钮，获取数据且重置提示框显示
       */
      this.phoneReset = val;
      this.resetDialogVisible = true;
    },
    resetCancelHandel() {
      /**
       * @description 重置密码弹出框取消
       */
      this.resetDialogVisible = false;
    },
    resetDifineHandel() {
      /**
       * @description 重置密码
       * @param {String} userId 用户ID
       * @param {String} oldPassword 老密码
       * @param {String} newPassword 新密码
       * @param {String} appId 应用ID
       * @param {Number} flag
       */
      let self = this;
      let resetInfo = JSON.parse(JSON.stringify(this.phoneReset));
      let param = {
        userId:resetInfo.id,
        oldPassword:null,
        newPassword:null,
        appId:appId,
        flag:self.modifyMode
      };
      resetPassword(param).then(res=>{
        if(res.err_CODE == 0){
          self.$message.success(res.err_MESSAGE);
        }else{
          this.$message.error(res.err_MESSAGE);
        }
      }).catch(() =>{})
      this.resetDialogVisible = false;
    },
    handleSizeChange(val) {
      /**
       * @description 分页方法
       */
      this.page.pageSize = val;
      this.userListAll();
    },
    handleCurrentChange(val) {
      /**
       * @description 当前页面查询
       */
      this.page.pageIndex = val;
      this.userListAll();
    },
    forbidenStartHandel(val) {
      /**
       * @description 表格中“禁用启用”按钮点击获取对应的用户数据及弹出框展示
       */
      this.forStartText = val;
      this.forStartDialogVisible = true;
    },
    forStartCancelHandel() {
      /**
       * @description 用户禁用弹出框 “取消”
       */
      this.forStartDialogVisible = false;
    },
    forStartDifineHandel() {
      /**
       * @description 用户状态修改：禁用or启用
       * @param {String} email
       * @param {Number} flag  0 代表编辑用户，1代表改变用户状态
       * @param {String} fullName 姓名
       * @param {Number} id 用户ID
       * @param {Array} Roles:[{clientId:0,roleIds:[]}]
       * @param {Number} Roles[0].clientId 应用ID
       * @param {Array} Roles[0].roleIds 选中的角色ID
       * @param {Number} status 用户状态： 1-启用; 2-禁用
       * @param {String} telephone 手机号
       * @param {String} userName 用户名
       */
      this.forStartDialogVisible = false;
      let obj = {};
      obj.id = this.forStartText.id;
      obj.flag =  1;
      obj.email = this.forStartText.email;
      obj.fullName = this.forStartText.fullName;
      obj.roles = this.forStartText.roles;
      obj.telephone = this.forStartText.telephone;
      obj.userName = this.forStartText.userName;
      obj.status = null;
      console.log(obj);
      //判断当前用户状态: 1-启用 2-禁用
      if (this.forStartText.status.code === 1) {
        obj.status = 2;
      } else if(this.forStartText.status.code === 2){
        obj.status = 1;
      }
      updateUser(obj).then(res => {
        if (res.err_CODE == 0) {
          this.$message.success(res.err_MESSAGE);
          this.userListAll();
        } else {
          this.$message.error(res.err_MESSAGE);
        }
      });

    },
    detailCancelHandel() {
      /**
       * @description 用户详情弹框取消
       */
      this.detailDialogVisible = false;
    },
    getRolesAndStatus(){
      /**
       * @description 获取角色列表和用户状态列表
       */
      roleAndUserStatus().then(res=>{
        if(res.err_CODE === 0){
          //用户状态列表下拉框数据渲染
          this.userStatusArr = [
            { code : null, name : '全部' },
            ...res.data.statusList
          ];
          //角色列表下拉框数据渲染
          this.applicationArr = [
            { value : null, label : '全部' },
            ...res.data.roleList
          ];
          this.applicationArr.map(item=>{
            item.name = item.label;

            item.label = item.label.length > 6 ? item.label.substr(0,5)+"..." : item.label;
            if(item.children){
              item.children.map(jtem=>{
                jtem.name = jtem.label;
                jtem.label = jtem.label.length > 6 ?  jtem.label.substr(0,5)+"..." : jtem.label;
              })
            }
          })
        }else{
          this.$message.error(res.err_MESSAGE);
        }
      })
    }
  }
};
</script>
<style lang="scss">
  @import "../../styles/user-manage/user-management.scss";
</style>


