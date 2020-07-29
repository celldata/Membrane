<template>
  <div id="permission-detail" class="permission-detail" :style="{minHeight: minHeight + 'px'}">
    <template>
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="功能权限" name="first">
          <router-view> </router-view>
          <function-permission></function-permission>
        </el-tab-pane>
        <el-tab-pane label="数据权限" name="second">
          <data-permission></data-permission>
        </el-tab-pane>
      </el-tabs>
    </template>
  </div>
</template>


<script>
import DataPermission from './data-permission.vue'
import FunctionPermission from './function-permission'
export default {
  data(){
    return{
      minHeight:null,
      activeName:'second'
    }
  },
  components:{ DataPermission, FunctionPermission },
  methods:{
    handleClick(){

    }
  },
  mounted(){
    this.minHeight = window.innerHeight - 95;
  }
}
</script>


<style lang="scss">
  @import '../../../styles/application-manage/application-component.scss'
</style>
