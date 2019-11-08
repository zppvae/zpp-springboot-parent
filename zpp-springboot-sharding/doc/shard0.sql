/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50714
 Source Host           : localhost:3306
 Source Schema         : shard0

 Target Server Type    : MySQL
 Target Server Version : 50714
 File Encoding         : 65001

 Date: 08/11/2019 18:18:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'user1', 10);
INSERT INTO `user` VALUES (2, 'user1', 10);
INSERT INTO `user` VALUES (3, 'user1', 10);
INSERT INTO `user` VALUES (4, 'user1', 10);
INSERT INTO `user` VALUES (5, 'user1', 10);
INSERT INTO `user` VALUES (6, 'user1', 10);
INSERT INTO `user` VALUES (7, 'user1', 10);
INSERT INTO `user` VALUES (8, 'user1', 10);
INSERT INTO `user` VALUES (9, 'user1', 10);
INSERT INTO `user` VALUES (10, 'user1', 10);
INSERT INTO `user` VALUES (11, '112user121', 12);
INSERT INTO `user` VALUES (12, '112user121', 232);
INSERT INTO `user` VALUES (13, '测试写数据', 13);
INSERT INTO `user` VALUES (14, '测试2', 11);

-- ----------------------------
-- Table structure for user_0
-- ----------------------------
DROP TABLE IF EXISTS `user_0`;
CREATE TABLE `user_0`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_0
-- ----------------------------
INSERT INTO `user_0` VALUES (1, 'user1', 10);
INSERT INTO `user_0` VALUES (2, 'user1', 10);
INSERT INTO `user_0` VALUES (3, 'user1', 10);
INSERT INTO `user_0` VALUES (4, 'user1', 10);
INSERT INTO `user_0` VALUES (5, 'user1', 10);
INSERT INTO `user_0` VALUES (6, 'user1', 10);
INSERT INTO `user_0` VALUES (7, 'user1', 10);
INSERT INTO `user_0` VALUES (8, 'user1', 10);
INSERT INTO `user_0` VALUES (9, 'user1', 10);
INSERT INTO `user_0` VALUES (10, 'user1', 10);
INSERT INTO `user_0` VALUES (11, '112user121', 12);
INSERT INTO `user_0` VALUES (12, '112user121', 232);

-- ----------------------------
-- Table structure for user_1
-- ----------------------------
DROP TABLE IF EXISTS `user_1`;
CREATE TABLE `user_1`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_1
-- ----------------------------
INSERT INTO `user_1` VALUES (1, '112user121', 89);
INSERT INTO `user_1` VALUES (2, '112user121', 87);

SET FOREIGN_KEY_CHECKS = 1;
