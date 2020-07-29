/*
 Navicat Premium Data Transfer

 Source Server         : sso
 Source Server Type    : MariaDB
 Source Server Version : 50565
 Source Host           : 39.**.***.***:3306
 Source Schema         : membrane

 Target Server Type    : MariaDB
 Target Server Version : 50565
 File Encoding         : 65001

 Date: 24/07/2020 14:17:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sso_api
-- ----------------------------
DROP TABLE IF EXISTS `sso_api`;
CREATE TABLE `sso_api`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PARENT_ID` int(11) NULL DEFAULT NULL COMMENT '父级节点Id',
  `CLIENT_ID` int(11) NULL DEFAULT NULL COMMENT '平台Id',
  `URI` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问路径',
  `TYPE` int(1) NULL DEFAULT NULL COMMENT '功能类型 0[非查询]，1[查询]',
  `API_DESC` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述（即功能名称）',
  `API_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `API_NUM` int(100) NULL DEFAULT NULL,
  `API_IS_DATA` int(1) NULL DEFAULT NULL COMMENT '是否有数据权限 1有  0无',
  `ENABLE_FLAG` int(1) NULL DEFAULT NULL,
  `CREATOR_ID` int(11) NULL DEFAULT NULL,
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL,
  `UPDATER_ID` int(11) NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  `IS_TREE` int(1) NULL DEFAULT NULL COMMENT '是否叶子节点',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 417 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for sso_api_uri
-- ----------------------------
DROP TABLE IF EXISTS `sso_api_uri`;
CREATE TABLE `sso_api_uri`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `API_ID` int(11) NULL DEFAULT NULL COMMENT 'API表主键ID',
  `URI_ID` int(11) NULL DEFAULT NULL COMMENT 'URL表主键ID',
  `ENABLE_FLAG` int(1) NULL DEFAULT NULL,
  `CREATOR_ID` int(11) NULL DEFAULT NULL,
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL,
  `UPDATER_ID` int(11) NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 654 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CH


-- ----------------------------
-- Table structure for sso_client
-- ----------------------------
DROP TABLE IF EXISTS `sso_client`;
CREATE TABLE `sso_client`  (
  `ID` int(8) NOT NULL AUTO_INCREMENT,
  `APP_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `APP_KEY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `APP_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IMG_URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `APP_DESC` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用描述',
  `VERIFICATION` int(1) NULL DEFAULT NULL COMMENT '认证方式    0: \"CAS\" ,1: \"OAUTH\" , 2 :\"SAML\"',
  `AUTHENTICATION` int(1) NULL DEFAULT NULL COMMENT '认证状态 0: \"未认证\", 1: \"已认证\"',
  `TOKEN_CHECK_TYPE` int(1) NULL DEFAULT NULL COMMENT 'Token 验证方式 0:安全优先, 1:性能优先',
  `APP_SECRET_VALIDITY` int(8) NULL DEFAULT NULL COMMENT 'APP_SECRET 有效时间 单位分钟',
  `APP_SECRET` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EXTERNAL_AUTH_ID` int(8) NULL DEFAULT NULL COMMENT '外部数据源ID',
  `ENABLE_FLAG` int(1) NULL DEFAULT NULL,
  `CREATOR_ID` int(11) NULL DEFAULT NULL,
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL,
  `UPDATER_ID` int(11) NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- Table structure for sso_data
-- ----------------------------
DROP TABLE IF EXISTS `sso_data`;
CREATE TABLE `sso_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `client_id` int(11) NULL DEFAULT NULL COMMENT '应用ID',
  `scr_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表达式名称',
  `scr_rule` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '录入规则',
  `scr_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `scr_json` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '表达式json串',
  `att_idList` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '属性ID list',
  `enable_flag` int(1) NULL DEFAULT NULL COMMENT '是否删除 0代表删除 1代表不删除',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` int(11) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- Table structure for sso_data_attribute
-- ----------------------------
DROP TABLE IF EXISTS `sso_data_attribute`;
CREATE TABLE `sso_data_attribute`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config_id` int(11) NULL DEFAULT NULL COMMENT '数据权限基础配置ID',
  `attribute_tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标识符',
  `attribute_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '属性名',
  `attribute_type` int(2) NULL DEFAULT NULL COMMENT '数据类型 0 整数，1浮点，2布尔，3字符串，4枚举，5数组，6时间/日期',
  `attribute_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `enable_flag` int(1) NULL DEFAULT NULL COMMENT '是否删除 0删除 1不删除',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建Id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` int(11) NULL DEFAULT NULL COMMENT '编辑id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- Table structure for sso_data_config
-- ----------------------------
DROP TABLE IF EXISTS `sso_data_config`;
CREATE TABLE `sso_data_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `client_id` int(11) NULL DEFAULT NULL COMMENT '应用ID',
  `config_tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标识符',
  `config_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `config_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名称',
  `config_type` int(1) NULL DEFAULT NULL COMMENT '资源类型 0代表顶级资源 1代表非顶级资源',
  `enable_flag` int(1) NULL DEFAULT NULL COMMENT '是否删除 0代表删除 1代表不删除',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` int(11) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for sso_external_auth
-- ----------------------------
DROP TABLE IF EXISTS `sso_external_auth`;
CREATE TABLE `sso_external_auth`  (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(2) NULL DEFAULT NULL COMMENT '外部数据源类型：1 ldap',
  `name` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `auth_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'url',
  `base` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ldap base',
  `user_dn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ladp userDn',
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外部数据源服务器连接密码',
  `filter` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过滤条件',
  `syc_user` int(1) NULL DEFAULT NULL COMMENT '是否同步数据 0代表不同步 1代表同步',
  `syc_sche` int(1) NULL DEFAULT NULL COMMENT '是否使用定时任务同步数据 0代表不使用 1代表使用',
  `syn_fixed` int(2) NULL DEFAULT NULL COMMENT '定时任务 多长时间同步一次数据 单位小时',
  `enable_flag` int(1) NULL DEFAULT NULL COMMENT '逻辑删除 0代表删除 1代表未删除',
  `creator_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `updater_id` int(11) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- Table structure for sso_extra
-- ----------------------------
DROP TABLE IF EXISTS `sso_extra`;
CREATE TABLE `sso_extra`  (
  `EXTRA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(8) NULL DEFAULT NULL,
  `EXTRA_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EXTRA_VALUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EXTRA_DESC` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ENABLE_FLAG` int(1) NULL DEFAULT NULL,
  `CREATOR_ID` int(11) NULL DEFAULT NULL,
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL,
  `UPDATER_ID` int(11) NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`EXTRA_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for sso_role
-- ----------------------------
DROP TABLE IF EXISTS `sso_role`;
CREATE TABLE `sso_role`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CLIENT_ID` int(11) NULL DEFAULT NULL COMMENT '平台Id',
  `ROLE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `IS_ALL_API` int(1) NULL DEFAULT NULL COMMENT '是否为全部功能权限: 1代表全部权限 非1代表部分权限',
  `IS_ALL_DATA` int(1) NULL DEFAULT NULL COMMENT '是否为全部数据权限: 1代表全部权限 非1代表部分权限',
  `ENABLE_FLAG` int(1) NULL DEFAULT NULL,
  `CREATOR_ID` int(11) NULL DEFAULT NULL,
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL,
  `UPDATER_ID` int(11) NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for sso_role_api
-- ----------------------------
DROP TABLE IF EXISTS `sso_role_api`;
CREATE TABLE `sso_role_api`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) NULL DEFAULT NULL COMMENT '角色Id',
  `API_ID` int(11) NULL DEFAULT NULL COMMENT '功能Id',
  `ENABLE_FLAG` int(1) NULL DEFAULT NULL,
  `CREATOR_ID` int(11) NULL DEFAULT NULL,
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL,
  `UPDATER_ID` int(11) NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3628 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- Table structure for sso_role_data
-- ----------------------------
DROP TABLE IF EXISTS `sso_role_data`;
CREATE TABLE `sso_role_data`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) NULL DEFAULT NULL COMMENT '角色Id',
  `DATA_ID` int(11) NULL DEFAULT NULL COMMENT '项目Id',
  `ENABLE_FLAG` int(1) NULL DEFAULT NULL,
  `CREATOR_ID` int(11) NULL DEFAULT NULL,
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL,
  `UPDATER_ID` int(11) NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- Table structure for sso_scr_att_relation
-- ----------------------------
DROP TABLE IF EXISTS `sso_scr_att_relation`;
CREATE TABLE `sso_scr_att_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `scr_id` int(11) NULL DEFAULT NULL COMMENT '条件ID',
  `con_id` int(11) NULL DEFAULT NULL COMMENT '资源ID',
  `att_id` int(11) NULL DEFAULT NULL COMMENT '属性ID',
  `enable_flag` int(1) NULL DEFAULT NULL COMMENT '是否删除 0代表删除 1代表不删除',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` int(11) NULL DEFAULT NULL COMMENT '编辑用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- Table structure for sso_uri
-- ----------------------------
DROP TABLE IF EXISTS `sso_uri`;
CREATE TABLE `sso_uri`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `URI` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问路径',
  `TYPE` int(1) NULL DEFAULT NULL COMMENT '0[非查询]，1[查询]',
  `ENABLE_FLAG` int(1) NULL DEFAULT NULL,
  `CREATOR_ID` int(11) NULL DEFAULT NULL,
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL,
  `UPDATER_ID` int(11) NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 779 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- Table structure for sso_user
-- ----------------------------
DROP TABLE IF EXISTS `sso_user`;
CREATE TABLE `sso_user`  (
  `ID` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `PASSWORD` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `FULL_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `TELEPHONE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `EMAIL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Email',
  `LOGIN_NUM` int(11) NULL DEFAULT NULL COMMENT '登录次数',
  `ACTIVE_FLAG` int(1) NULL DEFAULT NULL COMMENT '是否禁用：1[启用]，2[禁用]',
  `PRIVILEGE` int(1) NULL DEFAULT NULL COMMENT '是否权限管理员：0[无权限管理角色]；1[有权限管理角色]',
  `AUTHENTICATION` int(8) NULL DEFAULT NULL COMMENT '外部数据源 用户类型',
  `EXTERNAL_AUTH_ID` int(8) NULL DEFAULT NULL COMMENT '外部数据源 ID',
  `ENABLE_FLAG` int(1) NULL DEFAULT NULL COMMENT '是否失效',
  `CREATOR_ID` int(11) NULL DEFAULT NULL,
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL,
  `UPDATER_ID` int(11) NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 193 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- Table structure for sso_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sso_user_role`;
CREATE TABLE `sso_user_role`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `ROLE_ID` int(11) NULL DEFAULT NULL COMMENT '角色Id',
  `ENABLE_FLAG` int(1) NULL DEFAULT NULL,
  `CREATOR_ID` int(11) NULL DEFAULT NULL,
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL,
  `UPDATER_ID` int(11) NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37530 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;









