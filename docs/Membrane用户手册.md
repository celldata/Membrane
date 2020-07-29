# 1.总体流程
![总体流程](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%80%BB%E4%BD%93%E6%B5%81%E7%A8%8B.png)

# 2.系统登录
## 2.1用户登录
未登录的用户，在浏览器地址栏中输入权限管理平台网址后，将进入平台登录页面。

![系统登录](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E7%B3%BB%E7%BB%9F%E7%99%BB%E5%BD%95.png)</br>
图2-1 系统登录
本平台不支持用户注册，所有用户均由管理员登录系统后统一添加并授权。若拥有平台权限的用户忘记密码，可通过登录页“忘记密码”入口找回密码

![找回密码](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E7%B3%BB%E7%BB%9F%E7%99%BB%E5%BD%95.png)</br>
图2-2 找回密码</br>
“找回密码”弹框中用户名、验证码、密码、确认密码为必填项。其中，用户输入的新密码需要满足规则：以字母开头，由字母数字组成，长度范围在6-12位。
点击确认按钮后，系统会对上述四项内容进行校验，校验失败则无法完成密码找回操作；正确填写相关信息并成功提交后，新密码即刻生效，用户可直接使用新密码登录。找回密码校验失败提示信息如图2-3所示：

![找回密码-校验失败提示信息](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E7%B3%BB%E7%BB%9F%E7%99%BB%E5%BD%95.png)</br>
图2-3 找回密码-校验失败提示信息</br>
拥有平台权限的用户，在平台登录页填写有效的用户名及密码信息后，点击登录按钮可成功登入系统，登录后进入平台主界面（应用管理列表页），如图2-4所示：</br>
![平台主界面](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%B9%B3%E5%8F%B0%E4%B8%BB%E7%95%8C%E9%9D%A2.png)</br>
图2-4 平台主界面</br>
## 2.2修改密码
已登录的用户，可以在系统右上角看到一个用户图标，通过点击图标或用户名，可以看到平台为用户提供了两个功能按钮：修改密码、退出登录，如图2-5所示：</br>
![用户图标](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E7%94%A8%E6%88%B7%E5%9B%BE%E6%A0%87.png)</br>
图2-5 用户图标

管理员添加用户后，用户的初始密码会以短信或邮件的形式发送给用户，由于系统按密码规则生成的初始密码通常不便于记忆，因此，用户首次登录系统后，可通过上述入口找到修改密码按钮，将密码更新为便于用户记忆的字符组合。
正确填写相关信息并成功提交后，新密码即刻生效，系统将返回到系统登录页（见图2-1），用户可使用新密码重新登录系统。修改密码界面如图2-6所示：</br>
![修改密码](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E4%BF%AE%E6%94%B9%E5%AF%86%E7%A0%81.png)</br>
图2-6 修改密码</br>
“修改密码”弹框中原密码、新密码、确认密码为必填项。密码需满足规则：以字母开头，由字母数字组成，长度范围在6-12位。
点击确认按钮后，系统会对上述三项内容进行校验，校验失败则无法完成修改密码操作。修改密码校验失败提示信息如图2-7所示：</br>
![修改密码-校验失败提示信息](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E4%BF%AE%E6%94%B9%E5%AF%86%E7%A0%81-%E6%A0%A1%E9%AA%8C%E5%A4%B1%E8%B4%A5.png)</br>
图2-7 修改密码-校验失败提示信息
## 2.3退出登录
基于办公安全，为避免未授权人员在线下通过已登录的页面直接访问和操作本系统，造成数据安全隐患，用户需要在系统中完成相关业务操作后及时退出系统（功能入口参见图2-5）。用户退出登录后，将直接返回登录页（见图2-1）。


# 3.用户管理
## 3.1用户管理
### 3.1.1 tips展示
在用户管理栏目下，点击选择角色的下拉框，即可通过角色筛选快速找到正确的用户。如图3-1所示：</br>
![tips展示](https://github.com/celldata/Membrane/blob/master/image/quickstart/tips%E5%B1%95%E7%A4%BA.png)</br>
图3-1 tips展示
### 3.1.2用户状态下拉
还是在当前栏目，点击用户状态下拉框，就能筛选出已启用或者未启用的用户有哪些，如图3-2所示：</br>
![用户状态下拉](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E7%94%A8%E6%88%B7%E7%8A%B6%E6%80%81%E4%B8%8B%E6%8B%89.png)</br>
图3-2 用户状态下拉
## 3.2 重置密码
### 3.2.1禁用
在操作列中，点击－号即可禁用被选中用户的登录权限，如图3-3所示</br>
![禁用权限](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E7%A6%81%E7%94%A8%E6%9D%83%E9%99%90.png)</br>
图3-3禁用权限
### 3.2.2启用
在操作列中，点击播放键即可启用被选中用户的登录权限，如图3-4所示：</br>
![启用权限](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%90%AF%E7%94%A8%E6%9D%83%E9%99%90.png)</br>
图3-4 启用权限
## 3.3 新增用户
### 3.3.1未选择角色
在用户管理栏目下，点击新增用户按钮，但未选择角色，即出现下述界面，如图3-5所示：</br>
![新增用户-未选择角色](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%96%B0%E5%A2%9E%E7%94%A8%E6%88%B7-%E6%9C%AA%E9%80%89%E6%8B%A9%E8%A7%92%E8%89%B2.png)</br>
图3-5新增用户-未选择角色

### 3.3.2添加角色后
在角色栏目下，点击相应的平台，并点击→键即可将平台下的角色选中，并且至少需要选择一个角色，然后系统将会红字提醒用户输入正确格式的用户信息。如图3-6所示：</br>
![新增用户-添加角色后](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%96%B0%E5%A2%9E%E7%94%A8%E6%88%B7-%E6%B7%BB%E5%8A%A0%E8%A7%92%E8%89%B2.png)</br>
图3-6新增用户-添加角色后

## 3.4编辑用户
在用户管理栏目下，点击每一个用户信息右侧的铅笔图标即可进入编辑用户界面，如图3-7所示：</br>
![编辑用户](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E7%BC%96%E8%BE%91%E7%94%A8%E6%88%B7.png)</br>
图3-7编辑用户

## 3.5用户详情
在用户管理栏目下，点击每一个用户信息右侧的书本图标即可进入用户详情界面，如图3-8所示：</br>
![用户详情](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E7%94%A8%E6%88%B7%E8%AF%A6%E6%83%85.png)</br>
图3-8用户详情

## 3.6新密码输入不一致
当确认新密码和新密码内容不一致时，系统也会报错，避免用户由于人为失误导致忘记密码。如图3-9所示：</br>
![原密码错误](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%8E%9F%E5%AF%86%E7%A0%81%E9%94%99%E8%AF%AF.png)</br>
图3-9 原密码错误
## 3.7找回密码
在登录界面点击忘记密码后，即可进入找回密码的界面，如图3-10所示：</br>
![找回密码](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%89%BE%E5%9B%9E%E5%AF%86%E7%A0%81-%E7%99%BB%E5%BD%95%E7%95%8C%E9%9D%A2.png)</br>
图3-10 找回密码
## 3.8找回密码—格式错误
当用户未按照要求输入正确的用户名，验证码，或者修改后的新密码格式错误时，将被拒绝找回，避免了用户误触导致密码丢失和外部人员强行修改密码。如图3-11所示：</br>                                                          ![找回密码-格式错误](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%89%BE%E5%9B%9E%E5%AF%86%E7%A0%81-%E6%A0%BC%E5%BC%8F%E9%94%99%E8%AF%AF.png)</br>                             
图3-11 找回密码-格式错误
## 3.9找回密码备份—输入密码不一致
在找回密码设置新密码时，如果用户两次输入的密码不一致，也将被拒绝修改密码。如图3-12所示：</br>
![找回密码备份-输入密码不一致](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%89%BE%E5%9B%9E%E5%AF%86%E7%A0%81%E5%A4%87%E4%BB%BD-%E8%BE%93%E5%85%A5%E4%B8%8D%E4%B8%80%E8%87%B4.png)</br>
图3-12 找回密码备份-输入密码不一致


# 4.角色管理
## 4.1 角色管理
### 4.1.1所属应用下拉
进入角色管理栏目后，点击选择所属应用下拉框，如图4-1所示：</br>
![所属应用下拉](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%89%80%E5%B1%9E%E5%BA%94%E7%94%A8%E4%B8%8B%E6%8B%89.png)</br>
图4-1所属应用下拉
### 4.1.2确认删除
在角色管理栏目下，点击用户信息一行最右侧的垃圾桶图标，即可对该用户进行信息删除，如图4-2所示：</br>
![确认删除](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%89%80%E5%B1%9E%E5%BA%94%E7%94%A8-%E7%A1%AE%E8%AE%A4%E5%88%A0%E9%99%A4.png)</br>
图4-2确认删除
### 4.1.3复制角色
在角色管理栏目下，点击用户信息一行操作列的两个加号图标，即可对该用户进行角色复制，如图4-3所示：</br>
![复制角色](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%89%80%E5%B1%9E%E5%BA%94%E7%94%A8-%E5%A4%8D%E5%88%B6%E8%A7%92%E8%89%B2.png)</br>
图4-3复制角色
## 4.2 新增角色
### 4.2.1未选择
在角色管理栏目下，点击右上角的新增角色按钮，即可进入新增角色界面，如图4-4所示：</br>
![新增角色-未选择](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%96%B0%E5%A2%9E%E8%A7%92%E8%89%B2-%E6%9C%AA%E9%80%89%E6%8B%A9.png)</br>
图4-4新增角色-未选择
### 4.2.2报错提示
当你未按要求输入对应信息就按下确定按钮，系统则会报错提示，如图4-5所示：</br>
![新增角色-报错提示](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%96%B0%E5%A2%9E%E8%A7%92%E8%89%B2-%E6%8A%A5%E9%94%99.png)</br>
图4-5新增角色-报错提示
### 4.2.3新增角色示例
#### 4.2.3.1物联网平台
![新增角色-物联网平台](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E7%89%A9%E8%81%94%E7%BD%91%E5%B9%B3%E5%8F%B0.png)</br>
图4-6新增角色-物联网平台
#### 4.2.3.2业务平台
![新增角色-业务平台](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E4%B8%9A%E5%8A%A1%E5%B9%B3%E5%8F%B0.png)</br>
图4-7新增角色-业务平台
### 4.2.5选择部分权限
![选择部分权限](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%96%B0%E5%A2%9E%E8%A7%92%E8%89%B2-%E9%80%89%E6%8B%A9%E9%83%A8%E5%88%86%E6%9D%83%E9%99%90.png)</br>
图4-8新增角色-选择部分权限
### 4.2.6选择部分权限-报错
![选择部分权限-报错](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%96%B0%E5%A2%9E%E8%A7%92%E8%89%B2-%E6%8A%A5%E9%94%99.png)</br>
图4-9新增角色-选择部分权限-报错
## 4.3 编辑角色
在角色管理栏目下，点击用户信息一行操作列的铅笔图标，即可对该角色进行编辑</br>
![编辑角色](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%96%B0%E5%A2%9E%E8%A7%92%E8%89%B2-%E7%BC%96%E8%BE%91%E8%A7%92%E8%89%B2.png)</br>
图4-10编辑角色
## 4.4 角色详情
在角色管理栏目下，点击用户信息一行操作列的书本图标，即可对该角色的详情信息进行查看，如图4-11所示：</br>
![角色详情](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%96%B0%E5%A2%9E%E8%A7%92%E8%89%B2-%E8%A7%92%E8%89%B2%E8%AF%A6%E6%83%85.png)</br>
图4-11角色详情
# 5.应用管理
## 5.1 应用管理
### 5.1.1应用管理-下拉
在应用管理栏目下，可以点击认证方式、权限模式和应用名称来对所有应用进行筛选，也可以点击详情操作来查看应用的以下四个板块的详情信息，如图5-1所示：</br>
![应用管理-下拉](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%BA%94%E7%94%A8%E7%AE%A1%E7%90%86-%E4%B8%8B%E6%8B%89.png)</br>
图5-1应用管理-下拉
### 5.1.2 新增应用
在应用管理栏目下，点击右上角的新增应用按钮，即可进入新增应用界面，如图5-2所示：</br>
![新增应用](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%BA%94%E7%94%A8%E7%AE%A1%E7%90%86-%E6%96%B0%E5%A2%9E%E5%BA%94%E7%94%A8.png)</br>
图5-2新增应用
### 5.1.3 编辑应用
点击某个应用的详情，在应用信息板块下点击编辑应用，即可进入编辑应用界面，如图5-3所示：</br>
![编辑应用](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%BA%94%E7%94%A8%E7%AE%A1%E7%90%86%E7%BC%96%E8%BE%91%E5%BA%94%E7%94%A8.png)</br>
图5-3编辑应用


## 5.2 认证管理
### 5.2.1 编辑认证管理
点击某个应用的详情，在认证管理板块下点击认证配置，即可进入编辑认证管理界面，如图5-4所示：</br>
![编辑认证管理](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E7%BC%96%E8%BE%91%E8%AE%A4%E8%AF%81%E7%AE%A1%E7%90%86.png)</br>
图5-4编辑认证管理
## 5.3 功能权限录入
### 5.3.1新增模块
点击某个应用的详情，在权限录入板块下点击编辑权限，进入权限录入界面，在该界面下，点击右上角的新增模块，即可进入新增模块界面，如图5-5所示：</br>
![新增模块](https://github.com/celldata/Membrane/blob/master/image/quickstart/5-5%E5%8A%9F%E8%83%BD%E6%9D%83%E9%99%90-%E6%96%B0%E5%A2%9E%E6%A8%A1%E5%9D%97.png)</br>
图5-5新增模块
### 5.3.2新增模块后
新增模块后，将会在该平台下建立一个模块，管理者可以通过点击下拉框来查看该模块，如图5-6所示：</br>
![新增模块后](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%8A%9F%E8%83%BD%E6%9D%83%E9%99%90%E2%80%94%E6%96%B0%E5%A2%9E%E6%A8%A1%E5%9D%97%E5%90%8E.png)</br>
图5-6新增模块后

### 5.3.3新增功能
通过下拉框点击建立的模块后，再点击右上角的新增功能按钮，即可进入新增功能界面，如图5-7所示：</br>
![新增功能](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%8A%9F%E8%83%BD%E6%9D%83%E9%99%90-%E6%96%B0%E5%A2%9E%E5%8A%9F%E8%83%BD.png)</br>
图5-7新增功能
### 5.3.4展示模块
点击平台后不进入某个模块，即可浏览该平台的所有模块名称，如图5-8所示：</br>
![展示模块](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%8A%9F%E8%83%BD%E6%9D%83%E9%99%90-%E5%B1%95%E7%A4%BA%E6%A8%A1%E5%9D%97.png)</br>
图5-8展示模块

## 5.4 数据权限配置
数据权限配置是用户在对应的应用下，须先配置资源及属性后，在根据用户自身需求，去条件配置中选择之前已配置好的资源及属性，生成针对权限设置的表达式，方可在业务端根据配置去管理权限。
### 5.4.1 资源管理
5.4.1.1 资源列表查看
用户可在资源列表中，可根据资源类型“非顶级资源、顶级资源”进行筛选查看，也可以使用分页进行筛选，如下图所示：</br>
![资源列表](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E8%B5%84%E6%BA%90%E7%AE%A1%E7%90%86-%E8%B5%84%E6%BA%90%E5%88%97%E8%A1%A8.png)</br>
图 5-9 资源列表
#### 5.4.1.2 新增资源
点击“新增资源”按钮，可在弹出的对话框中，填写符合要求的资源信息：如下图所示：</br>
![新增资源](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E8%B5%84%E6%BA%90%E7%AE%A1%E7%90%86-%E6%96%B0%E5%A2%9E%E8%B5%84%E6%BA%90.png)</br>
图 5-10 新增资源
提示：标识符是唯一性的。
#### 5.4.1.3 编辑资源
在“资源列表”中点击“编辑”图标，可对选择的资源信息进行编辑，如下图所示：</br>
![编辑资源](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E8%B5%84%E6%BA%90%E7%AE%A1%E7%90%86-%E7%BC%96%E8%BE%91%E8%B5%84%E6%BA%90.png)</br>
图 5-11 编辑资源

#### 5.4.1.4 资源信息查看
点击资源列表中“资源名称”所在的列中某一个资源名称，可进入资源详情页，如下图所示：</br>
![资源详情](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E8%B5%84%E6%BA%90%E7%AE%A1%E7%90%86-%E8%B5%84%E6%BA%90%E8%AF%A6%E6%83%85.png)</br>
图 5-12 资源详情

#### 5.4.1.5 删除资源
点击某条资源列表中的删除图标，对未进行条件配置的资源进行删除，如下图所示：</br>
![删除资源](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E8%B5%84%E6%BA%90%E7%AE%A1%E7%90%86-%E5%88%A0%E9%99%A4%E8%B5%84%E6%BA%90.png)</br>
图5-13 删除资源
### 5.4.2 属性管理
#### 5.4.2.1 属性列表查看
在某条资源详情页，可查看已配置的属性列表，如下图所示：</br>
![属性列表](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%B1%9E%E6%80%A7%E7%AE%A1%E7%90%86-%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8.png)</br>
图5-14 属性列表
#### 5.4.2.2 新增属性
点击“添加属性”按钮，弹出对话框，在对话框中，输入符合要求的属性信息，如下图所示：</br>
![添加属性](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%B1%9E%E6%80%A7%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0%E5%B1%9E%E6%80%A7.png)</br>
图5-15 添加属性
#### 5.4.2.3 编辑属性
点击“编辑”图标，可对属性信息进行编辑，如下图所示：</br>
![编辑属性](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%B1%9E%E6%80%A7%E7%AE%A1%E7%90%86-%E7%BC%96%E8%BE%91%E5%B1%9E%E6%80%A7.png)</br>
图5-16 编辑属性
注意：已在条件配置中使用的属性，不可删除，且在编辑信息时，不能编辑该属性的标识符及修改数据类型，仅可修改属性名称及描述。
#### 5.4.2.4 删除属性
未在条件中使用的属性，可删除，一旦在条件中使用，该属性不可删除，除非修改条件配置。，如下图所示：</br>
![属性已被条件使用](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%B1%9E%E6%80%A7%E7%AE%A1%E7%90%86-%E5%88%A0%E9%99%A4%E5%B1%9E%E6%80%A7.png)</br>
图5-17 属性已被条件使用</br>
![属性未被条件使用](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%B1%9E%E6%80%A7%E7%AE%A1%E7%90%86-%E5%88%A0%E9%99%A4%E5%B1%9E%E6%80%A7-%E6%9C%AA%E8%A2%AB%E6%9D%A1%E4%BB%B6%E4%BD%BF%E7%94%A8.png)</br>
图5-18 属性未被条件使用
### 5.4.3 条件配置
条件配置是应用与数据权限中的核心功能，是用于与业务系统对接中数据是否显示的唯一条件，在条件配置中，会使用到之前配置的资源及属性拼合而成。
#### 5.4.3.1 条件列表查看
条件配置是属于某个具体的应用下，在列表页我们能看到条件配置的具体信息，如下图所示：</br>
![条件列表](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%9D%A1%E4%BB%B6%E9%85%8D%E7%BD%AE-%E6%9D%A1%E4%BB%B6%E5%88%97%E8%A1%A8.png)</br>
图5-19 条件列表
#### 5.4.3.2 新增条件
点击“自定义条件配置”按钮，弹出条件配置对话框，如下图所示：</br>
![添加条件配置](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%9D%A1%E4%BB%B6%E9%85%8D%E7%BD%AE-%E6%B7%BB%E5%8A%A0%E6%9D%A1%E4%BB%B6%E9%85%8D%E7%BD%AE.png)</br>
图5-20 添加条件配置
录入规则：是条件，是需要选择资源、属性及逻辑操作符及对应的值，
属性可见性：支持多选，选择这条 条件返回给用户可看到的有哪些字段。
#### 5.4.3.3 编辑条件
点击列表中的某条数据，可编辑该条数据的条件配置信息，如下图所示：</br>
![编辑条件](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%9D%A1%E4%BB%B6%E9%85%8D%E7%BD%AE-%E7%BC%96%E8%BE%91%E6%9D%A1%E4%BB%B6.png)</br>
图5-21 编辑条件
#### 5.4.3.4 删除条件
对已配置的条件，进行删除：如下图所示：</br>
![删除条件](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%9D%A1%E4%BB%B6%E9%85%8D%E7%BD%AE-%E5%88%A0%E9%99%A4%E6%9D%A1%E4%BB%B6.png)</br>
图5-22 删除条件
## 5.5 认证中心
认证中心是根据多种外部认证类型，支持管理（添加、编辑、删除）多种认证源，对已添加的不同类型的外部认证源，可在应用管理的认证配置中，选择外部认证源，一旦选择后，在进行身份校验时，会根据选择的外部认证源进行认证校验，例如：LDAP认证类型的某个认证源，企业用户在选择使用LDAP类型某一个认证源后，用户在SSO登录时，内部逻辑是根据登录用户判断认证源，根据判断是LDAP认证源后，校验行为会放在用户当初填写的LDAP服务器上进行校验判断，校验通过，会告知SSO，并放行，且在SSO数据库中针对该用户的一些信息进行保存。
### 5.5.1 添加认证源
点击“添加认证源”按钮，弹出“添加认证源”对话框，输入认证源信息，如下图所示：</br>
新增认证源分为两种信息填写：一种是基础信息填写，一种是服务器配置信息填写：</br>
1）基础信息：</br>
认证类型【必填项】：目前有CAS、LDAP（本Sprint只接入LDAP）、SAML、OAUTH；</br>
认证源名称【必填项】：长度为2-20个字符；</br>
描述【非必填项】：长度不超过30个字符；</br>
2）服务器配置：</br>
LDAP URL【必填项】：</br>
LDAP Base【必填项】：</br>
LDAP UserDn【必填项】：</br>
LDAP 密码【必填项】：</br>
过滤条件【非必填项】：</br>
![新增认证源](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%B7%BB%E5%8A%A0%E8%AE%A4%E8%AF%81%E6%BA%90-%E6%96%B0%E5%A2%9E%E8%AE%A4%E8%AF%81%E6%BA%90.png)</br>
图5-9新增认证源
### 5.5.2 编辑认证源信息
![编辑认证源](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%B7%BB%E5%8A%A0%E8%AE%A4%E8%AF%81%E6%BA%90-%E7%BC%96%E8%BE%91%E8%AE%A4%E8%AF%81%E6%BA%90.png)</br>
图5-10编辑认证源
### 5.5.3 删除认证源
1）批量删除：</br>
“批量删除”按钮在checkbox未被选中时，是不可点击的，只有在选中后，才可点击，当点击删除时，会出来提示弹框再次让用户确认，确认成功后，才可删除，已被使用的认证源不可删除，除非在应用管理中对使用的认证源进行解绑(更换成其他的认证源)才可删除。</br>
2）单个删除</br>
当点击删除时，会出来提示弹框再次让用户确认，确认成功后，才可删除，已被使用的认证源不可删除，除非在应用管理中对使用的认证源进行解绑(更换成其他的认证源)才可删除。</br>
![批量删除认证源](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E6%B7%BB%E5%8A%A0%E8%AE%A4%E8%AF%81%E6%BA%90-%E6%89%B9%E9%87%8F%E5%88%A0%E9%99%A4%E8%AE%A4%E8%AF%81%E6%BA%90.png)</br>
图5-11批量删除认证源

### 5.5.4 认证源列表查询
1）列表页：
列表页汇聚所有的认证信息查看，支持按照外部认证类型、认证名称检索查看。
支持分页查看
2）详情页：
图5-12认证源列表</br>
![找回密码](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E8%AE%A4%E8%AF%81%E6%BA%90%E5%88%97%E8%A1%A8.png)</br>
图5-13认证源详情</br>
![认证源详情](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E8%AE%A4%E8%AF%81%E6%BA%90%E8%AF%A6%E6%83%85.png)
### 5.5.5 应用与认证源绑定
在应用管理中，认证管理弹出框中，当选择外部认证时，外部认证源select展示，目前只接入了LADP类型的，目前只支持选中一种外部认证源。如下图所示：</br>
![应用与认证源绑定](https://github.com/celldata/Membrane/blob/master/image/quickstart/%E5%BA%94%E7%94%A8%E4%B8%8E%E8%AE%A4%E8%AF%81%E6%BA%90%E7%BB%91%E5%AE%9A.png)</br>


