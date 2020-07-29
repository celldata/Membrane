<template>
<!-- 认证源详情 -->
  <el-dialog
    title="认证源详情"
    :visible.sync="detailVisible"
    width="846px"
    :close-on-click-modal="false"
    @close="cancelHandle"
  >
  <div class="app-content" v-if="details">
        <template>
          <el-tabs v-model="activeName" type="card">
            <el-tab-pane label="认证源配置" name="first">
              <el-scrollbar id="scrollbar">
                <div class="tab-box detail-list">
                  <el-divider>基本信息</el-divider>
                    <ul>
                      <li>
                        <span :title="details.name && details.name.length > 10 ? details.name : null"><i class="label">认证源名称：</i>{{details.name && details.name.length > 10 ? details.name.substr(0,9)+"..." : details.name}}</span>
                        <span><i class="label">认证源类型：{{details.type === 1 ? 'LDAP' : '其他'}}</i></span>
                      </li>
                      <li>
                        <span :title="details.createName && details.createName.length > 10 ? details.createName : null"><i class="label">创建人：</i>{{details.createName && details.createName.length > 10 ? details.createName.substr(0,9)+"..." : details.createName}}</span>
                        <span><i class="label">创建时间：{{formatHandle(details.createTime)}}</i></span>
                      </li>
                      <li class="last">
                        <span><i class="label">描述：{{details.desc}}</i></span>
                      </li>
                    </ul>
                  <el-divider>服务器配置信息</el-divider>
                    <ul>
                      <li>
                        <span :title="details.parameters.url && details.parameters.url.length > 20 ? details.parameters.url : null"><i class="label">LDAP URL：</i>{{details.parameters.url && details.parameters.url.length > 20 ? details.parameters.url.substr(0,19)+"..." : details.parameters.url}}</span>
                        <span :title="details.parameters.base && details.parameters.base.length > 20 ? details.parameters.base : null"><i class="label">BASE：</i>{{details.parameters.base == null ? '暂无' : details.parameters.base.length > 20 ? details.parameters.base.substr(0,19)+"..." : details.parameters.base}}</span>
                      </li>
                      <li>
                        <span :title="details.parameters.userDn && details.parameters.userDn.length > 20 ? details.parameters.userDn : null"><i class="label">userDn：</i>{{details.parameters.userDn && details.parameters.userDn.length > 20 ? details.parameters.userDn.substr(0,19)+"..." : details.parameters.userDn}}</span>
                        <span :title="details.parameters.pwd && details.parameters.pwd.length > 20 ? details.parameters.pwd : null"><i class="label">pwd：</i>{{details.parameters.pwd && details.parameters.pwd.length > 20 ? details.parameters.pwd.substr(0,19)+"..." : details.parameters.pwd}}</span>
                      </li>
                      <li class="last">
                        <span><i class="label">过滤条件：</i>{{details.parameters.filter}}</span>
                      </li>
                    </ul>
                  <el-divider v-if="details.parameters.sycUser">LDAP数据同步状态</el-divider>
                    <ul v-if="details.parameters.sycUser">
                      <li>
                        <span><i class="label">LDAP同步数据源至权限系统</i>{{details.parameters.sycUser == 0 ? '否' : '是'}}</span>
                      </li>
                      <li v-if="details.parameters.sycUser != 0">
                        <span><i class="label">LDAP同步数据是否启用定时任务：</i>{{details.parameters.sycSche == 0 ? '否' : '是'}}</span>
                        <span v-if="details.parameters.sycSche != 0"><i class="label">定时任务间隔时间：</i>{{`${details.parameters.sycFixed}小时`}}</span>
                      </li>
                    </ul>
                </div>
              </el-scrollbar>
            </el-tab-pane>
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
    <el-button type="primary" @click="detailVisible = false">关闭</el-button>
  </span>
  </el-dialog>
</template>

<script>
import { getDate } from '../../../labs/tools'
export default {
  prop:['authDetail'],
  data(){
    return{
      detailVisible:false,
      activeName:'first',
      details:null,
    }
  },
  methods:{
    cancelHandle(){
      /**
       * @description 新增认证源弹出框取消
       */
      this.$emit("closeHandle",{name:'detail',value:false});
    },
    closeHandle(){
      /**
       * @description 新增认证源
       */

      this.$emit("closeHandle",{name:'detail',value:false})
    },
    formatHandle(date){
      /**
       * @description 时间转换器
       */
      return getDate(new Date(date).getTime()/1000)
    },
  },
  mounted(){
  }
}
</script>

<style lang="scss">

</style>
