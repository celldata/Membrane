import * as  Utils from './tools'
import { uploadFileToken } from '../api/api'

var OSS = require('ali-oss')

export default {

  /**
   * 创建随机字符串
   * @param num
   * @returns {string}
   */
  randomString(num) {
    let chars = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']
    let res = ''
    for (let i = 0; i < num; i++) {
      var id = Math.ceil(Math.random() * 35)
      res += chars[id]
    }
    return res
  },

  /**
   * 创建oss客户端对象
   * @returns {*}
   */
  createOssClient() {
    return new Promise((resolve, reject) => {
      uploadFileToken().then(res => {
        console.log(res);
        let result = res.data;
        let client = new OSS({
          region: 'oss-cn-beijing',
          accessKeyId: result['Access Key Id'],
          accessKeySecret: result['Access Key Secret'],
          bucket:result['bucketName'],
          stsToken:result['Security Token'],
        })
        resolve(client)
      })
    })
  },
  /**
   * 文件上传
   * @param option 参考csdn: https://blog.csdn.net/qq_27626333/article/details/81463139
   */
  ossUploadFile(option) {
    let file = option.file;
    const self = this
    return new Promise((resolve, reject) => {
      let date = Utils.dateFormat(new Date(), 'yyyyMMdd'); // 当前时间
      let dateTime = Utils.dateFormat(new Date(), 'yyyyMMddhhmmss'); // 当前时间
      let randomStr = self.randomString(4); //  4位随机字符串
      let extensionName = file.name.substr(file.name.indexOf('.')); // 文件扩展名
      let fileName = 'image/' + date + '/' + dateTime + '_' + randomStr + extensionName; // 文件名字（相对于根目录的路径 + 文件名）
      // 执行上传
      self.createOssClient().then(client => {
        // 异步上传,返回数据
        resolve({
          fileName: file.name,
          fileUrl: fileName
        })
        // 上传处理
        // 直接上传文件
        client.put(fileName, file, {
          progress: function (p) {
            let e = {};
            e.percent = Math.floor(p * 100);
            option.onProgress(e);
          }
        }).then((val) => {
          if (val.res.statusCode === 200) {
            option.onSuccess(val);
            return val
          } else {
            option.onError('上传失败');
          }
        }, err => {
          option.onError('上传失败');
          reject(err);
        })

        // 分片上传文件
        // client.multipartUpload(fileName, file, {
        //   progress: async function (p) {
        //     let e = {};
        //     e.percent = Math.floor(p * 100);
        //     option.onProgress(e);
        //   }
        // })
      })
    })
  }
}
