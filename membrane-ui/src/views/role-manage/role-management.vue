<template>
  <div id="role-manage" :style="{minHeight: minHeight + 'px'}">
    <!-- 条件筛选查询 -->
    	<div class="select-container">
    			<el-col>
    					选择所属应用
			        <el-select class="clacifi" v-model="page.clientId" size="mini" @change="selectedHandle()">
			          <el-option
			            v-for="(pro, index) in appPlatformArr"
			            :key="index"
			            :label="pro.clientName"
			            :value="pro.clientId"
			          ></el-option>
			        </el-select>
    			</el-col>
    			<el-col :span="3" style="text-align:right">
		        <el-button
			          type="primary"
			          style="border:none"
			          icon="el-icon-plus"
			          @click="addRoleBtn"
			        >新建角色</el-button>
		      </el-col>
    	</div>
      <!-- 角色列表 -->
    	<div class="table-container">
    				<template>
        			<el-table :data="tab_data">
        					<el-table-column min-width="205" label="角色名称">
                    <template slot-scope="scope" v-if="scope.row.roleName">
                      <el-tooltip :content="scope.row.roleName" placement="top" effect="dark">
                        <span>{{scope.row.roleName.length > 20 ? scope.row.roleName.substr(0,19)+'...' : scope.row.roleName}}</span>
                      </el-tooltip>
                    </template>
                  </el-table-column>
        					<el-table-column prop="clinetName" min-width="140" label="所属应用"></el-table-column>
        					<el-table-column prop="dataAuthority1" min-width="341" label="数据权限"></el-table-column>
        					<el-table-column prop="" min-width="270" label="功能权限">
                    <template slot-scope="scope">
                    <el-tooltip :content="scope.row.functionAuthority" placement="top" effect="dark">
                      <span>{{scope.row.functionAuthority1}}</span>
                    </el-tooltip>
                    </template>
                  </el-table-column>
        					<el-table-column min-width="200" label="操作">
        							<template slot-scope="scope">
        									<el-tooltip class="item" effect="dark" content="详情" placement="top">
	        									<em class="iconfont icon-xiangqing" @click="detailClick(scope.row.roleId)"></em>
	        								</el-tooltip>
	        								<el-tooltip class="item" effect="dark" content="编辑" placement="top">
	        									<em class="el-icon-edit" @click="editClick(scope.row)"></em>
	        								</el-tooltip>
	        								<el-tooltip class="item" effect="dark" content="复制" placement="top">
	        									<em class="iconfont icon-fuzhi" @click="copyClick(scope.row.roleId,scope.row.roleName)"></em>
	        								</el-tooltip>
	        								<el-tooltip class="item" effect="dark" content="删除" placement="top">
	        									<em class="el-icon-delete" @click="deleteClick(scope.row)"></em>
	        								</el-tooltip>
        							</template>
        					</el-table-column>
        			</el-table>
      			</template>
      			<div class="paging fr" style="margin-top:10px;">
			        <el-pagination
				          background
				          @size-change="handleSizeChange"
				          @current-change="handleCurrentChange"
				          :current-page.sync="page.pageIndex"
				          :page-sizes="[10, 20, 50, 100]"
				          :page-size="page.pageSize"
				          layout="total, sizes, prev, pager, next, jumper"
				          :total="total"
				          v-if="total"
				        ></el-pagination>
		      </div>
    	</div>
    	<!-- 新增角色 -->
      <el-dialog class="plusProModel" title="新增角色" :visible.sync="addDialog" width="746px" :before-close="resetForm" :top="addForm | newDialogTop">
      		<el-scrollbar :style="addForm | scrollHeight">
      		<el-form :model="addForm" ref="addForm" :rules="addTmpRule" @submit.native.prevent>
            <el-form-item label="所属应用" prop="appPlatform" label-width="80px" width="200">
              <el-select class="clacifi" v-model="addForm.appPlatform" size="mini" placeholder="请选择所属应用" @change="appPlatformSelect(addForm.appPlatform)">
                <el-option
                  v-for="(pro, index) in addForm.appPlatformArr"
                  :key="index"
                  :label="pro.clientName"
                  :value="pro.clientId"
                  :disabled="pro.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="角色名称" prop="roleName" label-width="80px">
	            <el-input v-model.trim="addForm.roleName" placeholder="请输入角色名称" size="mini"></el-input>
	          </el-form-item>
            <!-- 数据权限渲染 -->
	          <el-form-item v-if="addForm.dateAll.length > 0" label="数据权限" prop="dateLimit" label-width="80px">
	            <el-radio-group v-model="addForm.dateLimit" size="mini">
	              <el-radio :label=true>全部权限</el-radio>
	              <el-radio :label=false>选择部分权限</el-radio>
	            </el-radio-group>
	            <el-transfer v-if="!addForm.dateLimit" v-model="addForm.selectDate" :data="addForm.dateAll" :titles="['未添加数据权限', '已添加数据权限']"></el-transfer>
	          	<div class="emptyDate" v-if="!addForm.dateLimit&&addForm.dataLimitType">请选择数据权限</div>
	          </el-form-item>
            <!-- 功能权限渲染 -->
	          <el-form-item v-if="addForm.appPlatform&&addForm.treeData&&addForm.treeData.length" label="功能权限" prop="funcLimit" label-width="80px">
	            <el-radio-group v-model="addForm.funcLimit" size="mini">
	              <el-radio :label='true'>全部权限</el-radio>
	              <el-radio :label='false'>选择部分权限</el-radio>
	            </el-radio-group>
	            <!-- <div style="float:right;font-size:12px;">* 区分数据权限</div> -->
              <!-- 功能权限Tree -->
	            <el-tree
	            	v-if="!addForm.funcLimit"
	            	:data="addForm.treeData"
				        show-checkbox
				        node-key="id"
				        ref="tree"
				        :props = "addForm.defaultProps"
				        :default-expand-all="true"
  							:default-checked-keys="addForm.resourceCheckedKey"
  							:render-content="renderContent">
							</el-tree>
							<div v-if="!addForm.funcLimit&&addForm.funcLimitType" class="emptyDate">请选择功能权限</div>
	          </el-form-item>
          </el-form>
	        </el-scrollbar>
	        <span slot="footer" class="dialog-footer">
	          <el-button @click="resetForm('addForm')">取消</el-button>
            <el-button type="primary" @click="formPost('addForm')">确定</el-button>
	        </span>
      </el-dialog>
      <!--编辑角色-->
      <div v-if="editDialog">
      		 <el-dialog class="plusProModel" title="编辑角色" :visible.sync="editDialog" width="746px" :before-close="cancelForm" :top="editForm |editDialogTop">
		      		<el-scrollbar :style="editForm | editScrollHeight">
		      		<el-form :model="editForm" ref="editForm" :rules="editTmpRule" @submit.native.prevent>
                <el-form-item required label="所属应用" label-width="80px" width="200">
                  <span class="appName">{{editForm.clientName}}</span>
                </el-form-item>
			          <el-form-item label="角色名称" prop="roleName" label-width="80px">
			            <el-input v-model.trim="editForm.roleName" placeholder="请输入角色名称" size="mini"></el-input>
			          </el-form-item>
								<div class="logooutTooltip">注意：修改角色权限后，属于该角色的用户将退出登录，用户正在进行的操作无法保存，请确认没有影响后进行修改。</div>
			          <!-- 数据权限 -->
                <el-form-item v-if="editForm.authorityBean.allDataAuthority || editForm.authorityBean.haveDataAuthority.length"  label="数据权限" required label-width="80px">
			          	<el-radio-group v-model="editForm.authorityBean.allDataAuthority" size="mini">
			              <el-radio :label= true>全部权限</el-radio>
			              <el-radio :label= false>选择部分权限</el-radio>
			            </el-radio-group>
			            <el-transfer v-if= "!editForm.allDataAuthority" v-model="editForm.authorityBean.haveDataAuthority" :data="editForm.authorityBean.authority" :titles="['未添加数据权限', '已添加数据权限']"></el-transfer>
			          	<div class="emptyDate" v-if="!editForm.allDataAuthority&&editForm.dataLimitType">请选择数据权限</div>
			          </el-form-item>
                <!-- 功能权限 -->
			          <el-form-item v-if="editForm.functionAuthorityBean.functionAuthority&&editForm.functionAuthorityBean.functionAuthority.length"  label="功能权限" required label-width="80px">
			            <el-radio-group v-model="editForm.functionAuthorityBean.allFunctionAuthority" size="mini">
			              <el-radio :label= true>全部权限</el-radio>
			              <el-radio :label= false>选择部分权限</el-radio>
			            </el-radio-group>
			            <!-- <div style="float:right;font-size:12px;">* 区分数据权限</div> -->
			            <el-tree
			            	v-if="!editForm.functionAuthorityBean.allFunctionAuthority"
			            	:data="editForm.functionAuthorityBean.functionAuthority"
						        show-checkbox
						        node-key="id"
						        ref="tree1"
						        :props = "editForm.defaultProps"
						        :default-expand-all="true"
						        :expand-on-click-node="false"
		  							:default-checked-keys="editForm.functionAuthorityBean.haveAuthority"
		  							:render-content="renderContent">
									</el-tree>
									<div v-if="!editForm.functionAuthorityBean.allFunctionAuthority&&editForm.funcLimitType" class="emptyDate">请选择功能权限</div>
			          </el-form-item>
		          </el-form>
			        </el-scrollbar>
			        <span slot="footer" class="dialog-footer">
			          <el-button @click="cancelForm('editForm')">取消</el-button>
								<el-button type="primary" @click="updateRoleHandle('editForm')">确定</el-button>
			        </span>
		      </el-dialog>
      </div>
      <!--详情-->
      <div v-if="detailDialog">
      <el-dialog class="plusProModel" id="detailDialog" title="角色详情" :visible.sync="detailDialog" width="746px" :before-close="cancelDetail" top="30vh">
		  	<el-scrollbar style="height: 300px;padding-right: 2px;">
            <div class="detailFont"><span style="font-weight: bold;">所属应用：</span>{{detailData.clientName}}
            </div>
			  		<div class="detailFont"><span style="font-weight: bold;">角色名称：</span>{{detailData.roleName}}
			  		</div>
		  			<div v-if="detailData.authorityBean.allDataAuthority || detailData.authorityBean.haveDataAuthoritys.length > 0" class="detailFont">
              <span style="font-weight: bold;">数据权限：{{detailData.authorityBean.allDataAuthority ? '全部权限' : null}}</span>
		  				<div class="detailDatas" v-if="!detailData.authorityBean.allDataAuthority">
		  					<span v-for="(item,index) in detailData.authorityBean.haveDataAuthoritys" :key="index">{{(index == detailData.authorityBean.haveDataAuthoritys.length-1) ? item.label:item.label+'、'}}</span>
		  				</div>
		  			</div>
		  			<div v-if="detailData.functionAuthorityBean.functionAuthority.length" class="detailFont">
		  				<span class="span" style="font-weight: bold;">功能权限：</span><span v-if="detailData.functionAuthorityBean.allFunctionAuthority">全部权限</span>
  						<el-tree
                v-else
	            	:data="detailData.functionAuthorityBean.functionAuthority"
				        node-key="id"
				        :props = "editForm.defaultProps"
				        :default-expand-all="true"
				        :show-checkbox= "false"
				        :expand-on-click-node="true">
							</el-tree>
						</div>
				</el-scrollbar>
      </el-dialog>
      </div>
      <!--复制-->
      <el-dialog class="plusProModel" id="copyDialog" title="复制角色" :visible.sync="copyDialog" width="576px" :before-close="cancelCopy" top="35vh">
      		<el-form :model="copyForm" ref="copyForm" :rules="copyTmpRule" @submit.native.prevent>
      				<el-form-item label="重命名" prop="roleName" label-width="80px">
		            <el-input v-model.trim="copyForm.roleName" placeholder="请输入角色名称" size="mini"></el-input>
		          </el-form-item>
      		</el-form>
      		<span slot="footer" class="dialog-footer">
	          <el-button @click="cancelCopy">取消</el-button>
						<el-button type="primary" @click="sureCopy(copyForm)">确定</el-button>
	        </span>
      </el-dialog>
      <!--删除-->
      <el-dialog class="plusProModel" id="deleteDialog" title="确认删除" :visible.sync="deleteDialog" width="746px" :before-close="cancelDelete" top="35vh">
	  			<div class="deleteProject">删除<span>{{deleteDate.roleName}}</span>后，该角色将从所有用户的角色列表中删除，用户相应权限失效。该操作不可恢复，是否确认删除？</div>
      		<span slot="footer" class="dialog-footer">
	          <el-button @click="cancelDelete">取消</el-button>
						<el-button type="primary" @click="sureDelete(deleteDate.roleId)">确定</el-button>
	        </span>
      </el-dialog>
  </div>
</template>

<script>
  import
  {
    selectAllRole,
    addRole,
    updateRole,
    copyRole,
    roleDetail,
    getModuleById,
    deleteRole,
    applicationList,
    screeList
  } from "../../api/api.js";
export default {
  name: "",
  data() {
    return {
      minHeight: null,
      tab_data: [],
      appPlatformArr: [],
      clientId:null,
      page:{
      	  clientId: null, //所属应用
      		pageIndex: 1,
	        pageSize: 10,
      },
      total: 10,
      addDialog: false,
      addForm:{
      		roleName: null,
      		appPlatformArr: [],
      		dateLimit: true,
      		funcLimit: true,
      		clientId: null,
      		selectDate: [],
      		dateAll: [],
          treeData: [],
          treeData_d:[],
		      defaultProps: {
		      		children: 'children',
		          label: 'label'
		      },
		      dataLimitType: false,
	        funcLimitType: false,
	        resourceCheckedKey: [],
	        // extraList: []
      },
      addTmpRule: {
      		roleName: {
      				min: 1,
	            max: 50,
	            required: true,
	            message: "1-50个字符",
	            trigger: "blur"
      		},
      		appPlatform: [{
      				required: true,
      				message: "请选择所属应用",
	            trigger: "change"
      		}],
      		dateLimit: {
      				required: true,
      		},
      		funcLimit: {
      				required: true,
      		}
      },
      editTmpRule: {
      		roleName: {
      				min: 1,
	            max: 50,
	            required: true,
	            message: "1-50个字符",
	            trigger: "blur"
      		}
      },
      editDialog: false,
      editForm:{
      	 dataLimitType: false,
         funcLimitType: false,
         functionList:null
	      //  extraList: []
      },
      detailDialog: false,
      copyDialog: false,
      deleteDialog: false,
      detailData: {},	//角色详情数据
      deleteDate: {
      		roleId: null,
	  			roleName: null
      },
      copyForm: {
      		roleId: null,
      		roleName: null
      },
      copyTmpRule: {
      		roleName: {
      				min: 1,
	            max: 50,
	            required: true,
	            message: "1-50个字符",
	            trigger: "blur"
      		}
      },
    }
  },
  filters: {
  		newDialogTop(addForm) {
  			if(!addForm.appPlatform){
						return '30vh'
				}else if(!addForm.dateLimit){
						return '15vh'
				}else if(!addForm.funcLimit){
						return '15vh'
				}else {
						return '30vh'
				}
  		},
  		editDialogTop(editForm) {
  				if(editForm.authorityBean.haveDataAuthority&&editForm.authorityBean.haveDataAuthority.length&&!editForm.authorityBean.allDataAuthority){
							return '15vh'
					}else if(editForm.functionAuthorityBean.functionAuthority&&editForm.functionAuthorityBean.functionAuthority.length&&!editForm.functionAuthorityBean.allFunctionAuthority){
							return '15vh'
					}else {
							return '30vh'
          }
  				if(editForm.authorityBean){
							return '15vh'
					}else if(editForm.functionAuthorityBean.functionAuthority&&editForm.functionAuthorityBean.functionAuthority.length&&!editForm.functionAuthorityBean.allFunctionAuthority){
							return '15vh'
					}else {
							return '30vh'
					}
  		},
  		scrollHeight(addForm) {
				if(!addForm.appPlatform){
						return 'height: 277px'
				}else if(!addForm.dateLimit){
						return 'height: 540px'
				}else if(!addForm.funcLimit){
						return 'height: 540px'
				}else {
						return 'height: 277px'
				}
  		},
  		editScrollHeight(editForm) {
        	if(editForm.authorityBean.haveDataAuthority&&editForm.authorityBean.haveDataAuthority.length&&!editForm.authorityBean.allDataAuthority){
							return 'height: 540px'
					}else
  			  if(editForm.functionAuthorityBean.functionAuthority&&editForm.functionAuthorityBean.functionAuthority.length&&!editForm.functionAuthorityBean.allFunctionAuthority){
							return 'height: 540px'
					}else {
							return 'height: 277px'
					}
  		}
  },
  created() {
    this.globleItem();
  },
  mounted() {
    this.minHeight = window.innerHeight - 95;
    this.getRoleList();
  },
  methods: {
  		isRepeat(arr){
				var hash = {};
				for(var i in arr) {
          if(hash[arr[i]]) //hash 哈希
            return true;
          hash[arr[i]] = true;
				}
				return false;
			},
  		renderContent(h, { node, data, store }) {
  			if(data.haveData) {
  					return (
	          <span class="custom-tree-node">
	          <span>{node.label+'*'}</span>
	          </span>)
  			}else {
  					return (
  					<span class="custom-tree-node">
	          <span>{node.label}</span>
	          </span>)
  			}
  		},
  	  cancelCopy() {
  	  		this.copyDialog = false;
  	  		this.$refs['copyForm'].resetFields();
  	  },
  	  sureCopy(value) {
        /**
         * @description 角色拷贝功能
         * @param {Number} roleId 角色ID
         * @param {String} roleName 角色名称
         */
  	  	this.$refs.copyForm.validate(valid => {
		        if (valid) {
		        		copyRole({roleId:value.roleId,roleName:value.roleName}).then(res=>{
                  if(res.err_CODE === 0){
                    this.$message.success(res.err_MESSAGE);
                    this.copyDialog = false;
                    this.getRoleList();
                  }else{
                    this.$message.error(res.err_MESSAGE);
                    this.cancelCopy();
                  }
				       })
		    	}
		    })
  	  },
  		getAllParentId(treeData) {
  			let arr = [];
		    for(var i=0;i<treeData.length;i++){
		    		if(treeData[i].children&&treeData[i].children.length>0) {
		    				arr.push(treeData[i].id)
		    				for(var m=0;m<treeData[i].children.length;m++) {
		    						if(treeData[i].children[m].children&&treeData[i].children[m].children.length>0){
		    								arr.push(treeData[i].children[m].id)
		    						}
		    				}
		    		}
		    }
		    return arr;
  		},
  		globleItem() {
        /**
         * @description 获取应用列表
         * @param {Number} clientId 应用ID
         * @param {String} token 令牌
         * @param {Number} sign 默认值1
         */
        let self = this;
        let param = {
          sign:1,
          token:localStorage.getItem("sso_token"),
          authentication:null,
          tokenCheckType:null,
          verification:null,
          clientId:self.page.clientId,
          pageIndex:null,
          pageSize:null,
        };
				applicationList(param).then(res=>{
            let appPlatformArr = res.data.list;
             // 应用列表页根据应用ID筛选下拉列表赋值
						self.appPlatformArr = [{clientId:null,clientName:'全部'},
            ...appPlatformArr];
            //新增角色 应用列表下拉框数据赋值
            self.addForm.appPlatformArr = res.data.list;

            self.addForm.appPlatformArr.map(item=>{
              item.moduleList.length > 0 ? item.disabled = false : item.disabled = true;
              return item;
            })
        })
  		},
	  	deleteClick(item) {
        /**
         * @description 角色删除操作
         */
	  		this.deleteDate.roleId = item.roleId;
	  		this.deleteDate.roleName = item.roleName;
	  		this.deleteDialog = true;
	  	},
	  	cancelDelete() {
        /**
         * @description 取消角色删除
         */
	  		this.deleteDialog = false;
	  	},
	  	sureDelete(value) {
        /**
         * @description 删除角色
         * @param {Number} roleId 角色ID
         */
	  			deleteRole({roleId:value}).then(res=>{
            if(res.err_CODE === 0){
              this.$message.success(res.err_MESSAGE);
            }else{
              this.$message.error(res.err_MESSAGE);
            }
            // 更新列表数据
            this.getRoleList();
            this.deleteDialog = false;
	  			})

	  	},
	  	detailClick(value) {
        /**
         * @description 角色详情查询
         * @param {Number} roleId
         */
		  	roleDetail({roleId:value}).then(res=>{
		  			if(res.err_CODE == 0) {
              this.detailData = res.data;
              // 角色拥有的功能权限
							let permissionTree = this.detailData.functionAuthorityBean.functionAuthority;
							permissionTree.map(item =>{
								item.id = item.moduleId;
								item.label = item.moduleName;
								item.functionList.map(jtem =>{
									jtem.id = jtem.functionId;
									jtem.label = jtem.functionName;
								});
								item.children = item.functionList;
								return item;
							})
              this.detailData.functionAuthorityBean.functionAuthority = permissionTree;
              // 角色拥有的数据权限
              if(this.detailData.authorityBean.authority.length > 0 && this.detailData.authorityBean.haveDataAuthority.length > 0){
                this.detailData.authorityBean['haveDataAuthoritys'] =[];
                this.detailData.authorityBean.authority.forEach(item=>{
                  this.detailData.authorityBean.haveDataAuthority.forEach(jtem=>{
                    if(item.id == jtem){
                      this.detailData.authorityBean['haveDataAuthoritys'].push({ id: item.id,label: item.scrName})
                    }
                  })
                })
              }
              console.log(this.detailData);
		  				this.detailDialog = true;
		  			}else{
              this.$message.error(res.err_MESSAGE);
            }
		  	})
	  	},
	  	cancelDetail() {
        /**
         * @description 用户详情弹出框取消
         */
	  		this.detailDialog = false;
	  	},
	  	copyClick(roleId,roleName) {
        /**
         * @description 角色复制操作
         */
	  			this.copyDialog = true;
	  			this.copyForm.roleId = roleId;
	  			this.copyForm.roleName = roleName;
	  	},
  		editClick(value) {
        /**
         * @description 点击“角色编辑”按钮，获取该角色已包含的权限数据
         * @param {Number} roleId 角色ID
         */
        let param = {roleId:value.roleId};

        this.appPlatformSelect(value.clientId);
        roleDetail(param).then(res=>{
          if(res.err_CODE === 0){
            this.editDialog = true;
            this.editForm = JSON.parse(JSON.stringify(res.data));
            if(this.editForm.authorityBean.authority.length > 0){
              this.editForm.authorityBean.authority.map(item=>{
                item['label'] = item.scrName;
                item['key'] = item.id;
              })
            }
            // this.page.clientId = res.data.clientId;
            getModuleById({clientId:res.data.clientId,moduleId:null}).then(res=>{
              this.editForm.functionAuthorityBean.functionAuthority = res.data;
              this.editForm.functionAuthorityBean.functionAuthority.map(item=>{
                item.id = item.moduleId;
                item.label = item.moduleName;
                item.children = [];

                item.functionList.map(jtem =>{
                  jtem.id = jtem.functionId;
                  jtem.label = jtem.functionName;
                })
                item.children = item.functionList;
                return item;
              })
            })
          }else{
            this.$message.error(res.err_MESSAGE);
          }
        })

  		},
  		editReset() {
        /**
         * @description 编辑取消功能
         */
			 		this.$set(this.editForm, 'roleName', null)
  				this.$set(this.editForm, 'dataLimitType', false)
					this.$set(this.editForm, 'funcLimitType', false)
  				if(this.editForm.functionAuthorityBean.functionAuthority.length&&!this.editForm.functionAuthorityBean.allFunctionAuthority){
  						this.$refs.tree1.setCheckedKeys([]);
							for(var i=0;i<this.$refs.tree1.store._getAllNodes().length;i++){
			           this.$refs.tree1.store._getAllNodes()[i].expanded= false;
			       	}
  				}
  		},
  		cancelForm(editForm) {
  				this.$confirm("是否放弃编辑?", {
		        confirmButtonText: "确定",
		        cancelButtonText: "取消",
		        showClose: false
		      })
	        .then(e => {
	          	this.editDialog = false;
	         		this.editReset()
	        })
	        .catch(() => {});
      },
      updateRoleHandle(formName){
        /**
         * @description 编辑角色
         * @param {Number} roleId 角色ID
         * @param {String} roleName 角色名称
         * @param {Number} clientId 应用ID
         * @param {Boolean} allDataAuthority 是否拥有全部数据权限
         * @param {Boolean} allFunctionAuthority 是否拥有全部功能权限
         * @param {Array} dataAuthorityIds 选择的数据权限
         * @param {Array} functionAuthorityIds 选择的功能权限
         */
          let functionAuthorityIds = [];
          let haveFun,havaData = false;//数据或功能权限状态和验证和提示
          let validTips = true;

	        if(this.editForm.functionAuthorityBean){
	        		if(this.editForm.functionAuthorityBean.functionAuthority.length&&!this.editForm.functionAuthorityBean.allFunctionAuthority) {
	        				let allParentId = this.getAllParentId(this.editForm.functionAuthorityBean.functionAuthority);
					        functionAuthorityIds = this.$refs.tree1.getCheckedKeys().concat(this.$refs.tree1.getHalfCheckedKeys())
									if(allParentId.length) {
											allParentId.forEach((item,index)=>{
					    					let haveIndex = functionAuthorityIds.indexOf(item)
					    					if (haveIndex > -1) {
														functionAuthorityIds.splice(haveIndex, 1);
											  }
											})
								 }
	        		}
          };
	    		if(this.editForm.authorityBean) {
    					if(this.editForm.authorityBean.haveDataAuthority) {
    							if(this.editForm.authorityBean.haveDataAuthority.length){
    									havaData = true
                  }
                  else {
    									havaData = false
    							}
              }
              else {
    							havaData = false
    					}
	    		}
	    		if(this.editForm.functionAuthorityBean) {
    					if(this.editForm.functionAuthorityBean.functionAuthority) {
    							if(this.editForm.functionAuthorityBean.functionAuthority.length){
    									haveFun = true
                  }
                  else {
    									haveFun = false
    							}
              }
              else {
    							haveFun = false
    					}
	    		}
	    		if(!havaData){
		    			this.$set(this.editForm, 'authorityBean.allDataAuthority', false)
		    			this.$set(this.editForm, 'authorityBean.dataAuthorityIds', [])
		    	}
		    	if(!haveFun){
		    			this.$set(this.editForm, 'functionAuthorityBean.allFunctionAuthority', false)
		    			functionAuthorityIds = [];
		    	}
		    	if(this.editForm.authorityBean.dataAuthorityIds&&!this.editForm.authorityBean.dataAuthorityIds.length && !this.editForm.authorityBean.allDataAuthority) {
	        		this.$set(this.editForm, 'dataLimitType', true)
	       }else {
	        		this.$set(this.editForm, 'dataLimitType', false)
	        }
	        if(this.editForm.functionAuthorityBean.functionAuthority&&this.editForm.functionAuthorityBean.functionAuthority.length&& !this.editForm.functionAuthorityBean.allFunctionAuthority&&!this.$refs.tree1.getCheckedKeys().length) {
	        		this.$set(this.editForm, 'funcLimitType', true)
	        }else{
	        		this.$set(this.editForm, 'funcLimitType', false)
          };
	        if(havaData&&!haveFun){
		    			validTips = !this.editForm.dataLimitType;
		    	}else if(!havaData&&haveFun){
		    			validTips = !this.editForm.funcLimitType;
		    	}else if(havaData&&haveFun){
		    			validTips = !this.editForm.dataLimitType && !this.editForm.funcLimitType;
		    	}else {
		    			validTips = true
		    	}
					let params = {
              allDataAuthority: this.editForm.authorityBean.allDataAuthority,
					    allFunctionAuthority: this.editForm.functionAuthorityBean.allFunctionAuthority,
					    clientId: this.editForm.clientId,
              dataAuthorityIds: this.editForm.authorityBean.haveDataAuthority,
					    functionAuthorityIds: functionAuthorityIds,
					    roleId: this.editForm.roleId,
					    roleName: this.editForm.roleName,
          }

	        this.$refs[formName].validate(valid => {
		        if (valid && validTips) {
									updateRole(params).then(res=>{
                  // this.page.clientId = this.editForm.clientId;
                  this.getRoleList();
									if(functionAuthorityIds && functionAuthorityIds.length){
											this.$refs.tree1.setCheckedKeys([]);
											for(var i=0;i<this.$refs.tree1.store._getAllNodes().length;i++){
							           this.$refs.tree1.store._getAllNodes()[i].expanded= true;
							       }
									}
									this.editReset();
								  let messageParams = {
								  		message: res.err_MESSAGE
								  }
									if(res.err_CODE == 0){
											messageParams.type = 'success'
			  							this.$message(messageParams);
			  					}else {
			  						  messageParams.type = 'error'
			  							this.$message(messageParams);
			  					}
			  					this.editDialog = false;
								})
		        }
	        })

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
		  addreset() {
        /**
         * @description 新增取消
         */
		  		this.$set(this.addForm, 'roleName', null)
					this.$set(this.addForm, 'appPlatform', null)
					this.$set(this.addForm, 'selectDate', [])
					this.$set(this.addForm, 'dataLimitType', false)
					this.$set(this.addForm, 'funcLimitType', false)
		  },
	    resetForm(addForm) {
	    		this.$confirm("是否放弃编辑?", {
		        confirmButtonText: "确定",
		        cancelButtonText: "取消",
		        showClose: false
		      })
	        .then(e => {
	          	this.addDialog = false;
			    		this.$refs['addForm'].resetFields();
							this.addreset();
							if(this.addForm.treeData&&this.addForm.treeData.length) {
									this.$refs.tree.setCheckedKeys([]);
									for(var i=0;i<this.$refs.tree.store._getAllNodes().length;i++){
					           this.$refs.tree.store._getAllNodes()[i].expanded= false;
					        }
							}
	        })
	        .catch(() => {});
      },
	    formPost(addForm) {
	    	//数据或功能权限状态和验证和提示
	    	let haveDate = false;
        let haveFun = false;
        // console.log(this.addForm);
	    	if(this.addForm.appPlatform) {
          // 判断 数据权限 是部分选择还是全部
	    			if(this.addForm.dateAll) {
	    					if(this.addForm.dateAll.length) {
	    							haveDate = true
	    					}else {
	    							haveDate = false
	    					}
            }
            // 判断 功能权限 是部分选择还是全部
	    			if(this.addForm.treeData) {
	    					if(this.addForm.treeData.length) {
	    							haveFun = true
	    					}else{
	    							haveFun = false
	    					}
	    			}
        }

        // 当数据权限“全部”未选中时，selectDate 置空
	    	if(this.addForm.appPlatform&&!haveDate){
	    			this.$set(this.addForm, 'selectDate', [])
        }
        // 当功能权限“全部”未选中时，functionAuthorityIds 置空
	    	if(this.addForm.appPlatform&&!haveFun){
	    			functionAuthorityIds = [];
	    	}
        if(this.addForm.appPlatform && this.addForm.selectDate && !this.addForm.selectDate.length && !this.addForm.dateLimit ) {
					this.$set(this.addForm, 'dataLimitType', true)
        }else {
        		this.$set(this.addForm, 'dataLimitType', false)
        }
        if(this.addForm.appPlatform && !this.addForm.funcLimit && (this.$refs.tree.getCheckedKeys().length == 0)) {
        		this.$set(this.addForm, 'funcLimitType', true)
        }else{
        		this.$set(this.addForm, 'funcLimitType', false)
        }
        let isValid = true;
	    	if(this.addForm.dataLimitType && !this.addForm.funcLimitType){
	    			isValid = !this.addForm.dataLimitType;
	    	}else if(!this.addForm.dataLimitType && this.addForm.funcLimitType){
	    			isValid = !this.addForm.funcLimitType;
	    	}else if(this.addForm.dataLimitType && this.addForm.funcLimitType){
	    			isValid = !this.addForm.dataLimitType && !this.addForm.funcLimitType;
	    	}else {
	    			isValid = true
	    	}
        let functionAuthorityIds = [];
	    	if(this.addForm.treeData&&this.addForm.treeData.length&&!this.addForm.funcLimit) {
	    			let allParentId = this.getAllParentId(this.addForm.treeData);
	    			functionAuthorityIds = this.$refs.tree.getCheckedKeys().concat(this.$refs.tree.getHalfCheckedKeys())
						if(allParentId.length) {
								allParentId.forEach((item,index)=>{
		    					let haveIndex = functionAuthorityIds.indexOf(item)
		    					if (haveIndex > -1) {
											functionAuthorityIds.splice(haveIndex, 1);
								  }
								})
					  }
	    	}
	    	let params = {
	    		allDataAuthority: this.addForm.dateLimit,
			    allFunctionAuthority: this.addForm.funcLimit,
			    clientId: this.addForm.clientId,
			    dataAuthorityIds: this.addForm.selectDate,
			    functionAuthorityIds: functionAuthorityIds,
			    roleName: this.addForm.roleName,
        };

	      this.$refs[addForm].validate(valid => {
	        if (valid && isValid) {
	        		addRole(params).then(res=>{
                if(res.err_CODE === 0){
                  this.addDialog = false;
                  this.$refs[addForm].resetFields();
                  this.addreset();
                  this.getRoleList();
			        		if(functionAuthorityIds&&functionAuthorityIds.length) {
			        				this.$refs.tree.setCheckedKeys([]);
											for(var i=0;i<this.$refs.tree.store._getAllNodes().length;i++){
									       this.$refs.tree.store._getAllNodes()[i].expanded= false;
									   	}
			        		}
                }else{
                  this.$message.error(res.err_MESSAGE);
                }
	    				})

	        }
	      })
      },
			addRoleBtn() {
        /**
         * @description 新增角色弹出框显示
         */
         this.addDialog = true;
			},
		  appPlatformSelect(value){
        /**
         * @description 根据应用ID 获取对应的功能列表
         * @param {Number} clientID 应用ID
         */
		  		this.addForm.clientId = value;
		  		getModuleById({clientId:value}).then(res=>{
            // 拼接成符合要求的数据格式
              let treeData = res.data.map((item) =>{
                item.id = item.moduleId;
                item.label = item.moduleName;
                item.children = [];
                item.functionList.map(jtem =>{
                  jtem.id = jtem.functionId;
                  jtem.label = jtem.functionName;
                })
                item.children = item.functionList;
                return item;
              });
		  				this.addForm.treeData = treeData;
		  				this.$set(this.addForm, 'funcLimit', true)
		  				this.$set(this.addForm, 'funcLimitType', false)
		  				if(res.data.functionAuthority&&res.data.functionAuthority.length){
		  						this.$refs.tree.setCheckedKeys([]);
									for(var i=0;i<this.$refs.tree.store._getAllNodes().length;i++){
					           this.$refs.tree.store._getAllNodes()[i].expanded= false;
					       	}
		  				}
          });
          /**
           * @description　根据应用ID 获取对应的数据权限列表
           * @param {Number} clientId
           * @param {Number} pageSize
           * @param {Number} pageIndex
           */
          let params = {
            clientId: this.addForm.clientId,
            pageSize: null,
            pageIndex: null
          };
          screeList(params).then(res=>{
            if(res.err_CODE === 0){
              this.addForm.dateAll = res.data;
              if(this.addForm.dateAll.length > 0){
                this.addForm.dateAll.map(item=>{
                 item.label = item.scrName;
                 item.key = item.id;
                })
              }
            }
          })

      },
      getRoleList(){
        /**
         * @description 获取角色列表
         * @param {Number} clientId 应用ID
         * @param {Number} pageIndex
         * @param {Number} pageSize
         */
        let self = this;
        let param = {
          clientId:this.page.clientId,
          pageIndex: this.page.pageIndex,
          pageSize: this.page.pageSize,
        }
        selectAllRole(param).then(res=>{
          if(res.err_CODE === 0){
            if(res.data.list.length > 0){
              res.data.list.forEach((item,index) =>{
                //判断接口返回中有数据权限时
                if(item.dataAuthority){
                  // 字符串长度超过20，以...代替
                  if(item.dataAuthority.length > 20){
                    res.data.list[index].dataAuthority1 = item.dataAuthority.substring(0,20)+'...';
                  }else{
                    res.data.list[index].dataAuthority1 = item.dataAuthority
                  }
                }else{
                  // 没有数据权限，以“--”显示
                  res.data.list[index].dataAuthority1 = '--';
                }

                // 判断接口返回有功能权限时
                if(item.functionAuthority){
                  // 字符串长度超过20，以...代替
                  if(item.functionAuthority.length > 20){
                    res.data.list[index].functionAuthority1 = item.functionAuthority.substring(0,20)+'...';
                  }else{
                    res.data.list[index].functionAuthority1 = item.functionAuthority
                  }
                }else{
                  // 没有功能权限，以“--”显示
                  res.data.list[index].functionAuthority1 = '--';
                }
              })
              self.total = res.data.total;
              self.tab_data = res.data.list;
            }else{
              self.tab_data = [];
              self.total = res.data.total;
            }
          }else{
            self.$message.error(res.err_MESSAGE);
          }
        })

      },
      getFunctionList(){
        /**
         * @description 根据应用ID查询该应用下的功能权限
         * @param {String} clientId
         */
      },
      getDataList(){
        /**
         * @description 根据应用ID查询该应用下的数据权限
         * @param {String} clientId
         */

      },
      addRoles(form){
        /**
         * @description 添加角色
         * @param {Boolean} allDataAuthority 是否选择所有数据权限
         * @param {Boolean} allFunctionAuthority 是否选择所有功能权限
         * @param {Number} clientId 应用ID
         * @param {Array} dataAuthorityIds 数据权限ID
         * @param {Array} functionAuthorityIds 功能权限ID
         * @param {String} roleName 角色名称
         */
	    	let haveDate,haveFun=  false;
	    	if(this.addForm.appPlatform) {
	    			if(this.addForm.dateAll) {
	    					if(this.addForm.dateAll.length) {
	    							haveDate = true
	    					}else {
	    							haveDate = false
	    					}
	    			}
	    			if(this.addForm.treeData) {
	    					if(this.addForm.treeData.length) {
	    							haveFun = true
	    					}else{
	    							haveFun = false
	    					}
	    			}
	    	}
	    	if(this.addForm.appPlatform&&!haveDate){
	    			this.$set(this.addForm, 'selectDate', [])
	    	}
	    	if(this.addForm.appPlatform&&!haveFun){
	    			functionAuthorityIds = [];
	    	}
        if(this.addForm.appPlatform && this.addForm.selectDate && !this.addForm.selectDate.length && !this.addForm.dateLimit ) {
					this.$set(this.addForm, 'dataLimitType', true)
        }else {
        		this.$set(this.addForm, 'dataLimitType', false)
        }
        if(this.addForm.appPlatform && !this.addForm.funcLimit && (this.$refs.tree.getCheckedKeys().length == 0)) {
        		this.$set(this.addForm, 'funcLimitType', true)
        }else{
        		this.$set(this.addForm, 'funcLimitType', false)
        }
        let isValid = true;
	    	if(this.addForm.dataLimitType && !this.addForm.funcLimitType){
	    			isValid = !this.addForm.dataLimitType;
	    	}else if(!this.addForm.dataLimitType && this.addForm.funcLimitType){
	    			isValid = !this.addForm.funcLimitType;
	    	}else if(this.addForm.dataLimitType && this.addForm.funcLimitType){
	    			isValid = !this.addForm.dataLimitType && !this.addForm.funcLimitType;
	    	}else {
	    			isValid = true
	    	}
        let functionAuthorityIds = [];
	    	if(this.addForm.treeData&&this.addForm.treeData.length&&!this.addForm.funcLimit) {
	    			let allParentId = this.getAllParentId(this.addForm.treeData);
	    			functionAuthorityIds = this.$refs.tree.getCheckedKeys().concat(this.$refs.tree.getHalfCheckedKeys())
						if(allParentId.length) {
								allParentId.forEach((item,index)=>{
		    					let haveIndex = functionAuthorityIds.indexOf(item)
		    					if (haveIndex > -1) {
											functionAuthorityIds.splice(haveIndex, 1);
								  }
								})
					  }
        }

	    	let params = {
	    		allDataAuthority: this.addForm.dateLimit,
			    allFunctionAuthority: this.addForm.funcLimit,
			    clientId: this.addForm.clientId,
			    dataAuthorityIds: this.addForm.selectDate,
			    functionAuthorityIds: functionAuthorityIds,
			    roleName: this.addForm.roleName,
	    	};
	      this.$refs[addForm].validate(valid => {
	        if (valid && isValid) {
	        		addRole(params).then(res=>{
	        				if(res.err_MESSAGE != '该角色名已存在！') {
                      this.getRoleList();
			        				this.addDialog = false;
			        				this.$refs['addForm'].resetFields();
			        				this.addreset();
			        				if(functionAuthorityIds&&functionAuthorityIds.length) {
			        						this.$refs.tree.setCheckedKeys([]);
													for(var i=0;i<this.$refs.tree.store._getAllNodes().length;i++){
									           this.$refs.tree.store._getAllNodes()[i].expanded= false;
									       	}
			        				}
			  					}
	    						let messageParams = {
								  		message: res.err_MESSAGE
								  }
									if(res.err_CODE == 0){
											messageParams.type = 'success'
			  							this.$message(messageParams);
			  					}else {
			  						  messageParams.type = 'error'
			  							this.$message(messageParams);
				  				}
	    				})

	        }
	      })
      },
      selectedHandle(){
        /**
         * @description 获取选中某个应用下的角色列表数据
         * @param {Number} clientId 应用ID
         * @param {Number} pageSize
         * @param {Number} pageIndex
         */
        this.getRoleList();
      }
  }
}
</script>
<style lang="scss">
@import '../../styles/role-manage/role-management.scss';
</style>

