import axios from "axios";
import qs from "qs";
import router from '../router/index.js'

var instance = axios.create({});

instance.interceptors.request.use(function (config) {
  let url = config.url;
  // 判断请求方法是GET,对GET请求参数进行序列化
  if(config.method === 'get'){
    config.params = qs.parse(config.params);
  }
  // 除登录接口外，其他接口加上token校验
  if(url.indexOf('login') > -1){
    localStorage.setItem("sso_token","");
    config.headers.common['Authorization'] = "";
  }else{
    config.headers.common['Authorization'] = localStorage.getItem("sso_token");
  }
  config.headers['Content-Type'] = 'application/json;charset=UTF-8'
  return config;
}, function (err) {
  return Promise.reject(err);
});


instance.interceptors.response.use(function (res) {
  if (res.headers.Authorization) {
    localStorage.setItem('sso_token', res.headers.Authorization);
  }
  return res;
}, function (err) {
	var responseError = err;
  var statusCode = responseError.response.status;
  if(statusCode === 401){
    localStorage.removeItem('appId');
    localStorage.removeItem('isHavaSSOAuthority');
    localStorage.removeItem('sso_token');
    localStorage.removeItem('userName');
    localStorage.removeItem('userId');
  	router.push({ path: '/' })
  }else {
  		return err;
  }
});


export default instance;
