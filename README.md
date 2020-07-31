# Membrane
## 简介
Membrane是一个互联网应用基础组件，基于SpringBoot、SpringCloud、SpringSecurityOAuth2构建，着重于解决互联网应用分布式集群场景下的身份认证，并提供丰富的认证方式和社会化认证集成。也包含其他支撑系统，例如：用户中心、安全中心、认证中心等。能够满足所有互联网应用的所有认证需求，并通过基础组件来支撑互联网应用快速开发。

## 组件列表
| 组件代码          | 组件名称 | 说明                   |
| ----------------- | -------- | ---------------------- |
| membrane-auth     | 认证组件 | 提供认证服务           |
| membrane-security | 安全中心 | 提供凭证验证等安全服务 |

## 认证模式
- Jwt  Token认证（已接入）
- Ldap 认证（已接入）
## 登录方式
- 账号密码登录
- 第三方登录(Ldap账户)

通过Membrane可快速的多种构建认证服务
## 认证服务
- Membrane基于OAuth2构建SSO平台
- Spring Cloud微服务集群认证服务
- OpenAPI 基于OAuth2构建开放平台

通过对SpringSecurityOAuth2进行增强，支持短信验证码、第三方账号登录等授权模式
## OAuth2扩展授权模式
- 通过短信验证码获取Token
- 通过第三方AuthorizationCode获取Token

## Membrane 版本发布

- ### 版本V1.1.0

 Membrane 权限管理平台(SSO) V1.1.0正式开源，具体的功能模块如下：

|   模块   | 功能                                                         |
| :------: | ------------------------------------------------------------ |
|   登录   | 1. 用户名/密码登录；<br />2. 集成LDAP认证，支持第三方认证中心账号登录；<br />3. 忘记密码（支持手机号、邮件验证码两种方式找回密码）；<br />4. 修改密码；<br />5. 退出登录 |
| 应用管理 | 1. 创建应用及管理（编辑、删除、查看）；<br />2. 应用权限管理：<br />      2.1 功能权限：<br />      2.2 数据权限：<br />3. 认证集成：<br />4. 应用验证模式（在线、离线校验）及生命周期管理；<br />1. 创建应用及应用详情查看；<br />2. 修改应用信息；<br />3. 应用验证模式配置：在线、离线校验及验证生命周期管理;<br />4. 应用资源管理：<br />       4.1. 功能权限资源管理:<br />              4.1.1 一级资源增删改查；<br />              4.1.2 二级资源增删改查; <br />       4.2 数据权限资源管理：<br />             4.2.1 数据权限模型建立；<br />             4.2.2 模型与应用绑定；<br />             4.2.3 模型解析与对接； |
| 用户管理 | 1. 用户信息管理(增删改查);<br />2. 用户与角色绑定管理；<br />3. 用户启用/禁用；<br />4. 重置密码 |
| 角色管理 | 1. 新增角色；<br />2. 编辑角色；<br />3. 删除角色;  <br />4. 角色详情查看；<br />5. 角色复制；<br />6. 授权管理：<br />      6.1 角色与应用下的权限分类(功能权限、数据权限)进行进行绑定；<br />      6.2 用户与角色绑定 |
| 认证中心 | 1.多类型（CAS、LDAP、OAuth,目前已实现LDAP类型的认证源接入）认证源添加；<br />2. 认证源信息编辑；<br />3. 删除认证源；<br />4. 认证源信息查看； |

  

- ### RoadMap

| 模块     | 功能                                                          | 迭代版本 |
| -------- | ------------------------------------------------------------ | :------: |
| 角色管理 | 支持角色与应用一对多；                                     | V1.2.1 |
| 用户管理 | 1. 用户属性标签管理：增删改查；<br />2. 用户与属性绑定；<br />3. 用户组管理：<br />     3.1 灵活配置用户组，可对用户进行分组管理并可以将授权分配至用户组或组织结构上；<br />4. 组织管理：<br />     4.1 组织机构模型构建管理(增删改查)；<br />     4.2 组织机构与用户管理；<br />     4.3 组织机构与权限绑定；<br />5. 用户账号生命周期管理： |  V1.2.1  |
| 认证中心 | 1. 支持接入的认证源同步数据功能；<br />2. 接入OAuth2.0;      |  V1.2.2  |
|应用中心  |  应用区分权限类型：该应用具有功能权限或数据权限、功能权限两者都有； | V1.2.2 |
| 审计管理 | 1. 用来记录每个账号的日志记录，主要包括：认证记录、资源访问记录、操作记录等；<br />2. 支持EXCEL文件格式导出； |  V1.2.2  |
| 登录     | 1. 在用户管理中，添加使用第三方应用登录，并在实际应用中使用绑定的第三方应用进行登录；<br />2. 支持使用 手机号验证码进行登录； |  V1.2.3  |

如果您有任何需求，欢迎给我们提issue，我们将会及时给您回复。


## 优势
- 提供标准的认证接口以便于其他应用集成SSO，安全的移动接入，安全的API以及第三方认证服务整合；
- 封装独立的SDK，便于与其他系统快速对接，降低与原有代码之间的耦合度；
- 多种认证机制并存，各应用系统可保留原有认证机制，同时集成认证中心的认证；应用具有高度独立性，不依赖认证中心，又可以使用认证中心的认证，实现跨平台登录；
- 基于JAVA开发，采用Spring、MySQL、Tomcat、Apache Kafka、Redis等开源技术，支持微服务，扩展性强；
- 独立的配置中心，灵活配置应用权限管理。
- 各种开发文档简介明了，可快速入手。

## 文档列表
1. [安装部署文档](https://github.com/celldata/Membrane/blob/master/docs/Membrane%E9%83%A8%E7%BD%B2%E6%96%87%E6%A1%A3.md)
2. [业务流程时序图]（https://membrane-github.oss-cn-beijing.aliyuncs.com/image/%E6%97%B6%E5%BA%8F%E5%9B%BE.png）
3. 开发文档
- [前端开发文档](https://github.com/celldata/Membrane/blob/master/docs/Membrane%E5%89%8D%E7%AB%AF%E5%BC%80%E5%8F%91%E6%96%87%E6%A1%A3.md)
- [API文档](https://github.com/celldata/Membrane/blob/master/docs/Membrane_API.md)
- [业务系统后台对接Membrane说明文档](https://github.com/celldata/Membrane/blob/master/docs/%E4%B8%9A%E5%8A%A1%E7%B3%BB%E7%BB%9F%E5%90%8E%E5%8F%B0%E5%AF%B9%E6%8E%A5Membrane%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3.md)
4. 使用文档
- [用户手册](https://github.com/celldata/Membrane/blob/master/docs/Membrane%E7%94%A8%E6%88%B7%E6%89%8B%E5%86%8C.md)
## 体验demo环境
如您想体验demo环境，请发送申请邮件到membrane@celldata.cloud，会有工作人员为您开通体验账号。<br/>
[点我体验演示环境](http://sso.i.lsctl.com)

## Communication
如果您希望得到最快的响应，请给我们提issue，或者发送邮件到membrane@celldata.cloud。

## License
Membrane is under the MIT license. See the [License](https://github.com/celldata/Membrane/blob/master/LICENSE) file for details.

