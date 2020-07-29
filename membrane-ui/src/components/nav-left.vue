<template>
	<el-menu
      :default-active="$route.meta.active"
      class="el-menu-vertical-demo nav-left-box"
      background-color="#1F1F2B"
			unique-opened
      text-color="#ADADAD"
      active-text-color="#fff" router>

    <template v-for="(item,i) in $router.options.routes[0].children">
		  <el-submenu :index="item.path"  v-if="item.children" :key="i">
			   <template slot="title">
				   <i class="common-sty iconfont" :class="item.iconClass"></i>
					 <span>{{item.name}}</span>
				 </template>
				 <el-menu-item v-for="(jtem,index) in item.children" :index="jtem.path" v-if="item.children[index].leaf" :key="index">{{jtem.name}}</el-menu-item>
			</el-submenu>

			<el-menu-item :index="item.path" :key="i" v-else-if="item.leaf">
				<i class="common-sty iconfont" :class="item.iconClass"></i>
				<span slot="title">{{item.name}}</span>
			</el-menu-item>
		</template>
    </el-menu>
</template>
<script>
	export default {
		name:'navLeft',
		data(){
			return {

			}
		},
		methods: {
		},
		mounted(){
			// console.log(this.$router,this.$route.path,'this.$route.path')
		}
	}
</script>
<style>
	.el-menu-item.is-active{
		background:#464651 !important;
	}
	.nav-left-box .el-menu-item,.nav-left-box .el-submenu__title{
		  height: 44px;
    	line-height: 44px;
    	position: relative;
    	white-space: nowrap;
      list-style: none;
      font-size:12px;
  }
  .common-sty{
    margin-right:3px;
  }
  .fontSiz{
    font-size:18px;
  }
</style>

