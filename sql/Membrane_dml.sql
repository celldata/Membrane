/*
 *MIT License

Copyright (c) 2020 celldata

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

-- Records of sso_user,user : admin , password : membrane
INSERT INTO sso_user(ID,USER_NAME,PASSWORD,FULL_NAME,ACTIVE_FLAG,PRIVILEGE,ENABLE_FLAG,CREATOR_ID,CREATE_TIME)value(1,"admin","ca5af4478dc3b5ec230a3392a0459ef415a94732cc51e2e36b98f57d89cfca4d","管理员",1,1,1,1,NOW()) 

-- Records of sso_client,default APP_NAME : 权限管理平台
INSERT INTO sso_client(ID,APP_ID,APP_KEY,APP_NAME,APP_DESC,AUTHENTICATION,TOKEN_CHECK_TYPE,APP_SECRET_VALIDITY,APP_SECRET,ENABLE_FLAG,CREATOR_ID,CREATE_TIME)value(1,11111,11111,"权限管理系统","权限管理平台",0,0,15,UUID(),1,1,NOW())