<template>
  <!-- 数据权限 -->
  <div class="data-auth common-root" id="data-access">
    <!-- 基础配置 -->
    <div class="card-box">
      <div class="header">
        <el-row>
          <el-col :span="6" class="col-span">
            <i class="icon"></i><span class="col-font">基础配置</span>
          </el-col>
          <el-col :span="18" class="select-container">
              资源类型:
              <el-select size="mini" v-model="page.type" @change="getResource()">
                <el-option
                v-for="item in resourceType"
                :key="item.rId"
                :value="item.rId"
                :label="item.rName"
                >
                </el-option>
              </el-select>
              <el-button type="primary" size="mini" @click="adResourceVisible = true">新增资源</el-button>
          </el-col>
        </el-row>

      </div>
      <div class="content">
        <div class="table-container">
          <template>
            <el-table ref="resouceTab":data="sourceData" highlight-current-row>
              <el-table-column label="资源标识符" prop="tag" width="200">
              </el-table-column>
              <el-table-column label="资源名称">
                <template slot-scope="scope">
                  <router-link :to='{name: "资源详情",params:{param: JSON.stringify(scope.row)}}'><span class="resource-name">{{scope.row.name}}</span></router-link>
                </template>
              </el-table-column>
              <el-table-column label="资源类型" prop="type">
                <template slot-scope="scope">
                  {{scope.row.tag!= null && scope.row.tag == 1 ? '非顶级资源' : '顶级资源'}}
                </template>
              </el-table-column>
              <el-table-column label="创建时间" prop="createTime">
                <template slot-scope="scope">
                  {{handleFormat(scope.row.createTime)}}
                </template>
              </el-table-column>
              <el-table-column label="操作" width="200">
                <template slot-scope="scope">
	        				<el-tooltip class="item" effect="dark" content="编辑" placement="top">
	        					<em class="el-icon-edit"  @click="editHandle(scope.row)"></em>
	        				</el-tooltip>
	        				<el-tooltip class="item" effect="dark" content="删除" placement="top">
	        					<em class="el-icon-delete" @click="delResourceHandle(scope.row)"></em>
	        				</el-tooltip>
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
    </div>
    <!-- 新增资源 -->
    <el-dialog
      title="新增资源"
      :visible.sync="adResourceVisible"
      width="606px"
      :close-on-click-modal="false"
      top="15vh"
      class="dialog"
      @close="cancelClose('addResourceForm')">
      <div class="content">
        <el-form :model="addResourceForm" ref="addResourceForm" :rules="resourceRules" label-width="90px">
          <el-form-item label="标识符" prop="tag">
            <el-input size="mini" v-model="addResourceForm.tag" placeholder="请输入2-10位由英文小写字母组成的标识符"></el-input>
          </el-form-item>
          <el-form-item label="资源名称" prop="name">
            <el-input size="mini" v-model="addResourceForm.name" placeholder="请输入1-20位任意字母组成的资源名称"></el-input>
          </el-form-item>
          <el-form-item label="资源类型" class="mg10" label-width="80px">
            <el-select size="mini"  placeholder="请选择资源类型" v-model="addResourceForm.type">
              <el-option
                v-for="item in resourceTypeList"
                :key="item.rId"
                :value="item.rId"
                :label="item.rName"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="描述" class="mg10" label-width="80px" prop="desc">
            <el-input size="mini" resize="none" v-model="addResourceForm.desc" type="textarea" :rows="2" placeholder="请输入描述内容" maxlength="30" show-word-limit></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelClose('addResourceForm')">取消</el-button>
        <el-button type="primary" @click="addReSourceHandle('addResourceForm')">确定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑资源 -->
    <el-dialog
      title="编辑资源"
      :visible.sync="edResouceVisible"
      width="606px"
      top="15vh"
      class="dialog"
      @close="cancelClose('editResourceForm')">
      <div class="content">
        <el-form :model="editResourceForm" ref="editResourceForm" :rules="resourceRules" label-width="90px">
          <el-form-item label="标识符" prop="tag">
            <el-input size="mini" placeholder="请输入标识符" v-model="editResourceForm.tag"></el-input>
          </el-form-item>
          <el-form-item label="资源名称" prop="name">
            <el-input size="mini" placeholder="请输入资源名称" v-model="editResourceForm.name"></el-input>
          </el-form-item>
          <el-form-item label="资源类型" class="mg10" label-width="80px">
            <el-select size="mini" placeholder="请选择资源类型" v-model="editResourceForm.type">
              <el-option
                v-for="item in resourceTypeList"
                :key="item.rId"
                :value="item.rId"
                :label="item.rName"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="描述" class="mg10" label-width="80px">
            <el-input size="mini"  resize="none" v-model="editResourceForm.desc" type="textarea" :rows="2" placeholder="请输入描述内容" maxlength="30" show-word-limit></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelClose('editResourceForm')">取消</el-button>
        <el-button type="primary" @click="editResourceHandle('editResourceForm')">确定</el-button>
      </span>
    </el-dialog>
    <el-divider></el-divider>
    <!-- 条件配置 -->
    <div class="card-box">
      <div class="header">
        <el-row>
          <el-col :span="6" class="col-span">
            <i class="icon"></i><span class="col-font">条件配置</span>
          </el-col>
          <el-col :span="18" class="select-container">
              <el-button type="primary" size="mini" :disabled="sourceData.length == 0" @click="addConditionVisible = true">自定义条件配置</el-button>
          </el-col>
        </el-row>
      </div>
      <div class="content">
        <div class="table-container">
          <template>
            <el-table ref="cdfTab" :data="tabData" highlight-current-row>
              <el-table-column label="表达式名称" prop="scrName" width="200">
              </el-table-column>
              <el-table-column label="录入规则" prop="scrJson" width="400">
              </el-table-column>
              <el-table-column label="属性可见性" prop="attInfoList" width="400">
                <template slot-scope="scope">
                  {{scope.row.attInfoList != null ? tdHandle(scope.row.attInfoList) : '暂无属性'}}
                </template>
              </el-table-column>
              <el-table-column label="创建时间" prop="createTime">
                <template slot-scope="scope">
                  {{handleFormat(scope.row.createTime)}}
                </template>
              </el-table-column>
              <el-table-column label="操作" width="200">
                <template slot-scope="scope">
	        				<el-tooltip class="item" effect="dark" content="编辑" placement="top">
	        					<em class="el-icon-edit" @click="editCdHandle(scope.row)"></em>
	        				</el-tooltip>
	        				<el-tooltip class="item" effect="dark" content="删除" placement="top">
	        					<em class="el-icon-delete" @click="delConditionHandle(scope.row)"></em>
	        				</el-tooltip>
                </template>
              </el-table-column>
            </el-table>
          </template>
          <div class="paging fr">
            <el-pagination
              background
              @size-change="handleSizeChange_cd"
              @current-change="handleCurrentChange_cd"
              :current-page="page_cd.pageIndex"
              :page-sizes="[10, 20, 50, 100]"
              :page-size="page_cd.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="page_cd.total"
              v-if="page_cd.total"
            ></el-pagination>
          </div>
        </div>
      </div>
    </div>
    <!-- 新增自定义条件配置 -->
    <el-dialog
      title="自定义条件配置"
      :visible.sync="addConditionVisible"
      width="850px"
      :close-on-click-modal="false"
      top="15vh"
      class="dialog condition-dialog"
      @close="cancelClose('addConditionForm')">
      <div class="content">
        <el-form :model="addConditionForm" ref="addConditionForm" :rules="conditionRules" label-width="90px">
          <el-form-item label="名称" prop="scrName">
            <el-input size="mini" placeholder="请输入名称" v-model="addConditionForm.scrName"></el-input>
          </el-form-item>
          <!-- 组件:vue-query-builder-plus -->
          <el-form-item label="录入规则" prop="scrJson">
            <vue-query-builder ref="queryBuilder" :rules="rules" @input="getInput"></vue-query-builder>
          </el-form-item>
          <el-form-item label="属性可见性" prop="attIdList">
              <el-cascader
              style="width:100%"
              size="mini"
              ref="cascader"
              :options="rules"
              v-model="addConditionForm.attIdList"
              :show-all-levels="false"
              :props="props"
              clearable></el-cascader>
          </el-form-item>
          <el-form-item label="描述" class="mg10" label-width="80px" prop="scrDesc">
            <el-input size="mini"  resize="none" type="textarea" v-model="addConditionForm.scrDesc" :rows="2" placeholder="请输入描述内容" maxlength="30" show-word-limit></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelClose('addConditionForm')">取消</el-button>
        <el-button type="primary" @click="addConditionHandle('addConditionForm')">确定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑自定义配置信息 -->
    <el-dialog
      title="编辑自定义条件配置信息"
      :visible.sync="editConditionVisible"
      width="850px"
      :close-on-click-modal="false"
      top="15vh"
      class="dialog condition-dialog"
      @close="cancelClose('editConditionForm')">
      <div class="content">
        <el-form :model="editConditionForm" ref="editConditionForm" :rules="conditionRules" label-width="90px">
          <el-form-item label="名称" prop="scrName">
            <el-input size="mini" placeholder="请输入名称" v-model="editConditionForm.scrName"></el-input>
          </el-form-item>
          <!-- 组件:vue-query-builder-plus -->
          <el-form-item label="录入规则" prop="scrJson">
            <vue-query-builder ref="editBuilder" :rules="rules" @input="getInput"></vue-query-builder>
          </el-form-item>
          <el-form-item label="属性可见性" prop="attIdList">
              <el-cascader
              style="width:100%"
              size="mini"
              ref="editCascader"
              :options="rules"
              v-model="editConditionForm.attIdList"
              :show-all-levels="false"
              :props="props"
              clearable></el-cascader>
          </el-form-item>
          <el-form-item label="描述" class="mg10" label-width="80px">
            <el-input size="mini"  resize="none" type="textarea" v-model="editConditionForm.scrDesc" :rows="2" placeholder="请输入描述内容" maxlength="30" show-word-limit></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelClose('editConditionForm')">取消</el-button>
        <el-button type="primary" @click="editConditionHandle('editConditionForm')">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>


<script>
import {
   addResource,
   delResource,
   editResource,
   resourceList,
   attributeList,
   addScree,
   screeList,
   delScree,
   editScree
} from "../../../api/api"
import selectObj from '../../../labs/constant';
import {getDate,treeList} from '../../../labs/tools'
import qs from 'qs';
import vueQueryBuilder from '../../query-builder/VueQueryBuilder'
export default {
  data(){
    var validateTag = (rule, value, callback)=>{
      if(value === ""){
        callback(new Error("请输入2-10位字母或数字、下划线组成的标识符"));
      }else{
        if(!(/^(?![\d_]+$)\w+$/).test(value)){
          callback(new Error("请输入2-10位字母或数字、下划线组成的标识符"))
        }else if(value.length > 20 || value.length < 2){
          callback(new Error("请输入2-10位字母或数字、下划线组成的标识符"))
        }else{
          callback();
        }
      }
    }
    return{
      tabData:[],
      sourceData:[],
      resourceType:null,
      page:{
        total:10,
        pageIndex:1,
        pageSize:10,
        type:null,
        clientId:null,
      },
      page_cd:{
        total: 10,
        pageIndex: 1,
        pageSize: 10,
        clientId: null
      },
      props:{
        multiple: true,
        value: 'id',
        label: 'label',
        children: 'children'
      },
      addConditionForm:{
        scrName:null,
        scrRule: null,
        scrJson: null,
        attIdList:null,
        scrDesc: null,
        clientId: null
      },
      editConditionForm:{
        scrName:null,
        scrRule: null,
        scrJson: null,
        attIdList:null,
        scrDesc: null,
        clientId: null
      },
      conditionRules:{
        scrName:[
          { required: true, message: '请输入表达式名称', trigger: 'blur'}
        ],
        scrJson:[
          { required: true, message: '请输入录入规则', trigger: 'blur'}
        ],
        attIdList:[
          { required: true, message: '请选择属性标签', trigger: 'change'}
        ],
        scrDesc:[
          { required: false}
        ]
      },
      adResourceVisible:false,
      edResouceVisible: false,
      addConditionVisible:false,
      editConditionVisible: false,
      resourceTypeList:null,
      addResourceForm:{
        id:null,
        name:null,
        type:1,
        desc:null
      },
      editResourceForm:{
        id:null,
        name:null,
        type:1,
        desc:null
      },
      resourceRules:{
        tag:[
          { required: true,  message: '请输入2-10位由英文小写字母组成的标识符', trigger:'blur' },
          { patten:/[a-z]/, message:'', trigger:'blur'},
          { validator: validateTag, trigger: 'blur'}
        ],
        name:[
          { required: true, message: '请输入1-20位任意字符', trigger: 'blur' },
          { min: 1, max: 20, message: '请输入1-20位任意字符', trigger: 'blur'}
        ],
        desc:[
          { required: false}
        ]
      },
      rules:[],
      lists:[]
    }
  },
  components: { vueQueryBuilder },
  methods:{
    getInput(data){
      /**
       * @description 将接受的vue-query-builder 的数据转换成二叉树格式
       */
      // 二叉树
      this.addConditionForm.scrJson = treeList(data.children,data.logicalOperator);

      // vue-query-builder
      this.addConditionForm.scrRule = data;
    },
    cancelClose(formName){
      /**
       * @description 取消弹出框关闭
       */
      switch(formName){
        case 'addResourceForm':
          this.adResourceVisible = false;
          break;
        case 'editResourceForm':
          this.edResouceVisible = false;
          break;
        case 'addConditionForm':
          this.addConditionVisible = false;
          // vue-query-builder 数组还原
          this.$refs['queryBuilder'].query = {logicalOperator:'AND',children:[]};
          break;
        case 'editConditionForm':
          this.editConditionVisible = false;
      }
      this.$refs[formName].resetFields();
    },
    getResource(){
      /**
       * @description 获取资源
       * @param {Number} clientId 应用ID
       * @param {Number} type 资源类型 0-顶级资源 1-非顶级资源
       * @param {Number} pageIndex 当前页
       * @param {Number} pageSize 页大小
       */
      let self = this;
      let param = {
        clientId: this.page.clientId,
        type: this.page.type,
        pageIndex: this.page.pageIndex,
        pageSize: this.page.pageSize
      };
      let rule = null;
      this.rules = [];
      this.lists = [];
      resourceList(qs.stringify(param)).then(res=>{
        if(res.err_CODE === 0){
          res.data.list.length > 0 ? this.sourceData = res.data.list : this.sourceData = [];
          this.page.total = res.data.total;
          res.data.list.forEach(item=>{
            let list = {
              id:item.id,
              value:item.id,
              label: item.name,
              type:'text',
              children:new Array()
            };
            this.rules.push(list);
            this.rules.map(item=>{
              item['subRules'] = new Array();
            })
            let configId = null;
            if(this.rules.length > 0){
              this.rules.map(item=>{
                attributeList({
                  configId: item.id,
                  pageSize: null,
                  pageIndex: null
                }).then(res=>{
                  if(res.err_CODE === 0){
                    // console.log(res.data.length);
                    if(res.data.length > 0){
                      console.log(res.data);
                      item.subRules = res.data;
                      item.children = res.data;
                      item.children.map(jtems=>{
                        jtems['label'] = jtems.attributeName;
                        jtems['value'] = jtems.id;
                      })
                      item.subRules.map(jtem=>{
                        jtem['label'] = jtem.attributeName;
                        selectObj.dataType.forEach( type =>{
                         type.dId ==  jtem.attributeType ? jtem['type'] = type.value : jtem['type'] = 'text';
                        })
                      })
                    }
                  }
                })
              })
            }
          })

          this.lists = this.rules;
        }else{
          this.$message.error(res.err_MESSAGE)
        }
      })
    },
    addReSourceHandle(formName){
      /**
       * @description 新增资源
       * @param {Number} clientId 应用ID
       * @param {String} Tag 资源标识符'
       * @param {String} name 资源名称
       * @param {Number} type 资源类型 0-顶级资源 1-非顶级资源
       */
      let param = {
        clientId: this.page.clientId,
        name: this.addResourceForm.name,
        tag: this.addResourceForm.tag,
        type: this.addResourceForm.type,
        desc: this.addResourceForm.desc
      };
      this.$refs[formName].validate(valid=>{
        if(valid){
          addResource(param).then(res=>{
            if(res.err_CODE === 0){
              this.$message.success("添加成功");
              this.adResourceVisible = false;
              this.$refs[formName].resetFields();
              this.getResource();
            }else{
              this.$message.error(res.err_MESSAGE);
            }
          })
        }else{
          return false;
        }
      })

    },
    delResourceHandle(data){
      /**
       * @description 删除资源
       * @param {Array} idList 删除资源ID数组
       */
      this.$confirm("你确定要删除吗？删除后不可恢复",'提示',{
        confirmButtonText:'确定',
        cancelButtonText:'取消',
      }).then(() =>{
        // 确定删除
        let param = { idList : []};
        // 根据参数data判断传入的值类型是数组还是对象进行 删除参数的数值传递
        Array.isArray(data) ? param.idList = data.map((item)=>{return item.id}) : param.idList.push(data.id);
        delResource(qs.stringify(param,{ arrayFormat: 'repeat' })).then(res=>{
          if(res.err_CODE === 0){
            this.$message.success("删除成功");
            this.getResource();
          }else{
            this.$message.error(res.err_MESSAGE);
          }
        })
      }).catch(() =>{
        // 取消删除
      })
    },
    editHandle(data){
      /**
       * @description 资源编辑信息form绑定
       */
      this.edResouceVisible = true;
      this.editResourceForm = JSON.parse(JSON.stringify(data));
    },
    editResourceHandle(formName){
      /**
       * @description 资源修改信息提交
       * @param {Number} clientId 应用ID
       * @param {Number} id 资源主键ID
       * @param {String} tag 资源标识符
       * @param {String} name 资源名称
       * @param {Number} 资源类型 0-顶级资源 1-非顶级资源
       */
      this.$refs[formName].validate(valid=>{
        if(valid){
          let param = {
            id: this.editResourceForm.id,
            clientId: this.page.clientId,
            tag: this.editResourceForm.tag,
            name: this.editResourceForm.name,
            type: this.editResourceForm.type,
            desc: this.editResourceForm.desc
          };

          editResource(param).then(res=>{
            if(res.err_CODE === 0){
              this.$message.success('修改成功');
              this.edResouceVisible = false;
              this.$refs[formName].resetFields();
              this.getResource();
            }else{
              this.$message.error(res.err_MESSAGE);
            }
          })
        }else{
          return false;
        }
      })
    },
    handleFormat(date){
      /**
       * @description 时间格式化
       */
      return getDate(new Date(date).getTime()/1000);
    },
    handleSizeChange(val){
      /**
       * @description 分页大小 -- 资源性列表
       */
      this.page.pageSize = val;
      this.getResource();
    },
    handleCurrentChange(val){
      /**
       * @description 分页条数 -- 资源列表
       */
      this.page.pageIndex = val;
      this.getResource();
    },
    handleSizeChange_cd(val){
      /**
       * @description 分页大小 -- 条件配置
       */
      this.page_cd.pageSize = val;
      this.getConditionList();
    },
    handleCurrentChange_cd(val){
      /**
       * @description 分页条数 -- 条件配置
       */
      this.page_cd.pageIndex = val;
      this.getConditionList();
    },
    addConditionHandle(formName){
      /**
       * @description 新增条件
       * @param {Number} clientId 应用ID
       * @param {String} scrName 表达式名称
       * @param {String} scrRule 录入规则
       * @param {String} scrDesc 描述
       * @param {String} scrJson 表达式JSON串
       * @param {Array}  attIdList 属性ID列表
       */
      // 拼接给后端的二叉树结构 { n: null, e: null, c:[]} n-表达式名称  e: 二叉树表达式  c: 选择的属性标签
      let objJsons = { n: this.addConditionForm.scrName, e: this.addConditionForm.scrJson, c: []};

      // 获取级联选择的节点，转化成 “资源.属性” 结构
      let checkNodes = [];
      checkNodes = this.$refs['cascader'].getCheckedNodes();
      checkNodes.forEach(item=>{
        if(item.parent != null){
          objJsons.c.push(`${item.parent.label}.${item.label}`)
        }
      })

      // 获取级联选择的节点，转化成 “资源.属性” 结构
      let param = {
        clientId: this.page.clientId,
        scrName: this.addConditionForm.scrName,
        attIdList: this.addConditionForm.attIdList,
        scrJson: JSON.stringify(objJsons),
        scrRule: JSON.stringify(this.addConditionForm.scrRule),
        scrDesc: this.addConditionForm.scrDesc
      };
      this.$refs[formName].validate(valid=>{
        if(valid){
          addScree(param).then(res=>{
            if(res.err_CODE === 0){
              this.$message.success("新增成功");
              this.addConditionVisible = false;
              this.$refs[formName].resetFields();
              this.getConditionList();
              // vue-query-builder 数组还原
              this.$refs['queryBuilder'].query = {logicalOperator:'AND',children:[]};
            }else{
              this.$message.error(res.err_MESSAGE);
            }
          })
        }
      })
    },
    editCdHandle(data){
      /**
       * @description 条件配置信息编辑绑定
       */
      this.editConditionVisible = true;
      this.editConditionForm = JSON.parse(JSON.stringify(data));
      this.editConditionForm.attIdList = data.attrId;

      this.$nextTick(()=>{
        let ruleObj = JSON.parse(JSON.stringify(data)).scrRule;
        this.$refs['editBuilder'].query = JSON.parse(ruleObj);
      })
    },
    editConditionHandle(formName){
      /**
       * @description 编辑条件配置信息
       * @param {Number} id 条件配置ID
       * @param {Number} clientId 所属应用ID
       * @param {String} scrName 表达式名称
       * @param {String} scrRule 录入规则
       * @param {String} scrDesc 描述
       * @param {String} scrJson 表达式json串
       * @param {Array} attIdList 属性ID列表
       */
      // 拼接给后端的二叉树结构 { n: null, e: null, c:[]} n-表达式名称  e: 二叉树表达式  c: 选择的属性标签  注意：此处共用新增表单中的scrRule
      let objJsons = { n: this.editConditionForm.scrName, e: this.addConditionForm.scrJson, c: []};

      // 获取级联选择的节点，转化成 “资源.属性” 结构
      let checkNodes = [];
      checkNodes = this.$refs['editCascader'].getCheckedNodes();
      checkNodes.forEach(item=>{
        if(item.parent != null){
          objJsons.c.push(`${item.parent.label}.${item.label}`)
        }
      })
      let param = {
        id: this.editConditionForm.id,
        clientId: this.page.clientId,
        scrName: this.editConditionForm.scrName,
        attIdList: this.editConditionForm.attIdList,
        scrJson: JSON.stringify(objJsons),
        scrRule: JSON.stringify(this.addConditionForm.scrRule),
        scrDesc: this.editConditionForm.scrDesc
      };

      this.$refs[formName].validate(valid=>{
        if(valid){
          editScree(param).then(res=>{
            if(res.err_CODE === 0){
              this.$message.success("编辑成功");
              this.editConditionVisible = false;
              this.$refs[formName].resetFields();
              this.getConditionList();
              // vue-query-builder 数组还原 注意：此处共用新增表单中的scrRule
              this.$refs['editBuilder'].query = this.addConditionForm.scrRule;
            }else{
              this.$message.error(res.err_MESSAGE);
            }
          })
        }
      })

    },
    getConditionList(){
      /**
       * @description 获取配置的数据条件列表
       * @param {Number} clientId 应用ID
       * @param {Number} pageIndex 当前页
       * @param {Number} pageSize 页大小
       */
      let param = {
        clientId: this.page.clientId,
        pageSize: this.page_cd.pageSize,
        pageIndex: this.page_cd.pageIndex
      };

      screeList(param).then(res=>{
        if(res.err_CODE === 0){
          this.tabData = res.data.list;
          this.tabData.map(item=>{
            item['attrId'] = new Array();
            if(item.attInfoList != null){
              item.attInfoList.forEach(jtem=>{
                let ids = new Array();
                ids.push(jtem.configId);
                ids.push(jtem.attributeId);
                item.attrId.push(ids);
              })
            }
          });
          this.page_cd.total = res.data.total;
        }else{
          this.$message.error(res.err_MESSAGE);
        }
      })
    },
    tdHandle(data){
      /**
       * @description 属性单元格标签展示
       */
      let list = [];
      if(data.length > 0){
        data.forEach(item =>{
          list.push(item.attributeName);
        })
        return list.toString();
      }
    },
    delConditionHandle(data){
      /**
       * @description 删除某些或某个具体的条件语句
       * @param {Array} idList 删除条件ID数组
       */
      this.$confirm("你确定要删除吗？删除后不可恢复",'提示',{
        confirmButtonText:'确定',
        cancelButtonText:'取消',
      }).then(() =>{
        // 确定删除
        let param = { idList : []};
        // 根据参数data判断传入的值类型是数组还是对象进行 删除参数的数值传递
        Array.isArray(data) ? param.idList = data.map((item)=>{return item.id}) : param.idList.push(data.id);
        delScree(qs.stringify(param,{ arrayFormat: 'repeat' })).then(res=>{
          if(res.err_CODE === 0){
            this.$message.success("删除成功");
            this.getConditionList();
          }else{
            this.$message.error(res.err_MESSAGE);
          }
        })
      }).catch(() =>{
        // 取消删除
      })
    }
  },
  mounted(){
    this.getResource();
    this.getConditionList();
  },
  created(){
    this.resourceType = [{ rId: null, rName: '全部'},...selectObj.resourceType];
    this.resourceTypeList = selectObj.resourceType;
    this.page.clientId = this.$route.params.clientId;
  }
}
</script>

<style lang="scss">

</style>
