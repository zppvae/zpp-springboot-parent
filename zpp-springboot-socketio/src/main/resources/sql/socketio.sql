/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50714
 Source Host           : localhost:3306
 Source Schema         : socketio

 Target Server Type    : MySQL
 Target Server Version : 50714
 File Encoding         : 65001

 Date: 17/08/2018 10:16:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_chat_message
-- ----------------------------
DROP TABLE IF EXISTS `t_chat_message`;
CREATE TABLE `t_chat_message`  (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT,
  `send_user_id` int(11) NULL DEFAULT NULL COMMENT '发送者',
  `receive_user_id` int(11) NULL DEFAULT NULL COMMENT '接收者',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  `status` int(1) NULL DEFAULT 0 COMMENT '状态  0：未发送  1：已发送',
  PRIMARY KEY (`msg_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 144 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_chat_message
-- ----------------------------
INSERT INTO `t_chat_message` VALUES (98, 3, 1, 'qwqw', '2018-08-16 15:55:46', 1);
INSERT INTO `t_chat_message` VALUES (99, 3, 1, 'qqq', '2018-08-16 15:55:52', 1);
INSERT INTO `t_chat_message` VALUES (100, 3, 1, '李雪丹', '2018-08-16 15:55:55', 1);
INSERT INTO `t_chat_message` VALUES (101, 2, 1, 'dsajdas', '2018-08-16 15:56:30', 1);
INSERT INTO `t_chat_message` VALUES (102, 2, 1, '大bug', '2018-08-16 15:56:39', 1);
INSERT INTO `t_chat_message` VALUES (103, 2, 1, '大bug 了吧', '2018-08-16 15:56:50', 1);
INSERT INTO `t_chat_message` VALUES (104, 7, 2, 'hello', '2018-08-16 15:57:26', 0);
INSERT INTO `t_chat_message` VALUES (105, 2, 7, 'hehehhe', '2018-08-16 15:57:36', 0);
INSERT INTO `t_chat_message` VALUES (106, 2, 7, '是打算的', '2018-08-16 15:57:41', 0);
INSERT INTO `t_chat_message` VALUES (107, 7, 2, 'sadas', '2018-08-16 15:57:44', 0);
INSERT INTO `t_chat_message` VALUES (108, 7, 2, 'asdasd ', '2018-08-16 15:57:46', 0);
INSERT INTO `t_chat_message` VALUES (109, 2, 4, '但大师', '2018-08-16 15:58:06', 0);
INSERT INTO `t_chat_message` VALUES (110, 2, 4, 'dasldn', '2018-08-16 15:58:07', 0);
INSERT INTO `t_chat_message` VALUES (111, 2, 4, 'asdnaslkd', '2018-08-16 15:58:08', 0);
INSERT INTO `t_chat_message` VALUES (112, 2, 4, 'sadasnldk', '2018-08-16 15:58:08', 0);
INSERT INTO `t_chat_message` VALUES (113, 2, 4, '阿斯科利到那时离开；d', '2018-08-16 15:58:09', 0);
INSERT INTO `t_chat_message` VALUES (114, 2, 4, 'asldknas', '2018-08-16 15:58:10', 0);
INSERT INTO `t_chat_message` VALUES (115, 2, 4, '打扫', '2018-08-16 15:58:18', 0);
INSERT INTO `t_chat_message` VALUES (116, 2, 4, 'das\'asdas', '2018-08-16 15:58:25', 0);
INSERT INTO `t_chat_message` VALUES (117, 2, 4, 'asda', '2018-08-16 15:58:26', 0);
INSERT INTO `t_chat_message` VALUES (118, 2, 4, 'dasd', '2018-08-16 15:58:26', 0);
INSERT INTO `t_chat_message` VALUES (119, 2, 4, 'asdas', '2018-08-16 15:58:27', 0);
INSERT INTO `t_chat_message` VALUES (120, 2, 4, 'dasd', '2018-08-16 15:58:27', 0);
INSERT INTO `t_chat_message` VALUES (121, 2, 4, 'asd', '2018-08-16 15:58:28', 0);
INSERT INTO `t_chat_message` VALUES (122, 2, 4, 'asdas', '2018-08-16 15:58:28', 0);
INSERT INTO `t_chat_message` VALUES (123, 2, 4, 'd', '2018-08-16 15:58:28', 0);
INSERT INTO `t_chat_message` VALUES (124, 2, 4, 'asdad', '2018-08-16 15:58:30', 0);
INSERT INTO `t_chat_message` VALUES (125, 2, 3, 'das', '2018-08-16 16:00:23', 1);
INSERT INTO `t_chat_message` VALUES (126, 2, 3, 'dasd', '2018-08-16 16:00:25', 1);
INSERT INTO `t_chat_message` VALUES (127, 2, 1, 'asda', '2018-08-16 16:00:42', 1);
INSERT INTO `t_chat_message` VALUES (128, 2, 1, 'asdas', '2018-08-16 16:00:45', 1);
INSERT INTO `t_chat_message` VALUES (129, 2, 3, 'dasd', '2018-08-16 16:00:51', 1);
INSERT INTO `t_chat_message` VALUES (130, 7, 2, 'dasd', '2018-08-16 16:01:29', 0);
INSERT INTO `t_chat_message` VALUES (131, 2, 7, 'dasd', '2018-08-16 16:02:13', 0);
INSERT INTO `t_chat_message` VALUES (132, 2, 4, 'dasdas', '2018-08-16 16:20:14', 0);
INSERT INTO `t_chat_message` VALUES (133, 2, 4, 'sdaksjdkjas', '2018-08-16 16:24:50', 0);
INSERT INTO `t_chat_message` VALUES (134, 2, 4, 'hhh', '2018-08-16 17:22:32', 0);
INSERT INTO `t_chat_message` VALUES (135, 2, 4, 'dasjdbasdn', '2018-08-16 17:23:25', 0);
INSERT INTO `t_chat_message` VALUES (136, 2, 4, 'ahjgdasd', '2018-08-16 17:23:28', 0);
INSERT INTO `t_chat_message` VALUES (137, 2, 4, 'jjj', '2018-08-16 17:26:10', 0);
INSERT INTO `t_chat_message` VALUES (138, 1, 3, '7877', '2018-08-16 18:44:45', 1);
INSERT INTO `t_chat_message` VALUES (139, 1, 3, '33', '2018-08-16 18:50:48', 1);
INSERT INTO `t_chat_message` VALUES (140, 1, 3, '7888', '2018-08-16 18:51:35', 1);
INSERT INTO `t_chat_message` VALUES (141, 4, 2, '啊手动阀手动阀', '2018-08-17 10:06:20', 0);
INSERT INTO `t_chat_message` VALUES (142, 4, 2, '啊快点恢复卡的浪费', '2018-08-17 10:11:47', 0);
INSERT INTO `t_chat_message` VALUES (143, 2, 4, 'hhh', '2018-08-17 10:12:02', 0);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'zpp', NULL, NULL);
INSERT INTO `t_user` VALUES (2, 'lxd', NULL, NULL);
INSERT INTO `t_user` VALUES (3, 'zpp00', NULL, NULL);
INSERT INTO `t_user` VALUES (4, 'dy', NULL, NULL);
INSERT INTO `t_user` VALUES (5, 'dy00', NULL, NULL);
INSERT INTO `t_user` VALUES (6, 'dy01', NULL, NULL);
INSERT INTO `t_user` VALUES (7, 'lxd00', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
