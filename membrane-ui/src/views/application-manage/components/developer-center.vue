<template>
<!-- 开发者中心 -->
  <div class="appComponent">
    <el-card>
      <div slot="header" class="clearfix">
        <span>开发者中心</span>
      </div>
      <div class="text item">
        <div class="content">
          开发介绍
        </div>
        <div class="bottom clearfix">
          <el-button type="text" class="button detail" @click="checkDetails">查看详情</el-button>
          <!-- <el-button type="text" class="button edit" @click="configVisible=true">认证配置</el-button> -->
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  props:['appDetail'],
  data(){
    return{

    }
  },
  methods:{
    checkDetails(){
      /**
       * @description 查看详情
       */
      this.$message.error("此功能暂无开发")
    }
  }
}
</script>

<style lang="scss">
  @import "../../../styles/application-manage/application-component.scss";
</style>
