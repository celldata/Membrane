权限管理API设计

## 1 用户管理

### 1.1新增用户

接口描述：新增用户

请求方式：POST

请求地址：/api/user/add

请求参数：

|  参数名   | 参数类型 |  参数解释  |
| :-------: | :------: | :--------: |
| userName  |  String  |   用户名   |
| passWord  |  String  |    密码    |
| fullName  |  String  |    姓名    |
| telephone |  String  |   手机号   |
|   email   |  String  |    邮箱    |
| clientId  | Integer  |   平台id   |
|  roleIds  | Integer  | 平台角色id |

请求示例：

``` java
{
  "email": "string",
  "fullName": "string",
  "id": 0,
  "passWord": "string",
  "roles": [
    {
      "clientId": 0,
      "roleIds": [
        0
      ]
    }
  ],
  "telephone": "string",
  "userName": "string"
}
```

响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

``` java
{
  "data": {},
  "err_CODE": 0,
  "err_MESSAGE": "新增成功"
}
```



### 1.2角色下拉框，用户状态下拉框

接口描述：角色下拉框，用户状态下拉框

请求方式：Get

请求地址：/api/user/fetch

请求参数：无

请求示例：/api/user/fetch

响应参数：

|      参数名       | 参数类型 |    参数解释    |
| :---------------: | :------: | :------------: |
|       data        |  String  |    返回数据    |
|     err_CODE      | Integer  |     状态码     |
|    err_MESSAGE    |  String  |    返回信息    |
|  statusList.name  |  String  |  用户状态信息  |
|  statusList.code  | Integer  | 用户状态信息码 |
|  roleList.value   | Integer  |     角色值     |
|  roleList.label   |  String  |    角色名称    |
| roleList.children |          |   下一级角色   |

响应示例：

``` java
{
  "err_CODE": 0,
  "err_MESSAGE": "查询成功！",
  "data": {
    "statusList": [
      {
        "name": "已启用",
        "code": 1
      },
      {
        "name": "已禁用",
        "code": 2
      }
    ],
    "roleList": [
      {
        "value": 1,
        "label": "权限管理系统",
        "children": [
          {
            "value": 7,
            "label": "权限管理员"
          }
        ]
      }
    ]
  }
}
```



### 1.3用户列表

接口描述：根据条件查询用户列表

请求方式：Get

请求地址：/api/user/list

请求参数：

|   参数名   | 参数类型 |    参数解释    |
| :--------: | :------: | :------------: |
|   roleId   | Integer  |     角色id     |
| statusCode | Integer  |    状态code    |
| searchKey  |  String  | 模糊查询关键字 |
|  pageSize  | Integer  |     页大小     |
| pageIndex  | Integer  |     当前页     |
|  clientId  | Integer  |     应用ID     |

请求示例：/api/user/fetchUserInfoList?roleId=1&statusCode=1&searchKey=“刘”&pageSize=10&pageIndex=1&clientId=6

响应参数：

|   参数名    | 参数类型 |  参数解释   |
| :---------: | :------: | :---------: |
|    data     |  String  |  返回数据   |
|  err_CODE   | Integer  |   状态码    |
| err_MESSAGE |  String  |  返回信息   |
|   pageNum   | Integer  |   当前页    |
|  pageSize   | Integer  |   页大小    |
|     id      | Integer  |   用户id    |
|  userName   |  String  |   用户名    |
|  fullName   |  String  |    姓名     |
|  telephone  |  String  |   手机号    |
|    email    |  String  |    邮箱     |
|    role     | Integer  |    角色     |
| updateTime  |   Date   |  更新时间   |
|   updater   |  String  |   更新者    |
|    code     | Integer  |  状态code   |
|    name     |  String  |  状态名称   |
|  clientId   | Integer  |   应用ID    |
|   roleIds   |   List   | 角色ID List |

响应示例：

``` java
{
	"err_CODE": 0,
	"err_MESSAGE": "查询成功！",
	"data": {
		"pageNum": 1,
		"pageSize": 10,
		"list": [{
        "id": 172,
        "userName": "jjjj",
        "fullName": "kjjjjj",
        "telephone": "15412345678",
        "email": "1011754532@163.com",
        "role": "测试家里蹲",
        "updateTime": "2020.06.01 16:52:36",
        "updater": "jw",
        "status": {
          "code": 1,
          "name": "已启用"
        },
        "selectedRole": [
          {
            "clientId": 6,
            "roleIds": [
              19
            ]
          }
        ]
      }]
	}
}
```



### 1.4忘记密码

接口描述：忘记密码，可以通过邮箱或者手机号重置密码

请求方式：Post

请求地址：/api/user/forget

请求参数：

|    参数名    | 参数类型 |               参数解释                |
| :----------: | :------: | :-----------------------------------: |
|   userName   |  String  |                用户名                 |
|   passWord   |  String  |                 密码                  |
| verification |  String  |                验证码                 |
|     flag     | Integer  | 验证方式 0代表邮箱验证，1代表手机验证 |

请求示例：

``` java
{
  "flag": 0,
  "passWord": "string",
  "userName": "string",
  "verification": "string"
}
```



响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "data": {},
  "err_CODE": 0,
  "err_MESSAGE": "密码已修改"
}
```



### 1.5修改重置密码

接口描述：管理员在权限平台修改，或者重置密码

请求方式：Post

请求地址：/api/user/reset

请求参数：

|   参数名    | 参数类型 |           参数解释           |
| :---------: | :------: | :--------------------------: |
|   userId    | Integer  |            用户id            |
| oldPassword |  String  |           原始密码           |
| newPassword |  String  |            新密码            |
|    appId    |  String  |            appId             |
|    flag     | Integer  | 0代表重置密码，1代表修改密码 |

请求示例：

``` java
{
  "appId": "string",
  "flag": 0,
  "newPassword": "string",
  "oldPassword": "string",
  "userId": 0
}
```



响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

``` java
{
  "data": {},
  "err_CODE": 0,
  "err_MESSAGE": "密码已重置"
}
```

### 1.6发送验证码

接口描述：忘记密码，通过邮箱或者手机号发送验证码

请求方式：Get

请求地址：/api/user/send

请求参数：

|  参数名  | 参数类型 |                 参数解释                 |
| :------: | :------: | :--------------------------------------: |
| userName |  String  |                  用户名                  |
|   flag   | Integer  | 0代表发送邮箱验证码，1代表发送手机验证码 |

请求示例：/api/user/send?userName="admin"&flag=1

响应参数：

|   参数名    | 参数类型 |           参数解释           |
| :---------: | :------: | :--------------------------: |
|    data     |  String  | 返回已发送隐藏邮箱或者手机号 |
|  err_CODE   | Integer  |            状态码            |
| err_MESSAGE |  String  |           返回信息           |

响应示例：

``` java
{
  "data": "181****1111",
  "err_CODE": 0,
  "err_MESSAGE": "验证码已发送"
}
```



### 1.7编辑用户,修改用户状态

接口描述：编辑用户,修改用户状态

请求方式：Post

请求地址：/api/user/update

请求参数：

|  参数名   | 参数类型 |             参数解释              |
| :-------: | :------: | :-------------------------------: |
|    id     | Integer  |              用户id               |
| userName  |  String  |              用户名               |
| fullName  |  String  |               姓名                |
| telephone |  String  |              手机号               |
|   email   |  String  |               邮箱                |
|   flag    | Integer  | 0 代表编辑用户，1代表改变用户状态 |
|  status   | Integer  |      状态码: 1-启用; 2-禁用       |
| clientId  | Integer  |              平台id               |
|  roleIds  | Integer  |          平台角色id列表           |

请求示例：

```java
{
  "email": "string",
  "flag": 0,
  "fullName": "string",
  "id": 0,
  "roles": [
    {
      "clientId": 0,
      "roleIds": [
        0
      ]
    }
  ],
  "status": 0,
  "telephone": "string",
  "userName": "string"
}
```



响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

``` java
{
  "data": {},
  "err_CODE": 0,
  "err_MESSAGE": "启用成功"
}
```



## 2 登录管理

### 2.1登录

接口描述：登录

请求方式：Post

请求地址：/api/token/login

请求参数：

|  参数名  | 参数类型 |    参数解释     |
| :------: | :------: | :-------------: |
| userName |  String  |     用户名      |
| password |  String  |      密码       |
|  appId   |  String  |   业务平台ID    |
|   url    |  String  | 登录成功跳转URL |

请求示例：

``` java
{
  "appId": "string",
  "password": "string",
  "url": "string",
  "userName": "string"
}
```



响应参数：

|       参数名       | 参数类型 |                           参数解释                           |
| :----------------: | :------: | :----------------------------------------------------------: |
|        data        |  String  |                           返回数据                           |
|      err_CODE      | Integer  |                            状态码                            |
|    err_MESSAGE     |  String  |                           返回信息                           |
|       token        |  String  |                        权限平台token                         |
|      tokenKey      |  String  | 返回给各个平台的tokenKey  需要通过tokenKey查询 平台token（包括权限） |
|        url         |  String  |                     登陆成功后跳转的uri                      |
|    userInfo.id     | Integer  |                            用户ID                            |
| userInfo.username  |  String  |                            用户名                            |
|  isHavaAuthority   | Integer  |                   是否拥有登陆该平台的权限                   |
| isHavaSSOAuthority | Integer  |                  是否拥有登陆权限平台的权限                  |

响应示例：

``` java
{
  "err_CODE": 0,
  "err_MESSAGE": "登录成功",
  "data": {
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY3RpdmVfdGltZSI6MTgwMCwidG9rZW5faWQiOiIzNzE3NmRhNjlmNzQwMDBjOTczZmM5MjgxOTM1MmQ2N2MyZmQzZjk3YzIxODliZWZiMmM4MjI4ZDM0OTE4ODczIiwidXNlcl9pZCI6MTcxLCJjcmVhdGVfZGF0ZSI6MTU5MDQ2MDIzNzc4OH0.W2ANJd-NybsNSneaAlLuJ934neLQWmhV7-fCg7zR3MA",
    "tokenKey": null,
    "url": "string",
    "userInfo": {
      "id": 171,
      "username": "jw"
    },
    "isHavaAuthority": 1,
    "isHavaSSOAuthority": null
  }
}
```



### 2.2退出登录

接口描述：退出登录

请求方式：Post

请求地址：/api/token/logout

请求参数：

| 参数名 | 参数类型 |   参数解释    |
| :----: | :------: | :-----------: |
| appId  |  String  |  业务平台ID   |
| token  |  String  | 权限平台token |

请求示例：

```java
{
  "appId": "string",
  "token": "string"
}
```



响应参数：

|  参数名  | 参数类型 |    参数解释     |
| :------: | :------: | :-------------: |
| loginUrl |  String  | 退出登录跳转Url |



响应示例：

```java
{
  "loginUrl": "127.0.0.1"
}
```



### 2.3根据tokenKey换取业务平台权限token

接口描述：通过登录SSO返回的tokenKey换取业务平台权限token

请求方式：get

请求地址：/api/token/exchange

请求参数：

|  参数名  | 参数类型 | 参数解释 |
| :------: | :------: | :------: |
| tokenKey |  String  | tokenKey |

请求示例：/api/token/exchange?tokenKey=8536826659f94ff18ca1e5d09a47e96d

响应参数：



|     参数名     | 参数类型 |   参数解释    |
| :------------: | :------: | :-----------: |
|  err_MESSAGE   |  String  |   返回信息    |
|    err_CODE    | Integer  |    状态码     |
|      data      |  String  |   返回信息    |
| authorityToken |  String  | 业务平台token |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": {
    "authorityToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBsaWNhdGlvbl9uYW1lIjoibWVtYnJhbmUiLCJ1c2VyX2lkIjoxNzEsInVzZXJfbmFtZSI6Imp3IiwiaXNzdWVfdGltZSI6MTU5MTkzMTczNjU5NywiYXBwbGljYXRpb25faWQiOiIyMjIifQ.nQN1f8CRByxflxY2AUSjOoTJYISQwmEfGoa91aMqU2M",
    "authority": null,
    "menu": null
  }
}
```

### 2.4根据AppId 和 ssoToken获取业务平台tokenKey

接口描述：根据AppId 和 ssoToken获取业务平台tokenKey

请求方式：Post

请求地址：/api/token/getTokenKey

请求参数：

|  参数名  | 参数类型 | 参数解释 |
| :------: | :------: | :------: |
| authorization |  String  | SSO token |
| appId |  String  | appId |

请求示例：

```java
{
  "appId": "222",
  "authorization": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBsaWNhdGlvbl9uYW1lIjoibWVtYnJhbmUiLCJ1c2VyX2lkIjoxNzEsInVzZXJfbmFtZSI6Imp3IiwiaXNzdWVfdGltZSI6MTU5MjY2Nzc2NzM2OX0.YaqbLyLyFI8_DZgT2mHtiUoL1CywHcjUiJZCNoNwG3M"
}
```


响应参数：



|     参数名     | 参数类型 |   参数解释    |
| :------------: | :------: | :-----------: |
|  err_MESSAGE   |  String  |   返回信息    |
|    err_CODE    | Integer  |    状态码     |
|      data      |  String  |   返回信息    |
| tokenKey |  String  | 业务平台tokenKey |
| haveAccess | boolean | 是否有跳转其他平台的权限 true为有 false为没有，没有权限时tokenKey为空 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": {
    "tokenKey": null,
    "haveAccess": false
  }
}
```

## 3 角色管理

### 3.1新增角色

接口描述：新增角色

请求方式：Post

请求地址：/api/role/add

请求参数：

|        参数名        |   参数类型    |     参数解释     |
| :------------------: | :-----------: | :--------------: |
|       roleName       |    String     |     角色名称     |
|       clientId       |    Integer    |     所属平台     |
|   allDataAuthority   |    Boolean    | 是否全部数据权限 |
| allFunctionAuthority |    Boolean    | 是否全部功能权限 |
| functionAuthorityIds | List<Integer> |   功能权限ids    |
|   dataAuthorityIds   | List<Integer> |   数据权限ids    |

请求示例：

```java
{
  "allDataAuthority": true,
  "allFunctionAuthority": true,
  "clientId": 0,
  "dataAuthorityIds": [
    0
  ],
  "functionAuthorityIds": [
    0
  ],
  "roleName": "string"
}
```



响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
| err_MESSAGE |  String  | 返回信息 |
|  err_CODE   | Integer  |  状态码  |
|    data     |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "新增成功",
  "data": null
}
```



### 3.2复制角色

接口描述：复制角色

请求方式：Get

请求地址：/api/role/copy

请求参数：

|  参数名  | 参数类型 |  参数解释  |
| :------: | :------: | :--------: |
|  roleId  | Integer  | 复制角色ID |
| roleName |  String  |   角色名   |

请求示例：/api/role/copy?roleId=7&roleName='小明'

响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "复制成功！",
  "data": null
}
```



### 3.3查询角色详情

接口描述：查询角色详情

请求方式：Get

请求地址：/api/role/fetch

请求参数：

| 参数名 | 参数类型 | 参数解释 |
| :----: | :------: | :------: |
| roleId | Integer  |  角色ID  |

请求示例：/api/role/fetch?roleId=7

响应参数：

|      参数名       | 参数类型 |   参数解释   |
| :---------------: | :------: | :----------: |
|       data        |  String  |   返回数据   |
|     err_CODE      | Integer  |    状态码    |
|    err_MESSAGE    |  String  |   返回信息   |
|      roleId       | Integer  |    角色id    |
|     roleName      |  String  |   角色名称   |
|     clientId      | Integer  |    平台id    |
|    clientName     |  String  |   平台名称   |
|   dataAuthority   |   List   | 数据权限集合 |
| functionAuthority |   List   | 功能权限集合 |
| functionName |   String   | 功能名称 |
| functionId |   Integer   | 功能ID |
| allFunctionAuthority |   Boolean   | 是否为全部权限 |
| haveAuthority |   List   | 已有功能 |

响应示例：

``` java
{
	"err_CODE": 0,
	"err_MESSAGE": "查询成功！",
	"data": {
		"roleId": 19,
		"roleName": "测试家里蹲",
		"clientId": 6,
		"clientName": "业务平台",
		"authorityBean": null,
		"functionAuthorityBean": {
			"allFunctionAuthority": false,
			"functionAuthority": [{
				"moduleId": 16,
				"clientId": 6,
				"moduleName": "用户管理1212",
				"functionList": [{
						"functionId": 22,
						"clientId": 6,
						"functionName": "新增用户11",
						"apiType": 0,
						"apiUrlList": []
					},
					{
						"functionId": 21,
						"clientId": 6,
						"functionName": "新增用户3",
						"apiType": 0,
						"apiUrlList": [
							"api/user/add"
						]
					}
				]
			}],
			"haveAuthority": [
				22,
				21
			]
		}
	}
}
```

### 3.4查询角色分页

接口描述：查询角色分页

请求方式：Get

请求地址：/api/role/list

请求参数：

|  参数名   | 参数类型 | 参数解释 |
| :-------: | :------: | :------: |
| clientId  | Integer  |  平台id  |
| pageIndex | Integer  |   页号   |
| pageSize  | Integer  |  页大小  |

请求示例：/api/role/list?clientId=6&pageIndex=1&PageSize=10

响应参数：

|      参数名       | 参数类型 | 参数解释 |
| :---------------: | :------: | :------: |
|       data        |  String  | 返回数据 |
|     err_CODE      | Integer  |  状态码  |
|    err_MESSAGE    |  String  | 返回信息 |
|      pageNum      | Integer  |  当前页  |
|     pageSize      | Integer  |  页大小  |
|      roleId       | Integer  |  角色id  |
|     roleName      |  String  | 角色名称 |
|    clinetName     |  String  | 平台名称 |
|     clientId      | Integer  |  平台id  |
|   dataAuthority   |  String  | 数据权限 |
| functionAuthority |  String  | 功能权限 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "size": 8,
    "orderBy": null,
    "startRow": 1,
    "endRow": 10,
    "total": 8,
    "pages": 1,
    "list": [
      {
        "roleId": 19,
        "roleName": "测试家里蹲",
        "clinetName": "业务平台",
        "clientId": 6,
        "dataAuthority": null,
        "functionAuthority": ""
      },
      {
        "roleId": 9,
        "roleName": "权限管理员1",
        "clinetName": "权限管理系统",
        "clientId": 1,
        "dataAuthority": null,
        "functionAuthority": "全部权限"
      },
      {
        "roleId": 10,
        "roleName": "权限管理员2",
        "clinetName": "权限管理系统",
        "clientId": 1,
        "dataAuthority": null,
        "functionAuthority": "全部权限"
      },
      {
        "roleId": 11,
        "roleName": "权限管理员3",
        "clinetName": "权限管理系统",
        "clientId": 1,
        "dataAuthority": null,
        "functionAuthority": "全部权限"
      },
      {
        "roleId": 13,
        "roleName": "权限管理员5",
        "clinetName": "权限管理系统",
        "clientId": 1,
        "dataAuthority": null,
        "functionAuthority": "全部权限"
      },
      {
        "roleId": 16,
        "roleName": "权限管理员6",
        "clinetName": "权限管理系统",
        "clientId": 1,
        "dataAuthority": null,
        "functionAuthority": "全部权限"
      },
      {
        "roleId": 22,
        "roleName": "业务平台管理员",
        "clinetName": "业务平台",
        "clientId": 6,
        "dataAuthority": null,
        "functionAuthority": "新增角色,新增角色1,新增角色12"
      },
      {
        "roleId": 23,
        "roleName": "业务平台管理员1",
        "clinetName": "业务平台",
        "clientId": 6,
        "dataAuthority": null,
        "functionAuthority": "新增角色,新增角色1,新增角色12"
      }
    ],
    "firstPage": 1,
    "prePage": 0,
    "nextPage": 0,
    "lastPage": 1,
    "isFirstPage": true,
    "isLastPage": true,
    "hasPreviousPage": false,
    "hasNextPage": false,
    "navigatePages": 8,
    "navigatepageNums": [
      1
    ]
  }
}
```

### 3.5删除角色

接口描述：删除角色

请求方式：Get

请求地址：/api/role/remove

请求参数：

| 参数名 | 参数类型 | 参数解释 |
| :----: | :------: | :------: |
| roleId | Integer  |  角色ID  |

请求示例：/api/role/remove?roleId=1

响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "删除成功！",
  "data": null
}
```

### 3.6根据平台ID 查询角色列表

接口描述：根据平台ID 查询角色列表

请求方式：Get

请求地址：/api/role/select

请求参数：

|  参数名  | 参数类型 | 参数解释 |
| :------: | :------: | :------: |
| clientId | Integer  |  应用ID  |

请求示例：/api/role/select?clientId=6

响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |
|   roleId    | Integer  |  角色id  |
|  roleName   |  String  | 角色名称 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "查询成功！",
  "data": [
    {
      "roleId": 19,
      "roleName": "测试家里蹲"
    },
    {
      "roleId": 22,
      "roleName": "业务平台管理员"
    },
    {
      "roleId": 23,
      "roleName": "业务平台管理员1"
    }
  ]
}
```

### 3.7编辑角色

接口描述：编辑角色

请求方式：Post

请求地址：/api/role/update

请求参数：

|        参数名        | 参数类型 |     参数解释     |
| :------------------: | :------: | :--------------: |
|        roleId        | Integer  |      角色id      |
|       roleName       |  String  |     角色名称     |
|       clientId       | Integer  |      平台id      |
|   allDataAuthority   | Boolean  | 是否全部数据权限 |
| allFunctionAuthority | Boolean  | 是否全部功能权限 |
|   dataAuthorityIds   |   List   |   数据权限ids    |
| functionAuthorityIds |   List   |   功能权限ids    |

请求示例：

```java
{
  "allDataAuthority": true,
  "allFunctionAuthority": true,
  "clientId": 0,
  "dataAuthorityIds": [
    0
  ],
  "functionAuthorityIds": [
    0
  ],
  "roleId": 0,
  "roleName": "string"
}
```



响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "编辑成功！",
  "data": null
}
```



## 4应用管理

### 4.1应用列表

接口描述：应用列表

请求方式：Get

请求地址：/api/app/list

请求参数：

| 参数名 | 参数类型 |               参数解释               |
| :----: | :------: | :----------------------------------: |
|  sign  | Integer  | sign=0时过滤权限平台,非0返回所有平台 |
|   authentication |  Integer  |  认证状态 0: "未认证", 1: "已认证"     |
|  tokenCheckType  | Integer  |   验证方式  0:安全优先, 1:性能优先   |
|  verification  | Integer  |   认证方式    0: "CAS" ,1: "OAUTH" , 2 :"SAML"    |
| clientId | Integer | 应用ID |
| pageIndex | Integer | 当前页 |
| pageSize | Integer | 当前页最多条数 |



请求示例：/api/app/list?sign=0&authentication=0&verification=0&tokenCheckType=0&clientId=0

响应参数：

|     参数名     | 参数类型 |   参数解释   |
| :------------: | :------: | :----------: |
|      data      |  String  |   返回数据   |
|    err_CODE    | Integer  |    状态码    |
|  err_MESSAGE   |  String  |   返回信息   |
|    clientId    | Integer  |    应用ID    |
|   clientName   |  String  |   应用名称   |
|   createTime   |   Date   |   创建时间   |
|   updateTime   |   Date   |   修改时间   |
| authentication |  String  |   认证状态   |
|  verification  |  String  |   验证方式   |
|     appId      |  string  |    appId     |
|      url       |  string  | 应用图片地址 |
|     secret     |  String  |  app Secret  |
|   creatorId    | Integer  |    创建ID    |
|   updaterId    | Integer  |    编辑ID    |
| accessValidity   |  Integer |   Access Token 有效时间 单位分钟   |
|   authentication |  Integer  |  认证状态 0: "未认证", 1: "已认证"     |
|  tokenCheckType  | Integer  |   验证方式  0:安全优先, 1:性能优先   |
|  verification  | Integer  |   认证方式    0: "CAS" ,1: "OAUTH" , 2 :"SAML"    |
| pageNum | Integer | 当前页 |
| pageSize | Integer | 当前页最多条数 |
| size | Integer | 当前页条数 |
| creatorUserName | String | 创建用户用户名 |
| moduleList | list | 应用对应模块信息 |
| roleList | list | 应用对应角色信息 |
| externalAuthId | Integer | 外部数据源主键ID |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": {
    "pageNum": 1,
    "pageSize": 1,
    "size": 1,
    "orderBy": null,
    "startRow": 1,
    "endRow": 1,
    "total": 1,
    "pages": 1,
    "list": [
      {
        "clientId": 2,
        "authentication": null,
        "verification": null,
        "tokenCheckType": null,
        "accessValidity": null,
        "clientName": "史上12管理系统",
        "createTime": null,
        "updateTime": "2020-05-29T08:33:56.000+0000",
        "appId": "222",
        "url": null,
        "secret": "sadsw67423sad7826fa417162103f8085",
        "creatorId": null,
        "updaterId": 169,
        "appDesc": "描述1",
        "creatorUserName": null,
        "externalAuthId":0,
        "moduleList": [
          {
            "moduleId": 11,
            "clientId": 2,
            "moduleName": "测试模块",
            "functionList": []
          },
          {
            "moduleId": 28,
            "clientId": 2,
            "moduleName": "测试模块2",
            "functionList": [
              {
                "functionId": 35,
                "clientId": 2,
                "functionName": "查询模块信息",
                "apiType": 1,
                "apiUrlList": [
                  "api/module/select",
                  "api/module/detail"
                ]
              }
            ]
          },
          {
            "moduleId": 29,
            "clientId": 2,
            "moduleName": "测试模块3",
            "functionList": []
          }
        ],
        "roleList": [
          {
            "roleId": 34,
            "roleName": "我的速度"
          },
          {
            "roleId": 63,
            "roleName": "wewewe"
          },
          {
            "roleId": 64,
            "roleName": "孙彩云"
          }
        ]
      }
    ],
    "firstPage": 1,
    "prePage": 0,
    "nextPage": 0,
    "lastPage": 1,
    "isFirstPage": true,
    "isLastPage": true,
    "hasPreviousPage": false,
    "hasNextPage": false,
    "navigatePages": 8,
    "navigatepageNums": [
      1
    ]
  }
}
```



### 4.2新增应用

接口描述：新增应用

请求方式：Post

请求地址：/api/app/add

请求参数：

|     参数名     | 参数类型 |                  参数解释                  |
| :------------: | :------: | :----------------------------------------: |
|     appId      |  String  |                   appId                    |
|   clientName   |  String  |                  平台名称                  |
|     imgUrl     |  String  |                应用图片地址                |
|     secret     |  String  |                 app Secret                 |
|    appDesc     |  String  |                  应用描述                  |
| accessValidity | Integer  |        app Secret有效期单位（分钟）        |
| authentication | Integer  |       认证状态 0:内部认证,1外部认证        |
| tokenCheckType | Integer  |       验证方式 0:安全优先，1性能优先       |
|  verification  | Integer  | 认证方式 0: "CAS" ,1: "OAUTH" , 2 :"SAML"' |

请求示例：

```java
{
  "accessValidity": 18,
  "appDesc": "描述",
  "appId": "guodegang",
  "authentication": 1,
  "clientName": "应用名称",
  "imgUrl": "deyunshe",
  "secret": "ttttttttt",
  "tokenCheckType":1,
  "verification": 2
}
```



响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "新增成功！",
  "data": null
}
```



### 4.3认证列表下拉框，在线离线列表下拉框

接口描述：认证列表下拉框，在线离线列表下拉框

请求方式：Get

请求地址：/api/app/fetch

请求参数：无

请求示例：/api/app/fetch

响应参数：

|         参数名         | 参数类型 |       参数解释       |
| :--------------------: | :------: | :------------------: |
|          data          |  String  |       返回数据       |
|        err_CODE        | Integer  |        状态码        |
|      err_MESSAGE       |  String  |       返回信息       |
|       lineStatus       |   List   |    在线离线下拉框    |
|     authentication     |   List   |      认证下拉框      |
| authenticationEnumList |   List   | 已认证认证方式下拉框 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "查询成功！",
  "data": {
    "lineStatus": [
      {
        "name": "在线",
        "lineStatusCode": 1
      },
      {
        "name": "离线",
        "lineStatusCode": 2
      }
    ],
    "authentication": [
      {
        "name": "未认证",
        "authenticationEnumList": null,
        "authenticationCode": 1
      },
      {
        "name": "已认证",
        "authenticationEnumList": [
          {
            "name": "CAS",
            "authenticationCode": 1
          },
          {
            "name": "OAUTH",
            "authenticationCode": 2
          },
          {
            "name": "SAML",
            "authenticationCode": 3
          }
        ],
        "authenticationCode": 2
      }
    ]
  }
}
```



### 4.4编辑应用

接口描述：编辑应用

请求方式：Post

请求地址：/api/app/update

请求参数：

|   参数名   | 参数类型 |                           参数解释                           |
| :--------: | :------: | :----------------------------------------------------------: |
|  clientId  | Integer  |                            应用Id                            |
| clientName |  String  |                           平台名称                           |
|   appId    |  String  |                            appId                             |
|   imgUrl   |  String  |                         应用图片地址                         |
|   secret   |  String  | app Secret（字段不为空时 接口对应重置Secret 为空时对应编辑应用） |
|  appDesc   |  String  |                           应用描述                           |

请求示例：

```java
{
  "appId": "string",
  "clientId": 5,
  "clientName": "string",
  "imgUrl": "string",
  "secret": "string",
  "appDesc":"string"
}
```



响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "编辑成功！",
  "data": null
}
```

### 4.5新增模块，功能

接口描述：新增模块，功能

请求方式：Post

请求地址：/api/app/module/add

请求参数：

|    参数名    | 参数类型 |                 参数解释                  |
| :----------: | :------: | :---------------------------------------: |
|   clientId   | Integer  |                  应用ID                   |
|   moduleId   | Integer  |                  模块ID                   |
|  moduleName  |  String  |                  模块名                   |
| functionList |   List   | 功能List 空代表新怎模块，非空代表新增功能 |
| functionName |  String  |                 功能名称                  |
|   apiType    | Integer  |        功能类型 0[非查询]，1[查询]        |
|  apiUrlList  |   List   |             功能对应URL List              |

请求示例：

```java
{
  "clientId": 0,
  "functionList": [
    {
      "apiType": 0,
      "apiUrlList": [
        "string"
      ],
      "clientId": 0,
      "functionName": "string",
      "moduleId": 0
    }
  ],
  "moduleId": 0,
  "moduleName": "string"
}
```



响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "新增成功",
  "data": null
}
```



### 4.6查询应用对应模块和功能

接口描述：查询应用对应模块和功能

请求方式：Get

请求地址：/api/app/select

请求参数：

|  参数名  | 参数类型 | 参数解释 |
| :------: | :------: | :------: |
| clientId | Integer  |  应用ID  |
| moduleId | Integer  |  模块ID  |

请求示例：/api/app/select?clientId=6&moduleId=23

响应参数：

|    参数名    | 参数类型 |              参数解释               |
| :----------: | :------: | :---------------------------------: |
|     data     |  String  |              返回数据               |
|   err_CODE   | Integer  |               状态码                |
| err_MESSAGE  |  String  |              返回信息               |
|   moduleId   | Integer  |               模块ID                |
|   clientId   | Integer  |               应用ID                |
|  moduleName  |  String  |               模块名                |
| functionList |   List   |            模块对应功能             |
|  functionId  | Integer  |               功能ID                |
| functionName |  String  |              功能名称               |
|   apiType    | Integer  |     功能类型 0[非查询]，1[查询]     |
|   moduleId   | Integer  |           功能对应模块ID            |
|  apiUrlList  |   List   | 功能对应URL 一个功能可能对应多个URL |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": [
    {
      "moduleId": 23,
      "clientId": 6,
      "moduleName": "角色管理",
      "functionList": [
        {
          "functionId": 41,
          "clientId": 6,
          "functionName": "新增角色",
          "apiType": 0,
          "apiUrlList": [
            "api/role/add"
          ]
        },
        {
          "functionId": 42,
          "clientId": 6,
          "functionName": "新增角色1",
          "apiType": 0,
          "apiUrlList": [
            "api/role/add1"
          ]
        },
        {
          "functionId": 43,
          "clientId": 6,
          "functionName": "新增角色12",
          "apiType": 0,
          "apiUrlList": [
            "api/role/add1"
          ]
        }
      ]
    },
    {
      "moduleId": 16,
      "clientId": 6,
      "moduleName": "用户管理1212",
      "functionList": []
    }
  ]
```



### 4.7删除模块

接口描述：批量删除模块

请求方式：Post

请求地址：/api/app/module/remove

请求参数：

|  参数名  | 参数类型 | 参数解释 |
| :------: | :------: | :------: |
| clientId | Integer  |  应用ID  |
| ids | List  |  要删除模块ID 数组  |

请求示例：
``` java
{
  "clientId": 0,
  "ids": [
    0
  ]
}
```

响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

``` java
{
  "err_CODE": 0,
  "err_MESSAGE": "删除成功",
  "data": null
}
```



### 4.8删除功能

接口描述：批量删除功能

请求方式：Post

请求地址：/api/app/module/function/remove

请求参数：

|   参数名   | 参数类型 | 参数解释 |
| :--------: | :------: | :------: |
| clientId | Integer  |  应用ID  |
| ids | List  |  要删除功能ID 数组  |

请求示例：
请求示例：
``` java
{
  "clientId": 0,
  "ids": [
    0
  ]
}
```

响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "删除成功",
  "data": null
}
```


### 4.9编辑模块

接口描述：编辑模块

请求方式：Post

请求地址：/api/app/module/update

请求参数：

|   参数名   | 参数类型 |   参数解释   |
| :--------: | :------: | :----------: |
|  clientId  | Integer  |    应用Id    |
| moduleId   |  Integer |   模块ID   |
|   moduleName    |  String  |    模块名     |


请求示例：

```java
{
  "clientId": 5,
  "moduleId": 14,
  "moduleName": "模块2"
}
```



响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "编辑成功！",
  "data": null
}
```


### 4.10编辑功能

接口描述：编辑功能

请求方式：Post

请求地址：/api/app/module/function/update

请求参数：

|   参数名   | 参数类型 |   参数解释   |
| :--------: | :------: | :----------: |
|  clientId  | Integer  |    应用Id    |
| functionId   |  Integer |   应用ID   |
|   functionName |  String  |  功能名     |
|  moduleId  | Integer  |    模块ID    |
|  apiType  | Integer  |    功能类型 0[非查询]，1[查询]    |
|  apiUrlList  | List  |    URL    |



请求示例：

```java
{
  "apiType": 1,
  "apiUrlList": [
    "www","ss"
  ],
  "clientId":5,
  "functionId": 15,
  "functionName": "功能1",
  "moduleId": 14
}
```



响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "编辑成功！",
  "data": null
}
```

### 4.11删除应用

接口描述：删除应用

请求方式：Get

请求地址：/api/app/remove

请求参数：

|   参数名   | 参数类型 | 参数解释 |
| :--------: | :------: | :------: |
| clientId | Integer  |  应用ID  |

请求示例：/api/app/remove?clientId=7

响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "删除成功",
  "data": null
}
```


### 4.12认证管理

接口描述：编辑功能

请求方式：Post

请求地址：/api/app/certification

请求参数：

|   参数名   | 参数类型 |   参数解释   |
| :--------: | :------: | :----------: |
|  clientId  | Integer  |    应用Id    |
| accessValidity   |  Integer |   Access Token 有效时间 单位分钟   |
|   authentication |  Integer  |  认证状态 0: "未认证", 1: "已认证"     |
|  tokenCheckType  | Integer  |   验证方式  0:安全优先, 1:性能优先   |
|  verification  | Integer  |   认证方式    0: "CAS" ,1: "OAUTH" , 2 :"SAML"    |
| externalAuthId | Integer | 外部数据源主键ID |




请求示例：

```java
{
  "accessValidity": 0,
  "authentication": 0,
  "clientId": 0,
  "tokenCheckType": 0,
  "verification": 0,
  "externalAuthId":0
}
```



响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "认证成功！",
  "data": null
}
```

### 4.13根据平台id查询权限

接口描述：根据平台id查询权限

请求方式：Get

请求地址：/api/app/claim

请求参数：

|  参数名  | 参数类型 |          参数解释          |
| :------: | :------: | :------------------------: |
| clientId | Integer  |           应用ID           |
|   flag   | Integer  | 0代表功能权限1代表数据权限 |

请求示例：/api/app/permission/list?clientId=6&flag=0

响应参数：

|    参数名    | 参数类型 |          参数解释           |
| :----------: | :------: | :-------------------------: |
|     data     |  String  |          返回数据           |
|   err_CODE   | Integer  |           状态码            |
| err_MESSAGE  |  String  |          返回信息           |
|   moduleId   | Integer  |           模块ID            |
|   clientId   | Integer  |           应用ID            |
|  moduleName  |  String  |           模块名            |
| functionList |   List   |          功能List           |
|  functionId  | Integer  |           功能ID            |
| functionName |  String  |           功能名            |
|   apiType    | Integer  | 功能类型 0[非查询]，1[查询] |
|  apiUrlList  |   List   |      功能对应URL list       |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "查询成功！",
  "data": [
    {
      "moduleId": 23,
      "clientId": 6,
      "moduleName": "角色管理",
      "functionList": []
    },
    {
      "moduleId": 16,
      "clientId": 6,
      "moduleName": "用户管理1212",
      "functionList": [
        {
          "functionId": 19,
          "clientId": 6,
          "functionName": "编辑用户",
          "apiType": 0,
          "apiUrlList": [
            "api/user/add",
            "api/user/update"
          ]
        },
        {
          "functionId": 20,
          "clientId": 6,
          "functionName": "编辑用户21",
          "apiType": 0,
          "apiUrlList": [
            "api/user/add",
            "api/user/del"
          ]
        },
        {
          "functionId": 18,
          "clientId": 6,
          "functionName": "编辑用户信息12",
          "apiType": 0,
          "apiUrlList": [
            "api/user/update"
          ]
        },
        {
          "functionId": 22,
          "clientId": 6,
          "functionName": "新增用户11",
          "apiType": 0,
          "apiUrlList": []
        },
        {
          "functionId": 21,
          "clientId": 6,
          "functionName": "新增用户3",
          "apiType": 0,
          "apiUrlList": [
            "api/user/add"
          ]
        }
      ]
    }
  ]
}
```

### 4.14获取oss相关参数

接口描述：获取oss相关参数

请求方式：Get

请求地址：/api/app/oss

请求参数：无

请求示例：/api/app/oss

响应参数：

|   参数名    | 参数类型 |  参数解释   |
| :---------: | :------: | :---------: |
|    data     |  String  |  返回数据   |
|  err_CODE   | Integer  |   状态码    |
| err_MESSAGE |  String  |  返回信息   |
|  Expiration   |  String  | oss固定参数 |
| Security Token  |  String  | oss固定参数 |
| Access Key Id |  String  | oss固定参数 |
|   bucketName    |  String  | oss固定参数 |
|   Access Key Secret    |  String  | oss固定参数 |
|   RequestId    |  String  | oss固定参数 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": {
    "Expiration": "2020-06-20T15:48:39Z",
    "Security Token": "CAIS7QF1q6Ft5B2yfSjIr5DPIfvdjJ5yhfuaelHTsTMTRu1vgP3CqDz2IH9MfnJuCegZsv4zmW5U6PsalqF2RppPX0XCYJOARVesNEXzDbDasumZsJYm6vT8a0XxZjf/2MjNGZabKPrWZvaqbX3diyZ32sGUXD6+XlujQ/br4NwdGbZxZASjaidcD9p7PxZrrNRgVUHcLvGwKBXn8AGyZQhKwlMi2TsnuP3knJbMsEqD1wLAp7VL99irEP+NdNJxOZpzadCx0dFte7DJuCwqsEcSrvwo3PQbpW2b44/BXQQJ+XucOu/T6cZ//aGoiUzTjcQagAEn3Bd2w7o8hfRL0xcroajJBQAQyT6VPoKYhD73q/xpvvs5YBanx/HPdn9BVkEef0bfPa+3L4vw4iute67m9TmjrTdYl5M2fQOLwXTrikyoqVf3V8rHm5CpuY8uJNxPe7+aMyHmkYWR80KkhjXZcqVnAIkODC9SdceD9w9rcPSabQ==",
    "Access Key Id": "STS.NSzjAiaAS29qxwbU3FJbCo8iJ",
    "bucketName": "membrane",
    "Access Key Secret": "7Zdt1BncphYEpLUvayYpdBr7YWGrsq3AGBpSAyGFKDN9",
    "RequestId": "43AF8C52-909F-450B-9E16-8C8609655DA4"
  }
}
```

### 4.15 新增数据权限属性配置

接口描述：新增数据权限属性配置

请求方式：Post

请求地址：/api/app/attribute/add

请求参数：

|    参数名     | 参数类型 |  参数解释  |
| :-----------: | :------: | :--------: |
|   configId    | Integer  | 基本配置ID |
| attributeName |  String  |   属性名   |
| attributeDesc |  String  |    描述    |
| attributeTag  |  String  |   标识符   |
| attributeType | Integer  |  数据类型  |

请求示例：

```java
{
  "configId": 1,
  "attributeName": "属性名",
  "attributeDesc": "描述",
  "attributeTag": "标识符",
  "attributeType": 1            // 0 整数，1浮点，2布尔，3字符串，4枚举，5数组，6时间/日期'
}
```

响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": null
}
```

### 4.16 修改数据权限属性配置

接口描述：修改数据权限属性配置

请求方式：Post

请求地址：/api/app/attribute/update

请求参数：

|    参数名     | 参数类型 |  参数解释  |
| :-----------: | :------: | :--------: |
|      id       | Integer  | 属性配置ID |
|   configId    | Integer  | 基本配置ID |
| attributeName |  String  |   属性名   |
| attributeDesc |  String  |    描述    |
| attributeTag  |  String  |   标识符   |
| attributeType | Integer  |  数据类型  |

请求示例：

```java
{
  "id": 1,
  "configId": 1,
  "attributeName": "属性名",
  "attributeDesc": "描述",
  "attributeTag": "标识符",
  "attributeType": 0           //数据类型（0 整数，1浮点，2布尔，3字符串，4枚举，5数组，6时间/日期'）
}
```

响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": null
}
```

### 4.17 数据权限属性配置列表

接口描述：数据权限属性配置列表

请求方式：Get

请求地址：/api/app/attribute/list

请求参数：

|  参数名  | 参数类型 |  参数解释  |
| :------: | :------: | :--------: |
| configId | Integer  | 基本配置ID |

请求示例：/api/app/attribute/list?configId=0

响应参数：

|    参数名     | 参数类型 |    参数解释    |
| :-----------: | :------: | :------------: |
|     data      |  String  |    返回数据    |
|   err_CODE    | Integer  |     状态码     |
|  err_MESSAGE  |  String  |    返回信息    |
|    pageNum    | Integer  |     当前页     |
|   pageSize    | Integer  | 当前页最多条数 |
|     size      | Integer  |   当前页条数   |
|      id       | Integer  |   属性配置ID   |
|   configId    | Integer  |   基本配置ID   |
| attributeTag  |  String  |     标识符     |
| attributeName |  String  |     属性名     |
| attributeType | Integer  |    数据类型    |
| attributeDesc |  String  |      描述      |
|   userName    |  String  |     创建人     |
|  createTime   |   Date   |    创建时间    |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "size": 1,
    "orderBy": null,
    "startRow": 1,
    "endRow": 1,
    "total": 1,
    "pages": 1,
    "list": [
      {
        "id": 1,
        "configId": 0,
        "attributeTag": "string",
        "attributeName": "string",
        "attributeType": 0,
        "attributeDesc": "string",
        "userName": "admin",
        "createTime": "2020-07-17T02:32:46.000+0000"
      }
    ],
    "firstPage": 1,
    "prePage": 0,
    "nextPage": 0,
    "lastPage": 1,
    "isFirstPage": true,
    "isLastPage": true,
    "hasPreviousPage": false,
    "hasNextPage": false,
    "navigatePages": 8,
    "navigatepageNums": [
      1
    ]
  }
}
```

### 4.18 删除数据权限属性配置

接口描述：删除数据权限属性配置列表

请求方式：Get

请求地址：/api/app/attribute/remove

请求参数：

| 参数名 | 参数类型 |    参数解释    |
| :----: | :------: | :------------: |
| idList | Integer  | 属性配置ID列表 |

请求示例：/api/app/attribute/remove?idList=1,2

响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": null
}
```

### 4.15 新增条件配置

接口描述：新增条件配置

请求方式：Post

请求地址：/api/app/scree/add

请求参数：

|  参数名   | 参数类型 |   参数解释   |
| :-------: | :------: | :----------: |
| clientId  | Integer  |  所属应用ID  |
|  scrName  |  String  |  表达式名称  |
|  scrRule  |  String  |   录入规则   |
|  scrDesc  |  String  |     描述     |
|  scrJson  |  String  | 表达式json串 |
| attIdList |  String  |  属性ID列表  |

请求示例：

```java
{
  "clientId": 0,
  "scrDesc": "string",
  "scrJson": "string",
  "scrName": "string",
  "scrRule": "string",
  "attIdList": "1,2"
}
```

响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": null
}
```

### 4.16 修改条件配置

接口描述：修改条件配置

请求方式：Post

请求地址：/api/app/scree/update

请求参数：

|  参数名   | 参数类型 |   参数解释   |
| :-------: | :------: | :----------: |
|    id     | Integer  |  条件配置ID  |
| clientId  | Integer  |  所属应用ID  |
|  scrName  |  String  |  表达式名称  |
|  scrRule  |  String  |   录入规则   |
|  scrDesc  |  String  |     描述     |
|  scrJson  |  String  | 表达式json串 |
| attIdList |  String  |  属性ID列表  |

请求示例：

```java
{
  "id": 0,
  "clientId": 0,
  "scrDesc": "string",
  "scrJson": "string",
  "scrName": "string",
  "scrRule": "string",
  "attIdList": "1,2"
}
```

响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": null
}
```

### 4.17 条件配置列表

接口描述：条件配置列表

请求方式：Get

请求地址：/api/app/scree/list

请求参数：

|  参数名   | 参数类型 | 参数解释 |
| :-------: | :------: | :------: |
| clientId  | Integer  |  应用ID  |
| pageIndex | Integer  |  当前页  |
| pageSize  | Integer  |  页大小  |

请求示例：/api/app/scree/list?clientId=1

响应参数：

|   参数名    | 参数类型 |    参数解释    |
| :---------: | :------: | :------------: |
|    data     |  String  |    返回数据    |
|  err_CODE   | Integer  |     状态码     |
| err_MESSAGE |  String  |    返回信息    |
|   pageNum   | Integer  |     当前页     |
|  pageSize   | Integer  | 当前页最多条数 |
|    size     | Integer  |   当前页条数   |
|     id      | Integer  |   条件配置ID   |
|  clientId   | Integer  |   所属应用ID   |
|   scrName   |  String  |   表达式名称   |
|   scrRule   |  String  |    录入规则    |
|   scrDesc   |  String  |      描述      |
|   scrJson   |  String  |  表达式json串  |
|  attIdList  |  String  |   属性ID列表   |
|  userName   |  String  |     创建人     |
| createTime  |   Date   |    创建时间    |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "size": 1,
    "orderBy": null,
    "startRow": 1,
    "endRow": 1,
    "total": 1,
    "pages": 1,
    "list": [
      {
        "id": 1,
        "clientId": 2,
        "scrName": "string",
        "scrRule": "string",
        "scrDesc": "string",
        "scrJson": "string",
        "attIdList": "1,2",
        "userName": "admin",
        "createTime": "2020-07-20T07:33:01.000+0000"
      }
    ],
    "firstPage": 1,
    "prePage": 0,
    "nextPage": 0,
    "lastPage": 1,
    "isFirstPage": true,
    "isLastPage": true,
    "hasPreviousPage": false,
    "hasNextPage": false,
    "navigatePages": 8,
    "navigatepageNums": [
      1
    ]
  }
}
```

### 4.18 删除条件配置

接口描述：删除条件列表

请求方式：Get

请求地址：/api/app/scree/remove

请求参数：

| 参数名 | 参数类型 |    参数解释    |
| :----: | :------: | :------------: |
| idList | Integer  | 属性配置ID列表 |

请求示例：/api/app/scree/remove?idList=1,2

响应参数：

|   参数名    | 参数类型 | 参数解释 |
| :---------: | :------: | :------: |
|    data     |  String  | 返回数据 |
|  err_CODE   | Integer  |  状态码  |
| err_MESSAGE |  String  | 返回信息 |

响应示例：

```java
{
  "err_CODE": 0,
  "err_MESSAGE": "请求成功",
  "data": null
}
```

