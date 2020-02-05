/*
 Navicat Premium Data Transfer

 Source Server         : local_docker_mysql
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:32769
 Source Schema         : crawle

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 02/02/2020 22:00:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'mingc',
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `createId` int(0) NULL DEFAULT NULL,
  `version` int(0) NOT NULL DEFAULT 1,
  `ts` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_auth
-- ----------------------------
DROP TABLE IF EXISTS `config_auth`;
CREATE TABLE `config_auth`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `auth_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '权限编码',
  `auth_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '权限名称',
  `vail_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'Y' COMMENT '是否有效',
  `createid` int(0) NULL DEFAULT NULL,
  `version` int(0) NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_auth
-- ----------------------------
INSERT INTO `config_auth` VALUES (1, 'AUTH_OPERATION', '权限操作', 'Y', 12, 4, '2019-04-24 15:28:57');
INSERT INTO `config_auth` VALUES (2, 'AUTH_ADD_USER', '添加用户', 'Y', 12, 0, '2019-04-22 09:16:09');
INSERT INTO `config_auth` VALUES (3, 'USER_OPTION', '用户操作权限', 'Y', 12, 0, '2019-05-07 15:23:44');

-- ----------------------------
-- Table structure for config_auth_relate
-- ----------------------------
DROP TABLE IF EXISTS `config_auth_relate`;
CREATE TABLE `config_auth_relate`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色编码',
  `auth_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '权限编码',
  `vail_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'Y' COMMENT '是否有效',
  `createid` int(0) NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '权限-角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_auth_relate
-- ----------------------------
INSERT INTO `config_auth_relate` VALUES (5, 'test', 'AUTH_OPERATION', 'Y', 12, NULL);

-- ----------------------------
-- Table structure for config_detail
-- ----------------------------
DROP TABLE IF EXISTS `config_detail`;
CREATE TABLE `config_detail`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `pid` int(0) NULL DEFAULT NULL,
  `key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createid` int(0) NULL DEFAULT NULL,
  `ts` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_log_file
-- ----------------------------
DROP TABLE IF EXISTS `config_log_file`;
CREATE TABLE `config_log_file`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 123 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '爬虫日子记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_log_file
-- ----------------------------
INSERT INTO `config_log_file` VALUES (1, '7785c71fad614d17bf27fe0562135d11', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/7785c71fad614d17bf27fe0562135d11', 0, '2019-01-19 14:34:08');
INSERT INTO `config_log_file` VALUES (2, '8cc8274cfaa541188ede1bb3a805e628', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/8cc8274cfaa541188ede1bb3a805e628', 0, '2019-01-19 14:40:48');
INSERT INTO `config_log_file` VALUES (3, 'a47c3664d8ff4e1286845e20a14dbf56', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/a47c3664d8ff4e1286845e20a14dbf56.txt', 0, '2019-01-19 15:04:22');
INSERT INTO `config_log_file` VALUES (4, 'e5e102db285f49a6961623a51f8541b9', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/e5e102db285f49a6961623a51f8541b9.txt', 0, '2019-01-19 15:15:28');
INSERT INTO `config_log_file` VALUES (5, 'd354002aaebe4559b3c39e0f417cbc3f', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/d354002aaebe4559b3c39e0f417cbc3f.txt', 0, '2019-01-19 15:21:04');
INSERT INTO `config_log_file` VALUES (6, 'ea11102ae2394de4a32bb3e305f5304f', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/ea11102ae2394de4a32bb3e305f5304f.txt', 0, '2019-01-19 15:27:09');
INSERT INTO `config_log_file` VALUES (7, 'cf199ad7ec26414d94f966d8193fd6c8', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/cf199ad7ec26414d94f966d8193fd6c8.txt', 0, '2019-01-19 15:30:39');
INSERT INTO `config_log_file` VALUES (8, '1e7086e1334745e885d404b51c73ddec', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/1e7086e1334745e885d404b51c73ddec.txt', 0, '2019-01-19 15:31:02');
INSERT INTO `config_log_file` VALUES (9, '94c4d6843fc54690853cf579e8a99089', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/94c4d6843fc54690853cf579e8a99089.txt', 0, '2019-01-19 15:34:18');
INSERT INTO `config_log_file` VALUES (10, '11ed7fbda29d461aa79de8dd65d161ea', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/11ed7fbda29d461aa79de8dd65d161ea.txt', 4, '2019-01-19 16:00:04');
INSERT INTO `config_log_file` VALUES (11, 'cc9836955b074adaa0043ef2bbb33f91', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/cc9836955b074adaa0043ef2bbb33f91.txt', 4, '2019-01-19 16:01:39');
INSERT INTO `config_log_file` VALUES (12, 'ee6675c1f3a344de8edeb1e11c4e0948', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/ee6675c1f3a344de8edeb1e11c4e0948.txt', 4, '2019-01-19 16:06:59');
INSERT INTO `config_log_file` VALUES (13, '025eb62c333244e7be446c40bad50864', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/025eb62c333244e7be446c40bad50864.txt', 0, '2019-01-19 16:11:06');
INSERT INTO `config_log_file` VALUES (14, 'f5b7303280ef4415a8fd3024e1e97487', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/f5b7303280ef4415a8fd3024e1e97487.txt', 4, '2019-01-19 16:21:08');
INSERT INTO `config_log_file` VALUES (15, 'bef6178466bd4608b68f269aa0239121', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/bef6178466bd4608b68f269aa0239121.txt', 0, '2019-01-19 16:21:35');
INSERT INTO `config_log_file` VALUES (16, '31d0026069fa41f5aa382d68a1f22f74', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/31d0026069fa41f5aa382d68a1f22f74.txt', 4, '2019-01-19 16:25:41');
INSERT INTO `config_log_file` VALUES (17, '27c2634729ca424f9b18dbcabc3405b6', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/27c2634729ca424f9b18dbcabc3405b6.txt', 4, '2019-01-19 16:44:59');
INSERT INTO `config_log_file` VALUES (18, '2c453c70dfd944f0a0d975ceff2874c6', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/2c453c70dfd944f0a0d975ceff2874c6.txt', 4, '2019-01-19 16:47:04');
INSERT INTO `config_log_file` VALUES (19, '1343168db8be4d80b6585766813293cd', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/1343168db8be4d80b6585766813293cd.txt', 0, '2019-01-19 16:49:24');
INSERT INTO `config_log_file` VALUES (20, '6f292328ccfb4e4192812860f8e51edc', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/6f292328ccfb4e4192812860f8e51edc.txt', 0, '2019-01-19 16:54:14');
INSERT INTO `config_log_file` VALUES (21, '1a77a0f1835c450ab252f57cad90cb32', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/1a77a0f1835c450ab252f57cad90cb32.txt', 4, '2019-01-19 17:08:26');
INSERT INTO `config_log_file` VALUES (22, 'a0f21833ccad4188915382eb72ca4b8d', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/a0f21833ccad4188915382eb72ca4b8d.txt', 4, '2019-01-19 17:12:00');
INSERT INTO `config_log_file` VALUES (23, '217a7d1544b94ecfb59e5dcfcabdf0d9', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/217a7d1544b94ecfb59e5dcfcabdf0d9.txt', 0, '2019-01-19 17:12:16');
INSERT INTO `config_log_file` VALUES (24, '2396b5ee1404447e91d320acd0ea0fd0', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/2396b5ee1404447e91d320acd0ea0fd0.txt', 0, '2019-01-19 17:12:56');
INSERT INTO `config_log_file` VALUES (25, '9bf1334d95bb4b1cb5896464c6218919', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/9bf1334d95bb4b1cb5896464c6218919.txt', 0, '2019-01-19 17:17:39');
INSERT INTO `config_log_file` VALUES (26, '3ce618000dce49be837b93b5f8556368', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/3ce618000dce49be837b93b5f8556368.txt', 0, '2019-01-19 17:19:46');
INSERT INTO `config_log_file` VALUES (27, '269d15ef04534e33b627e21b5c0ff7a5', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/269d15ef04534e33b627e21b5c0ff7a5.txt', 4, '2019-01-19 19:37:42');
INSERT INTO `config_log_file` VALUES (28, '2057291786904160aeefc9a2857e17d3', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/2057291786904160aeefc9a2857e17d3.txt', 0, '2019-01-19 19:38:12');
INSERT INTO `config_log_file` VALUES (29, '2a23b1f163b94561bd278faf0ecba4dc', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/2a23b1f163b94561bd278faf0ecba4dc.txt', 0, '2019-01-19 19:39:09');
INSERT INTO `config_log_file` VALUES (30, '647ce733f4dc4f2c9b6f8d590515f882', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/647ce733f4dc4f2c9b6f8d590515f882.txt', 0, '2019-01-19 19:40:26');
INSERT INTO `config_log_file` VALUES (31, '5d0c7a8eaffa4250828ee977b03fb534', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/5d0c7a8eaffa4250828ee977b03fb534.txt', 0, '2019-01-19 19:43:28');
INSERT INTO `config_log_file` VALUES (32, 'fa38b1d5c39c4b36ac7b1f80776db40a', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/fa38b1d5c39c4b36ac7b1f80776db40a.txt', 0, '2019-01-19 19:44:26');
INSERT INTO `config_log_file` VALUES (33, '5bbe75fa84374f259d26b00195cfc986', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/5bbe75fa84374f259d26b00195cfc986.txt', 0, '2019-01-19 19:45:54');
INSERT INTO `config_log_file` VALUES (34, 'dea9cefb2c9a4c9cb10ed83970f57bd9', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/dea9cefb2c9a4c9cb10ed83970f57bd9.txt', 0, '2019-01-19 19:46:22');
INSERT INTO `config_log_file` VALUES (35, 'e8ee8c142c3b48e4ba709ba971c3a725', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/e8ee8c142c3b48e4ba709ba971c3a725.txt', 0, '2019-01-19 19:47:57');
INSERT INTO `config_log_file` VALUES (36, '348dcc43d905434bbe345a9a096de509', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/348dcc43d905434bbe345a9a096de509.txt', 0, '2019-01-19 19:49:05');
INSERT INTO `config_log_file` VALUES (37, '6e7f0688e7234f1d8c901f4d78019f88', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/6e7f0688e7234f1d8c901f4d78019f88.txt', 0, '2019-01-19 20:03:31');
INSERT INTO `config_log_file` VALUES (38, '8b863337bdc343af8c29a9d62d975ff6', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/8b863337bdc343af8c29a9d62d975ff6.txt', 2, '2019-01-19 20:10:00');
INSERT INTO `config_log_file` VALUES (39, '942a1cbf7a3e4840a0e074d5e927b370', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/942a1cbf7a3e4840a0e074d5e927b370.txt', 4, '2019-01-19 20:14:51');
INSERT INTO `config_log_file` VALUES (40, 'c14d9f85d7f74e95bf7f684e02d41c23', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/c14d9f85d7f74e95bf7f684e02d41c23.txt', 4, '2019-01-19 20:17:37');
INSERT INTO `config_log_file` VALUES (41, 'ceba42090155452d896b0e6c92884365', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/ceba42090155452d896b0e6c92884365.txt', 4, '2019-01-19 20:18:16');
INSERT INTO `config_log_file` VALUES (42, 'e70db35acb67414faabd6da0b5282104', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/e70db35acb67414faabd6da0b5282104.txt', 4, '2019-01-19 20:18:56');
INSERT INTO `config_log_file` VALUES (43, '87004321f6ca40a28730e21c0a6bacbb', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/87004321f6ca40a28730e21c0a6bacbb.txt', 4, '2019-01-19 20:42:45');
INSERT INTO `config_log_file` VALUES (44, '6da05c2084874351856b0cac038eef21', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/6da05c2084874351856b0cac038eef21.txt', 4, '2019-01-19 20:47:04');
INSERT INTO `config_log_file` VALUES (45, '87b08007e1614300a8a3c6a4fa4601a8', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/87b08007e1614300a8a3c6a4fa4601a8.txt', 4, '2019-01-20 11:25:26');
INSERT INTO `config_log_file` VALUES (46, '28c5d5bf8fef4a0f80bd7f417023bd79', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/28c5d5bf8fef4a0f80bd7f417023bd79.txt', 4, '2019-01-20 11:27:53');
INSERT INTO `config_log_file` VALUES (47, '5a63799d2eae4f51a9390d8b9465918a', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/5a63799d2eae4f51a9390d8b9465918a.txt', 4, '2019-01-20 11:30:16');
INSERT INTO `config_log_file` VALUES (48, '47641aeaeb5a4047975b94a10c9caa08', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/47641aeaeb5a4047975b94a10c9caa08.txt', 4, '2019-01-20 11:35:32');
INSERT INTO `config_log_file` VALUES (49, '8396c897ecc84cd78c73655e0af42c0b', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/8396c897ecc84cd78c73655e0af42c0b.txt', 4, '2019-01-20 11:41:03');
INSERT INTO `config_log_file` VALUES (50, '9e2a124efa624513aab28c80c22a833b', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/9e2a124efa624513aab28c80c22a833b.txt', 4, '2019-01-20 13:45:41');
INSERT INTO `config_log_file` VALUES (51, 'ba9fbb359230432b9f8d279c75c9535c', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/ba9fbb359230432b9f8d279c75c9535c.txt', 4, '2019-01-20 13:45:51');
INSERT INTO `config_log_file` VALUES (52, '00d6e0509c7f440f9d0619f7654539f1', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/00d6e0509c7f440f9d0619f7654539f1.txt', 4, '2019-01-20 13:47:19');
INSERT INTO `config_log_file` VALUES (53, '552543ec1506458da810a3c59c3538cb', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/552543ec1506458da810a3c59c3538cb.txt', 4, '2019-01-20 13:47:22');
INSERT INTO `config_log_file` VALUES (54, '62b472de7e034061a4748ab64e211b1f', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/62b472de7e034061a4748ab64e211b1f.txt', 4, '2019-01-20 13:49:15');
INSERT INTO `config_log_file` VALUES (55, '6f3056fe05d64b6881716915d679b4a1', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/6f3056fe05d64b6881716915d679b4a1.txt', 4, '2019-01-21 09:44:47');
INSERT INTO `config_log_file` VALUES (56, '35c54499a91e4b31b0370d0436475c64', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/35c54499a91e4b31b0370d0436475c64.txt', 4, '2019-01-21 09:58:40');
INSERT INTO `config_log_file` VALUES (57, 'a00df880bf134c60a3c1825d3dad9f17', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/a00df880bf134c60a3c1825d3dad9f17.txt', 4, '2019-01-21 10:00:42');
INSERT INTO `config_log_file` VALUES (58, '59bd14a82bde4dd68c8839e9f0efdaba', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/59bd14a82bde4dd68c8839e9f0efdaba.txt', 4, '2019-01-21 10:02:29');
INSERT INTO `config_log_file` VALUES (59, 'de07678a10574fa0936ae2ff72330cbd', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/de07678a10574fa0936ae2ff72330cbd.txt', 4, '2019-01-21 10:04:50');
INSERT INTO `config_log_file` VALUES (60, '9be209f0984d45869b6dac7147087f1a', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/9be209f0984d45869b6dac7147087f1a.txt', 4, '2019-01-21 10:07:53');
INSERT INTO `config_log_file` VALUES (61, 'c6271f66890048fdacac9289511cca4c', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/c6271f66890048fdacac9289511cca4c.txt', 4, '2019-01-21 10:10:57');
INSERT INTO `config_log_file` VALUES (62, '3fe7695bf16442a6b0ae570988e2d17f', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/3fe7695bf16442a6b0ae570988e2d17f.txt', 4, '2019-01-21 10:14:50');
INSERT INTO `config_log_file` VALUES (63, '077ab8b9c8ce4217942b8ee30791e419', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/077ab8b9c8ce4217942b8ee30791e419.txt', 4, '2019-01-21 10:16:58');
INSERT INTO `config_log_file` VALUES (64, '7e6e0d85fb524b00a495e018e1905691', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/7e6e0d85fb524b00a495e018e1905691.txt', 4, '2019-01-21 10:37:04');
INSERT INTO `config_log_file` VALUES (65, 'eaa1797a87ed4851a6f49dae23ade656', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/eaa1797a87ed4851a6f49dae23ade656.txt', 4, '2019-01-21 10:39:03');
INSERT INTO `config_log_file` VALUES (66, '017657362b934289915e776bd7bf287c', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/017657362b934289915e776bd7bf287c.txt', 4, '2019-01-21 10:40:02');
INSERT INTO `config_log_file` VALUES (67, '421f4691ebaa4f9eadf05414bfcdcf9c', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/421f4691ebaa4f9eadf05414bfcdcf9c.txt', 4, '2019-01-21 10:40:41');
INSERT INTO `config_log_file` VALUES (68, '71bbb7a15ddb4148a5846de63d9ee674', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/71bbb7a15ddb4148a5846de63d9ee674.txt', 4, '2019-01-21 12:57:15');
INSERT INTO `config_log_file` VALUES (69, '3e8bf6cf02064a07ab97220108cc4761', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/3e8bf6cf02064a07ab97220108cc4761.txt', 4, '2019-01-21 12:59:47');
INSERT INTO `config_log_file` VALUES (70, '32c5a979c45142d89f5596713f1fab5b', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/32c5a979c45142d89f5596713f1fab5b.txt', 4, '2019-01-21 13:03:07');
INSERT INTO `config_log_file` VALUES (71, '31457dea8c634bc39ab3912a71f3bc4f', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/31457dea8c634bc39ab3912a71f3bc4f.txt', 4, '2019-01-21 13:04:26');
INSERT INTO `config_log_file` VALUES (72, 'bc03eb4f905d4083b6d4367841be9eaa', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/bc03eb4f905d4083b6d4367841be9eaa.txt', 4, '2019-01-21 13:07:00');
INSERT INTO `config_log_file` VALUES (73, '622a49681d344d068d820edc60f88cc2', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/622a49681d344d068d820edc60f88cc2.txt', 0, '2019-01-21 13:09:15');
INSERT INTO `config_log_file` VALUES (74, 'bb41dca149494fb8baaa0fbdad429827', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/bb41dca149494fb8baaa0fbdad429827.txt', 0, '2019-01-21 13:11:26');
INSERT INTO `config_log_file` VALUES (75, '2dd3ad217f7e4d3c8dbb825330966cd2', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/2dd3ad217f7e4d3c8dbb825330966cd2.txt', 4, '2019-01-21 13:14:01');
INSERT INTO `config_log_file` VALUES (76, '4422d12a8ccc4f48ae95a4d670e18a5d', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/4422d12a8ccc4f48ae95a4d670e18a5d.txt', 4, '2019-01-21 13:15:45');
INSERT INTO `config_log_file` VALUES (77, 'b6679d9939854df893965f9968aafa85', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/b6679d9939854df893965f9968aafa85.txt', 4, '2019-01-21 13:16:24');
INSERT INTO `config_log_file` VALUES (78, '657c73266cc94500b211ee1fea893f21', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/657c73266cc94500b211ee1fea893f21.txt', 4, '2019-01-21 13:32:02');
INSERT INTO `config_log_file` VALUES (79, '7db871de3aef41cd96fcee54928328dd', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/7db871de3aef41cd96fcee54928328dd.txt', 4, '2019-01-21 14:46:14');
INSERT INTO `config_log_file` VALUES (80, 'c2656ed363ef4e769dd12df500f4608b', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/c2656ed363ef4e769dd12df500f4608b.txt', 4, '2019-01-21 14:48:11');
INSERT INTO `config_log_file` VALUES (81, 'b756bf73e733423b8d681a53ba4468ec', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/b756bf73e733423b8d681a53ba4468ec.txt', 4, '2019-01-21 15:07:51');
INSERT INTO `config_log_file` VALUES (82, '33c632b7a750435480d49d0adf8a3ec0', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/33c632b7a750435480d49d0adf8a3ec0.txt', 4, '2019-01-21 15:12:50');
INSERT INTO `config_log_file` VALUES (83, '56afb7f61f5c4e6f814eab5f2db12d8d', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/56afb7f61f5c4e6f814eab5f2db12d8d.txt', 0, '2019-01-21 15:29:00');
INSERT INTO `config_log_file` VALUES (84, 'dfd13a088324414f87f563d302a35257', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/dfd13a088324414f87f563d302a35257.txt', 0, '2019-01-21 15:34:24');
INSERT INTO `config_log_file` VALUES (85, '4be2de2168b74340a8e6bb388b77a198', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/4be2de2168b74340a8e6bb388b77a198.txt', 0, '2019-01-21 15:39:55');
INSERT INTO `config_log_file` VALUES (86, 'da7b8232e654420cb51da54b976db760', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/da7b8232e654420cb51da54b976db760.txt', 0, '2019-01-21 15:45:00');
INSERT INTO `config_log_file` VALUES (87, '9d0397d100d346fb815e07a363f6bd7a', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/9d0397d100d346fb815e07a363f6bd7a.txt', 4, '2019-01-21 15:55:26');
INSERT INTO `config_log_file` VALUES (88, 'c52803e681094650a634668d5c23b93e', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/c52803e681094650a634668d5c23b93e.txt', 4, '2019-01-21 16:01:56');
INSERT INTO `config_log_file` VALUES (89, '071623a9cfba4c24bbd873ffcddee197', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/071623a9cfba4c24bbd873ffcddee197.txt', 4, '2019-01-21 16:04:28');
INSERT INTO `config_log_file` VALUES (90, '36b257aae43449699e5fc595a45a5509', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/36b257aae43449699e5fc595a45a5509.txt', 4, '2019-01-21 16:12:14');
INSERT INTO `config_log_file` VALUES (91, 'bc00029227c74839bc8ccb0b7702b51a', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/bc00029227c74839bc8ccb0b7702b51a.txt', 4, '2019-01-21 16:12:37');
INSERT INTO `config_log_file` VALUES (92, '52bbcce1d32a49ab974e45dfaf9a4f07', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/52bbcce1d32a49ab974e45dfaf9a4f07.txt', 4, '2019-01-21 16:14:38');
INSERT INTO `config_log_file` VALUES (93, '502e5ce4db7046c9aab8d2e05910a3aa', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/502e5ce4db7046c9aab8d2e05910a3aa.txt', 4, '2019-01-21 16:24:45');
INSERT INTO `config_log_file` VALUES (94, '24de9c525d79403086bcc04dc60a69f9', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/24de9c525d79403086bcc04dc60a69f9.txt', 4, '2019-01-21 16:28:54');
INSERT INTO `config_log_file` VALUES (95, '2022019ca66547b589e8829d75d25755', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/2022019ca66547b589e8829d75d25755.txt', 4, '2019-01-21 16:31:04');
INSERT INTO `config_log_file` VALUES (96, 'ba1b31c26fdf4c8a971476485e8d18ff', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/ba1b31c26fdf4c8a971476485e8d18ff.txt', 4, '2019-01-22 10:16:20');
INSERT INTO `config_log_file` VALUES (97, '76d8a1e460ee4e91ac3c547f76399e14', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/76d8a1e460ee4e91ac3c547f76399e14.txt', 4, '2019-01-22 10:16:42');
INSERT INTO `config_log_file` VALUES (98, '4f6be7ecb1274d7cba529cf57e465102', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/4f6be7ecb1274d7cba529cf57e465102.txt', 4, '2019-01-22 10:17:48');
INSERT INTO `config_log_file` VALUES (99, 'e7fa012c6f514769bcb9ac6b0a14dabf', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/e7fa012c6f514769bcb9ac6b0a14dabf.txt', 4, '2019-01-23 13:15:34');
INSERT INTO `config_log_file` VALUES (100, '1f91d46dd48540a6aec8921c2a921508', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/1f91d46dd48540a6aec8921c2a921508.txt', 4, '2019-01-23 13:15:49');
INSERT INTO `config_log_file` VALUES (101, '3c58d465b704437387f387effd0047d4', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/3c58d465b704437387f387effd0047d4.txt', 4, '2019-01-23 13:18:11');
INSERT INTO `config_log_file` VALUES (102, 'e73fc62ed8d048a68a94cb131124ad38', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/e73fc62ed8d048a68a94cb131124ad38.txt', 4, '2019-01-23 13:18:23');
INSERT INTO `config_log_file` VALUES (103, '346c23c73ccd4e6e919cbb5237202462', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/346c23c73ccd4e6e919cbb5237202462.txt', 4, '2019-01-23 13:19:06');
INSERT INTO `config_log_file` VALUES (104, '32623fdbac3e42aabff89dc429d16908', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/32623fdbac3e42aabff89dc429d16908.txt', 4, '2019-01-23 13:21:22');
INSERT INTO `config_log_file` VALUES (105, '8d05b533f282419e97dee074a634b739', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/8d05b533f282419e97dee074a634b739.txt', 4, '2019-01-23 13:22:29');
INSERT INTO `config_log_file` VALUES (106, '059b8bf892fb4632a7e7dc8020271f4c', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/059b8bf892fb4632a7e7dc8020271f4c.txt', 4, '2019-01-23 13:22:48');
INSERT INTO `config_log_file` VALUES (107, 'a0504317db1945c9b3ae58284257bbcb', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/a0504317db1945c9b3ae58284257bbcb.txt', 4, '2019-01-23 13:23:04');
INSERT INTO `config_log_file` VALUES (108, 'be611c9c16e94233987137abe9971d43', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/be611c9c16e94233987137abe9971d43.txt', 4, '2019-01-23 13:23:09');
INSERT INTO `config_log_file` VALUES (109, 'c3bd0fd0947c4566b6fcf7e9bccb94c2', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/c3bd0fd0947c4566b6fcf7e9bccb94c2.txt', 4, '2019-01-23 13:23:18');
INSERT INTO `config_log_file` VALUES (110, '3021eabfee834236b70e910bf1800399', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/3021eabfee834236b70e910bf1800399.txt', 4, '2019-01-23 13:23:37');
INSERT INTO `config_log_file` VALUES (111, '2c241a58b8874eb08e2673339d95a1bd', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/2c241a58b8874eb08e2673339d95a1bd.txt', 4, '2019-01-23 13:23:44');
INSERT INTO `config_log_file` VALUES (112, '7e8db004fd3e47579a89ac988c73b22b', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/7e8db004fd3e47579a89ac988c73b22b.txt', 4, '2019-01-23 13:23:52');
INSERT INTO `config_log_file` VALUES (113, 'b6d83b1d71ab4a16bcfbc0edfb4ddd95', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/b6d83b1d71ab4a16bcfbc0edfb4ddd95.txt', 4, '2019-01-23 14:19:24');
INSERT INTO `config_log_file` VALUES (114, '4e80980c9f414b9c8567bc86bf901d61', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/01/4e80980c9f414b9c8567bc86bf901d61.txt', 4, '2019-01-23 14:26:23');
INSERT INTO `config_log_file` VALUES (115, 'a50bb2afa56b4d3c9cdb732d27992294', 'file:/F:/工作/spring-jar/web_crawle-0.0.2-SNAPSHOT.jar!/BOOT-INF/classes!//log/2019/02/a50bb2afa56b4d3c9cdb732d27992294.txt', 4, '2019-02-27 12:55:00');
INSERT INTO `config_log_file` VALUES (116, '5bd72f21f0ba4d058d601efc89b9b918', 'file:/F:/工作/spring-jar/web_crawle-0.0.2-SNAPSHOT.jar!/BOOT-INF/classes!//log/2019/02/5bd72f21f0ba4d058d601efc89b9b918.txt', 4, '2019-02-27 13:01:00');
INSERT INTO `config_log_file` VALUES (117, '53ac2f3802cd41a2ab22355b6620a884', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/02/53ac2f3802cd41a2ab22355b6620a884.txt', 4, '2019-02-27 13:24:57');
INSERT INTO `config_log_file` VALUES (118, '1fba70aaf0f54d909a036d1a2e9ec2c1', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/02/1fba70aaf0f54d909a036d1a2e9ec2c1.txt', 4, '2019-02-27 13:30:09');
INSERT INTO `config_log_file` VALUES (119, 'd7d5ea6a7bad499293ea8edfe3c53e26', 'file:/F:/工作/spring-jar/log/2019/02/d7d5ea6a7bad499293ea8edfe3c53e26.txt', 4, '2019-02-27 13:39:01');
INSERT INTO `config_log_file` VALUES (120, 'a01ec6f466b84f0797b2c64805dbc715', 'F:/工作/spring-ja/log/2019/02/a01ec6f466b84f0797b2c64805dbc715.txt', 4, '2019-02-27 13:57:40');
INSERT INTO `config_log_file` VALUES (121, '30d001ba62fe4fa3b62e376c74db6b11', 'F:/工作/spring-ja/log/2019/02/30d001ba62fe4fa3b62e376c74db6b11.txt', 4, '2019-02-27 14:03:06');
INSERT INTO `config_log_file` VALUES (122, 'e3c4cc3cdc7b4b1d8d479cbf10e1889e', '/E:/private/project_crawle/web_crawle/target/classes//log/2019/04/e3c4cc3cdc7b4b1d8d479cbf10e1889e.txt', 4, '2019-04-19 17:26:12');

-- ----------------------------
-- Table structure for config_role
-- ----------------------------
DROP TABLE IF EXISTS `config_role`;
CREATE TABLE `config_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色名称',
  `vail_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'Y' COMMENT '角色名称',
  `createid` int(0) NULL DEFAULT NULL,
  `version` int(0) NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_role
-- ----------------------------
INSERT INTO `config_role` VALUES (3, 'test', '测试', 'Y', 12, 0, '2019-04-17 16:25:03');
INSERT INTO `config_role` VALUES (4, 'client-user', '测试用户', 'Y', 12, 0, '2019-04-24 15:58:12');

-- ----------------------------
-- Table structure for config_table
-- ----------------------------
DROP TABLE IF EXISTS `config_table`;
CREATE TABLE `config_table`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '表名',
  `table_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '表编码',
  `group_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '分组',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '描述',
  `createId` int(0) NULL DEFAULT NULL,
  `version` int(0) NOT NULL DEFAULT 1,
  `ts` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '时间戳',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '前端表设计--表名' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_table
-- ----------------------------
INSERT INTO `config_table` VALUES (18, '测试', 'test_demo', 'test', 'ceshi', -1, 1, '2019-01-06 14:25:41');

-- ----------------------------
-- Table structure for config_table_column
-- ----------------------------
DROP TABLE IF EXISTS `config_table_column`;
CREATE TABLE `config_table_column`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `tableid` int(0) NULL DEFAULT NULL COMMENT '表id',
  `attr` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '列名',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '类型',
  `is_null` int(0) NULL DEFAULT 0,
  `is_primary` int(0) NULL DEFAULT 0,
  `createId` int(0) NULL DEFAULT NULL,
  `ts` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '时间戳',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '前端表设计--列名' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_table_column
-- ----------------------------
INSERT INTO `config_table_column` VALUES (1, 18, 'id', '主键', 'int', 0, 1, -1, '2019-01-06 14:25:41');
INSERT INTO `config_table_column` VALUES (2, 18, 'name', '名称', 'varchart', 0, 0, -1, '2019-01-06 14:25:41');
INSERT INTO `config_table_column` VALUES (3, 18, 'key', '键', 'varchart', 0, 0, -1, '2019-01-06 14:25:41');

-- ----------------------------
-- Table structure for config_user_role_relate
-- ----------------------------
DROP TABLE IF EXISTS `config_user_role_relate`;
CREATE TABLE `config_user_role_relate`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL,
  `role_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `createid` int(0) NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户-角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_user_role_relate
-- ----------------------------
INSERT INTO `config_user_role_relate` VALUES (2, 6, 'test', 12, '2019-05-05 09:50:06');
INSERT INTO `config_user_role_relate` VALUES (3, 6, 'client-user', 12, '2019-05-05 09:50:06');

-- ----------------------------
-- Table structure for crawler_config
-- ----------------------------
DROP TABLE IF EXISTS `crawler_config`;
CREATE TABLE `crawler_config`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '调用的class',
  `depth` int(0) NULL DEFAULT 5,
  `top_n` int(0) NULL DEFAULT 10,
  `threads` int(0) NULL DEFAULT 10,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `js` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '调用的js',
  `version` int(0) NOT NULL DEFAULT 0 COMMENT '版本号',
  `ts` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `config_code_unqine`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '爬虫配置主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crawler_config_filter
-- ----------------------------
DROP TABLE IF EXISTS `crawler_config_filter`;
CREATE TABLE `crawler_config_filter`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `config_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '配置编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '过滤条件',
  `ts` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '爬虫配置--过滤器配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crawler_config_url
-- ----------------------------
DROP TABLE IF EXISTS `crawler_config_url`;
CREATE TABLE `crawler_config_url`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `config_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '配置编码',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求方法',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `condtion` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'url生成条件',
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '爬虫配置--爬取的url' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for demo
-- ----------------------------
DROP TABLE IF EXISTS `demo`;
CREATE TABLE `demo`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of demo
-- ----------------------------
INSERT INTO `demo` VALUES (1, 'temp1', '2019-09-27 15:52:02');
INSERT INTO `demo` VALUES (2, 'temp1', '2019-09-27 16:20:48');
INSERT INTO `demo` VALUES (3, 'temp1', '2019-09-27 16:21:50');
INSERT INTO `demo` VALUES (4, 'temp1', '2019-09-27 16:44:07');
INSERT INTO `demo` VALUES (5, 'temp1', '2019-09-27 21:47:57');
INSERT INTO `demo` VALUES (6, 'temp1', '2019-09-27 21:51:28');
INSERT INTO `demo` VALUES (7, 'temp1', '2019-09-27 21:51:28');
INSERT INTO `demo` VALUES (8, 'temp1', '2019-09-27 21:53:12');
INSERT INTO `demo` VALUES (9, 'temp1', '2019-09-27 21:53:59');
INSERT INTO `demo` VALUES (10, 'temp1', '2019-09-27 21:54:29');
INSERT INTO `demo` VALUES (11, 'temp1', '2019-09-27 22:14:29');
INSERT INTO `demo` VALUES (12, 'temp1', '2019-09-27 22:14:52');
INSERT INTO `demo` VALUES (13, 'temp1', '2019-09-27 22:15:01');
INSERT INTO `demo` VALUES (14, 'temp1', '2019-09-27 22:18:18');
INSERT INTO `demo` VALUES (15, 'temp1', '2019-09-27 22:20:48');
INSERT INTO `demo` VALUES (16, 'temp1', '2019-09-27 22:22:02');
INSERT INTO `demo` VALUES (17, 'temp1', '2019-09-27 22:23:24');
INSERT INTO `demo` VALUES (18, 'temp1', '2019-09-27 22:27:05');
INSERT INTO `demo` VALUES (19, 'temp1', '2019-09-27 22:32:20');
INSERT INTO `demo` VALUES (20, 'temp1', '2019-09-27 22:36:34');
INSERT INTO `demo` VALUES (21, 'temp1', '2019-09-27 22:37:11');
INSERT INTO `demo` VALUES (22, 'temp1', '2019-09-27 22:41:00');
INSERT INTO `demo` VALUES (23, 'temp1', '2019-09-27 22:42:43');
INSERT INTO `demo` VALUES (24, 'temp1', '2019-09-28 13:45:43');
INSERT INTO `demo` VALUES (25, 'temp1', '2019-09-28 13:52:03');
INSERT INTO `demo` VALUES (26, 'temp1', '2019-09-28 13:54:09');
INSERT INTO `demo` VALUES (27, 'temp1', '2019-09-28 13:57:44');
INSERT INTO `demo` VALUES (28, 'temp1', '2019-09-28 14:03:40');
INSERT INTO `demo` VALUES (29, 'temp1', '2019-09-28 14:10:05');
INSERT INTO `demo` VALUES (30, 'temp1', '2019-09-28 14:12:02');
INSERT INTO `demo` VALUES (31, 'temp1', '2019-09-28 14:14:56');
INSERT INTO `demo` VALUES (32, 'temp1', '2019-09-28 14:16:43');
INSERT INTO `demo` VALUES (33, 'temp1', '2019-09-28 14:18:43');
INSERT INTO `demo` VALUES (34, 'temp1', '2019-09-28 14:29:04');
INSERT INTO `demo` VALUES (35, 'temp1', '2019-09-28 14:37:56');
INSERT INTO `demo` VALUES (36, 'temp1', '2019-09-28 14:38:52');
INSERT INTO `demo` VALUES (37, 'temp1', '2019-09-28 14:42:20');
INSERT INTO `demo` VALUES (38, 'temp1', '2019-09-28 14:59:13');
INSERT INTO `demo` VALUES (39, 'temp1', '2019-09-28 15:05:34');
INSERT INTO `demo` VALUES (40, 'temp1', '2019-09-28 15:09:41');
INSERT INTO `demo` VALUES (41, 'temp1', '2019-09-28 15:19:58');
INSERT INTO `demo` VALUES (42, 'temp1', '2019-09-28 15:21:42');
INSERT INTO `demo` VALUES (43, 'temp1', '2019-09-28 15:28:40');
INSERT INTO `demo` VALUES (44, 'temp1', '2019-09-28 15:30:36');
INSERT INTO `demo` VALUES (45, 'temp1', '2019-09-28 15:31:47');
INSERT INTO `demo` VALUES (46, 'temp1', '2019-09-28 15:44:07');
INSERT INTO `demo` VALUES (47, 'temp1', '2019-09-28 16:32:08');
INSERT INTO `demo` VALUES (48, 'temp1', '2019-09-28 16:33:25');
INSERT INTO `demo` VALUES (49, 'temp1', '2019-09-28 16:42:03');
INSERT INTO `demo` VALUES (50, 'temp1', '2019-09-28 17:06:34');
INSERT INTO `demo` VALUES (51, 'temp1', '2019-09-28 17:08:39');
INSERT INTO `demo` VALUES (52, 'temp1', '2019-09-28 17:09:44');
INSERT INTO `demo` VALUES (53, 'temp1', '2019-09-28 17:11:00');
INSERT INTO `demo` VALUES (54, 'temp1', '2019-09-28 17:12:03');
INSERT INTO `demo` VALUES (55, 'temp1', '2019-09-28 17:13:48');
INSERT INTO `demo` VALUES (56, 'temp1', '2019-09-28 17:14:30');
INSERT INTO `demo` VALUES (57, 'temp1', '2019-09-28 17:15:01');
INSERT INTO `demo` VALUES (58, 'temp1', '2019-09-28 17:15:27');
INSERT INTO `demo` VALUES (59, 'temp1', '2019-09-28 17:16:26');
INSERT INTO `demo` VALUES (60, 'temp1', '2019-09-28 17:17:11');
INSERT INTO `demo` VALUES (61, 'temp1', '2019-09-28 17:17:32');
INSERT INTO `demo` VALUES (62, 'temp1', '2019-09-28 17:19:59');
INSERT INTO `demo` VALUES (63, 'temp1', '2019-09-28 17:23:15');
INSERT INTO `demo` VALUES (64, 'temp1', '2019-09-28 17:23:27');
INSERT INTO `demo` VALUES (65, 'temp1', '2019-09-28 17:23:36');
INSERT INTO `demo` VALUES (66, 'temp1', '2019-09-28 17:24:38');
INSERT INTO `demo` VALUES (67, 'temp1', '2019-09-28 17:24:43');
INSERT INTO `demo` VALUES (68, 'temp1', '2019-09-30 09:23:20');
INSERT INTO `demo` VALUES (69, 'temp1', '2019-10-08 09:29:16');
INSERT INTO `demo` VALUES (70, 'temp1', '2019-10-08 09:33:19');
INSERT INTO `demo` VALUES (71, 'temp1', '2019-10-08 09:34:13');
INSERT INTO `demo` VALUES (72, 'temp1', '2019-10-08 09:34:46');
INSERT INTO `demo` VALUES (73, 'temp1', '2019-10-08 09:37:46');
INSERT INTO `demo` VALUES (74, 'temp1', '2019-10-08 09:38:39');
INSERT INTO `demo` VALUES (75, 'temp1', '2019-10-08 09:44:06');
INSERT INTO `demo` VALUES (76, 'temp1', '2019-10-08 09:44:47');
INSERT INTO `demo` VALUES (77, 'temp1', '2019-10-08 09:46:56');
INSERT INTO `demo` VALUES (78, 'temp1', '2019-10-08 10:15:07');
INSERT INTO `demo` VALUES (79, 'temp1', '2019-10-08 10:15:55');
INSERT INTO `demo` VALUES (80, 'temp1', '2019-10-08 10:16:45');
INSERT INTO `demo` VALUES (81, 'temp1', '2019-10-08 10:24:35');
INSERT INTO `demo` VALUES (82, 'temp1', '2019-10-08 10:26:46');
INSERT INTO `demo` VALUES (83, 'temp1', '2019-10-08 10:37:04');
INSERT INTO `demo` VALUES (84, 'temp1', '2019-10-08 13:13:58');
INSERT INTO `demo` VALUES (85, 'temp1', '2019-10-08 13:17:03');
INSERT INTO `demo` VALUES (86, 'temp1', '2019-10-08 13:32:10');
INSERT INTO `demo` VALUES (87, 'temp1', '2019-10-08 13:33:11');
INSERT INTO `demo` VALUES (88, 'temp1', '2019-10-08 13:37:15');
INSERT INTO `demo` VALUES (89, 'temp1', '2019-10-08 13:39:53');
INSERT INTO `demo` VALUES (90, 'temp1', '2019-10-08 13:41:33');
INSERT INTO `demo` VALUES (91, 'temp1', '2019-10-08 13:42:04');
INSERT INTO `demo` VALUES (92, 'temp1', '2019-10-08 13:59:54');
INSERT INTO `demo` VALUES (93, 'temp1', '2019-10-08 14:03:38');
INSERT INTO `demo` VALUES (94, 'temp1', '2019-10-08 14:43:35');
INSERT INTO `demo` VALUES (95, 'temp1', '2019-10-08 14:49:36');
INSERT INTO `demo` VALUES (96, 'temp1', '2019-10-08 14:54:00');
INSERT INTO `demo` VALUES (97, 'temp1', '2019-10-08 14:57:08');
INSERT INTO `demo` VALUES (98, 'temp1', '2019-10-08 14:59:12');
INSERT INTO `demo` VALUES (99, 'temp1', '2019-10-08 15:03:37');
INSERT INTO `demo` VALUES (100, 'temp1', '2019-10-08 15:06:17');
INSERT INTO `demo` VALUES (101, 'temp1', '2019-10-08 15:08:59');
INSERT INTO `demo` VALUES (102, 'temp1', '2019-10-08 15:17:02');
INSERT INTO `demo` VALUES (103, 'temp1', '2019-10-08 15:18:29');
INSERT INTO `demo` VALUES (104, 'temp1', '2019-10-08 15:22:11');
INSERT INTO `demo` VALUES (105, 'temp1', '2019-10-08 15:27:07');
INSERT INTO `demo` VALUES (106, 'temp1', '2019-10-08 15:31:47');
INSERT INTO `demo` VALUES (107, 'temp1', '2019-10-08 15:34:38');
INSERT INTO `demo` VALUES (108, 'temp1', '2019-10-08 15:36:43');
INSERT INTO `demo` VALUES (109, 'temp1', '2019-10-08 15:38:43');
INSERT INTO `demo` VALUES (110, 'temp1', '2019-10-08 15:39:28');
INSERT INTO `demo` VALUES (111, 'temp1', '2019-10-08 15:41:30');
INSERT INTO `demo` VALUES (112, 'temp1', '2019-10-08 15:43:55');
INSERT INTO `demo` VALUES (113, 'temp1', '2019-10-08 15:46:06');
INSERT INTO `demo` VALUES (114, 'temp1', '2019-10-08 17:03:12');
INSERT INTO `demo` VALUES (115, 'temp1', '2019-10-08 17:03:52');

-- ----------------------------
-- Table structure for job_task
-- ----------------------------
DROP TABLE IF EXISTS `job_task`;
CREATE TABLE `job_task`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `task_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `task_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `target_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `group_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `json_params` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT 0,
  `run_type` int(0) NULL DEFAULT 0,
  `run_times` int(0) NULL DEFAULT 0,
  `run_err_times` int(0) NULL DEFAULT 0,
  `run_err_msg` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `schedule_model` int(0) NULL DEFAULT 0,
  `limit_target_node` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `version` int(0) NULL DEFAULT 0,
  `createid` int(0) NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '任务调度--执行任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job_task
-- ----------------------------
INSERT INTO `job_task` VALUES (1, 'test_1234567', 'ceshi', 'com.sky.task.demo.SimpleJob', '1.0.0_dev.test', NULL, '{\"id\":2,\"name\":\"tome\"}', 0, 0, 7, 0, NULL, 0, NULL, NULL, 13, -1, '2019-02-19 13:18:47');
INSERT INTO `job_task` VALUES (2, '7b5b0c5a97c040b4bd970b0f91c0c971', 'job1', 'com.sky.task.demo.SimpleJob', '1.0.0_dev.test', '* * * * * ? ', NULL, 0, 0, 1, 0, NULL, 0, NULL, 'cehiix', 2, -1, '2019-02-19 10:34:48');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pid` int(0) NOT NULL,
  `createId` int(0) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` int(0) NOT NULL DEFAULT 1,
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code_name_same`(`code`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '爬虫配置--目录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 'sys_set', '设置', 0, NULL, '/config/page/setting', 0, '2018-12-29 14:50:27');
INSERT INTO `menu` VALUES (2, 'sys_config', '配置', 0, NULL, '#', 0, '2018-12-23 18:37:11');
INSERT INTO `menu` VALUES (37, 'sys_config_table', '数据库配置', 2, -1, '/config/page/table/index', 2, '2018-12-29 10:08:59');
INSERT INTO `menu` VALUES (40, 'sys_sql_w', 'sql书写', 2, -1, '/config/page/sql/index', 1, '2019-01-07 16:26:31');
INSERT INTO `menu` VALUES (41, 'sys_crwaler_code', '爬虫设置', 2, -1, '/config/page/crawler/index', 1, '2019-01-18 09:42:52');

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(0) NULL DEFAULT NULL,
  `refresh_token_validity` int(0) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for property_enum
-- ----------------------------
DROP TABLE IF EXISTS `property_enum`;
CREATE TABLE `property_enum`  (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `group_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '枚举组编码',
  `group_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '枚举组名称',
  `project` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目编码',
  `profile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目环境',
  `version_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目版本',
  `version` int(0) NULL DEFAULT NULL,
  `createid` varchar(0) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `enum_group_unique`(`group_no`, `project`, `profile`, `version_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of property_enum
-- ----------------------------
INSERT INTO `property_enum` VALUES (0000000001, 'menu_test', '测试菜单', 'demo_client', 'test', '1.0.0', 0, NULL, '2020-02-01 06:30:42');

-- ----------------------------
-- Table structure for property_enum_value
-- ----------------------------
DROP TABLE IF EXISTS `property_enum_value`;
CREATE TABLE `property_enum_value`  (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `enum_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '枚举编码',
  `enum_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '枚举名称',
  `group_id` int(0) NOT NULL COMMENT '枚举分组id',
  `local` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '语言环境',
  `version` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `createid` varchar(0) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of property_enum_value
-- ----------------------------
INSERT INTO `property_enum_value` VALUES (0000000001, 'sql_test', 'sql测试', 1, NULL, '0', NULL, '2020-02-01 11:27:48');
INSERT INTO `property_enum_value` VALUES (0000000002, 'page_qs', 'qs页面', 1, NULL, '0', NULL, '2020-02-01 11:52:42');

-- ----------------------------
-- Table structure for property_value
-- ----------------------------
DROP TABLE IF EXISTS `property_value`;
CREATE TABLE `property_value`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `key` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `project` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '项目名称',
  `profile` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '项目环境（分支）',
  `version_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '项目版本',
  `is_global` int(0) NULL DEFAULT 0 COMMENT '是否全局',
  `local` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '本地化语言',
  `version` int(0) NULL DEFAULT 0,
  `createid` int(0) NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `un_project_version_key`(`key`, `project`, `profile`, `version_code`, `local`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '链路--服务属性配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of property_value
-- ----------------------------
INSERT INTO `property_value` VALUES (1, 'service.name', 'demo_time', 'demo', 'dev', NULL, 0, NULL, 0, NULL, '2019-03-08 09:46:20');
INSERT INTO `property_value` VALUES (2, 'app.name', 'appces', 'demo_client', 'test', '1.0.0', 0, NULL, 0, NULL, '2019-03-08 15:31:02');
INSERT INTO `property_value` VALUES (3, 'app_client', 'test-1', 'demo', 'dev', '1.0', 1, NULL, 7, NULL, '2019-05-11 15:08:32');
INSERT INTO `property_value` VALUES (4, 'app.profile', 'dev', 'demo', 'test', '1.0', 0, NULL, 0, NULL, '2019-05-11 15:33:01');
INSERT INTO `property_value` VALUES (5, 'rpc.node.prefix', 'rpc_node_', 'demo_client', 'dev', '1.0.0', 0, 'zh_CN', 1, NULL, '2019-12-11 14:21:16');
INSERT INTO `property_value` VALUES (6, 'rpc.version', '0.0.1', 'demo_client', 'dev', '1.0.0', 0, 'zh_CN', 1, NULL, '2019-12-11 14:21:13');
INSERT INTO `property_value` VALUES (7, 'rpc.group', 'group', 'demo_client', 'dev', '1.0.0', 0, 'zh_CN', 1, NULL, '2019-12-11 14:21:11');
INSERT INTO `property_value` VALUES (8, 'rpc.zookeeper.url', '127.0.0.1:2181', 'demo_client', 'dev', '1.0.0', 0, 'zh_CN', 1, NULL, '2019-12-11 14:21:08');
INSERT INTO `property_value` VALUES (9, 'demo.test.dd', '9000总比', 'demo_client', 'test', '1.0.0', 0, 'zh_CN', 1, NULL, '2019-12-11 14:21:04');
INSERT INTO `property_value` VALUES (15, 'demo.test.dd', '800h', 'demo_client', 'test', '1.0.0', 0, 'en_US', 0, NULL, '2019-12-11 14:05:14');

-- ----------------------------
-- Table structure for property_value_his
-- ----------------------------
DROP TABLE IF EXISTS `property_value_his`;
CREATE TABLE `property_value_his`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `project` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `profile` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `version_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `is_global` int(0) NULL DEFAULT 0 COMMENT '是否是全局',
  `pid` int(0) NULL DEFAULT NULL,
  `local` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '本地化支持',
  `version` int(0) NULL DEFAULT 0,
  `createid` int(0) NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '链路--服务属性配置历史' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of property_value_his
-- ----------------------------
INSERT INTO `property_value_his` VALUES (1, 'app_client', 'test-1', 'demo', 'dev', '1.0', 0, 3, NULL, 0, NULL, '2019-05-11 15:00:50');
INSERT INTO `property_value_his` VALUES (2, 'app_client', 'test-1', 'demo', 'dev', '1.0', 0, 3, NULL, 0, NULL, '2019-05-11 15:01:29');
INSERT INTO `property_value_his` VALUES (3, 'app_client', 'test-1', 'demo', 'dev', '1.0', 0, 3, NULL, 0, NULL, '2019-05-11 15:04:09');
INSERT INTO `property_value_his` VALUES (4, 'app_client', 'test-1', 'demo', 'dev', '1.0', 0, 3, NULL, 0, NULL, '2019-05-11 15:04:19');
INSERT INTO `property_value_his` VALUES (5, 'app_client', 'test-1', 'demo', 'dev', '1.0', 0, 3, NULL, 0, NULL, '2019-05-11 15:04:59');
INSERT INTO `property_value_his` VALUES (6, 'app_client', 'test-1', 'demo', 'dev', '1.0', 0, 3, NULL, 0, NULL, '2019-05-11 15:08:23');
INSERT INTO `property_value_his` VALUES (7, 'app_client', 'test-1', 'demo', 'dev', '1.0', 0, 3, NULL, 0, NULL, '2019-05-11 15:08:32');

-- ----------------------------
-- Table structure for task_group
-- ----------------------------
DROP TABLE IF EXISTS `task_group`;
CREATE TABLE `task_group`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分组id',
  `group_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '分组名称',
  `status` int(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `version` int(0) NULL DEFAULT NULL,
  `createid` int(0) NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '任务调度--分组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task_group
-- ----------------------------
INSERT INTO `task_group` VALUES (1, '1.0.0_dev.test', '测试', 0, NULL, 0, NULL, '2019-02-15 16:38:05');

-- ----------------------------
-- Table structure for trace_limit_define
-- ----------------------------
DROP TABLE IF EXISTS `trace_limit_define`;
CREATE TABLE `trace_limit_define`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `server_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '项目名称',
  `profile` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '项目环境',
  `server_version` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `count` int(0) NULL DEFAULT NULL,
  `priod` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '持续时间',
  `is_global` int(2) UNSIGNED ZEROFILL NULL DEFAULT 00 COMMENT '是否是全局适配',
  `version` int(0) NULL DEFAULT NULL,
  `createid` int(0) NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '链路--限流配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trace_limit_define
-- ----------------------------
INSERT INTO `trace_limit_define` VALUES (1, '/limit/{word}', 'demo', 'dev', '1.0', 5, '1', 00, 3, NULL, '2019-05-12 11:57:14');
INSERT INTO `trace_limit_define` VALUES (2, '/page/{name}', 'demo_client', 'test', '1.0.0', 10, '1', 01, 1, NULL, '2019-05-12 11:42:48');

-- ----------------------------
-- Table structure for trace_project_profile
-- ----------------------------
DROP TABLE IF EXISTS `trace_project_profile`;
CREATE TABLE `trace_project_profile`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `profile` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `version_list` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `server_name_profile`(`service_name`, `profile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '链路--项目基本信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trace_project_profile
-- ----------------------------
INSERT INTO `trace_project_profile` VALUES (1, 'demo', 'dev', '1.0.1,1.2', '2019-05-11 13:20:21');
INSERT INTO `trace_project_profile` VALUES (2, 'demo', 'test', '1.0', '2019-05-11 12:16:53');
INSERT INTO `trace_project_profile` VALUES (3, 'demo_client', 'test', '1.0.0', '2020-02-02 12:46:20');

-- ----------------------------
-- Table structure for trace_record
-- ----------------------------
DROP TABLE IF EXISTS `trace_record`;
CREATE TABLE `trace_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `trace_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '链路id',
  `trace_pid` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `group_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '分组id',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '接口路径',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '接口类型',
  `status` int(0) NULL DEFAULT NULL,
  `request_body` varchar(3000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求参数',
  `response_body` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `headers` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'header',
  `session_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '会话id',
  `cost` int(0) NULL DEFAULT NULL COMMENT '耗时/ms',
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `trace_group_session_id_url_unique`(`trace_id`, `trace_pid`, `group_id`, `url`, `session_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '链路--url记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trace_record
-- ----------------------------
INSERT INTO `trace_record` VALUES (1, '24303c58-891c-43cf-b134-923c3f2117e8', '97a8e3b3-2ce3-48eb-8230-4a562ee577a3', '24303c58-891c-43cf-b134-923c3f2117e8', 'http:\\localhost:8800/say/sfsdsw', 'GET', 200, NULL, NULL, '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=912984C6203393FB0875409516071A4F\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\"}', '912984C6203393FB0875409516071A4F', 111, '2019-03-22 09:01:13');
INSERT INTO `trace_record` VALUES (7, '54bb9bf0-3ede-406a-a332-02c31e5c7d2b', '13f258d7-c78b-4c77-a91a-942de87fb276', '54bb9bf0-3ede-406a-a332-02c31e5c7d2b', 'http://localhost:8800/talk', 'GET', 200, 'word=3232', '{\"code\":\"0\",\"data\":\"you talk:3232\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=C638FEFCC712E1D67259C6C74A69AD81\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\"}', 'C638FEFCC712E1D67259C6C74A69AD81', 26, '2019-03-22 09:01:06');
INSERT INTO `trace_record` VALUES (8, '398c9c57-78d5-46cf-8b57-bc56d901afcd', NULL, NULL, 'http://localhost:9000/project/regist', 'POST', 200, '{\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://localhost:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', NULL, '', NULL, 1440, '2019-05-08 14:33:26');
INSERT INTO `trace_record` VALUES (9, '75736019-d289-4e1f-a0fb-058bd286b016', NULL, NULL, 'http://localhost:9000/project/regist', 'POST', 200, '{\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://localhost:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', NULL, '', NULL, 244, '2019-05-08 14:51:56');
INSERT INTO `trace_record` VALUES (10, '2175e307-c040-4143-9e0c-6b4e01219be0', NULL, '2175e307-c040-4143-9e0c-6b4e01219be0', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; JSESSIONID=80ED5D5D415974AF2D81EA42EE59827C; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 117, '2019-05-08 14:53:34');
INSERT INTO `trace_record` VALUES (11, '189b260a-5f2e-4dc9-89aa-f1041f91ab85', '2175e307-c040-4143-9e0c-6b4e01219be0', '189b260a-5f2e-4dc9-89aa-f1041f91ab85', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 67, '2019-05-08 15:12:24');
INSERT INTO `trace_record` VALUES (12, 'cb03b52f-b1e8-4be7-93ea-9e16a818d3e9', '2175e307-c040-4143-9e0c-6b4e01219be0', 'cb03b52f-b1e8-4be7-93ea-9e16a818d3e9', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 39, '2019-05-08 15:12:52');
INSERT INTO `trace_record` VALUES (13, '4afbe80e-6a01-42ea-b416-6ecb65656f73', '2175e307-c040-4143-9e0c-6b4e01219be0', '4afbe80e-6a01-42ea-b416-6ecb65656f73', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 40461, '2019-05-08 15:12:53');
INSERT INTO `trace_record` VALUES (14, '62ae3dbf-fc09-48d4-8a4e-ac2ce7c1abb4', '2175e307-c040-4143-9e0c-6b4e01219be0', '62ae3dbf-fc09-48d4-8a4e-ac2ce7c1abb4', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 158, '2019-05-08 15:13:33');
INSERT INTO `trace_record` VALUES (15, 'b80c6fdd-23b8-4a0b-aa97-8f15af26e1df', '2175e307-c040-4143-9e0c-6b4e01219be0', 'b80c6fdd-23b8-4a0b-aa97-8f15af26e1df', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"empty\":false,\"model\":{\"timestamp\":1557299618756,\"status\":200,\"error\":\"OK\",\"exception\":\"java.lang.IllegalStateException\",\"message\":\"getWriter() has already been called for this response\",\"path\":\"/say/sfsdsw\"},\"modelMap\":{\"$ref\":\"$.model\"},\"reference\":true,\"viewName\":\"error\"}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 52, '2019-05-08 15:13:39');
INSERT INTO `trace_record` VALUES (16, 'e7534585-2b63-4c66-88fe-a27ec38a0ff8', '2175e307-c040-4143-9e0c-6b4e01219be0', 'e7534585-2b63-4c66-88fe-a27ec38a0ff8', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 47, '2019-05-08 15:14:01');
INSERT INTO `trace_record` VALUES (17, '6fa9f06b-951c-4ecf-a3b6-32bd2852a281', '2175e307-c040-4143-9e0c-6b4e01219be0', '6fa9f06b-951c-4ecf-a3b6-32bd2852a281', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 28, '2019-05-08 15:14:04');
INSERT INTO `trace_record` VALUES (18, '04954f90-8e5e-468c-b522-7eadbc941872', '2175e307-c040-4143-9e0c-6b4e01219be0', '04954f90-8e5e-468c-b522-7eadbc941872', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 41, '2019-05-08 15:14:05');
INSERT INTO `trace_record` VALUES (19, '6e8c0045-3ce6-48c8-b77c-772de57cb9cb', '2175e307-c040-4143-9e0c-6b4e01219be0', '6e8c0045-3ce6-48c8-b77c-772de57cb9cb', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 37, '2019-05-08 15:14:07');
INSERT INTO `trace_record` VALUES (20, 'd3651004-d6b6-4303-9041-026168e65aa8', '2175e307-c040-4143-9e0c-6b4e01219be0', 'd3651004-d6b6-4303-9041-026168e65aa8', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 44, '2019-05-08 15:14:08');
INSERT INTO `trace_record` VALUES (21, 'ad27006b-150f-49fc-ac54-6993320e0a2f', '2175e307-c040-4143-9e0c-6b4e01219be0', 'ad27006b-150f-49fc-ac54-6993320e0a2f', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"empty\":false,\"model\":{\"timestamp\":1557299648227,\"status\":200,\"error\":\"OK\",\"exception\":\"java.lang.IllegalStateException\",\"message\":\"getWriter() has already been called for this response\",\"path\":\"/say/sfsdsw\"},\"modelMap\":{\"$ref\":\"$.model\"},\"reference\":true,\"viewName\":\"error\"}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 137, '2019-05-08 15:14:08');
INSERT INTO `trace_record` VALUES (22, '4af4cae4-c20b-4f3d-9002-1adbeb61428d', '2175e307-c040-4143-9e0c-6b4e01219be0', '4af4cae4-c20b-4f3d-9002-1adbeb61428d', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 82, '2019-05-08 15:17:39');
INSERT INTO `trace_record` VALUES (23, '51d8e19b-f582-45e3-9f96-664b40a062f6', '2175e307-c040-4143-9e0c-6b4e01219be0', '51d8e19b-f582-45e3-9f96-664b40a062f6', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 38, '2019-05-08 15:17:42');
INSERT INTO `trace_record` VALUES (24, '3f56ba77-faa9-45ba-bc4d-edce01195783', '2175e307-c040-4143-9e0c-6b4e01219be0', '3f56ba77-faa9-45ba-bc4d-edce01195783', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 49, '2019-05-08 15:17:46');
INSERT INTO `trace_record` VALUES (25, 'a0d0b663-a308-4705-b4db-81d458e84794', '2175e307-c040-4143-9e0c-6b4e01219be0', 'a0d0b663-a308-4705-b4db-81d458e84794', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 35, '2019-05-08 15:17:48');
INSERT INTO `trace_record` VALUES (26, '611a2eb6-2f34-4f99-b6e9-0b59a494bc33', '2175e307-c040-4143-9e0c-6b4e01219be0', '611a2eb6-2f34-4f99-b6e9-0b59a494bc33', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 85, '2019-05-08 15:17:50');
INSERT INTO `trace_record` VALUES (27, '6f80d599-885b-4f10-a026-9be81d12dc54', '2175e307-c040-4143-9e0c-6b4e01219be0', '6f80d599-885b-4f10-a026-9be81d12dc54', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"empty\":false,\"model\":{\"timestamp\":1557299869939,\"status\":200,\"error\":\"OK\",\"exception\":\"java.lang.IllegalStateException\",\"message\":\"getWriter() has already been called for this response\",\"path\":\"/say/sfsdsw\"},\"modelMap\":{\"$ref\":\"$.model\"},\"reference\":true,\"viewName\":\"error\"}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '8AF11B59C603429D5B8A853849EABFBC', 69, '2019-05-08 15:17:50');
INSERT INTO `trace_record` VALUES (28, 'b6d5b9fe-92eb-43f0-8e4c-bb3dcc29d01a', NULL, NULL, 'http://localhost:9000/project/regist', 'POST', 200, '{\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://localhost:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', NULL, '', NULL, 495, '2019-05-08 15:20:04');
INSERT INTO `trace_record` VALUES (29, '5979c527-d279-4df0-8f02-c1f75a92efdd', NULL, NULL, 'http://127.0.0.1:9000/project/regist', 'POST', 200, '{\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://127.0.0.1:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', NULL, '', NULL, 172, '2019-05-08 15:25:05');
INSERT INTO `trace_record` VALUES (30, '9b62a0a0-6caf-45cc-bb8e-451567b3e8ab', NULL, NULL, 'http://127.0.0.1:9000/project/regist', 'POST', 200, '{\"enablelimit\":false,\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://127.0.0.1:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', NULL, '', NULL, 88, '2019-05-08 15:30:23');
INSERT INTO `trace_record` VALUES (31, '11ccc621-a1de-476c-8d1b-f8a0de22feb7', NULL, '11ccc621-a1de-476c-8d1b-f8a0de22feb7', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=8AF11B59C603429D5B8A853849EABFBC\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '973C1F67DE481ED29F49A4DECE720661', 151, '2019-05-08 15:34:10');
INSERT INTO `trace_record` VALUES (32, '46b76574-358b-4b05-825d-2129b0232d1a', '11ccc621-a1de-476c-8d1b-f8a0de22feb7', '46b76574-358b-4b05-825d-2129b0232d1a', 'http://localhost:8800/talk', 'GET', 200, 'word=sfsdsw', '{\"code\":\"0\",\"data\":\"you talk:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=973C1F67DE481ED29F49A4DECE720661\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '973C1F67DE481ED29F49A4DECE720661', 93, '2019-05-08 15:34:36');
INSERT INTO `trace_record` VALUES (33, '535d0f9c-3081-4cef-b983-c2fbb5d5114d', '11ccc621-a1de-476c-8d1b-f8a0de22feb7', '535d0f9c-3081-4cef-b983-c2fbb5d5114d', 'http://localhost:8800/talk', 'GET', 200, 'word=sfsdsw', '{\"code\":\"0\",\"data\":\"you talk:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=973C1F67DE481ED29F49A4DECE720661\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '973C1F67DE481ED29F49A4DECE720661', 38, '2019-05-08 15:37:46');
INSERT INTO `trace_record` VALUES (34, '60d35082-d0d6-4ab1-ad85-8e3c77a75b25', NULL, NULL, 'http://127.0.0.1:9000/project/regist', 'POST', 200, '{\"enablelimit\":false,\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://127.0.0.1:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', NULL, '', NULL, 219, '2019-05-08 15:39:17');
INSERT INTO `trace_record` VALUES (35, '24da7571-fbd2-4591-8f18-9560cb13b0f9', NULL, '24da7571-fbd2-4591-8f18-9560cb13b0f9', 'http://localhost:8800/talk', 'GET', 200, 'word=sfsdsw', '{\"code\":\"0\",\"data\":\"you talk:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=973C1F67DE481ED29F49A4DECE720661\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'A3F155FF0D7883ABAE98807187169519', 114, '2019-05-08 15:39:41');
INSERT INTO `trace_record` VALUES (36, '124ca8a1-591c-4c9c-88c0-e6cefe93d1c0', '24da7571-fbd2-4591-8f18-9560cb13b0f9', '124ca8a1-591c-4c9c-88c0-e6cefe93d1c0', 'http://localhost:8800/talk', 'GET', 200, 'word=sfsdsw', '{\"code\":\"0\",\"data\":\"you talk:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=A3F155FF0D7883ABAE98807187169519\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'A3F155FF0D7883ABAE98807187169519', 85, '2019-05-08 15:41:08');
INSERT INTO `trace_record` VALUES (37, '6d2cddad-d265-4730-9c71-6942c7104d20', NULL, NULL, 'http://127.0.0.1:9000/project/regist', 'POST', 200, '{\"enablelimit\":false,\"urls\":[{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.talk\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/talk\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.goPage\",\"params\":[\"java.lang.String\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/page/{name}\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/writer\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.say\",\"params\":[\"java.lang.String\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/say/{word}\"]},{\"types\":[\"POST\"],\"method\":{\"methodName\":\"com.sky.demo.controller.PageController.dosome\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"com.sky.pub.Result\"},\"paths\":[\"/do\"]},{\"types\":[],\"method\":{\"methodName\":\"com.sky.pub.controller.BaseConfigController.getConfigJs\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/config/config.js\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.error\",\"params\":[\"javax.servlet.http.HttpServletRequest\"],\"returnType\":\"org.springframework.http.ResponseEntity\"},\"paths\":[\"/error\"]},{\"types\":[],\"method\":{\"methodName\":\"org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml\",\"params\":[\"javax.servlet.http.HttpServletRequest\",\"javax.servlet.http.HttpServletResponse\"],\"returnType\":\"org.springframework.web.servlet.ModelAndView\"},\"paths\":[\"/error\"]}],\"port\":\"8800\",\"profile\":\"test\",\"readTimeout\":60000,\"location\":\"http://127.0.0.1:9000\",\"serviceName\":\"demo_client\",\"version\":\"1.0.0\"}', NULL, '', NULL, 959, '2019-05-08 16:23:25');
INSERT INTO `trace_record` VALUES (38, '803eee52-6d85-4438-9be3-b2bc6c6e523d', NULL, '803eee52-6d85-4438-9be3-b2bc6c6e523d', 'http://localhost:8800/say/sfsdsw', 'GET', 200, NULL, '{\"code\":\"0\",\"data\":\"you say:sfsdsw\",\"message\":\"OK\",\"success\":true}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"fine_auth_token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYW5ydWFuIiwiaWF0IjoxNTU3MjgxNzkwLCJleHAiOjE1NTcyODUzOTAsInN1YiI6IndhbmdmYW41MTc0IiwiZGVzY3JpcHRpb24iOiJ3YW5nZmFuNTE3NCh3YW5nZmFuNTE3NCkiLCJqdGkiOiJqd3QifQ.SV3BYv4hF97J587tmLArd2DweZLzavpVAPBOdGE17_M; SCM_USER_AUTH=U0000007023; USER_SESSION_ID=B80562442420408AAAC74DC1F0BF5CE5; corpNo=423; corpName=%E6%B1%9F%E8%8B%8F%E5%86%B0%E6%B4%81%E6%97%B6%E5%B0%9A%E6%9C%8D%E9%A5%B0%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E7%94%B5%E5%95%86; JSESSIONID=A3F155FF0D7883ABAE98807187169519\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '1212A2E044CF61D90FE734A1634970CB', 88, '2019-05-08 16:24:23');
INSERT INTO `trace_record` VALUES (39, 'dc8e97f2-3d62-420e-9074-7c75156fcc6f', NULL, 'dc8e97f2-3d62-420e-9074-7c75156fcc6f', 'http://localhost:8810/sql', 'GET', 500, 'table=flow_main', '{\"code\":\"E-0\",\"data\":\"nested exception is org.apache.ibatis.reflection.ReflectionException: There is no getter for property named \'sql\' in \'class java.lang.String\'\",\"message\":\"未知异常\",\"success\":false}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=ADCB73C2D8AC676032E025636B359214\",\"host\":\"localhost:8810\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '1A15A19DE3190D4E84E0C7A68DF263C8', 384, '2019-09-19 13:42:52');
INSERT INTO `trace_record` VALUES (40, '18a87724-ad63-45ac-b7ae-fc0142987ebd', 'dc8e97f2-3d62-420e-9074-7c75156fcc6f', '18a87724-ad63-45ac-b7ae-fc0142987ebd', 'http://localhost:8810/sql', 'GET', 500, 'table=flow_main', '{\"code\":\"E-0\",\"data\":\"nested exception is org.apache.ibatis.reflection.ReflectionException: There is no getter for property named \'sql\' in \'class java.lang.String\'\",\"message\":\"未知异常\",\"success\":false}', '{\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=1A15A19DE3190D4E84E0C7A68DF263C8\",\"host\":\"localhost:8810\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '1A15A19DE3190D4E84E0C7A68DF263C8', 352, '2019-09-19 13:47:02');
INSERT INTO `trace_record` VALUES (41, 'ddcb0574-1107-457d-8fd0-6184291fd42b', NULL, 'ddcb0574-1107-457d-8fd0-6184291fd42b', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=126805A458E9056F20642E483F5DE249\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '72404BB0B45BD19F99FFF04797AE8D7A', 1602, '2019-10-08 14:57:07');
INSERT INTO `trace_record` VALUES (42, '64c2e0ca-d74b-4c26-a9c5-533b524a3636', 'ddcb0574-1107-457d-8fd0-6184291fd42b', '64c2e0ca-d74b-4c26-a9c5-533b524a3636', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=72404BB0B45BD19F99FFF04797AE8D7A\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '72404BB0B45BD19F99FFF04797AE8D7A', 575, '2019-10-08 14:59:12');
INSERT INTO `trace_record` VALUES (43, 'ac5240d7-f620-4e2b-b24a-18f391891d34', NULL, 'ac5240d7-f620-4e2b-b24a-18f391891d34', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=72404BB0B45BD19F99FFF04797AE8D7A\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '020093EB58156D7BD3E631AE962696B0', 2141, '2019-10-08 15:03:36');
INSERT INTO `trace_record` VALUES (44, '827dcdfd-2ea5-416a-ba1e-9f980b26f84a', 'ac5240d7-f620-4e2b-b24a-18f391891d34', '827dcdfd-2ea5-416a-ba1e-9f980b26f84a', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=020093EB58156D7BD3E631AE962696B0\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '020093EB58156D7BD3E631AE962696B0', 1032, '2019-10-08 15:06:17');
INSERT INTO `trace_record` VALUES (45, 'f884e252-ce6b-4622-b0a5-289a4d37ec6e', 'ac5240d7-f620-4e2b-b24a-18f391891d34', 'f884e252-ce6b-4622-b0a5-289a4d37ec6e', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=020093EB58156D7BD3E631AE962696B0\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '020093EB58156D7BD3E631AE962696B0', 2678, '2019-10-08 15:08:59');
INSERT INTO `trace_record` VALUES (46, '78fea2fe-1bb6-4e5b-8363-cee4fcad5886', 'ac5240d7-f620-4e2b-b24a-18f391891d34', '78fea2fe-1bb6-4e5b-8363-cee4fcad5886', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=020093EB58156D7BD3E631AE962696B0\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '020093EB58156D7BD3E631AE962696B0', 795, '2019-10-08 15:17:02');
INSERT INTO `trace_record` VALUES (47, 'b11f234c-0125-42a4-af4b-c222a7cc71ce', 'ac5240d7-f620-4e2b-b24a-18f391891d34', 'b11f234c-0125-42a4-af4b-c222a7cc71ce', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=020093EB58156D7BD3E631AE962696B0\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '020093EB58156D7BD3E631AE962696B0', 605, '2019-10-08 15:27:08');
INSERT INTO `trace_record` VALUES (48, '0856db14-0874-4927-ba64-a579b9c1b6dd', 'ac5240d7-f620-4e2b-b24a-18f391891d34', '0856db14-0874-4927-ba64-a579b9c1b6dd', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=020093EB58156D7BD3E631AE962696B0\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '020093EB58156D7BD3E631AE962696B0', 794, '2019-10-08 15:31:48');
INSERT INTO `trace_record` VALUES (49, '9a30a792-7e6c-456e-a4f2-8aa1d76e2b67', 'ac5240d7-f620-4e2b-b24a-18f391891d34', '9a30a792-7e6c-456e-a4f2-8aa1d76e2b67', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=020093EB58156D7BD3E631AE962696B0\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '020093EB58156D7BD3E631AE962696B0', 480, '2019-10-08 15:36:43');
INSERT INTO `trace_record` VALUES (50, '38537971-fe95-4202-95ac-f3b5b6d89fec', 'ac5240d7-f620-4e2b-b24a-18f391891d34', '38537971-fe95-4202-95ac-f3b5b6d89fec', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=020093EB58156D7BD3E631AE962696B0\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '020093EB58156D7BD3E631AE962696B0', 393, '2019-10-08 15:39:28');
INSERT INTO `trace_record` VALUES (51, '98db7bfc-2942-4d82-b3c7-013458f1e205', 'ac5240d7-f620-4e2b-b24a-18f391891d34', '98db7bfc-2942-4d82-b3c7-013458f1e205', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=020093EB58156D7BD3E631AE962696B0\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '020093EB58156D7BD3E631AE962696B0', 267, '2019-10-08 15:41:30');
INSERT INTO `trace_record` VALUES (52, '7ed64697-bceb-4d58-a0bf-e2755bd09d61', 'ac5240d7-f620-4e2b-b24a-18f391891d34', '7ed64697-bceb-4d58-a0bf-e2755bd09d61', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=020093EB58156D7BD3E631AE962696B0\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', '020093EB58156D7BD3E631AE962696B0', 193, '2019-10-08 15:43:55');
INSERT INTO `trace_record` VALUES (53, 'afc75aea-563a-4c39-8397-92b86c61fd5a', NULL, 'afc75aea-563a-4c39-8397-92b86c61fd5a', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=020093EB58156D7BD3E631AE962696B0\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B0262D05DDC44420CC36F24B82735C72', 2363, '2019-10-08 17:03:10');
INSERT INTO `trace_record` VALUES (54, '91ad265d-422b-41dd-adc1-d0bd330be405', 'afc75aea-563a-4c39-8397-92b86c61fd5a', '91ad265d-422b-41dd-adc1-d0bd330be405', 'http://localhost:8800/sql', 'GET', 200, 'table=menu', '{\"code\":\"0\",\"data\":[{\"code\":\"sys_set\",\"name\":\"设置\",\"pid\":0,\"id\":1,\"version\":0,\"url\":\"/config/page/setting\",\"ts\":1546066227000},{\"code\":\"sys_config\",\"name\":\"配置\",\"pid\":0,\"id\":2,\"version\":0,\"url\":\"#\",\"ts\":1545561431000},{\"code\":\"sys_config_table\",\"createId\":-1,\"name\":\"数据库配置\",\"pid\":2,\"id\":37,\"version\":2,\"url\":\"/config/page/table/index\",\"ts\":1546049339000},{\"code\":\"sys_sql_w\",\"createId\":-1,\"name\":\"sql书写\",\"pid\":2,\"id\":40,\"version\":1,\"url\":\"/config/page/sql/index\",\"ts\":1546849591000},{\"code\":\"sys_crwaler_code\",\"createId\":-1,\"name\":\"爬虫设置\",\"pid\":2,\"id\":41,\"version\":1,\"url\":\"/config/page/crawler/index\",\"ts\":1547775772000}],\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=B0262D05DDC44420CC36F24B82735C72\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B0262D05DDC44420CC36F24B82735C72', 654, '2019-10-08 17:03:52');
INSERT INTO `trace_record` VALUES (55, '980be982-50e9-472e-a88e-d62d0fe9c254', '1c4e1fd2-cf39-4251-bfe2-3f016919adc0', '980be982-50e9-472e-a88e-d62d0fe9c254', 'http://localhost:8800/talk', 'GET', 200, 'test=sds', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=E8C8981C2449FA0582B5539F92A9F037\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'E8C8981C2449FA0582B5539F92A9F037', 15, '2019-12-11 13:36:36');
INSERT INTO `trace_record` VALUES (56, 'a8cc4746-0f97-4161-8edb-7b33add0d1c3', '1c4e1fd2-cf39-4251-bfe2-3f016919adc0', 'a8cc4746-0f97-4161-8edb-7b33add0d1c3', 'http://localhost:8800/talk', 'GET', 200, 'test=sds&locale=en_us', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=E8C8981C2449FA0582B5539F92A9F037\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'E8C8981C2449FA0582B5539F92A9F037', 2392, '2019-12-11 13:39:24');
INSERT INTO `trace_record` VALUES (57, '860a2a20-9c34-498c-9449-afe9dbac78d3', '1c4e1fd2-cf39-4251-bfe2-3f016919adc0', '860a2a20-9c34-498c-9449-afe9dbac78d3', 'http://localhost:8800/talk', 'GET', 200, 'test=sds&locale=en_us', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=E8C8981C2449FA0582B5539F92A9F037\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'E8C8981C2449FA0582B5539F92A9F037', 15, '2019-12-11 13:39:59');
INSERT INTO `trace_record` VALUES (58, 'd3265b98-eeb6-4daa-a08e-51a729d9367f', '1c4e1fd2-cf39-4251-bfe2-3f016919adc0', 'd3265b98-eeb6-4daa-a08e-51a729d9367f', 'http://localhost:8800/talk', 'GET', 200, 'test=sds&locale=en_us', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=E8C8981C2449FA0582B5539F92A9F037\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'E8C8981C2449FA0582B5539F92A9F037', 27, '2019-12-11 13:40:35');
INSERT INTO `trace_record` VALUES (59, 'acb0003e-d6e3-4741-ac7b-496d5253ffda', '1c4e1fd2-cf39-4251-bfe2-3f016919adc0', 'acb0003e-d6e3-4741-ac7b-496d5253ffda', 'http://localhost:8800/talk', 'GET', 200, 'test=sds&locale=en_us', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=E8C8981C2449FA0582B5539F92A9F037\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'E8C8981C2449FA0582B5539F92A9F037', 15, '2019-12-11 13:41:19');
INSERT INTO `trace_record` VALUES (60, 'c122abfe-d207-4b78-9ca2-5a15bd73720a', NULL, 'c122abfe-d207-4b78-9ca2-5a15bd73720a', 'http://localhost:8800/talk', 'GET', 200, 'test=sds&locale=en_us', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=E8C8981C2449FA0582B5539F92A9F037\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 53, '2019-12-11 13:50:50');
INSERT INTO `trace_record` VALUES (61, '7becabd6-907a-40c1-a46e-5cad5aa38caa', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', '7becabd6-907a-40c1-a46e-5cad5aa38caa', 'http://localhost:8800/talk', 'GET', 200, 'test=sds&locale=en_us', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 13, '2019-12-11 13:51:46');
INSERT INTO `trace_record` VALUES (62, '5499710e-752e-4b30-8fd2-31b8e65a9ced', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', '5499710e-752e-4b30-8fd2-31b8e65a9ced', 'http://localhost:8800/talk', 'GET', 200, 'test=sds&locale=en_us', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 19, '2019-12-11 13:52:21');
INSERT INTO `trace_record` VALUES (63, '2637a25a-f17a-4487-abd7-2a6f93a822a9', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', '2637a25a-f17a-4487-abd7-2a6f93a822a9', 'http://localhost:8800/talk', 'GET', 200, 'test=sds&locale=en_us', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 20, '2019-12-11 13:53:57');
INSERT INTO `trace_record` VALUES (64, 'f8667bfb-48ed-40d7-9d7b-70f9c8b61eb3', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', 'f8667bfb-48ed-40d7-9d7b-70f9c8b61eb3', 'http://localhost:8800/talk', 'GET', 200, 'test=sds&locale=en_us', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 30, '2019-12-11 13:54:09');
INSERT INTO `trace_record` VALUES (65, '77f741c2-16ed-454e-9bf2-8d6d983ee4a9', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', '77f741c2-16ed-454e-9bf2-8d6d983ee4a9', 'http://localhost:8800/talk', 'GET', 200, 'test=sds&locale=en_us', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 3, '2019-12-11 14:03:42');
INSERT INTO `trace_record` VALUES (66, 'c50e4fc0-6869-4711-833d-e40701b158ca', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', 'c50e4fc0-6869-4711-833d-e40701b158ca', 'http://localhost:8800/talk', 'GET', 200, 'test=sds&locale=en_us', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 17, '2019-12-11 14:08:43');
INSERT INTO `trace_record` VALUES (67, '4333b3af-ceea-42ba-b474-4ea8eab2bdce', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', '4333b3af-ceea-42ba-b474-4ea8eab2bdce', 'http://localhost:8800/talk', 'GET', 200, 'test=sds&locale=en_us', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 169, '2019-12-11 14:17:59');
INSERT INTO `trace_record` VALUES (68, '73b36fcc-8044-42ee-b647-4a731e9ddc9c', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', '73b36fcc-8044-42ee-b647-4a731e9ddc9c', 'http://localhost:8800/talk', 'GET', 200, 'test=sds', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 34, '2019-12-11 14:18:23');
INSERT INTO `trace_record` VALUES (69, 'c905a940-4edc-4939-9096-10b0e3ba580b', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', 'c905a940-4edc-4939-9096-10b0e3ba580b', 'http://localhost:8800/talk', 'GET', 200, 'test=sds', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 33, '2019-12-11 14:19:50');
INSERT INTO `trace_record` VALUES (70, '077dfe2e-73b5-4d15-a1f7-87d3ae7fe7e8', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', '077dfe2e-73b5-4d15-a1f7-87d3ae7fe7e8', 'http://localhost:8800/talk', 'GET', 200, 'test=sds', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 35, '2019-12-11 14:20:00');
INSERT INTO `trace_record` VALUES (71, '386c6c73-bd79-4415-bfb5-ce2825c8e5a6', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', '386c6c73-bd79-4415-bfb5-ce2825c8e5a6', 'http://localhost:8800/talk', 'GET', 200, 'test=sds', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 12, '2019-12-11 14:21:21');
INSERT INTO `trace_record` VALUES (72, 'bfcd350e-660a-4c04-930f-672e790e14b2', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', 'bfcd350e-660a-4c04-930f-672e790e14b2', 'http://localhost:8800/talk', 'GET', 200, 'test=sds', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 32, '2019-12-11 14:24:47');
INSERT INTO `trace_record` VALUES (73, '67acee91-df0e-47a9-b1a9-083738fab480', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', '67acee91-df0e-47a9-b1a9-083738fab480', 'http://localhost:8800/talk', 'GET', 200, 'test=sds', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 190, '2019-12-11 14:26:16');
INSERT INTO `trace_record` VALUES (74, '6328cf50-be1a-4c85-8d32-d0655e19bf75', 'c122abfe-d207-4b78-9ca2-5a15bd73720a', '6328cf50-be1a-4c85-8d32-d0655e19bf75', 'http://localhost:8800/talk', 'GET', 200, 'test=sds', '{\"code\":\"0\",\"data\":\"you talk:null\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9,is;q=0.8,hmn;q=0.7,en;q=0.6\",\"cookie\":\"JSESSIONID=B499B133E2DC4CB114BE4FF08B31BD09\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"cache-control\":\"max-age=0\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}', 'B499B133E2DC4CB114BE4FF08B31BD09', 21, '2019-12-11 14:35:19');
INSERT INTO `trace_record` VALUES (75, '0bd7f626-d9ce-49b7-ae05-070a9767d5a7', NULL, '0bd7f626-d9ce-49b7-ae05-070a9767d5a7', 'http://localhost:8800/talk', 'GET', 200, 'test=sds', '{\"code\":\"0\",\"data\":\"you talk:null with:9000总比\",\"message\":\"OK\",\"success\":true}', '{\"sec-fetch-mode\":\"navigate\",\"sec-fetch-site\":\"none\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"cookie\":\"JSESSIONID=C3892F1F684D1C74F505142717051CFA\",\"host\":\"localhost:8800\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"sec-fetch-user\":\"?1\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\"}', '033AA1020A85F9401F23BD403BB285DA', 44, '2020-01-03 15:14:49');

-- ----------------------------
-- Table structure for user_base
-- ----------------------------
DROP TABLE IF EXISTS `user_base`;
CREATE TABLE `user_base`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '编码',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `identity_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号',
  `country` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '国籍',
  `mail` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '电话号',
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `age` int(0) NULL DEFAULT NULL,
  `version` int(0) NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `use_code`(`code`) USING BTREE,
  UNIQUE INDEX `id_code`(`identity_code`) USING BTREE,
  UNIQUE INDEX `phone_unp`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_base
-- ----------------------------
INSERT INTO `user_base` VALUES (6, 'user_com_-498702880', 'admin', '$2a$10$I1WgTLMWbaVmanuZTA1ApOqkPSHBNwPGD/zL9ZD/ai.YFYEO5dSsu', NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-01-28 10:20:58');
INSERT INTO `user_base` VALUES (12, 'user_com_1942818232', 'test', '$2a$10$534NzPLArXw5aiO6j9yZnujTSGNp5va3mwXNoN7T9OmXFhz.9jtPa', NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-03-23 22:10:45');
INSERT INTO `user_base` VALUES (14, 'user_com_-858606152', 'tom', '$2a$10$P8CxSp8NfLExSOe1qhU4tenpEndHoiTVEBpURPk/KvuPCB4Hu0MB2', '12324454', 'usa', 'sdfsw', '23242', NULL, 232, 0, '2019-05-07 14:23:10');

SET FOREIGN_KEY_CHECKS = 1;
