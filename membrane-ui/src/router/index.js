import Vue from 'vue'
import Router from 'vue-router'
import Main from '../views/main.vue'
import Login from '../views/login/login.vue'  //登陆页面
import UserManagement from '../views/user-manage/user-management.vue' //用户管理
import RoleManagement from '../views/role-manage/role-management.vue'  //角色管理
import ApplicationManagement from "../views/application-manage/index.vue" //应用管理
import AuthenticationManagement from "../views/authentication-manage/index.vue" //认证管理
import PermissionDetail from "../views/application-manage/permission-manage/permission-details.vue"// 权限管理详情
import dataPermissionDetail from "../views/application-manage/permission-manage/data-details.vue"//数据权限详情页

Vue.use(Router)

const whiteList = ['/']; // 不重定向白名单
const router = new Router({
    routes: [{
            path: '/user',
            name: 'home',
            component: Main,
            redirect: '/application',
            children: [
              {
                path:'/application',
                name:'应用管理',
                leaf: true,
                iconClass:'icon-yingyong',
                meta:{
                  active:'/application'
                },
                component:ApplicationManagement,
              },
              {
                path:'/permissionDetail/:clientName/:clientId',
                name:'权限管理',
                leaf:false,
                meta:{
                  active:'/application'
                },
                component:PermissionDetail
              },
              {
                path:'/dataDetails/:param',
                name:'资源详情',
                leaf:false,
                meta:{
                  active:'/application'
                },
                component: dataPermissionDetail
              },
              {
                    path: '/userManagement',
                    name: '用户管理',
                    iconClass: 'icon-yonghu_',
                    meta: {
                        active: '/userManagement'
                    },
                    leaf: true,
                    component: UserManagement,
                },
                {
                    path: '/roleManagement',
                    name: '角色管理',
                    iconClass: 'icon-jsgl',
                    meta: {
                        active: '/roleManagement'
                    },
                    leaf: true,
                    component: RoleManagement,
                },
                {
                  path:'/authentication',
                  name:'认证中心',
                  leaf:true,
                  iconClass:'icon-approve',
                  meta:{
                    active:'/authentication'
                  },
                  component:AuthenticationManagement
                }
            ]
        },
        {
            path: '/',
            name: 'login',
            component: Login,
        }
    ]
})

router.beforeEach((to, from, next) => {
    var token = window.localStorage.getItem("sso_token");
    let haveSSOAuthority = window.localStorage.getItem("haveSSOAuthority");
    if(whiteList.indexOf(to.path) !== -1){
		next()
	}else {
		if(token && haveSSOAuthority) {
			next();
		}else {
			next('/')
		}
	}
})

export default router
