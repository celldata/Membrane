import md5 from 'js-md5'

/**
 * @param {Number} timeStamp 传入的时间戳  该方法如果不传参数，则返回当前日期
 * 返回值为：xxxx.xx.xx
 */
export const getDate = (timeStamp) => {
	// console.log(timeStamp,'ooo')
	const d = timeStamp ? new Date(timeStamp*1000) : new Date();
	// console.log(d)
	const year = d.getFullYear()
	let month = d.getMonth() + 1;
	month = month<10 ? '0' + month : month
	let date = d.getDate()
	date = date<10 ? '0' + date : date
	let hours = d.getHours()
	hours = hours<10 ? '0' + hours : hours
	let minutes = d.getMinutes()
	minutes = minutes<10 ? '0' + minutes : minutes
	let seconds = d.getSeconds()
	seconds = seconds<10 ? '0' + seconds : seconds
	let resStr = ''
	resStr = year + '.' + month + '.' + date + ' ' + hours + ":" + minutes + ':' + seconds
	return resStr
}

/**
 * @param {Number} timeStamp 传入的时间戳  该方法如果不传参数，则返回当前是周几
 * 返回值为：星期x
 */
export const getWeek = (timeStamp) => {
	console.log(timeStamp,'ooo')
	const d = timeStamp ? new Date(timeStamp) : new Date();
	const week = d.getDay();
	let day;
	switch(week){
		case 0 : day = "星期天";
				break;
		case 1 : day = "星期一";
				break;
		case 2 : day = "星期二";
				break;
		case 3 : day = "星期三";
				break;
		case 4 : day = "星期四";
				break;
		case 5 : day = "星期五";
				break;
		case 6 : day = "星期六";
				break;
	}

	return day;
}

/**
 * 时间日期格式化
 * @param format
 * @returns {*}
*/

export const dateFormat = (dateObj, format) => {
	let date = {
		'M+': dateObj.getMonth() + 1,
		'd+': dateObj.getDate(),
		'h+': dateObj.getHours(),
		'm+': dateObj.getMinutes(),
		's+': dateObj.getSeconds(),
		'q+': Math.floor((dateObj.getMonth() + 3) / 3),
		'S+': dateObj.getMilliseconds()
	}
	if (/(y+)/i.test(format)) {
		format = format.replace(RegExp.$1, (dateObj.getFullYear() + '').substr(4 - RegExp.$1.length))
	}
	for (let k in date) {
		if (new RegExp('(' + k + ')').test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length === 1
				? date[k] : ('00' + date[k]).substr(('' + date[k]).length))
		}
	}
	return format
}
/**
 * formatMoney(s,type)
 * 功能：金额按千位逗号分隔
 * 参数：s，需要格式化的金额数值.
 * 参数：type,判断格式化后的金额是否需要小数位.
 * 返回：返回格式化后的数值字符串
*/
export const formatMoney = (s, type) => {
	if (/[^0-9\.]/.test(s))
         return "0.00";
     if (s == null || s == "null" || s == "")
         return "0.00";
     s = s.toString().replace(/^(\d*)$/, "$1.");
     s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
     s = s.replace(".", ",");
     var re = /(\d)(\d{3},)/;
     while (re.test(s))
         s = s.replace(re, "$1,$2");
     s = s.replace(/,(\d\d)$/, ".$1");
     if (type == 0) {
         var a = s.split(".");
         if (a[1] == "00") {
             s = a[0];
         }
     }
     return s;
}
/**
 * 返回数字
*/
export const removeFormatMoney = (s) => {
	 return parseFloat(s.replace(/[^\d\.-]/g, ""));
}

/**
 * 姓名正则验证
 */
export const fullNamePattern = (rule, value, callback) => {
  const reg = /^[\s]*$/;
  const reg2 = /.{2,49}$/;
  console.log(reg2.test(value),!value)
  if (reg.test(value) || !reg2.test(value) || !value) {
    return callback(new Error("2-50个字符"));
  } else {
    callback();
  }
}

/**
 * MD5加密
 */
export const resetSecret = (param)=>{
  let encrypt = (Math.random()*9000+1000).toString().split('.')[0];
  param = md5(param);
  param = `${param}${encrypt}`;
  return param;
}

/**
 * 函数节流
 */
export function throttle(fn,delay){
  let lastTime;
  let timer;
  var delay = delay || 200;

  return function(){
    let args = arguments;
    // 记录当前函数触发时间
    let nowTime = Date.now();
    if(lastTime &&　nowTime-lastTime < delay){
      clearTimeout(timer);
      timer = setTimeout(()=>{
        // 记录上一次函数触发时间
        lastTime = nowTime;
        // 修正this指向问题
        fn.apply(this,args)
      },delay)
    }else{
      lastTime = nowTime;
      fn.apply(this,args);
    }
  }
}

export const treeList = (arr, op) =>{
  //判断数组长度小于1就返回
  if(op == undefined || op == null){
    op = 'AND';
  }

  if (arr == null || arr == undefined || !(arr instanceof Array) || arr.length < 1) {
      return null;
  }

  if(arr.length == 1){
    if(arr[0].type == "query-builder-rule"){
      var r1 =  {"l":arr[0].query.rule,"o":arr[0].query.selectedOperator,"r":arr[0].query.value};
      return r1;
    }else{
      return treeList(arr[0].query.children, arr[0].query.logicalOperator);
    }
  }
  var mid = Math.floor(arr.length / 2);
  var left = arr.slice(0,mid);
  var right = arr.slice(mid);

  var tr = {
          // "type": (arr.slice(0, (arr.length - 1))).length == 0 ? (arr[0].operation == "match" ? "model" : "leaf") : 'logic',
          "l": treeList(left),
          "o": (arr.slice(0, (arr.length - 1))).length == 0 ?  op : (arr[0].type == 'query-builder-group' ? arr[0].query.logicalOperator : op),
          "r": treeList(right),
      };
      console.log(JSON.stringify(tr));
  return tr;
}




