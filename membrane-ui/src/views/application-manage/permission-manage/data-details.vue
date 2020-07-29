<template>
  <!-- 数据权限详情 -->
  <div class="common-root" id="data-detail" :style="{minHeight: minHeight + 'px'}">
    <!-- 基本信息 -->
    <div class="title"><el-button type="primary" size="mini" icon="el-icon-arrow-left" @click="preHandle">返回</el-button><span class="name">{{page.resourceDetail.name}}</span></div>
    <el-divider content-position="left">基本信息</el-divider>
    <div class="basic-info details-content">
      <ul>
        <li><span class="label">标识符：</span><span class="value">{{page.resourceDetail.tag}}</span></li>
        <li><span class="label">资源名称：</span><span class="value">{{page.resourceDetail.name}}</span></li>
        <li><span class="label">类型：</span><span class="value">{{page.resourceDetail.type == '1' ? '顶级资源' : '非顶级资源'}}</span></li>
        <li><span class="label">备注：</span><span class="value">{{'desc'}}</span></li>
      </ul>
    </div>
    <!-- 属性配置 -->
    <el-divider content-position="left">属性配置</el-divider>
    <div class="protype-config details-content">
      <el-row>
        <el-col :span="24" class="fr">
          <el-button type="primary" size="mini" @click="addDialogVisible = true">添加属性</el-button>
        </el-col>
      </el-row>
      <div class="content">
        <div class="table-container">
          <template>
            <el-table ref="table" :data="tabData" highlight-current-row>
              <el-table-column label="标识符" prop="attributeTag" width="200">
              </el-table-column>
              <el-table-column label="属性名称" prop="attributeName">
              </el-table-column>
              <el-table-column label="数据类型" prop="attributeType">
                <template slot-scope="scope">
                  {{codeHandle(scope.row.attributeType)}}
                </template>
              </el-table-column>
              <el-table-column label="操作类型" prop="type">
                <template slot-scope="scope">
                  {{'用户输入'}}
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
	        					<em class="el-icon-edit" @click="editHandle(scope.row)"></em>
	        				</el-tooltip>
	        				<el-tooltip class="item" effect="dark" v-if="scope.row.status == 0" content="删除" placement="top">
	        					<em class="el-icon-delete" @click="delAttributeHanle(scope.row)"></em>
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
    <!-- 添加属性 -->
    <el-dialog
      title="添加属性"
      :visible.sync="addDialogVisible"
      width="700px"
      top="15vh"
      class="dialog"
      :close-on-click-modal="false"
      @close="cancelClose('addForm')">
      <div class="content">
        <el-form :model="addForm" ref="addForm" :rules="addRules" label-width="100px">
          <el-form-item label="标识符" prop="attributeTag">
            <el-input size="mini" placeholder="请输入长度在2-20个由小写字母组成的标识符" v-model="addForm.attributeTag"></el-input>
          </el-form-item>
          <el-form-item label="属性名" prop="attributeName">
            <el-input size="mini" placeholder="请输入长度为2-20个任意组成的属性名称" v-model="addForm.attributeName"></el-input>
          </el-form-item>
          <el-form-item label="数据类型" prop="attributeType">
            <el-select size="mini" placeholder="请选择数据类型" v-model="addForm.attributeType">
              <el-option
                v-for="item in dataTypes"
                :key="item.dId"
                :value="item.dId"
                :label="item.dName"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="描述" class="mg10" label-width="90px" prop="attributeDesc">
            <el-input size="mini" type="textarea"  v-model="addForm.attributeDesc" :rows="2" placeholder="请输入描述内容" maxlength="30" show-word-limit></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelClose('addForm')">取消</el-button>
        <el-button type="primary" @click="addAttributeHandle('addForm')">确定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑属性 -->
    <el-dialog
      title="编辑属性"
      :visible.sync="editDialogVisible"
      width="700px"
      top="15vh"
      class="dialog"
      :close-on-click-modal="false"
      @close="cancelClose('editForm')">
      <div class="content">
        <el-form :model="editForm" ref="editForm" :rules="addRules" label-width="100px">
          <el-form-item label="标识符" prop="attributeTag">
            <el-input size="mini" placeholder="请输入标识符" :disabled="editForm.status != 0" v-model="editForm.attributeTag"></el-input>
          </el-form-item>
          <el-form-item label="属性名" prop="attributeName">
            <el-input size="mini" placeholder="请输入属性名" v-model="editForm.attributeName"></el-input>
          </el-form-item>
          <el-form-item label="数据类型" prop="attributeType" class="txtArea">
            <el-select size="mini" placeholder="请选择数据类型"  :disabled="editForm.status != 0" v-model="editForm.attributeType">
              <el-option
                v-for="item in dataTypes"
                :key="item.dId"
                :value="item.dId"
                :label="item.dName"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="描述" class="mg10" label-width="90px">
            <el-input size="mini" type="textarea"  v-model="editForm.attributeDesc" :rows="2" placeholder="请输入描述内容" maxlength="30" show-word-limit></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelClose('editForm')">取消</el-button>
        <el-button type="primary" @click="editDifineHandel('editForm')">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  addAttribute,
  editAttribute,
  attributeList,
  delAttribute
} from '../../../api/api'
import selectObj from '../../../labs/constant'
import qs from 'qs'
import {getDate} from '../../../labs/tools'
export default {
  data(){
    var tagValid = (rule,value,callback)=>{
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
      minHeight:null,
      addDialogVisible: false,
      editDialogVisible: false,
      dataDetails:{
        id:'',
        name:'',
        type:1,
        desc:''
      },
      dataTypes:null,
      tabData:[],
      addForm:{
        configId:null,
        attributeName:null,
        attributeTag:null,
        attributeType:null,
        attributeDesc:null
      },
      editForm:{
        id: null,
        status:0,
        configId:null,
        attributeName:null,
        attributeTag:null,
        attributeType:null,
        attributeDesc:null
      },
      addRules:{
        attributeTag:[
          { required: true, validator: tagValid, trigger:'blur' },
        ],
        attributeName:[
          { required: true, message: '请输入属性名称', trigger: 'blur' },
          { min: 2, max: 20, message: '请输入长度为2-20个任意组成的字符',trigger: 'blur'},
        ],
        attributeType:[
          { required: true, message: '请选择数据类型', trigger: 'change'}
        ],
        attributeDesc:[
          { required: false }
        ]
      },
      page:{
        total:10,
        pageIndex:1,
        pageSize:10,
        clientId:null,
        resourceDetail:null,
      },
    }
  },
  methods:{
    cancelClose(formName){
      /**
       * @description 取消弹出框关闭
       */
      switch(formName){
        case 'addForm':
          this.addDialogVisible = false;
          break;
        case 'editForm':
          this.editDialogVisible = false
      }
      this.$refs[formName].resetFields();
    },
    editCancelHandel(){
      this.editDialogVisible = false;
    },
    editHandle(data){
      this.editDialogVisible = true;
      this.editForm = JSON.parse(JSON.stringify(data));
    },
    editDifineHandel(formName){
      let param = {
        id:this.editForm.id,
        configId: this.editForm.configId,
        attributeName: this.editForm.attributeName,
        attributeTag: this.editForm.attributeTag,
        attributeType: this.editForm.attributeType,
        attributeDesc: this.editForm.attributeDesc
      };

      this.$refs[formName].validate(valid=>{
        if(valid){
          editAttribute(param).then(res=>{
            if(res.err_CODE === 0){
              this.editDialogVisible = false;
              this.$message.success('修改成功');
              this.$refs[formName].resetFields();
              this.getAttribute();
            }else{
              this.$message.error(res.err_MESSAGE);
            }
          })
        }
      })
    },
    preHandle(){
      this.$router.go(-1);
    },
    handleFormat(date){
      /**
       * @description 时间格式化
       */
      return getDate(new Date(date).getTime()/1000);
    },
    handleSizeChange(val){
      /**
       * @description 分页功能
       */
      this.page.pageSize = val;
      this.getAttribute();
    },
    handleCurrentChange(val){
      /**
       * description 分页功能
       */
      this.page.pageIndex = val;
      this.getAttribute();
    },
    getAttribute(){
      /**
       * @description 获取属性列表
       * @param {Number} configId 资源ID
       */
      let param = {
        configId : this.page.resourceDetail.id,
        pageSize: this.page.pageSize,
        pageIndex: this.page.pageIndex
      };
      attributeList(qs.stringify(param)).then(res=>{
        if(res.err_CODE === 0){
          res.data.list.length > 0 ? this.tabData = res.data.list : this.tabData = [];
          this.page.total = res.data.total;
        }else{
          this.$message.error(res.err_MESSAGE);
        }
      })

    },
    codeHandle(type){
      /**
       * @description 数据类型转换
       */
      let codeMsg = null;
      switch(type){
        case 1:
          codeMsg = "数值型";
          break;
        case 2:
          codeMsg = "字符串";
          break;
        case 3:
          codeMsg = "时间/日期";
          break;
      };
      return codeMsg;
    },
    addAttributeHandle(formName){
      /**
       * @description 添加属性
       * @param {Number} configId 资源ID
       * @param {String} attributeName 属性名称
       * @param {String} attributeTag 属性标识符
       * @param {Number} attributeType 数据类型
       * @param {Number} attributeDesc 描述
       */

      let param = {
        configId: this.page.resourceDetail.id,
        attributeName: this.addForm.attributeName,
        attributeTag: this.addForm.attributeTag,
        attributeType: this.addForm.attributeType,
        attributeDesc: this.addForm.attributeDesc
      };

      this.$refs[formName].validate(valid=>{
        if(valid){
          addAttribute(param).then(res=>{
            if(res.err_CODE === 0){
              this.$message.success('添加成功');
              this.addDialogVisible = false;
              this.$refs[formName].resetFields();
              this.getAttribute();
            }else{
              this.$message.error(res.err_MESSAGE);
            }
          })
        }else{
          return false;
        }
      })
    },
    delAttributeHanle(data){
      /**
       * @description 删除属性
       * @param {Array} idList 删除属性ID数组
       */
      this.$confirm("你确定要删除吗？删除后不可恢复",'提示',{
        confirmButtonText:'确定',
        cancelButtonText:'取消',
      }).then(() =>{
        // 确定删除
        let param = { idList : []};
        // 根据参数data判断传入的值类型是数组还是对象进行 删除参数的数值传递
        Array.isArray(data) ? param.idList = data.map((item)=>{return item.id}) : param.idList.push(data.id);
        delAttribute(qs.stringify(param,{ arrayFormat: 'repeat' })).then(res=>{
          if(res.err_CODE === 0){
            this.$message.success("删除成功");
            this.getAttribute();
          }else{
            this.$message.error(res.err_MESSAGE);
          }
        })
      }).catch(() =>{
        // 取消删除
      })
    },
    editAttributeHandle(){
      let param = {
        configId: this.page.resourceDetail.id,
        attributeName: '新增2',
        attributeTag: '333',
        attributeType: 1,
        attributeDesc: ''
      }

      editAttribute(param).then(res=>{
        if(res.err_CODE === 0){
          this.$message.success('修改成功')
        }else{
          this.$message.error(res.err_MESSAGE);
        }
      })
    }
  },
  mounted(){
    this.minHeight = window.innerHeight - 95;
    this.getAttribute();
  },
  created(){
    this.page.resourceDetail = JSON.parse(this.$route.params.param);
    this.dataTypes = selectObj.dataType;
  }
}
</script>

<style lang="scss">
  @import "../../../styles/application-manage/application-component.scss"
</style>
