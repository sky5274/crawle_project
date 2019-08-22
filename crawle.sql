/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : crawle

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-07-16 12:04:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'mingc',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `createId` int(11) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of config
-- ----------------------------

-- ----------------------------
-- Table structure for config_auth
-- ----------------------------
DROP TABLE IF EXISTS `config_auth`;
CREATE TABLE `config_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '权限编码',
  `auth_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '权限名称',
  `vail_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT 'Y' COMMENT '是否有效',
  `createid` int(11) DEFAULT NULL,
  `version` int(10) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限表';

-- ----------------------------
-- Records of config_auth
-- ----------------------------
INSERT INTO `config_auth` VALUES ('1', 'AUTH_OPERATION', '权限操作', 'Y', '12', '4', '2019-04-24 15:28:57');
INSERT INTO `config_auth` VALUES ('2', 'AUTH_ADD_USER', '添加用户', 'Y', '12', '0', '2019-04-22 09:16:09');
INSERT INTO `config_auth` VALUES ('3', 'USER_OPTION', '用户操作权限', 'Y', '12', '0', '2019-05-07 15:23:44');

-- ----------------------------
-- Table structure for config_auth_relate
-- ----------------------------
DROP TABLE IF EXISTS `config_auth_relate`;
CREATE TABLE `config_auth_relate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色编码',
  `auth_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '权限编码',
  `vail_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT 'Y' COMMENT '是否有效',
  `createid` int(11) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限-角色关联表';

-- ----------------------------
-- Records of config_auth_relate
-- ----------------------------
INSERT INTO `config_auth_relate` VALUES ('5', 'test', 'AUTH_OPERATION', 'Y', '12', null);

-- ----------------------------
-- Table structure for config_detail
-- ----------------------------
DROP TABLE IF EXISTS `config_detail`;
CREATE TABLE `config_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `key` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `createid` int(11) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of config_detail
-- ----------------------------

-- ----------------------------
-- Table structure for config_log_file
-- ----------------------------
DROP TABLE IF EXISTS `config_log_file`;
CREATE TABLE `config_log_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `path` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='爬虫日子记录表';

-- ----------------------------
-- Records of config_log_file
-- ----------------------------

-- ----------------------------
-- Table structure for config_role
-- ----------------------------
DROP TABLE IF EXISTS `config_role`;
CREATE TABLE `config_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `vail_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT 'Y' COMMENT '角色名称',
  `createid` int(11) DEFAULT NULL,
  `version` int(10) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

-- ----------------------------
-- Records of config_role
-- ----------------------------
INSERT INTO `config_role` VALUES ('3', 'test', '测试', 'Y', '12', '0', '2019-04-17 16:25:03');
INSERT INTO `config_role` VALUES ('4', 'client-user', '测试用户', 'Y', '12', '0', '2019-04-24 15:58:12');

-- ----------------------------
-- Table structure for config_table
-- ----------------------------
DROP TABLE IF EXISTS `config_table`;
CREATE TABLE `config_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT '' COMMENT '表名',
  `table_code` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '表编码',
  `group_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '分组',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '描述',
  `createId` int(11) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='前端表设计--表名';

-- ----------------------------
-- Records of config_table
-- ----------------------------
INSERT INTO `config_table` VALUES ('18', '测试', 'test_demo', 'test', 'ceshi', '-1', '1', '2019-01-06 14:25:41');

-- ----------------------------
-- Table structure for config_table_column
-- ----------------------------
DROP TABLE IF EXISTS `config_table_column`;
CREATE TABLE `config_table_column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tableid` int(11) DEFAULT NULL COMMENT '表id',
  `attr` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '列名',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `is_null` int(11) DEFAULT '0',
  `is_primary` int(11) DEFAULT '0',
  `createId` int(11) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='前端表设计--列名';

-- ----------------------------
-- Records of config_table_column
-- ----------------------------
INSERT INTO `config_table_column` VALUES ('1', '18', 'id', '主键', 'int', '0', '1', '-1', '2019-01-06 14:25:41');
INSERT INTO `config_table_column` VALUES ('2', '18', 'name', '名称', 'varchart', '0', '0', '-1', '2019-01-06 14:25:41');
INSERT INTO `config_table_column` VALUES ('3', '18', 'key', '键', 'varchart', '0', '0', '-1', '2019-01-06 14:25:41');

-- ----------------------------
-- Table structure for config_user_role_relate
-- ----------------------------
DROP TABLE IF EXISTS `config_user_role_relate`;
CREATE TABLE `config_user_role_relate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `createid` int(11) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户-角色关联表';

-- ----------------------------
-- Records of config_user_role_relate
-- ----------------------------
INSERT INTO `config_user_role_relate` VALUES ('2', '6', 'test', '12', '2019-05-05 09:50:06');
INSERT INTO `config_user_role_relate` VALUES ('3', '6', 'client-user', '12', '2019-05-05 09:50:06');

-- ----------------------------
-- Table structure for crawler_config
-- ----------------------------
DROP TABLE IF EXISTS `crawler_config`;
CREATE TABLE `crawler_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '调用的class',
  `depth` int(11) DEFAULT '5',
  `top_n` int(11) DEFAULT '10',
  `threads` int(11) DEFAULT '10',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `js` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '调用的js',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `config_code_unqine` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='爬虫配置主表';

-- ----------------------------
-- Records of crawler_config
-- ----------------------------

-- ----------------------------
-- Table structure for crawler_config_filter
-- ----------------------------
DROP TABLE IF EXISTS `crawler_config_filter`;
CREATE TABLE `crawler_config_filter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `config_code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '配置编码',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `value` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '过滤条件',
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='爬虫配置--过滤器配置';

-- ----------------------------
-- Records of crawler_config_filter
-- ----------------------------

-- ----------------------------
-- Table structure for crawler_config_url
-- ----------------------------
DROP TABLE IF EXISTS `crawler_config_url`;
CREATE TABLE `crawler_config_url` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `config_code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '配置编码',
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '请求方法',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `condtion` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'url生成条件',
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='爬虫配置--爬取的url';

-- ----------------------------
-- Records of crawler_config_url
-- ----------------------------

-- ----------------------------
-- Table structure for job_task
-- ----------------------------
DROP TABLE IF EXISTS `job_task`;
CREATE TABLE `job_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `task_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `target_class` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `group_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `cron_expression` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `json_params` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `status` int(2) DEFAULT '0',
  `run_type` int(2) DEFAULT '0',
  `run_times` int(10) DEFAULT '0',
  `run_err_times` int(2) DEFAULT '0',
  `run_err_msg` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `schedule_model` int(2) DEFAULT '0',
  `limit_target_node` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `version` int(3) DEFAULT '0',
  `createid` int(11) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='任务调度--执行任务';

-- ----------------------------
-- Records of job_task
-- ----------------------------
INSERT INTO `job_task` VALUES ('1', 'test_1234567', 'ceshi', 'com.sky.task.demo.SimpleJob', '1.0.0_dev.test', null, '{\"id\":2,\"name\":\"tome\"}', '0', '0', '7', '0', null, '0', null, null, '13', '-1', '2019-02-19 13:18:47');
INSERT INTO `job_task` VALUES ('2', '7b5b0c5a97c040b4bd970b0f91c0c971', 'job1', 'com.sky.task.demo.SimpleJob', '1.0.0_dev.test', '* * * * * ? ', null, '0', '0', '1', '0', null, '0', null, 'cehiix', '2', '-1', '2019-02-19 10:34:48');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pid` int(11) NOT NULL,
  `createId` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_name_same` (`code`,`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='爬虫配置--目录';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', 'sys_set', '设置', '0', null, '/config/page/setting', '0', '2018-12-29 14:50:27');
INSERT INTO `menu` VALUES ('2', 'sys_config', '配置', '0', null, '#', '0', '2018-12-23 18:37:11');
INSERT INTO `menu` VALUES ('37', 'sys_config_table', '数据库配置', '2', '-1', '/config/page/table/index', '2', '2018-12-29 10:08:59');
INSERT INTO `menu` VALUES ('40', 'sys_sql_w', 'sql书写', '2', '-1', '/config/page/sql/index', '1', '2019-01-07 16:26:31');
INSERT INTO `menu` VALUES ('41', 'sys_crwaler_code', '爬虫设置', '2', '-1', '/config/page/crawler/index', '1', '2019-01-18 09:42:52');

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(250) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(250) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------

-- ----------------------------
-- Table structure for property_value
-- ----------------------------
DROP TABLE IF EXISTS `property_value`;
CREATE TABLE `property_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `value` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `project` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目名称',
  `profile` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目环境（分支）',
  `version_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目版本',
  `is_global` int(3) DEFAULT '0' COMMENT '是否全局',
  `version` int(10) DEFAULT '0',
  `createid` int(11) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='链路--服务属性配置';

-- ----------------------------
-- Records of property_value
-- ----------------------------
INSERT INTO `property_value` VALUES ('1', 'service.name', 'demo_time', 'demo', 'dev', null, '0', '0', null, '2019-03-08 09:46:20');
INSERT INTO `property_value` VALUES ('2', 'app.name', 'appces', 'demo_client', 'test', '1.0.0', '0', '0', null, '2019-03-08 15:31:02');
INSERT INTO `property_value` VALUES ('3', 'app_client', 'test-1', 'demo', 'dev', '1.0', '1', '7', null, '2019-05-11 15:08:32');
INSERT INTO `property_value` VALUES ('4', 'app.profile', 'dev', 'demo', 'test', '1.0', '0', '0', null, '2019-05-11 15:33:01');

-- ----------------------------
-- Table structure for property_value_his
-- ----------------------------
DROP TABLE IF EXISTS `property_value_his`;
CREATE TABLE `property_value_his` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `value` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `project` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `profile` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `version_code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `is_global` int(3) DEFAULT '0' COMMENT '是否是全局',
  `pid` int(11) DEFAULT NULL,
  `version` int(10) DEFAULT '0',
  `createid` int(11) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='链路--服务属性配置历史';

-- ----------------------------
-- Records of property_value_his
-- ----------------------------
INSERT INTO `property_value_his` VALUES ('7', 'app_client', 'test-1', 'demo', 'dev', '1.0', '0', '3', '0', null, '2019-05-11 15:08:32');

-- ----------------------------
-- Table structure for task_group
-- ----------------------------
DROP TABLE IF EXISTS `task_group`;
CREATE TABLE `task_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '分组id',
  `group_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '分组名称',
  `status` int(2) DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `version` int(10) DEFAULT NULL,
  `createid` int(11) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='任务调度--分组';

-- ----------------------------
-- Records of task_group
-- ----------------------------
INSERT INTO `task_group` VALUES ('1', '1.0.0_dev.test', '测试', '0', null, '0', null, '2019-02-15 16:38:05');

-- ----------------------------
-- Table structure for trace_limit_define
-- ----------------------------
DROP TABLE IF EXISTS `trace_limit_define`;
CREATE TABLE `trace_limit_define` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) COLLATE utf8_bin NOT NULL,
  `server_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '项目名称',
  `profile` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '项目环境',
  `server_version` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `priod` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '持续时间',
  `is_global` int(2) unsigned zerofill DEFAULT '00' COMMENT '是否是全局适配',
  `version` int(255) DEFAULT NULL,
  `createid` int(11) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='链路--限流配置';

-- ----------------------------
-- Records of trace_limit_define
-- ----------------------------
INSERT INTO `trace_limit_define` VALUES ('1', '/limit/{word}', 'demo', 'dev', '1.0', '5', '1', '00', '3', null, '2019-05-12 11:57:14');
INSERT INTO `trace_limit_define` VALUES ('2', '/page/{name}', 'demo_client', 'test', '1.0.0', '10', '1', '01', '1', null, '2019-05-12 11:42:48');

-- ----------------------------
-- Table structure for trace_project_profile
-- ----------------------------
DROP TABLE IF EXISTS `trace_project_profile`;
CREATE TABLE `trace_project_profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `profile` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `version_list` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `server_name_profile` (`service_name`,`profile`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='链路--项目基本信息';

-- ----------------------------
-- Records of trace_project_profile
-- ----------------------------
INSERT INTO `trace_project_profile` VALUES ('1', 'demo', 'dev', '1.0.1,1.2', '2019-05-11 13:20:21');
INSERT INTO `trace_project_profile` VALUES ('2', 'demo', 'test', '1.0', '2019-05-11 12:16:53');
INSERT INTO `trace_project_profile` VALUES ('3', 'demo_client', 'test', '1.0.0', '2019-05-12 19:29:14');

-- ----------------------------
-- Table structure for trace_record
-- ----------------------------
DROP TABLE IF EXISTS `trace_record`;
CREATE TABLE `trace_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trace_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '链路id',
  `trace_pid` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `group_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分组id',
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '接口路径',
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '接口类型',
  `status` int(5) DEFAULT NULL,
  `request_body` varchar(3000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '请求参数',
  `response_body` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `headers` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'header',
  `session_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '会话id',
  `cost` int(10) DEFAULT NULL COMMENT '耗时/ms',
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `trace_group_session_id_url_unique` (`trace_id`,`trace_pid`,`group_id`,`url`,`session_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='链路--url记录';

-- ----------------------------
-- Records of trace_record
-- ----------------------------
INSERT INTO `trace_record` VALUES ('1', '24303c58-891c-43cf-b134-923c3f2117e8', '97a8e3b3-2ce3-48eb-8230-4a562ee577a3', '24303c58-891c-43cf-b134-923c3f2117e8', 'http:\\localhost:8800/say/sfsdsw', 'GET', '200', null, null, '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=912984C6203393FB0875409516071A4F\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\"}', '912984C6203393FB0875409516071A4F', '111', '2019-03-22 09:01:13');
INSERT INTO `trace_record` VALUES ('7', '54bb9bf0-3ede-406a-a332-02c31e5c7d2b', '13f258d7-c78b-4c77-a91a-942de87fb276', '54bb9bf0-3ede-406a-a332-02c31e5c7d2b', 'http://localhost:8800/talk', 'GET', '200', 'word=3232', '{\"code\":\"0\",\"data\":\"you talk:3232\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=C638FEFCC712E1D67259C6C74A69AD81\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\"}', 'C638FEFCC712E1D67259C6C74A69AD81', '26', '2019-03-22 09:01:06');
INSERT INTO `trace_record` VALUES ('8', '398c9c57-78d5-46cf-8b57-bc56d901afcd', null, null, 'http://localhost:9000/project/regist', 'POST', '200', '{\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://localhost:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', null, '', null, '1440', '2019-05-08 14:33:26');
INSERT INTO `trace_record` VALUES ('9', '75736019-d289-4e1f-a0fb-058bd286b016', null, null, 'http://localhost:9000/project/regist', 'POST', '200', '{\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://localhost:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', null, '', null, '244', '2019-05-08 14:51:56');
INSERT INTO `trace_record` VALUES ('10', '2175e307-c040-4143-9e0c-6b4e01219be0', null, '2175e307-c040-4143-9e0c-6b4e01219be0', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; JSESSIONID=80ED5D5D415974AF2D81EA42EE59827C; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '117', '2019-05-08 14:53:34');
INSERT INTO `trace_record` VALUES ('11', '189b260a-5f2e-4dc9-89aa-f1041f91ab85', '2175e307-c040-4143-9e0c-6b4e01219be0', '189b260a-5f2e-4dc9-89aa-f1041f91ab85', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '67', '2019-05-08 15:12:24');
INSERT INTO `trace_record` VALUES ('12', 'cb03b52f-b1e8-4be7-93ea-9e16a818d3e9', '2175e307-c040-4143-9e0c-6b4e01219be0', 'cb03b52f-b1e8-4be7-93ea-9e16a818d3e9', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '39', '2019-05-08 15:12:52');
INSERT INTO `trace_record` VALUES ('13', '4afbe80e-6a01-42ea-b416-6ecb65656f73', '2175e307-c040-4143-9e0c-6b4e01219be0', '4afbe80e-6a01-42ea-b416-6ecb65656f73', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '40461', '2019-05-08 15:12:53');
INSERT INTO `trace_record` VALUES ('14', '62ae3dbf-fc09-48d4-8a4e-ac2ce7c1abb4', '2175e307-c040-4143-9e0c-6b4e01219be0', '62ae3dbf-fc09-48d4-8a4e-ac2ce7c1abb4', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '158', '2019-05-08 15:13:33');
INSERT INTO `trace_record` VALUES ('15', 'b80c6fdd-23b8-4a0b-aa97-8f15af26e1df', '2175e307-c040-4143-9e0c-6b4e01219be0', 'b80c6fdd-23b8-4a0b-aa97-8f15af26e1df', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"empty\":false,\"model\":{\"timestamp\":1557299618756,\"status\":200,\"error\":\"OK\",\"exception\":\"java.lang.IllegalStateException\",\"message\":\"getWriter() has already been called for this response\",\"path\":\"/say/sfsdsw\"},\"modelMap\":{\"$ref\":\"$.model\"},\"reference\":true,\"viewName\":\"error\"}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '52', '2019-05-08 15:13:39');
INSERT INTO `trace_record` VALUES ('16', 'e7534585-2b63-4c66-88fe-a27ec38a0ff8', '2175e307-c040-4143-9e0c-6b4e01219be0', 'e7534585-2b63-4c66-88fe-a27ec38a0ff8', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '47', '2019-05-08 15:14:01');
INSERT INTO `trace_record` VALUES ('17', '6fa9f06b-951c-4ecf-a3b6-32bd2852a281', '2175e307-c040-4143-9e0c-6b4e01219be0', '6fa9f06b-951c-4ecf-a3b6-32bd2852a281', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '28', '2019-05-08 15:14:04');
INSERT INTO `trace_record` VALUES ('18', '04954f90-8e5e-468c-b522-7eadbc941872', '2175e307-c040-4143-9e0c-6b4e01219be0', '04954f90-8e5e-468c-b522-7eadbc941872', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '41', '2019-05-08 15:14:05');
INSERT INTO `trace_record` VALUES ('19', '6e8c0045-3ce6-48c8-b77c-772de57cb9cb', '2175e307-c040-4143-9e0c-6b4e01219be0', '6e8c0045-3ce6-48c8-b77c-772de57cb9cb', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '37', '2019-05-08 15:14:07');
INSERT INTO `trace_record` VALUES ('20', 'd3651004-d6b6-4303-9041-026168e65aa8', '2175e307-c040-4143-9e0c-6b4e01219be0', 'd3651004-d6b6-4303-9041-026168e65aa8', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '44', '2019-05-08 15:14:08');
INSERT INTO `trace_record` VALUES ('21', 'ad27006b-150f-49fc-ac54-6993320e0a2f', '2175e307-c040-4143-9e0c-6b4e01219be0', 'ad27006b-150f-49fc-ac54-6993320e0a2f', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"empty\":false,\"model\":{\"timestamp\":1557299648227,\"status\":200,\"error\":\"OK\",\"exception\":\"java.lang.IllegalStateException\",\"message\":\"getWriter() has already been called for this response\",\"path\":\"/say/sfsdsw\"},\"modelMap\":{\"$ref\":\"$.model\"},\"reference\":true,\"viewName\":\"error\"}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '137', '2019-05-08 15:14:08');
INSERT INTO `trace_record` VALUES ('22', '4af4cae4-c20b-4f3d-9002-1adbeb61428d', '2175e307-c040-4143-9e0c-6b4e01219be0', '4af4cae4-c20b-4f3d-9002-1adbeb61428d', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '82', '2019-05-08 15:17:39');
INSERT INTO `trace_record` VALUES ('23', '51d8e19b-f582-45e3-9f96-664b40a062f6', '2175e307-c040-4143-9e0c-6b4e01219be0', '51d8e19b-f582-45e3-9f96-664b40a062f6', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '38', '2019-05-08 15:17:42');
INSERT INTO `trace_record` VALUES ('24', '3f56ba77-faa9-45ba-bc4d-edce01195783', '2175e307-c040-4143-9e0c-6b4e01219be0', '3f56ba77-faa9-45ba-bc4d-edce01195783', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '49', '2019-05-08 15:17:46');
INSERT INTO `trace_record` VALUES ('25', 'a0d0b663-a308-4705-b4db-81d458e84794', '2175e307-c040-4143-9e0c-6b4e01219be0', 'a0d0b663-a308-4705-b4db-81d458e84794', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '35', '2019-05-08 15:17:48');
INSERT INTO `trace_record` VALUES ('26', '611a2eb6-2f34-4f99-b6e9-0b59a494bc33', '2175e307-c040-4143-9e0c-6b4e01219be0', '611a2eb6-2f34-4f99-b6e9-0b59a494bc33', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '85', '2019-05-08 15:17:50');
INSERT INTO `trace_record` VALUES ('27', '6f80d599-885b-4f10-a026-9be81d12dc54', '2175e307-c040-4143-9e0c-6b4e01219be0', '6f80d599-885b-4f10-a026-9be81d12dc54', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"empty\":false,\"model\":{\"timestamp\":1557299869939,\"status\":200,\"error\":\"OK\",\"exception\":\"java.lang.IllegalStateException\",\"message\":\"getWriter() has already been called for this response\",\"path\":\"/say/sfsdsw\"},\"modelMap\":{\"$ref\":\"$.model\"},\"reference\":true,\"viewName\":\"error\"}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', '69', '2019-05-08 15:17:50');
INSERT INTO `trace_record` VALUES ('28', 'b6d5b9fe-92eb-43f0-8e4c-bb3dcc29d01a', null, null, 'http://localhost:9000/project/regist', 'POST', '200', '{\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://localhost:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', null, '', null, '495', '2019-05-08 15:20:04');
INSERT INTO `trace_record` VALUES ('29', '5979c527-d279-4df0-8f02-c1f75a92efdd', null, null, 'http://127.0.0.1:9000/project/regist', 'POST', '200', '{\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://127.0.0.1:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', null, '', null, '172', '2019-05-08 15:25:05');
INSERT INTO `trace_record` VALUES ('30', '9b62a0a0-6caf-45cc-bb8e-451567b3e8ab', null, null, 'http://127.0.0.1:9000/project/regist', 'POST', '200', '{\"enablelimit\":false,\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://127.0.0.1:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', null, '', null, '88', '2019-05-08 15:30:23');
INSERT INTO `trace_record` VALUES ('31', '11ccc621-a1de-476c-8d1b-f8a0de22feb7', null, '11ccc621-a1de-476c-8d1b-f8a0de22feb7', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '973C1F67DE481ED29F49A4DECE720661', '151', '2019-05-08 15:34:10');
INSERT INTO `trace_record` VALUES ('32', '46b76574-358b-4b05-825d-2129b0232d1a', '11ccc621-a1de-476c-8d1b-f8a0de22feb7', '46b76574-358b-4b05-825d-2129b0232d1a', 'http://localhost:8800/talk', 'GET', '200', 'word=sfsdsw', '{\"code\":\"0\",\"data\":\"you talk:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=973C1F67DE481ED29F49A4DECE720661\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '973C1F67DE481ED29F49A4DECE720661', '93', '2019-05-08 15:34:36');
INSERT INTO `trace_record` VALUES ('33', '535d0f9c-3081-4cef-b983-c2fbb5d5114d', '11ccc621-a1de-476c-8d1b-f8a0de22feb7', '535d0f9c-3081-4cef-b983-c2fbb5d5114d', 'http://localhost:8800/talk', 'GET', '200', 'word=sfsdsw', '{\"code\":\"0\",\"data\":\"you talk:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=973C1F67DE481ED29F49A4DECE720661\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '973C1F67DE481ED29F49A4DECE720661', '38', '2019-05-08 15:37:46');
INSERT INTO `trace_record` VALUES ('34', '60d35082-d0d6-4ab1-ad85-8e3c77a75b25', null, null, 'http://127.0.0.1:9000/project/regist', 'POST', '200', '{\"enablelimit\":false,\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://127.0.0.1:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', null, '', null, '219', '2019-05-08 15:39:17');
INSERT INTO `trace_record` VALUES ('35', '24da7571-fbd2-4591-8f18-9560cb13b0f9', null, '24da7571-fbd2-4591-8f18-9560cb13b0f9', 'http://localhost:8800/talk', 'GET', '200', 'word=sfsdsw', '{\"code\":\"0\",\"data\":\"you talk:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=973C1F67DE481ED29F49A4DECE720661\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'A3F155FF0D7883ABAE98807187169519', '114', '2019-05-08 15:39:41');
INSERT INTO `trace_record` VALUES ('36', '124ca8a1-591c-4c9c-88c0-e6cefe93d1c0', '24da7571-fbd2-4591-8f18-9560cb13b0f9', '124ca8a1-591c-4c9c-88c0-e6cefe93d1c0', 'http://localhost:8800/talk', 'GET', '200', 'word=sfsdsw', '{\"code\":\"0\",\"data\":\"you talk:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=A3F155FF0D7883ABAE98807187169519\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'A3F155FF0D7883ABAE98807187169519', '85', '2019-05-08 15:41:08');
INSERT INTO `trace_record` VALUES ('37', '6d2cddad-d265-4730-9c71-6942c7104d20', null, null, 'http://127.0.0.1:9000/project/regist', 'POST', '200', '{\"enablelimit\":false,\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://127.0.0.1:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', null, '', null, '959', '2019-05-08 16:23:25');
INSERT INTO `trace_record` VALUES ('38', '803eee52-6d85-4438-9be3-b2bc6c6e523d', null, '803eee52-6d85-4438-9be3-b2bc6c6e523d', 'http://localhost:8800/say/sfsdsw', 'GET', '200', null, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=A3F155FF0D7883ABAE98807187169519\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '1212A2E044CF61D90FE734A1634970CB', '88', '2019-05-08 16:24:23');

-- ----------------------------
-- Table structure for user_base
-- ----------------------------
DROP TABLE IF EXISTS `user_base`;
CREATE TABLE `user_base` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '编码',
  `username` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `identity_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `country` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '国籍',
  `mail` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号',
  `sex` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `age` int(10) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `use_code` (`code`),
  UNIQUE KEY `id_code` (`identity_code`),
  UNIQUE KEY `phone_unp` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息';

-- ----------------------------
-- Records of user_base
-- ----------------------------
INSERT INTO `user_base` VALUES ('6', 'user_com_-498702880', 'admin', '$2a$10$I1WgTLMWbaVmanuZTA1ApOqkPSHBNwPGD/zL9ZD/ai.YFYEO5dSsu', null, null, null, null, null, '0', '0', '2019-01-28 10:20:58');
INSERT INTO `user_base` VALUES ('12', 'user_com_1942818232', 'test', '$2a$10$534NzPLArXw5aiO6j9yZnujTSGNp5va3mwXNoN7T9OmXFhz.9jtPa', null, null, null, null, null, '0', '0', '2019-03-23 22:10:45');
INSERT INTO `user_base` VALUES ('14', 'user_com_-858606152', 'tom', '$2a$10$P8CxSp8NfLExSOe1qhU4tenpEndHoiTVEBpURPk/KvuPCB4Hu0MB2', '12324454', 'usa', 'sdfsw', '23242', null, '232', '0', '2019-05-07 14:23:10');
