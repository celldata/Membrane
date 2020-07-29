# Membrane 权限管理系统前端开发说明

#### 介绍

该项目是权限管理系统前端代码项目，由VUE开发。

#### 目录结构
```

│  .babelrc
│  .editorconfig
│  .gitignore
│  .postcssrc.js
│  .project
│  index.html
│  package-lock.json
│  package.json
│  README.md
│  
├─.vscode
│      settings.json
│      
├─build
│      build.js
│      check-versions.js
│      utils.js
│      vue-loader.conf.js
│      webpack.base.conf.js
│      webpack.dev.conf.js
│      webpack.prod.conf.js
│      
├─config
│      dev.env.js
│      index.js
│      prod.env.js
│      
├─node_modules
├─src
│  │  App.vue
│  │  main.js
│  │  
│  ├─api
│  │      api.js
│  │      config.js
│  │      http.js
│  │      
│  ├─assets
│  │  ├─iconfont
│  │  │      iconfont.css
│  │  │      iconfont.eot
│  │  │      iconfont.svg
│  │  │      iconfont.ttf
│  │  │      iconfont.woff
│  │  │      
│  │  ├─image
│  │  │      de.png
│  │  │      login-bg.png
│  │  │      
│  │  └─style
│  │          element-variables.scss
│  │          
│  ├─components
│  │      nav-left.vue
│  │      
│  ├─labs
│  │      constant.js
│  │      deepClone.js
│  │      tools.js
│  │      upload.js
│  │      
│  ├─router
│  │      index.js
│  │      
│  ├─store
│  │      actions.js
│  │      getters.js
│  │      index.js
│  │      mutations.js
│  │      
│  ├─styles
│  │  │  base.scss
│  │  │  common.scss
│  │  │  login.scss
│  │  │  main.scss
│  │  │  
│  │  ├─application-manage
│  │  │      application-component.scss
│  │  │      index.scss
│  │  │      
│  │  ├─authentication-manage
│  │  │      auth-component.scss
│  │  │      index.scss
│  │  │      
│  │  ├─role-manage
│  │  │      role-management.scss
│  │  │      
│  │  └─user-manage
│  │          user-management.scss
│  │          
│  └─views
│      │  main.vue
│      │  
│      ├─application-manage  应用管理
│      │  │  index.vue
│      │  │  
│      │  ├─components 
│      │  │      authentication-config.vue 认证配置
│      │  │      developer-center.vue 开发中心
│      │  │      information-manage.vue 应用信息
│      │  │      
│      │  └─permission-manage  权限管理
│      │          data-details.vue
│      │          data-permission.vue
│      │          function-permission.vue
│      │          permission-details.vue
│      │          permission-manage.vue
│      │          
│      ├─authentication-manage 认证中心
│      │  │  index.vue
│      │  │  
│      │  └─components
│      │          add-auth-source.vue
│      │          auth-source-details.vue
│      │          edit-auth-source.vue
│      │          
│      ├─login  登录
│      │      login.vue
│      │      
│      ├─query-builder querybuilder组件
│      │  │  index.vue
│      │  │  VueQueryBuilder.vue
│      │  │  
│      │  └─components
│      │          QueryBuilderGroup.vue
│      │          QueryBuilderRule.vue
│      │          
│      ├─role-manage 角色管理
│      │      role-management.vue
│      │      
│      └─user-manage 用户管理
│              user-management.vue
│              
└─static

```




#### 安装教程

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).

#### 使用说明

以下步骤是对接业务系统中所需步骤及核心代码介绍：

1. 自定义指令封装

```
在main.js中引入封装好的fence.js文件：
import { fence } from "./directive/fence";
```

2. 路由拦截器

```
在在router/index.js中添加路由拦截器：
router.beforeEach((to, from, next) => { //路由拦截器
  if (to.path === '/noAccess') next();
	window.localStorage.setItem("toPath",to.path);
	window.localStorage.setItem("toName",to.name);
    let token = window.localStorage.getItem("token");
    if(!token){
      let name = 'tokenKey';
      let tokenKey = decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null
      //如果没有token，需要判断url中是否存在token key，如果有的话去sso中换回真实的token
      if(tokenKey){
        obtainToken({tokenKey: tokenKey})
          .then(res => {
            if(res.err_CODE === 0){
              let menu = res.data.menu;
              let newToken = res.data.authorityToken;
              //token存在时对后台返回数据进行base64解码存储
              if(newToken){
                let payload = jwt.decode(newToken);
                window.localStorage.setItem('token', newToken);
                window.localStorage.setItem('userId', payload.user_id);
                window.localStorage.setItem('userName', payload.userName);
                window.localStorage.setItem('fullAccess', res.data.allData ? 'fullAccess' : '');
                window.localStorage.setItem('menu', menu ? Base64.decode(menu) : null);
              }
            }
            checkTo(to, next);
            to.params.functions = getFunctions(to.name);
            next();
          });
      } else {
        next();
      }
    } else {
      checkTo(to, next);
      to.params.functions = getFunctions(to.name);
      next();
    }
});
```



3. 权限设置

```
3.1 渲染前标记菜单权限
	beforeMount(){
      //是否全部权限
      this.fullAccess = window.localStorage.getItem("fullAccess");
      if (!this.fullAccess) {
        //获取菜单权限
        this.menuJson = JSON.parse(window.localStorage.getItem("menu"));
        //标记路由权限
        if (this.menuJson) this.markRouter(this.$router.options.routes[0].children);
      }
		}
3.2 根据菜单标记权限
    <template v-for="(item, i) in $router.options.routes[0].children">
      <el-submenu :index="item.path" v-if="fullAccess || item.available" :key="i">
        <template slot="title">
          <span>{{item.name}}</span>
        </template>
        <el-menu-item
          v-if="fullAccess || item.children[index].available"
          v-for="(subItem,index) in item.children"
          :index="subItem.path"
          :key="index"
        >{{subItem.name}}</el-menu-item>
      </el-submenu>
      <el-menu-item :index="item.path" :key="i" v-else-if="fullAccess || item.available">
        <span slot="title">{{item.name}}</span>
      </el-menu-item>
    </template>
3.3 具体按钮权限设置
      <el-col :md="12" :sm="24">
        <el-button
          v-fence="{name: 'button-1.1.1', functions: $route.params.functions}"
        >button-1.1.1</el-button>
      </el-col>
      <el-col :md="12" :sm="24">
        <el-button
          v-fence="{name: 'button-1.1.2', functions: $route.params.functions}"
        >button-1.1.2</el-button>
      </el-col>
```



