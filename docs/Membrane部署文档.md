# 部署文档
## 一、环境准备    						

```
系统：linux7.6+
Cpu：2核+ 
内存：8G+
Java：1.8.0+
Npm：6.4.1+
Mvn：3.6.0+
Nodejs：10.13.0+

```


## 二．编译打包项目
项目拉取地址 https://github.com/celldata/Membrane.git

拉取到本地后
- 编译前端 
- 进入到前端代码的目录
```
npm install 
npm run build
```
打包后端代码

```
mvn clean package
```
## 三．搭建nginx（version 1.16.1）

```
yum –y install nginx
vim /etc/nginx/conf.d/membrane.conf #编辑nginx的配置文件

```

```
server {
        listen 80;
        server_name localhost;

        location / {

#add_header Access-Control-Allow-Origin *;
#  add_header Access-Control-Allow-Headers "Origin, X-Requested-With, Content-Type, Accept";
#  add_header Access-Control-Allow-Methods "GET, POST, OPTIONS";
#add_header origin "http://sso.dev.lsctl.com";
                root /data/ssoq/dist/;       #注：这个是打包好的前端的存放路径
                try_files $uri $uri/ @router;
                index index.html;
        }

        location @router {
                rewrite ^.*$ /index.html last;
        }

        location /api/ {
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header Host $host;
                proxy_set_header Origin "http://127.0.0.1";
                proxy_pass http://127.0.0.1:8085/api/;
        }

        location /sso/api/ {
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header Host $host;
                proxy_set_header Origin "http://127.0.0.1";
                proxy_pass http://127.0.0.1:8085/api/;
        }
}

```


## 四．搭建MariaDB（version 5.5.65）数据库

```
yum –y install mariadb
systemctl start mariadb  #启动数据库

#进入数据库添加后端相关的数据库以及添加数据库的密码跟客户端连接用户
MariaDB [(none)]> CREATE DATABASE membrane;
MariaDB [(none)]> update user set password=password(‘你的密码‘) where user=‘你的用户‘;
MariaDB [(none)]> Grant all privileges on *.* to '你的用户'@'%' identified by '你的密码' with grant option;
MariaDB [(none)]> use membrane;
#找到sql文件夹绝对路径，用source命令导入建表语句
MariaDB [(none)]> source ./sql/Membrane_ddl.sql
MariaDB [(none)]> source ./sql/Membrane_dml.sql
MariaDB [(none)]> flush privileges;
MariaDB [(none)]> exit；
```


## 五．搭建redis（version 3.2.12）

```
yum –y install redis
#启动redis
redis-server 
#进入redis给redis设置密码
config set requirepass 你的密码
```

## 六．部署membrane后台项目
在打包好的jar包里面修改MySQL和redis相关的配置，添加sso登陆页面地址。

编辑其中application-dev.yml 文件

```
server:
  port: 8085
spring:
  datasource:
    driverClassName : com.mysql.cj.jdbc.Driver
# mysql配置
    url: jdbc:mysql://127.0.0.1:3306/membrane?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    username: 
    password: 
# redis配置
  redis:
    host: 127.0.0.1
    port: 6379
    jedis:
      pool:
        max-active: 20
        min-idle: 20
        max-wait: 1ms
        max-idle: 8
    timeout: 100000
    database: 6
#  放行前端 options 请求
#  mvc:
#    dispatch-options-request: true
#  邮箱配置
  mail:
    host: smtp.qq.com
    port: 25
    username: xxxxxx@qq.com
    password: 
    default-encoding: utf-8


redis_message_topic: __keyevent@0__:expired
#sso登陆页面地址
sso_request_url: 

```


## 七．启动项目

```
启动nginx命令： nginx
启动redis命令： redis-server &
启动数据库命令： systemctl start mariadb
```

启动项目membrane-0.0.1-SNAPSHOT.jar：脚本如下

==注：启动jar的脚本必须跟jar包在同一级目录下==

```
vim start.sh
```

```
#!/bin/bash
#==============================================================#
#   @Program    : start.sh                                     #
#   @Version    : 0.1                                          #
#   @Writer     :                                              #
#   @Date       :                                              #
#==============================================================#
export PATH=$PATH:/usr/sbin:/usr/bin:/bin/:/sbin
#--------------------------------------------------------------#

DIR="./"
app=`ls |grep .jar$`
OPTION=""

post_start() {
        echo -e "\033[34m \033[1m ===== start ${app} =====  \033[34m \033[0m"
        nohup java -jar ${OPTION} ${app} > ${app}.out 2>&1 &

        [ $? -eq 0 ] && echo -e "\033[32m \033[1m ${app} is ok ! \033[32m \033[0m" || echo -e "\033[31m \033[1m START ERROR!!! \033[31m\033[0m"
}

post_stop() {
        echo -e "\033[34m \033[1m =====> stop ${app}  \033[34m \033[0m"
        ps aux |grep -w "java -jar ${OPTION}${app}" |grep -v grep |awk '{print $2}'|xargs -i kill -9 {}
        [ $? -eq 0 ] && echo -e "\033[32m \033[1m ${app} stop \033[32m \033[0m"  || echo -e "\033[31m \033[1m STOP ERROR!!! \033[31m \033[0m"
}

if [ $# != 1 ];then
    echo -e "\nNeed one parameter only:\n"
    echo -e "Usage: sh $0 {start|stop}\n"
    exit 1
fi

case ${1} in
        start)
               cd ${DIR}
                post_start
                ;;
        stop)
                cd ${DIR}
                post_stop
                ;;
        stop)
                cd ${DIR}
                post_stop
                ;;
        restart)
                cd ${DIR}
                post_stop
                post_start
                ;;
        *)
                echo  "ERROR!!! "
                ;;
esac
```

```
#脚本授权
chmod +x start.sh
#运行脚本
./start.sh start
```

## 八．检查SSO项目是否正常启动

```
ps aux | grep jar

ss –lntp 
```

访问本机的80端口登陆权限管理平台

初始化账号密码是： 账号 admin  密码 membrane      

登陆后可以修改密码











