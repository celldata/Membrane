/**
 * @description 项目中所用到的下拉框数据
 */
let selectObj = {
      authStatus: [{
          value: null,
          label: '全部'
        },
        {
          value: 0,
          label: '内部认证中心'
        },
        {
          value: 1,
          label: '外部认证中心',
          children: [{
            value: 0,
            label: 'CAS(暂无接通)'
            },
            {
              value: 1,
              label: 'LDAP'
            },
            {
              value:2,
              label:'OAUTH(暂无接通)'
            },
            {
              value: 3,
              label: 'SAML(暂无接通)'
            }
          ]
        }
      ], //应用管理-认证状态
      validMode: [{
          modeId: null,
          modeName: '全部'
        },
        {
          modeId: 0,
          modeName: '强安全模式'
        },
        {
          modeId: 1,
          modeName: '高性能模式'
        }
      ], //应用管理-验证方式
      functionType: [{
          fId: 1,
          fName: '查询类'
        },
        {
          fId: 0,
          fName: '编辑类'
        }
      ], //应用管理-功能类型
      resourceType: [
      {
        rId: 0,
        rName: '顶级资源'
      },
      {
        rId: 1,
        rName: '非顶级资源'
      }
      ],//数据权限-资源类型
      dataType:[
        {
          dId: 1,
          dName: '数值型',
          value: 'number'
        },
        {
          dId: 2,
          dName: '字符串',
          value: 'text'
        },
        {
          dId: 3,
          dName: '时间/日期',
          value: 'date'
        }
      ],//数据权限-属性-数据类型
}

export default selectObj;

