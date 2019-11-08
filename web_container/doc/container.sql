/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : container

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-11-08 16:05:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for container
-- ----------------------------
DROP TABLE IF EXISTS `container`;
CREATE TABLE `container` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL COMMENT '分组id',
  `container_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '容器id',
  `container_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `image_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '镜像id',
  `image_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `image_version` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '镜像版本',
  `jar_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'jar web 路径',
  `run_port` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `run_cmds` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `status` int(2) DEFAULT NULL COMMENT '容器状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='web 容器';

-- ----------------------------
-- Table structure for container_group
-- ----------------------------
DROP TABLE IF EXISTS `container_group`;
CREATE TABLE `container_group` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `valid` int(11) DEFAULT NULL COMMENT '是否有效：',
  `ts` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='容器分组';
