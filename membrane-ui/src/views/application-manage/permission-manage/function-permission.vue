<template>
  <div class="common-root" id="function-permission" :style="{minHeight: minHeight + 'px'}">
    <el-container>
      <el-aside width="200px">
        <el-tree
          :data="permissionList"
          :props="defaultProps"
          :expand-on-click-node="false"
          node-key="id"
          default-expand-all
          :default-expanded-keys="checkedNode"
          :default-checked-keys ="checkedNode"
          accordion
          :render-content="renderHandle"
          highlight-current
          @node-click="handleNodeClick">
        </el-tree>
      </el-aside>
      <el-main class="content">
        <div class="head-content">
          <el-button type="primary" :class="noClick ? 'disBtn' : 'delsBtn'" size="mini" :disabled="noClick" @click="delHandle">批量删除</el-button>
          <el-button v-if="mdShow" type="primary" size="mini" @click="addModuleVisible=true">新增模块</el-button>
          <el-button v-else type="primary" size="mini" @click="addFunctionVisible=true">新增功能</el-button>
        </div>
        <div class="table-container">
          <!-- 应用下所有模块数据表格 -->
          <template>
              <el-table
                v-if="mdShow"
                ref="moduleTb"
                :data="moduleData"
                @selection-change="handleSelectionChange">
                <el-table-column
                  type="selection"
                  width="45">
                </el-table-column>
                <el-table-column label="模块名称">
                  <template slot-scope="scope" v-if="scope.row.moduleName">
                    <el-tooltip :content="scope.row.moduleName" placement="top" effect="dark">
                      <span>{{scope.row.moduleName}}</span>
                    </el-tooltip>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="200">
                  <template slot-scope="scope">
                    <i class="el-icon-edit" @click="editDialogHandle('editModuleForm',scope.row)"></i>
                    <i v-show="scope.row.functionList.length === 0" class="el-icon-delete" @click="del(scope.row)"></i>
                  </template>
                </el-table-column>
              </el-table>
          <!-- 模块下所有功能数据表格 -->
              <el-table
                v-else
                ref="funTb"
                :data="functionData"
                @selection-change="handleSelectionChange">
                <el-table-column
                  type="selection"
                  width="45">
                </el-table-column>
                <el-table-column label="功能名称">
                  <template slot-scope="scope" v-if="scope.row.functionName">
                    <el-tooltip :content="scope.row.functionName" placement="top" effect="dark">
                      <span>{{scope.row.functionName.length > 10 ? scope.row.functionName.substr(0,9)+'...' : scope.row.functionName}}</span>
                    </el-tooltip>
                  </template>
                </el-table-column>
                <el-table-column label="功能类型" prop="apiType">
                  <template slot-scope="scope">
                    {{scope.row.apiType === 0 ? '编辑类' : '查询类'}}
                  </template>
                </el-table-column>
                <el-table-column label="URL" width="500">
                  <template slot-scope="scope">
                  <el-tooltip :content="scope.row.apiUrlList" placement="top" effect="dark">
                     <span>{{scope.row.url}}</span>
                  </el-tooltip>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="300">
                  <template slot-scope="scope">
                    <i class="el-icon-edit" @click="editDialogHandle('editFunForm',scope.row)"></i>
                    <i class="el-icon-delete" @click="del(scope.row)"></i>
                  </template>
                </el-table-column>
              </el-table>
          </template>
        </div>
      </el-main>
    </el-container>
    <!-- 新增模块 -->
    <el-dialog
      title="新增模块"
      :visible.sync="addModuleVisible"
      width="404px"
      :close-on-click-modal="false"
      top="36vh"
      height="178px"
      class="appDialog add-module"
      @close="cancelHandle('moduleForm')">
      <div class="app-content">
        <el-form :model="moduleForm" :rules="moduleRules" ref="moduleForm" label-width="81px" >
          <el-form-item label="模块名称" prop="moduleName" size="mini">
            <el-input v-model.trim="moduleForm.moduleName"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer footer-fl" style="text-align:right">
        <el-button @click="cancelHandle('moduleForm')">取消</el-button>
        <el-button type="primary" :disabled="moduleBtn" @click="addAppModule('moduleForm')">确定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑模块 -->
    <el-dialog
      title="编辑模块"
      :visible.sync="editModuleVisible"
      :close-on-click-modal="false"
      width="404px"
      top="36vh"
      height="178px"
      @close="cancelHandle('editModuleForm')"
      class="appDialog add-module"
      >
      <div class="app-content">
        <el-form :model="editModuleForm" :rules="moduleRules" ref="editModuleForm" label-width="81px" >
          <el-form-item label="模块名称" prop="moduleName" size="mini">
            <el-input v-model.trim="editModuleForm.moduleName"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer footer-fl" style="text-align:right">
        <el-button @click="cancelHandle('editModuleForm')">取消</el-button>
        <el-button type="primary" @click="updateModule('editModuleForm')">确定</el-button>
      </span>
    </el-dialog>
    <!-- 新增功能 -->
    <el-dialog
      title="新增功能"
      :visible.sync="addFunctionVisible"
      :close-on-click-modal="false"
      width="604px"
      top="36vh"
      @close="cancelHandle('functionForm')"
      class="appDialog add-module">
      <div class="app-content">
        <el-form :model="functionForm" :rules="functionRules" ref="functionForm" label-width="81px" >
          <el-form-item label="功能名称" prop="functionName" size="mini">
            <el-input v-model.trim="functionForm.functionName" placeholder="请输入功能名称"></el-input>
          </el-form-item>
          <el-form-item label="功能类型" size="mini" class="mg10" label-width="71px">
            <el-select v-model="functionForm.apiType">
              <el-option v-for="(item,index) in functionType" :key="index" :label="item.fName" :value="item.fId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="URL" prop="apiUrlList" size="mini">
            <el-input v-model="functionForm.apiUrlList" placeholder="请输入API URL"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer footer-fl" style="text-align:right">
        <el-button @click="cancelHandle('functionForm')">取消</el-button>
        <el-button type="primary" :disabled="funBtn" @click="addAppModule('functionForm')">确定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑功能 -->
    <el-dialog
      title="编辑功能"
      :visible.sync="editFunctionVisible"
      :close-on-click-modal="false"
      width="604px"
      top="36vh"
      @close="cancelHandle('editFunForm')"
      class="appDialog add-module">
      <div class="app-content">
        <el-form :model="editFunForm" :rules="functionRules" ref="editFunForm" label-width="81px" >
          <el-form-item label="功能名称" prop="functionName" size="mini">
            <el-input v-model.trim="editFunForm.functionName"></el-input>
          </el-form-item>
          <el-form-item label="功能类型" size="mini">
            <el-select v-model="editFunForm.apiType">
              <el-option v-for="(item,index) in functionType" :key="index" :label="item.fName" :value="item.fId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="URL" prop="apiUrlList" size="mini">
            <el-input v-model="editFunForm.apiUrlList"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer footer-fl" style="text-align:right">
        <el-button @click="cancelHandle('editFunForm')">取消</el-button>
        <el-button type="primary" @click="updateFunc('editFunForm')">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>


<script>
import {
  getModuleById,
  addAppModule,
  editAppModule,
  editAppFunction,
  delAppModule,
  delAppFunction
} from "../../../api/api";
import constant from "../../../labs/constant"
export default {
  data(){
    return{
      minHeight:null,
      data: [{
          label: '一级 1',
          children: [{
            label: '二级 1-1',
            children: [{
              label: '三级 1-1-1'
            }]
          }]
      }],
      page: {
        pageIndex:1,
        pageSize: 10,
        total:0
      },
      appDetails:null,
      permissionVisible:false,
      permissionDetailVisible:false,
      mdShow:true,
      AppDetailDialogVisible:false,
      addModuleVisible:false,
      addFunctionVisible:false,
      editModuleVisible:false,
      moduleBtn:false,
      funBtn:false,
      editFunctionVisible:false,
      functionType:[],
      defaultProps:{
        children:'children',
        label:'label',
      },
      publicParam:{
        clientId:null,
        moduleId:null,
      },//公共参数
      checkedNode:[1],//默认权限列表展开的节点
      permissionList:[],
      moduleData:[],
      functionData:[],
      checkModules:[],
      checkFunctions:[],
      moduleForm:{
        moduleName:''
      },//新增模块Form
      editModuleForm:{
        moduleName:''
      },//编辑模块Form
      moduleRules:{
        moduleName:[
          { required:true, message:'请输入模块名称', trigger:'blur' },
          { min:2, max:20, message:'长度在2到20个字符！', trigger:'blur' }
        ]
      },
      appPermission:null,
      delModuleParam:[],
      delFunParam:[],
      functionForm:{
        functionName:null,
        apiUrlList:null,
        moduleId:null,
        clientId:null,
        moduleName:null,
        apiType:1
      },//新增功能Form
      noClick:true,
      editFunForm:{
        functionId:null,
        functionName:null,
        apiUrlList:null,
        moduleId:null,
        apiType:0
      },//编辑功能Form
      functionRules:{
        functionName:[
          { required:true, message:'请输入功能名称', trigger:'blur' },
          { min:2, max:20, message:'长度在2到20个字符！', trigger:'blur' }
        ],
        apiUrlList:[
          { required:true, message:'请输入url，超过1个时，请以英文逗号(,)隔开',trigger:'blur'},
          { min: 3, max: 100, message: '长度在 3 到 100 个字符', trigger: 'blur' },
          { pattern:/\//, message:'请输入正确的url', trigger:'blur'}
        ]
      },
      permissionDetail:[],//权限录入详情
      renderHandle(h, { node, data, store }){
        /**
         * @description 树节点的内容区的渲染
         */
        return(
          <span class="custom-tree-node">
            <span title={data.name}>{node.label}</span>
          </span>
        )
      },
    }
  },
  watch:{
    appDetail:{
      deep:true,
      handler(param){
        this.appDetails = JSON.parse(JSON.stringify(param));
      }
    },
    checkModules:{
      deep:true,
      handler(val){
        val.length > 0 ? this.noClick = false : this.noClick = true;
      }
    },
    checkFunctions:{
      deep:true,
      handler(val){
        val.length > 0 ? this.noClick = false : this.noClick = true;
      }
    }
  },
  methods:{

    handleNodeClick(data,node){
      /**
       * @description 获取当前选中的节点数据,‘新增功能’ 与 ‘新增模块’按钮切换
       */
      this.publicParam = {clientId:null,moduleId:null}
      if(data.level == 1){// 点击一级
        this.mdShow = true;
        this.moduleData = this.appPermission.children;

      }else if(data.level == 2){//点击二级
        this.publicParam.moduleId = data.moduleId;
        this.mdShow = false;
        this.functionForm.moduleId = data.moduleId;
        let moduleInfo = this.appPermission.children.filter((item) => item.moduleId == data.moduleId);
        this.functionData = moduleInfo[0].children;

      }else if(data.level == 3){//点击三点
        this.publicParam.moduleId = node.parent.data.moduleId;
        this.mdShow = false;
        let moduleInfo = this.appPermission.children.filter((item) => item.moduleId == node.parent.data.moduleId);
        this.functionData = moduleInfo[0].children;
      }
    },
    handleSelectionChange(val){
      /**
       * @description 获取批量选中的数据
       */
      this.mdShow ? this.checkModules = val : this.checkFunctions = val;
    },
    addAppModule(formName){
      /**
       * @description 新增模块
       * @param {Number} clientId 应用ID
       * @param {Number} moduleId 模块ID
       * @param {String} moduleName 模块名称
       * @param {Array} functionList 功能列表：新增模块时：functionList:null,新增功能时：functionList[{functionName:'',apiType:'',apiUrlList:''}]
       * @param {String} functionName 功能名称
       * @param {Number} apiType 功能类型：0-编辑类，1-查询类
       * @param {Array} apiUrlList 功能对应的API
       */
      let self = this;
      // formName == "moduleBtn" ? self.moduleBtn = true : self.funBtn = true;
      self.$refs[formName].validate(valid=>{
        if(valid){
          // 防止按钮多次点击
          formName == "moduleForm" ? self.moduleBtn = true : self.funBtn = true;
          let param ={
            clientId: self.appDetails.clientId,
            moduleId:null,
            moduleName:self.moduleForm.moduleName,
            functionList:[]
          };
          // 根据formName 判断是新增模块还是新增功能,不同的新增传递不同的参数值
          if(formName === "moduleForm"){
            param.moduleId = null;
            param.functionList = [];
          }else{
            param.moduleId = self.publicParam.moduleId;
            param.moduleName = self.functionForm.moduleName;
            param.functionList.push(
              {
                apiType : self.functionForm.apiType,
                functionName: self.functionForm.functionName,
                apiUrlList: self.functionForm.apiUrlList.split(',')
              }
            )
          }
          addAppModule(param).then(res=>{
            if(res.err_CODE === 0){
              self.addModuleVisible = false;
              self.addFunctionVisible = false;
              self.$refs[formName].resetFields();
              self.$message.success(res.err_MESSAGE);
              setTimeout(()=>{
                formName == "moduleForm" ? self.moduleBtn = false : self.funBtn = false;
              },1000)
              self.getPermission(param.clientId,null);
              self.getFunList(param.clientId,param.moduleId);
            }else{
              formName == "moduleForm" ? self.moduleBtn = false : self.funBtn = false;
              self.$message.error(res.err_MESSAGE);
            }
          })
        }else{
          return false;
        }
      })
    },
    disableHandle(formName){
      /**
       * @description 防止鼠标多次点击
       */
      if(formName == "moduleName"){
        setTimeout(()=>{
          this.moduleBtn = false;
        },200)
      }else{
        setTimeout(()=>{
          this.funBtn = false;
        },200)
      }
    },
    editDialogHandle(formName,data){
      /**
       * @description 模块、功能的编辑交互及Form表单数据获取
       */
      if(formName == 'editModuleForm'){
        this.editModuleVisible = true;
        this.editModuleForm = JSON.parse(JSON.stringify(data));
      }else{
        this.editFunctionVisible = true;
        //转换成form表单中需要的数据类型（数组转换成字符串）
        this.editFunForm = JSON.parse(JSON.stringify(data));
      }

    },
    updateModule(formName){
      /**
       * @description 编辑模块
       * @param {Number} clientId 应用ID
       * @param {Number} moduleId 模块ID
       * @param {String} moduleName 模块名称
       */
      let param = {
        clientId:this.editModuleForm.clientId,
        moduleId:this.editModuleForm.moduleId,
        moduleName:this.editModuleForm.moduleName
      };
      this.$refs[formName].validate(valid=>{
        if(valid){
          editAppModule(param).then(res=>{
            if(res.err_CODE === 0){
              this.$message.success(res.err_MESSAGE);
              this.editModuleVisible = false;
              this.$refs[formName].resetFields();
              this.getPermission(this.editModuleForm.clientId,null);
            }else{
              this.$message.error(res.err_MESSAGE);
            }
          })
        }else{
          return false;
        }
      })

    },
    updateFunc(formName){
      /**
       * @description 编辑功能
       * @param {Number} clientId 应用ID
       * @param {Number} moduleId 模块ID
       * @param {Number} functionId 功能ID
       * @param {String} functionName 功能名称
       * @param {Number} apiType 功能类型 0-新增 1-编辑
       * @param {Array}  apiUrlList url
       */
      let param = {
        clientId: this.editFunForm.clientId,
        moduleId: this.editFunForm.moduleId,
        functionId: this.editFunForm.functionId,
        functionName: this.editFunForm.functionName,
        apiType: this.editFunForm.apiType,
        apiUrlList: this.editFunForm.apiUrlList.split(',')
      };
      this.$refs[formName].validate((valid) =>{
        if(valid){
          editAppFunction(param).then(res=>{
            if(res.err_CODE === 0){
              this.$message.success(res.err_MESSAGE);
              this.editFunctionVisible = false;
              this.$refs[formName].resetFields();
              this.getPermission(param.clientId,null);
              this.getFunList(this.editFunForm.clientId,this.editFunForm.moduleId);
            }else{
              this.$message.error(res.err_MESSAGE);
            }
          })
        }else{
          return false;
        }
      })
    },
    delHandle(){
      /**
       * @description 批量删除功能or模块
       */
      if(this.mdShow){
        /**
         * @description 删除模块
         * @param {Number} clientId 应用ID
         * @param {Array} ids 要删除模块ID
         */

        let param = {
          clientId:null,
          ids:[]
        };

        this.checkModules.forEach(item=>{
          param.clientId = item.clientId;
          param.ids.push(item.moduleId);
        })
        if(param.ids.length > 0){
          delAppModule(JSON.stringify(param)).then(res=>{
            if(res.err_CODE === 0){
              this.$message.success(res.err_MESSAGE);
              this.noClick = true;
              this.getPermission(this.checkModules[0].clientId,null);
            }else{
              this.$message.error(res.err_MESSAGE);
            }
          })
        }

      }else{
        /**
         * @description 删除功能
         * @param {Number} clientId 应用ID
         * @param {Array} ids 要删除模块ID
         */
        let param = {
          clientId:'',
          ids:[]
        };
        this.checkFunctions.forEach(item=>{
          param.clientId = item.clientId;
          param.ids.push(item.functionId);
        })
        if(param.ids.length > 0){
          delAppFunction(param).then(res=>{
            if(res.err_CODE === 0){
              this.$message.success(res.err_MESSAGE);
              this.noClick = true;
              this.getPermission(param.clientId,null);
              this.getFunList(param.clientId,this.checkFunctions[0].moduleId);
            }else{
              this.$message.error(res.err_MESSAGE);
            }
          })
        }
      }
    },
    del(data){
      if(this.mdShow){
        this.checkModules.push(data);
      }else{
        this.checkFunctions.push(data);
      }
      this.delHandle();
    },
    permissionHandle(){
      /**
       * @description 编辑权限弹出框数据渲染
       */
      // this.permissionVisible = true;
      // let appDetail = JSON.parse(JSON.stringify(this.appDetails));
      // this.getPermission(appDetail.clientId,null);
      // this.$router.push('');
      // this.$router.push('/permissionDetail');
      // console.log(this.$route.push('/permissionDetail'));
    },
    getPermission(clientId,moduleId){
      /**
       * @description 获取应用下模块列表
       * @param {Number} clientId 应用ID
       * @param {Number} moduleId 模块ID：查询模块时，该字段对应的值为null
       */
      this.permissionList = [];
      this.moduleData = [];
      this.functionData = [];

      let param =  {
        clientId:clientId,
        moduleId:moduleId
      };

      getModuleById(param).then(res=>{
        if(res.err_CODE === 0){
          console.log("模块列表");
          console.log(res);
            res.data.map(item=>{
                // 拼接成前端需要的数据结构
                item.id = item.moduleId;
                item.label = item.moduleName.length > 15 ? (item.moduleName).substring(0,14)+"..." :item.moduleName;
                item.name = item.moduleName;
                item.level = 2;

                item.functionList.map(jtem =>{
                  jtem.id = jtem.functionId;
                  jtem.label = jtem.functionName.length > 15 ?  (jtem.functionName).substring(0,14)+"..." : jtem.functionName;
                  jtem.level = 3;
                  jtem.name = jtem.functionName;
                  jtem.moduleId = item.moduleId;
                  // 判断url list 长度，并拼接字符串，字符串超过15，...代替
                  if(jtem.apiUrlList.length >0){
                    jtem.url = jtem.apiUrlList.join("");
                    if(jtem.url.length > 30){
                      jtem.url = (jtem.url).substring(0,29)+"...";
                    }else{
                      jtem.url = jtem.url;
                    }
                  }else{
                    jtem.url = "--";
                  }
                  jtem.apiUrlList = jtem.apiUrlList.length > 0 ? jtem.apiUrlList.join(',') : '--';
                })
                item.children = item.functionList;
                return item;
            })
            // 根节点数据拼接
            let root = {
                id:this.appDetails.clientId,
                label:this.appDetails.clientName.length > 18 ? this.appDetails.clientName.substring(0,17)+"..." : this.appDetails.clientName,
                name:this.appDetails.clientName,
                level:1,
                children:res.data
            }
            this.appPermission = JSON.parse(JSON.stringify(root));
            this.permissionList.push(this.appPermission);
            this.moduleData = this.appPermission.children;
            this.permissionDetail = this.permissionList;
        }else{
          this.$message.error(res.err_MESSAGE);
        }
      })
    },
    getFunList(clientId,moduleId){
      /**
       * @description 获取应用下模块列表
       * @param {Number} clientId 应用ID
       * @param {Number} moduleId 模块ID
       */
      this.functionData = [];

      let param =  {
        clientId:clientId,
        moduleId:moduleId
      }
      getModuleById(param).then(res=>{
        if(res.err_CODE === 0){
          this.functionData = res.data[0].functionList;
          this.functionData.map(item =>{
            item.moduleId = moduleId;
            if(item.apiUrlList.length > 0){
              let url = item.apiUrlList.join(",");
              url.length > 20 ? url.substring(0,19)+"..." : url;
              item.url = url;
            }else{
              item.url = "--";
            }
            // item.url = item.apiUrlList.length > 0 ? (item.apiUrlList.join(',')).substring(0,8)+"..." : item.url = '--';
            item.apiUrlList = item.apiUrlList.length > 0 ? item.apiUrlList.join(',') : '--';
          })
        }else{
          this.$message.error(res.err_MESSAGE);
        }
      })
    },
    cancelHandle(formName){
      switch(formName){
        case "editFunForm" :
           this.editFunctionVisible = false;
           break;
        case "moduleForm":
            this.addModuleVisible = false;
            break;
        case "editModuleForm":
            this.editModuleVisible = false;
            break;
        case "functionForm":
            this.addFunctionVisible = false;
            break;
      }
      this.$refs[formName].resetFields();
    },
    handleSizeChange(val) {
      /**
       * @description 分页方法
       */
      this.page.pageSize = val;
      // this.getPermission();
    },
    handleCurrentChange(val) {
      /**
       * @description 当前页面查询
       */
      this.page.pageIndex = val;
     // this.getPermission();
    },
  },
  mounted(){
    this.minHeight = window.innerHeight - 95;
    // 新增功能时，功能类型下拉菜单数据渲染
    this.functionType = constant.functionType;
    this.getPermission(this.appDetails.clientId,null);
  },
  created(){
    this.appDetails = JSON.parse(JSON.stringify(this.$route.params));
  }
}
</script>

<style lang="scss">
  @import "../../../styles/application-manage/application-component.scss";
</style>
