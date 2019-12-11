/*
Navicat MySQL Data Transfer

Source Database       : crawle

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-12-11 15:13:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
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
  `key` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `value` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `project` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目名称',
  `profile` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目环境（分支）',
  `version_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目版本',
  `is_global` int(3) DEFAULT '0' COMMENT '是否全局',
  `local` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '本地化语言',
  `version` int(10) DEFAULT '0',
  `createid` int(11) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_project_version_key` (`key`,`project`,`profile`,`version_code`,`local`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='链路--服务属性配置';

-- ----------------------------
-- Records of property_value
-- ----------------------------
INSERT INTO `property_value` VALUES ('1', 'service.name', 'demo_time', 'demo', 'dev', null, '0', null, '0', null, '2019-03-08 09:46:20');
INSERT INTO `property_value` VALUES ('2', 'app.name', 'appces', 'demo_client', 'test', '1.0.0', '0', null, '0', null, '2019-03-08 15:31:02');
INSERT INTO `property_value` VALUES ('3', 'app_client', 'test-1', 'demo', 'dev', '1.0', '1', null, '7', null, '2019-05-11 15:08:32');
INSERT INTO `property_value` VALUES ('4', 'app.profile', 'dev', 'demo', 'test', '1.0', '0', null, '0', null, '2019-05-11 15:33:01');
INSERT INTO `property_value` VALUES ('5', 'rpc.node.prefix', 'rpc_node_', 'demo_client', 'dev', '1.0.0', '0', 'zh_CN', '1', null, '2019-12-11 14:21:16');
INSERT INTO `property_value` VALUES ('6', 'rpc.version', '0.0.1', 'demo_client', 'dev', '1.0.0', '0', 'zh_CN', '1', null, '2019-12-11 14:21:13');
INSERT INTO `property_value` VALUES ('7', 'rpc.group', 'group', 'demo_client', 'dev', '1.0.0', '0', 'zh_CN', '1', null, '2019-12-11 14:21:11');
INSERT INTO `property_value` VALUES ('8', 'rpc.zookeeper.url', '127.0.0.1:2181', 'demo_client', 'dev', '1.0.0', '0', 'zh_CN', '1', null, '2019-12-11 14:21:08');
INSERT INTO `property_value` VALUES ('9', 'demo.test.dd', '9000总比', 'demo_client', 'test', '1.0.0', '0', 'zh_CN', '1', null, '2019-12-11 14:21:04');
INSERT INTO `property_value` VALUES ('15', 'demo.test.dd', '800h', 'demo_client', 'test', '1.0.0', '0', 'en_US', '0', null, '2019-12-11 14:05:14');

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
  `local` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '本地化支持',
  `version` int(10) DEFAULT '0',
  `createid` int(11) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='链路--服务属性配置历史';

-- ----------------------------
-- Records of property_value_his
-- ----------------------------
INSERT INTO `property_value_his` VALUES ('1', 'app_client', 'test-1', 'demo', 'dev', '1.0', '0', '3', null, '0', null, '2019-05-11 15:00:50');
INSERT INTO `property_value_his` VALUES ('2', 'app_client', 'test-1', 'demo', 'dev', '1.0', '0', '3', null, '0', null, '2019-05-11 15:01:29');
INSERT INTO `property_value_his` VALUES ('3', 'app_client', 'test-1', 'demo', 'dev', '1.0', '0', '3', null, '0', null, '2019-05-11 15:04:09');
INSERT INTO `property_value_his` VALUES ('4', 'app_client', 'test-1', 'demo', 'dev', '1.0', '0', '3', null, '0', null, '2019-05-11 15:04:19');
INSERT INTO `property_value_his` VALUES ('5', 'app_client', 'test-1', 'demo', 'dev', '1.0', '0', '3', null, '0', null, '2019-05-11 15:04:59');
INSERT INTO `property_value_his` VALUES ('6', 'app_client', 'test-1', 'demo', 'dev', '1.0', '0', '3', null, '0', null, '2019-05-11 15:08:23');
INSERT INTO `property_value_his` VALUES ('7', 'app_client', 'test-1', 'demo', 'dev', '1.0', '0', '3', null, '0', null, '2019-05-11 15:08:32');

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
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='链路--项目基本信息';

-- ----------------------------
-- Records of trace_project_profile
-- ----------------------------
INSERT INTO `trace_project_profile` VALUES ('1', 'demo', 'dev', '1.0.1,1.2', '2019-05-11 13:20:21');
INSERT INTO `trace_project_profile` VALUES ('2', 'demo', 'test', '1.0', '2019-05-11 12:16:53');
INSERT INTO `trace_project_profile` VALUES ('3', 'demo_client', 'test', '1.0.0', '2019-12-11 13:50:38');

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
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='链路--url记录';

-- ----------------------------
-- Records of trace_record
-- ----------------------------
INSERT INTO `trace_record` VALUES ('1', '24303c58-891c-43cf-b134-923c3f2117e8', '97a8e3b3-2ce3-48eb-8230-4a562ee577a3', '24303c58-891c-43cf-b134-923c3f2117e8', 'http:\\localhost:8800/say/sfsdsw', 'GET', '200', null, null, '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=912984C6203393FB0875409516071A4F\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\"}', '912984C6203393FB0875409516071A4F', '111', '2019-03-22 09:01:13');
INSERT INTO `trace_record` VALUES ('2', '54bb9bf0-3ede-406a-a332-02c31e5c7d2b', '13f258d7-c78b-4c77-a91a-942de87fb276', '54bb9bf0-3ede-406a-a332-02c31e5c7d2b', 'http://localhost:8800/talk', 'GET', '200', 'word=3232', '{\"code\":\"0\",\"data\":\"you talk:3232\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=C638FEFCC712E1D67259C6C74A69AD81\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\"}', 'C638FEFCC712E1D67259C6C74A69AD81', '26', '2019-03-22 09:01:06');

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
