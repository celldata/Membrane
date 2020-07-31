import Vue from 'vue'

const fence = Vue.directive('fence', { //自定义指令
  inserted: function (el, binding, vnode) {
    //是否全部权限
    if (!window.localStorage.getItem("fullAccess")) {
      //判断当前node是否有权限
      let fun = binding.value.functions.find(fun => {
        if (fun.functionName === binding.value.name) return fun;
      });
      //无权限则移除当前node
      if (!fun) el.parentNode && el.parentNode.removeChild(el);
    }
  }
});

export { fence };

