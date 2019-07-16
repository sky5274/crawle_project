/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : flow

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-07-16 12:01:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for flow_group
-- ----------------------------
DROP TABLE IF EXISTS `flow_group`;
CREATE TABLE `flow_group` (
  `id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分组-id',
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分组名称',
  `create_code` varchar(11) DEFAULT NULL,
  `create_name` varchar(100) DEFAULT NULL,
  `version` int(10) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程控制--分组';

-- ----------------------------
-- Records of flow_group
-- ----------------------------

-- ----------------------------
-- Table structure for flow_main
-- ----------------------------
DROP TABLE IF EXISTS `flow_main`;
CREATE TABLE `flow_main` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '流程id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程名称',
  `start_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程起始id',
  `group_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分组id',
  `status` int(10) DEFAULT '0' COMMENT '流程状态：0，正常；-1作废',
  `create_code` varchar(50) DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(10) DEFAULT NULL COMMENT '创建人名称',
  `version` int(10) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程控制--主体';

-- ----------------------------
-- Records of flow_main
-- ----------------------------
INSERT INTO `flow_main` VALUES ('1012432328', '测试', null, '1234', '0', null, null, '1', '2019-06-30 12:03:42');
INSERT INTO `flow_main` VALUES ('98206404485054464', 'demo', null, '1234', null, null, null, '1', '2019-05-27 12:00:36');
INSERT INTO `flow_main` VALUES ('98254174117953536', '用户事件', null, '1234', '0', null, null, '0', '2019-06-30 10:56:58');

-- ----------------------------
-- Table structure for flow_node
-- ----------------------------
DROP TABLE IF EXISTS `flow_node`;
CREATE TABLE `flow_node` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流程节点id',
  `n_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程节点名称',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '节点类型：start,end,choose,container,base',
  `status` int(11) DEFAULT '0' COMMENT '节点状态：0，正常；-1作废',
  `flow_id` varchar(255) DEFAULT NULL COMMENT '所属流程id',
  `auth_codes` varchar(255) DEFAULT NULL COMMENT '需要控制权限编码',
  `create_code` varchar(255) DEFAULT NULL COMMENT '创建人编码',
  `create_name` varchar(255) DEFAULT NULL COMMENT '创建人名称',
  `version` int(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程控制--流程节点';

-- ----------------------------
-- Records of flow_node
-- ----------------------------
INSERT INTO `flow_node` VALUES ('1012432328', 'contianer-1', '容器1', 'container', '0', '98206404485054464', null, null, null, '0', '2019-06-30 12:04:05');
INSERT INTO `flow_node` VALUES ('98211998830952451', 'start-1', '开始', 'start', '0', '98206404485054464', null, null, null, '1', '2019-06-03 15:59:54');
INSERT INTO `flow_node` VALUES ('98211998830952456', 'end-1', '结束', 'end', '0', '98206404485054464', null, null, null, '0', '2019-05-27 14:26:13');
INSERT INTO `flow_node` VALUES ('98211998830952458', 'select-1', '选择1', 'choose', '0', '98206404485054464', 'tome,sky', null, null, '1', '2019-07-15 14:49:45');
INSERT INTO `flow_node` VALUES ('98211998830952459', 'choose-1', '选择类型', 'choose', '0', '98206404485054464', 'toky', null, null, '1', '2019-07-15 14:49:57');
INSERT INTO `flow_node` VALUES ('98211998830952460', 'base1', '基本1', 'base', '0', '98206404485054464', null, null, null, '2', '2019-06-03 16:16:11');
INSERT INTO `flow_node` VALUES ('98211998830952466', 'if-select', '是否审核', 'select', '0', '98206404485054464', null, null, null, '2', '2019-06-12 14:45:58');
INSERT INTO `flow_node` VALUES ('98226735652274208', 'demo-start', '开始-1', 'start', '0', '1012432328', 'tim', null, null, '1', '2019-07-15 14:50:06');
INSERT INTO `flow_node` VALUES ('98226735652274209', 'demo-base', '节点1', 'base', '0', '1012432328', null, null, null, '2', '2019-06-30 12:03:57');
INSERT INTO `flow_node` VALUES ('98226735652274210', 'demo-end', 'jieshu', 'end', '0', '1012432328', null, null, null, '0', '2019-06-30 12:03:59');
INSERT INTO `flow_node` VALUES ('98254174117953536', 'demo-evt', '事件容器', 'container', '0', '1012432328', null, null, null, '0', '2019-06-30 12:04:02');
INSERT INTO `flow_node` VALUES ('98254174117953537', 'evt-start', '事件起始', 'start', '0', '98254174117953536', null, null, null, '1', '2019-06-30 11:01:52');
INSERT INTO `flow_node` VALUES ('98254174117953538', 'evt-e', '事件结束', 'end', '0', '98254174117953536', null, null, null, '1', '2019-06-30 11:04:17');
INSERT INTO `flow_node` VALUES ('98254174117953539', 'evt-select', '事件选择', 'choose', '0', '98254174117953536', null, null, null, '0', '2019-06-30 11:01:21');

-- ----------------------------
-- Table structure for flow_node_attr
-- ----------------------------
DROP TABLE IF EXISTS `flow_node_attr`;
CREATE TABLE `flow_node_attr` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `a_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `flow_id` varchar(50) DEFAULT NULL,
  `node_id` varchar(50) DEFAULT NULL,
  `version` int(5) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='流程节点--节点属性';

-- ----------------------------
-- Records of flow_node_attr
-- ----------------------------
INSERT INTO `flow_node_attr` VALUES ('1', 'end-1', '结束标识', 'end-last', '98206404485054464', '98211998830952456', '0', '2019-05-27 14:27:40');
INSERT INTO `flow_node_attr` VALUES ('10', 'c-1', '条件1', 'phone', '98206404485054464', '98211998830952458', '0', '2019-06-03 16:01:22');
INSERT INTO `flow_node_attr` VALUES ('11', 'c2', '条件2', 'deo', '98206404485054464', '98211998830952459', '4', '2019-06-28 09:21:51');
INSERT INTO `flow_node_attr` VALUES ('13', 'check-contion', '审核条件', 'flag', '98206404485054464', '98211998830952466', '0', '2019-06-12 14:45:58');
INSERT INTO `flow_node_attr` VALUES ('15', 'evt-s-select-1', '条件1', 'cavas', '98254174117953536', '98254174117953539', '0', '2019-06-30 11:01:21');
INSERT INTO `flow_node_attr` VALUES ('16', 'evt-s_select-2', '条件2', 'user', '98254174117953536', '98254174117953539', '0', '2019-06-30 11:01:21');
INSERT INTO `flow_node_attr` VALUES ('17', 'et-s-p', '名称', 'param', '98254174117953536', '98254174117953537', '0', '2019-06-30 11:01:52');

-- ----------------------------
-- Table structure for flow_node_event
-- ----------------------------
DROP TABLE IF EXISTS `flow_node_event`;
CREATE TABLE `flow_node_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `flow_id` varchar(50) DEFAULT NULL,
  `node_id` varchar(50) DEFAULT NULL,
  `e_key` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '事件key',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '事件名称',
  `order_index` int(3) DEFAULT NULL COMMENT '事件执行顺序',
  `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '事件类型：in-进入;out-离开节点;user-用户事件;back-回滚事件;其他自定义事件',
  `event_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '事件执行类型:js/class/method',
  `content` varchar(1000) DEFAULT NULL COMMENT '事件内容',
  `create_code` varchar(255) DEFAULT NULL,
  `create_name` varchar(255) DEFAULT NULL,
  `version` int(10) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='流程节点--节点事件';

-- ----------------------------
-- Records of flow_node_event
-- ----------------------------
INSERT INTO `flow_node_event` VALUES ('1', '98206404485054464', '98211998830952459', 'e-test', '测试时间', '0', 'method', 'other', 'com.sky.demo.RpcFlowEventTestHandel.invoke', null, null, null, '2019-07-12 14:52:55');
INSERT INTO `flow_node_event` VALUES ('7', '98206404485054464', '98226735652274209', 'sdf', '测试', null, 'method', 'test', 'com.sky.demo.RpcFlowEventTestHandel.doEvent', null, null, '0', '2019-07-12 14:52:59');

-- ----------------------------
-- Table structure for flow_node_link
-- ----------------------------
DROP TABLE IF EXISTS `flow_node_link`;
CREATE TABLE `flow_node_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `l_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '链接key',
  `condition_con` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '链接条件',
  `flow_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `up_node_id` varchar(255) DEFAULT NULL COMMENT '上级节点id',
  `down_node_id` varchar(255) DEFAULT NULL COMMENT '下级节点id',
  `version` int(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='流程控制--节点链接';

-- ----------------------------
-- Records of flow_node_link
-- ----------------------------
INSERT INTO `flow_node_link` VALUES ('2', 'start-1', '', '98206404485054464', '98211998830952451', '98211998830952458', '0', '2019-06-03 15:59:54');
INSERT INTO `flow_node_link` VALUES ('3', 'choose-1', '#{flag}==\'Y\'', '98206404485054464', '98211998830952458', '98211998830952459', '0', '2019-06-11 09:12:06');
INSERT INTO `flow_node_link` VALUES ('4', 'choose-2', '#{flag}==\'S\'', '98206404485054464', '98211998830952458', '98211998830952460', '0', '2019-06-11 09:12:13');
INSERT INTO `flow_node_link` VALUES ('5', 'choose-3', '#{flag}==\'N\'', '98206404485054464', '98211998830952458', '98211998830952456', '0', '2019-06-11 09:12:17');
INSERT INTO `flow_node_link` VALUES ('7', 'SELECT_1', '', '98206404485054464', '98211998830952460', '98211998830952466', '0', '2019-06-03 16:16:11');
INSERT INTO `flow_node_link` VALUES ('8', 'find-1', '#{cd}=1', '98206404485054464', '98211998830952459', '98211998830952466', '0', '2019-06-11 09:54:50');
INSERT INTO `flow_node_link` VALUES ('10', 'demo-l-start', '', '1012432328', '98226735652274208', '98226735652274209', '0', '2019-06-30 12:04:26');
INSERT INTO `flow_node_link` VALUES ('14', 'check_ok', '#{flag}==\'Y\'', '98206404485054464', '98211998830952466', '98211998830952458', '0', '2019-06-12 14:45:58');
INSERT INTO `flow_node_link` VALUES ('15', 'start-1', '#{flag}==\'S\'', '98206404485054464', '98211998830952466', '98226735652274208', '0', '2019-06-12 14:45:58');
INSERT INTO `flow_node_link` VALUES ('28', null, '#{cd}=2', '98206404485054464', '98211998830952459', '98226735652274209', '0', '2019-06-29 13:20:42');
INSERT INTO `flow_node_link` VALUES ('29', null, '#{cd}=6', '98206404485054464', '98211998830952460', '98226735652274208', '0', '2019-06-29 13:21:27');
INSERT INTO `flow_node_link` VALUES ('30', null, '#{flag}==\'N\'', '98206404485054464', '98211998830952460', '98211998830952456', '0', '2019-06-29 13:24:10');
INSERT INTO `flow_node_link` VALUES ('31', '结束1', '', '98254174117953536', '98254174117953539', '98254174117953538', '0', '2019-06-30 11:01:21');
INSERT INTO `flow_node_link` VALUES ('32', 'evt-s-link', '', '98254174117953536', '98254174117953537', '98254174117953539', '0', '2019-06-30 11:01:52');
INSERT INTO `flow_node_link` VALUES ('33', 'demo-l-end', '', '1012432328', '98226735652274209', '98226735652274210', '0', '2019-06-30 12:04:28');
INSERT INTO `flow_node_link` VALUES ('34', 'demo-select', '#{param}=2', '1012432328', '98226735652274209', '98254174117953537', '0', '2019-06-30 12:04:31');
INSERT INTO `flow_node_link` VALUES ('35', 'evt-e-link-c', '', '98254174117953536', '98254174117953538', '98226735652274210', '0', '2019-06-30 11:04:17');

-- ----------------------------
-- Table structure for task_flow
-- ----------------------------
DROP TABLE IF EXISTS `task_flow`;
CREATE TABLE `task_flow` (
  `id` varchar(30) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务名称',
  `flow_id` varchar(30) DEFAULT NULL COMMENT '流程id',
  `flow_name` varchar(100) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `message` varchar(5000) DEFAULT NULL,
  `desc_con` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务描述',
  `params` json DEFAULT NULL COMMENT '任务参数',
  `create_code` varchar(30) DEFAULT NULL,
  `create_name` varchar(30) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程任务';

-- ----------------------------
-- Records of task_flow
-- ----------------------------
INSERT INTO `task_flow` VALUES ('98226735652274193', '测试任务1', '98206404485054464', 'demo', '0', 'llaces', '任务测试', '{\"cd\": \"6\", \"flag\": \"Y\", \"time\": 1559981648721, \"canves\": \"demo\", \"node_id\": \"98282931860013067\", \"descname\": \"my country is china\", \"select-1\": true, \"node_name\": \"开始\", \"TASK_STATUS\": 0, \"node_node_key\": \"start-1\", \"node_node_name\": \"开始\", \"TASK_RECORD_STATUS\": 0, \"TASK_RECORD_NODE_TYPE\": \"start\"}', null, null, '2019-07-15 10:29:04');
INSERT INTO `task_flow` VALUES ('98282931860013066', '测试任务，流程：demo', '98206404485054464', 'demo', '0', '开启', '流程测试', null, null, null, '2019-07-15 11:54:16');
INSERT INTO `task_flow` VALUES ('98282931860013078', '流程任务测试：测试', '1012432328', '测试', '2', '开启任务', '测试流程-demo', '{\"param\": \"2\", \"node_id\": \"98282931860013082\", \"node_name\": \"节点1\", \"node_node_key\": \"demo-base\", \"node_node_name\": \"节点1\", \"TASK_RECORD_NOW\": \"98282931860013080\", \"TASK_RECORD_BACK\": \"98282931860013079\", \"TASK_RECORD_LINKS\": \"98254174117953537,98226735652274209\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98282931860013081\", \"TASK_RECORD_NODE_TYPE\": \"base\"}', null, null, '2019-07-15 11:54:21');

-- ----------------------------
-- Table structure for task_flow_node
-- ----------------------------
DROP TABLE IF EXISTS `task_flow_node`;
CREATE TABLE `task_flow_node` (
  `id` varchar(30) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `task_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务id',
  `flow_id` varchar(30) DEFAULT NULL COMMENT '流程id',
  `flow_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程名称',
  `node_id` varchar(30) DEFAULT NULL COMMENT '节点id',
  `node_key` varchar(50) DEFAULT NULL,
  `node_name` varchar(100) DEFAULT NULL COMMENT '任务节点名称',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务节点内容',
  `desc_con` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务节点描述',
  `param` json DEFAULT NULL COMMENT '节点额外赋值参数',
  `limit_user` varchar(255) DEFAULT NULL COMMENT '任务节点限制人员',
  `create_code` varchar(30) DEFAULT NULL,
  `create_name` varchar(30) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程任务节点';

-- ----------------------------
-- Records of task_flow_node
-- ----------------------------
INSERT INTO `task_flow_node` VALUES ('98226735652274194', null, '98226735652274193', '98206404485054464', 'demo', '98211998830952451', 'start-1', '开始', null, null, '{\"TASK_STATUS\": 0}', null, null, null, '2019-06-08 16:14:12');
INSERT INTO `task_flow_node` VALUES ('98226735652274195', null, '98226735652274193', '98206404485054464', 'demo', '98211998830952458', 'select-1', '选择1', null, '环节测试', '{\"select-1\": true, \"TASK_RECORD_LINKS\": \"98211998830952451,98211998830952458\", \"TASK_RECORD_STATUS\": 0}', null, null, null, '2019-06-10 13:33:51');
INSERT INTO `task_flow_node` VALUES ('98226735652274202', null, '98226735652274193', '98206404485054464', 'demo', '98211998830952451', 'start-1', '开始', null, null, '{\"TASK_STATUS\": 0, \"TASK_RECORD_STATUS\": 2}', null, null, null, '2019-06-10 13:44:50');
INSERT INTO `task_flow_node` VALUES ('98226735652274203', null, '98226735652274193', '98206404485054464', 'demo', '98211998830952458', 'select-1', '选择1', null, '环节测试', '{\"select-1\": true, \"TASK_RECORD_LINKS\": \"98211998830952451,98211998830952458\", \"TASK_RECORD_STATUS\": 2}', null, null, null, '2019-06-10 13:47:25');
INSERT INTO `task_flow_node` VALUES ('98226735652274207', null, '98226735652274193', '98206404485054464', 'demo', '98211998830952459', 'choose-1', '选择类型', null, '环节测试', '{\"flag\": \"Y\", \"select-1\": true, \"TASK_RECORD_LINKS\": \"98211998830952458,98211998830952459\", \"TASK_RECORD_STATUS\": 1, \"TASK_RECORD_UP_NODE\": \"98226735652274203\"}', null, null, null, '2019-06-11 09:28:43');
INSERT INTO `task_flow_node` VALUES ('98249753589972992', null, '98226735652274193', '98206404485054464', 'demo', '98211998830952458', 'select-1', '选择1', null, '环节测试', '{\"TASK_RECORD_NOW\": \"98226735652274203\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98226735652274207\"}', null, null, null, '2019-06-22 12:51:59');
INSERT INTO `task_flow_node` VALUES ('98249753589972993', '基本环节-测试', '98226735652274193', '98206404485054464', 'demo', '98211998830952459', 'choose-1', '选择类型', 'jiben环节测试过程', '环节测试', '{\"flag\": \"Y\", \"select-1\": true, \"TASK_RECORD_LINKS\": \"98211998830952458,98211998830952459\", \"TASK_RECORD_STATUS\": 1, \"TASK_RECORD_CONTENT\": \"环节测试\", \"TASK_RECORD_UP_NODE\": \"98249753589972992\", \"TASK_RECORD_NODE_TYPE\": \"choose\"}', 'tome', null, null, '2019-07-16 09:12:34');
INSERT INTO `task_flow_node` VALUES ('98249753589972994', null, '98226735652274193', '98206404485054464', 'demo', '98211998830952458', 'select-1', '选择1', null, '环节测试', '{\"TASK_RECORD_NOW\": \"98249753589972992\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98249753589972993\"}', 'sky', null, null, '2019-07-16 09:12:38');
INSERT INTO `task_flow_node` VALUES ('98271385729630208', 'demo-test', '98226735652274193', '98206404485054464', 'demo', '98211998830952460', 'base1', '基本1', '测试流程', '流程测试赛调度', '{\"flag\": \"S\", \"TASK_RECORD_LINKS\": \"98211998830952458,9821199883095246098211998830952458,9821199883095246098211998830952458,98211998830952460,98211998830952460\", \"TASK_RECORD_STATUS\": 1, \"TASK_RECORD_UP_NODE\": \"98249753589972994\", \"TASK_RECORD_NODE_TYPE\": \"base\"}', null, null, null, '2019-07-08 15:10:00');
INSERT INTO `task_flow_node` VALUES ('98271385729630210', '流程审核', '98226735652274193', '98206404485054464', 'demo', '98226735652274208', 'demo-start', '开始-1', '测试审核', '审核理财任务', '{\"cd\": \"6\", \"canves\": \"demo\", \"TASK_RECORD_LINKS\": \"98211998830952460,98226735652274208\", \"TASK_RECORD_STATUS\": 1, \"TASK_RECORD_UP_NODE\": \"98271385729630208\", \"TASK_RECORD_NODE_TYPE\": \"container_start\"}', null, null, null, '2019-07-08 15:57:03');
INSERT INTO `task_flow_node` VALUES ('98271385729630213', 'doamin-dse', '98226735652274193', '98206404485054464', 'demo', '98226735652274208', 'demo-start', '开始-1', 'say task do', 'do animaie', '{\"cd\": \"6\", \"TASK_RECORD_LINKS\": \"98226735652274208,98226735652274208\", \"TASK_RECORD_STATUS\": 1, \"TASK_RECORD_UP_NODE\": \"98271385729630210\", \"TASK_RECORD_NODE_TYPE\": \"container_start\"}', null, null, null, '2019-07-08 16:49:57');
INSERT INTO `task_flow_node` VALUES ('98271385729630227', '流程审核', '98226735652274193', '98206404485054464', 'demo', '98226735652274208', 'demo-start', '开始-1', '测试审核', '审核理财任务', '{\"TASK_RECORD_NOW\": \"98271385729630210\", \"TASK_RECORD_BACK\": \"98271385729630210\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98271385729630213\"}', null, null, null, '2019-07-08 16:50:30');
INSERT INTO `task_flow_node` VALUES ('98271385729630228', '流程审核', '98226735652274193', '98206404485054464', 'demo', '98226735652274208', 'demo-start', '开始-1', '测试审核', '审核理财任务', '{\"TASK_RECORD_NOW\": \"98271385729630210\", \"TASK_RECORD_BACK\": \"98271385729630213\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98271385729630227\"}', null, null, null, '2019-07-08 16:51:04');
INSERT INTO `task_flow_node` VALUES ('98271385729630229', 'doamin-dse', '98226735652274193', '98206404485054464', 'demo', '98226735652274208', 'demo-start', '开始-1', 'say task do', 'do animaie', '{\"TASK_RECORD_NOW\": \"98271385729630213\", \"TASK_RECORD_BACK\": \"98271385729630227\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98271385729630228\"}', null, null, null, '2019-07-08 16:53:27');
INSERT INTO `task_flow_node` VALUES ('98271385729630231', '流程审核', '98226735652274193', '98206404485054464', 'demo', '98226735652274208', 'demo-start', '开始-1', '测试审核', '审核理财任务', '{\"TASK_RECORD_NOW\": \"98271385729630210\", \"TASK_RECORD_BACK\": \"98271385729630208\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98271385729630229\", \"TASK_RECORD_NODE_TYPE\": \"container_start\"}', null, null, null, '2019-07-08 17:06:54');
INSERT INTO `task_flow_node` VALUES ('98271385729630232', 'demo-test', '98226735652274193', '98206404485054464', 'demo', '98211998830952460', 'base1', '基本1', '测试流程', '流程测试赛调度', '{\"TASK_RECORD_NOW\": \"98271385729630208\", \"TASK_RECORD_BACK\": \"98249753589972994\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98271385729630231\", \"TASK_RECORD_NODE_TYPE\": \"base\"}', null, null, null, '2019-07-08 17:07:29');
INSERT INTO `task_flow_node` VALUES ('98271385729630233', null, '98226735652274193', '98206404485054464', 'demo', '98211998830952458', 'select-1', '选择1', null, '环节测试', '{\"TASK_RECORD_NOW\": \"98249753589972994\", \"TASK_RECORD_BACK\": \"98249753589972993\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98271385729630232\"}', null, null, null, '2019-07-08 17:08:24');
INSERT INTO `task_flow_node` VALUES ('98271385729630256', '基本1', '98226735652274193', '98206404485054464', 'demo', '98211998830952460', 'base1', '基本1', 'llaces', '德国法国和进口量', '{\"flag\": \"S\", \"TASK_RECORD_LINKS\": \"98211998830952458,98211998830952460\", \"TASK_RECORD_STATUS\": 1, \"TASK_RECORD_UP_NODE\": \"98271385729630233\", \"TASK_RECORD_NODE_TYPE\": \"base\"}', null, null, null, '2019-07-12 21:43:04');
INSERT INTO `task_flow_node` VALUES ('98271385729630257', '基本环节-测试', '98226735652274193', '98206404485054464', 'demo', '98211998830952459', 'choose-1', '选择类型', 'jiben环节测试过程', '环节测试', '{\"TASK_RECORD_NOW\": \"98249753589972993\", \"TASK_RECORD_BACK\": \"98249753589972992\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98271385729630256\", \"TASK_RECORD_NODE_TYPE\": \"choose\"}', null, null, null, '2019-07-12 21:43:11');
INSERT INTO `task_flow_node` VALUES ('98271385729630258', null, '98226735652274193', '98206404485054464', 'demo', '98211998830952458', 'select-1', '选择1', null, '环节测试', '{\"TASK_RECORD_NOW\": \"98249753589972992\", \"TASK_RECORD_BACK\": \"98226735652274207\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98271385729630257\"}', null, null, null, '2019-07-12 21:43:32');
INSERT INTO `task_flow_node` VALUES ('98282931860013061', null, '98226735652274193', '98206404485054464', 'demo', '98211998830952459', 'choose-1', '选择类型', null, '环节测试', '{\"TASK_RECORD_NOW\": \"98226735652274207\", \"TASK_RECORD_BACK\": \"98226735652274203\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98271385729630258\"}', null, null, null, '2019-07-15 09:37:21');
INSERT INTO `task_flow_node` VALUES ('98282931860013062', null, '98226735652274193', '98206404485054464', 'demo', '98211998830952458', 'select-1', '选择1', null, '环节测试', '{\"TASK_RECORD_NOW\": \"98226735652274203\", \"TASK_RECORD_LINKS\": \"98211998830952459,98211998830952458\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98282931860013061\"}', null, null, null, '2019-07-15 09:37:39');
INSERT INTO `task_flow_node` VALUES ('98282931860013063', null, '98226735652274193', '98206404485054464', 'demo', '98211998830952458', 'select-1', '选择1', null, '环节测试', '{\"TASK_RECORD_NOW\": \"98226735652274203\", \"TASK_RECORD_LINKS\": \"98211998830952458,98211998830952451\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98282931860013062\"}', null, null, null, '2019-07-15 09:44:49');
INSERT INTO `task_flow_node` VALUES ('98282931860013064', null, '98226735652274193', '98206404485054464', 'demo', '98211998830952458', 'select-1', '选择1', null, '环节测试', '{\"TASK_RECORD_NOW\": \"98282931860013062\", \"TASK_RECORD_BACK\": \"98282931860013061\", \"TASK_RECORD_LINKS\": \"98211998830952458,98211998830952451\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98282931860013063\"}', null, null, null, '2019-07-15 09:45:11');
INSERT INTO `task_flow_node` VALUES ('98282931860013067', '开始', '98282931860013066', '98206404485054464', 'demo', '98211998830952451', 'start-1', '开始', null, null, '{\"TASK_RECORD_STATUS\": 0, \"TASK_RECORD_NODE_TYPE\": \"start\"}', null, null, null, '2019-07-15 10:29:03');
INSERT INTO `task_flow_node` VALUES ('98282931860013079', '开始-1', '98282931860013078', '1012432328', '测试', '98226735652274208', 'demo-start', '开始-1', null, null, '{\"TASK_RECORD_STATUS\": 0, \"TASK_RECORD_NODE_TYPE\": \"start\"}', null, null, null, '2019-07-15 11:02:09');
INSERT INTO `task_flow_node` VALUES ('98282931860013080', '节点1', '98282931860013078', '1012432328', '测试', '98226735652274209', 'demo-base', '节点1', '开启任务', null, '{\"TASK_RECORD_AUTO\": true, \"TASK_RECORD_LINKS\": \"98226735652274208,98226735652274209\", \"TASK_RECORD_STATUS\": 1, \"TASK_RECORD_UP_NODE\": \"98282931860013079\", \"TASK_RECORD_NODE_TYPE\": \"base\", \"TASK_RECORD_CONTROLLER\": false}', null, null, null, '2019-07-15 11:06:33');
INSERT INTO `task_flow_node` VALUES ('98282931860013081', '事件起始', '98282931860013078', '1012432328', '测试', '98254174117953537', 'evt-start', '事件起始', '开启任务', null, '{\"param\": \"2\", \"TASK_RECORD_AUTO\": true, \"TASK_RECORD_LINKS\": \"98226735652274209,98254174117953537\", \"TASK_RECORD_STATUS\": 1, \"TASK_RECORD_UP_NODE\": \"98282931860013080\", \"TASK_RECORD_NODE_TYPE\": \"container_start\", \"TASK_RECORD_CONTROLLER\": false}', null, null, null, '2019-07-15 11:06:42');
INSERT INTO `task_flow_node` VALUES ('98282931860013082', '节点1', '98282931860013078', '1012432328', '测试', '98226735652274209', 'demo-base', '节点1', '开启任务', null, '{\"TASK_RECORD_NOW\": \"98282931860013080\", \"TASK_RECORD_BACK\": \"98282931860013079\", \"TASK_RECORD_LINKS\": \"98254174117953537,98226735652274209\", \"TASK_RECORD_STATUS\": 2, \"TASK_RECORD_UP_NODE\": \"98282931860013081\", \"TASK_RECORD_NODE_TYPE\": \"base\"}', null, null, null, '2019-07-15 11:06:46');

-- ----------------------------
-- Function structure for flow_container
-- ----------------------------
DROP FUNCTION IF EXISTS `flow_container`;
DELIMITER ;;
CREATE DEFINER=`%`@`%` FUNCTION `flow_container`(`container` varchar(50)) RETURNS varchar(4000) CHARSET utf8
    SQL SECURITY INVOKER
    COMMENT '根据流程id获取流程及含有的流程容器id'
BEGIN
       DECLARE sTemp VARCHAR(4000);
       DECLARE sTempChd VARCHAR(4000);

       SET sTemp = '';
       SET sTempChd =container ;
				
       WHILE sTempChd is not null DO
					SET sTemp =  concat(sTemp,',',sTempChd);
         SELECT group_concat(id) INTO sTempChd FROM flow.flow_node where FIND_IN_SET(flow_id,sTempChd)>0 and type='container';
				
       END WHILE;
       RETURN sTemp;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for task_has_user
-- ----------------------------
DROP FUNCTION IF EXISTS `task_has_user`;
DELIMITER ;;
CREATE DEFINER=`%`@`%` FUNCTION `task_has_user`(`usercode` varchar(20),`taskid` varchar(50)) RETURNS int(10)
    READS SQL DATA
    SQL SECURITY INVOKER
BEGIN
	declare row_num int(10) default 0;
	DECLARE user_code VARCHAR(20);
	DECLARE t_id VARCHAR(50);
	DECLARE f_id VARCHAR(50);
	set user_code=usercode;
	set t_id=taskid;
	SELECT flow_id from task_flow WHERE id=t_id into f_id;
	SELECT count(*) from(
		SELECT auth_codes as user_codes FROM flow_node where flow_container( f_id) like concat('%',flow_id ,'%') 
		UNION 
		select create_code as user_codes FROM task_flow_node where task_id=t_id 
		UNION 
		select limit_user as user_codes FROM task_flow_node where task_id=t_id 
		) tabs 
		where tabs.user_codes is not null and (tabs.user_codes LIKE CONCAT('%',user_code,',%') or tabs.user_codes=user_code)
		INTO row_num;
	set row_num=IF(user_code is null or user_code ='',1,row_num) ;
	RETURN row_num;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for user_in_tasknode
-- ----------------------------
DROP FUNCTION IF EXISTS `user_in_tasknode`;
DELIMITER ;;
CREATE DEFINER=`%`@`%` FUNCTION `user_in_tasknode`(`usercode` varchar(20),`tasknodeid` varchar(50)) RETURNS int(10)
    READS SQL DATA
    SQL SECURITY INVOKER
BEGIN
	declare row_num int(10) default 0;
	DECLARE user_code VARCHAR(20);
	DECLARE t_id VARCHAR(50);
	
	set user_code=usercode;
	set t_id=tasknodeid;

	SELECT count(*) from(
		SELECT auth_codes as user_codes FROM flow_node where id in (select node_id FROM task_flow_node where id=t_id) 
		UNION 
		select limit_user as user_codes FROM task_flow_node where id=t_id 
		) tabs 
		where ((user_code='' or user_code is null )and tabs.user_codes is  null) or ( tabs.user_codes is not null and tabs.user_codes LIKE CONCAT('%',user_code,',%') or tabs.user_codes=user_code)
		INTO row_num;
	
	RETURN row_num;
END
;;
DELIMITER ;
