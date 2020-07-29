// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en'
import axios from 'axios'
import './assets/style/element-variables.scss'
import './styles/common.scss'
import './assets/iconfont/iconfont.css'
import store from './store'

Vue.use(ElementUI)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})

/*
Vue.prototype.$eventHub= Vue.prototype.$eventHub || new Vue();
Vue.prototype.$eventHub.$on('routerChange',function(data){
  var from = data.from;
  var to = data.to;
  var isBackFromDetail = false;
  snapConfig.forEach(item=>{
    if(item.from === from.path && item.to === to.path){
      isBackFromDetail = true;
    }
  });
  //更新vuex中的状态
  store.state.isBackFromDetailPage = isBackFromDetail;
});
*/


