/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : pinellia-oa

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 09/06/2023 17:36:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oa_calendar
-- ----------------------------
DROP TABLE IF EXISTS `oa_calendar`;
CREATE TABLE `oa_calendar`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `begin_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oa_calendar
-- ----------------------------
INSERT INTO `oa_calendar` VALUES (1, '公司成立', '2023-02-04 10:20:40', '2023-02-05 09:21:10', '公司成立', '2023-02-04 13:21:31', NULL);
INSERT INTO `oa_calendar` VALUES (2, '产品发布', '2023-04-18 09:00:00', '2023-05-31 15:34:00', '产品上线', '2023-04-20 13:23:05', '2023-04-21 15:38:38');
INSERT INTO `oa_calendar` VALUES (3, '未来规划', '2023-06-30 15:34:10', '2023-07-01 15:34:08', '公司规划', '2023-04-20 13:23:50', '2023-04-21 15:38:57');
INSERT INTO `oa_calendar` VALUES (7, '测试', '2023-05-15 00:00:00', '2023-05-30 00:00:00', NULL, '2023-05-01 17:04:25', '2023-05-01 17:04:45');
INSERT INTO `oa_calendar` VALUES (8, '测试2', '2023-06-01 00:00:00', '2023-06-11 00:00:00', NULL, '2023-05-01 17:07:37', NULL);

-- ----------------------------
-- Table structure for oa_cc
-- ----------------------------
DROP TABLE IF EXISTS `oa_cc`;
CREATE TABLE `oa_cc`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `clock_id` bigint NOT NULL COMMENT '考勤记录表id',
  `notes_id` bigint NOT NULL COMMENT '考勤统计表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_cc
-- ----------------------------
INSERT INTO `oa_cc` VALUES (25, 2, 40);
INSERT INTO `oa_cc` VALUES (27, 4, 42);
INSERT INTO `oa_cc` VALUES (28, 1, 43);

-- ----------------------------
-- Table structure for oa_clock
-- ----------------------------
DROP TABLE IF EXISTS `oa_clock`;
CREATE TABLE `oa_clock`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `begin_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `count` int NOT NULL DEFAULT 0 COMMENT '当前的签到人数',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '(0 未开始 1进行中 2已结束 )',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_clock
-- ----------------------------
INSERT INTO `oa_clock` VALUES (1, '8:00 点签到', '2023-06-04 10:19:47', '2023-06-04 10:19:55', 1, NULL, NULL, '2');
INSERT INTO `oa_clock` VALUES (2, '下班签到', '2023-05-08 17:13:13', '2023-05-08 17:13:18', 1, '2023-04-20 19:21:40', NULL, '2');
INSERT INTO `oa_clock` VALUES (4, '测试1', '2023-05-17 15:48:37', '2023-05-17 15:49:04', 1, '2023-04-20 19:25:46', NULL, '2');
INSERT INTO `oa_clock` VALUES (5, '测试2', '2023-05-02 15:44:38', '2023-05-02 15:44:40', 0, '2023-04-20 20:33:16', NULL, '2');

-- ----------------------------
-- Table structure for oa_clock_notes
-- ----------------------------
DROP TABLE IF EXISTS `oa_clock_notes`;
CREATE TABLE `oa_clock_notes`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '考勤统计表id',
  `user_id` bigint NOT NULL COMMENT '员工id',
  `is_punch` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '(0 未打卡 ， 1 已打卡)',
  `punch_time` datetime NULL DEFAULT NULL COMMENT '打卡时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_clock_notes
-- ----------------------------
INSERT INTO `oa_clock_notes` VALUES (40, 100001, '1', '2023-05-08 17:13:15');
INSERT INTO `oa_clock_notes` VALUES (42, 100001, '1', '2023-05-17 15:48:42');
INSERT INTO `oa_clock_notes` VALUES (43, 100001, '1', '2023-06-04 10:19:50');

-- ----------------------------
-- Table structure for oa_company
-- ----------------------------
DROP TABLE IF EXISTS `oa_company`;
CREATE TABLE `oa_company`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Pinellia' COMMENT '名称',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发布人',
  `time` date NULL DEFAULT NULL COMMENT '日期',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_company
-- ----------------------------
INSERT INTO `oa_company` VALUES (2, '员工守则', NULL, '管理员', '2023-04-29', NULL, '1、一周工作5天；\n2、每天工作6小时；\n3、缺勤1次扣200元；\n4、迟到扣50元；\n5、不允许早退；\n6、不允许上班摸鱼，发现一次扣50；\n7、每月满勤有满勤奖；\n8、每年年末有年终奖；\n9、不许顶撞上司。');
INSERT INTO `oa_company` VALUES (3, '公司简介', '璃月', '超级管理员', '2023-05-20', NULL, '我已经远离了你的河流，绝不是疏远，因为我已是身居异土。远远地，依然听得到你翻山越岭、日夜兼程的脚步声，依然无比清晰地看得到你的那条洒满星星的清澈的河流。只要，也只有想起你——故乡，就是异域的那条河流也会洒满故乡的星星。无论醒着还是沉睡，都会重复着那个让人心旌摇曳的画面，星星都在你的河流里百媚地眨眼。因为那条洒满星星的河流途经我无猜的孩童，无知的少年，迷茫的青春，还要经过可知的未来，是不是今后还要交付给大海了呢？那样，大海的那些星星一定是故乡送给他们的馈赠，没有故乡的孕育，大海也会失去灵气，因为大海的源头就在故乡的这头……\n星星的感情，在城市里总是被耽误。是星星的感情单一，还是城市的滥情呢？如果城市是舞台，星星就不是市民，所以也不是演员。如果天空是舞台，星星就是居民，所以能成为明星。如果河流是舞台，星星就是精灵，她就是大地的灵魂。如今，爱情在城市里就是一个错误，比金钱的质量轻百倍，所以比海洛因更危险。爱情在乡村的河流里，就有了星星的光辉，成就的是海枯石烂的传奇……\n晴空万里的白昼，总爱涂脂抹粉打扮一番。这样的时候，只有清泉敢从浓妆艳抹的云端踏过，只有披着满身星星的鱼儿敢从蓝天穿过，将自满表现的太阳戏弄一番。浆洗的女人们总爱把笑声抛在河面上，拍打着棒槌当爵士乐的节拍，委婉的歌声随着幸福的河水流淌，斑斓的衣服在白云间飘舞成了彩虹。我听到了大地与天空的窃语，彻底悟出了萨顶顶唱的《万物生》里的深刻与凝重：我看见山鹰在寂寞两条鱼上飞 ，两条鱼儿穿过海一样咸的河水， 一片河水落下来遇见人们破碎 ，人们在行走，身上落满山鹰的灰……\n睡得沉沉的夜，呵欠连连，漫长得没有尽头，无论夜几多的黑，可故乡的河流从也不会迷路，因为有星星这明亮的眼睛。你的人生没有迷路，你的梦没有迷失方向，你的未来总在奔跑的路上，是不是也是借了故乡河流里的星星做慧眼呢？如果河流看不到星星，那肯定会落魄的。如果人生看不到那条飘着星星的河流，如果梦里没有那条飘着星星的河流，如果未来的路上没有那条飘着星星的河流，你是不是都会惊慌失措呢？陌生人能通过那条河流找到你的家，如果没有了她你的心能到家吗？\n故乡那条洒满星星的河流，已经成了一幕刻骨铭心的风景了，我知道能刻骨铭心的是最致命的东西，我是一个能被往事打动的人，我还敢不把她珍藏在比记忆还要深刻的心头吗？如果哪天放手丢失了，我的儿子、孙子会替我捡起永远珍藏下去的。\n我翻阅过世界地图，也翻阅过中国地图，让我大失所望的是没能找到故乡那条洒满星星的河流。他只是奔流着的一根毛细血管，往往被大意的世人疏忽。打开谷歌地图，还是难以描绘出她窈窕的身姿。看到两旁葱葱郁郁的树，飘飘舞舞的路，歪歪斜斜的村落……才恍然大悟这是滋生生命的源头！\n那条河流，滋生过五谷杂粮，抚慰过萝卜白菜，滋养过冒冒失失的人们……我是数着河里的星星，翻开人生的第一页，演算过老师递过来的复杂的难题，懂得了几个因为，见识了几个所以。再后来那条河流一直陪着我，从故乡走到异乡……\n心中有了那条洒满星星的河流，心灵就不会陷入绝境。让我把她和与她的过往都珍藏起来了，交付给未来吧。');

-- ----------------------------
-- Table structure for oa_department
-- ----------------------------
DROP TABLE IF EXISTS `oa_department`;
CREATE TABLE `oa_department`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `department_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门名称',
  `income` bigint NULL DEFAULT 0 COMMENT '部门收入',
  `invest` bigint NULL DEFAULT 0 COMMENT '资金投入',
  `people_num` int NULL DEFAULT 0 COMMENT '员工数',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 505 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_department
-- ----------------------------
INSERT INTO `oa_department` VALUES (100, '管理层', 0, 0, 1, '管理层', '2023-03-18 18:01:37', '2023-06-04 10:18:18');
INSERT INTO `oa_department` VALUES (201, '研发部', 0, 0, 0, '研发部', '2023-03-02 15:58:22', '2023-04-18 20:42:08');
INSERT INTO `oa_department` VALUES (202, '运营部', 0, 0, 1, '运营部', '2023-03-02 15:58:29', '2023-06-04 10:18:33');
INSERT INTO `oa_department` VALUES (203, '产品部', 0, 0, 0, '产品部', '2023-03-02 15:58:35', '2023-04-18 20:42:17');
INSERT INTO `oa_department` VALUES (204, '市场部', 0, 0, 0, '市场部', '2023-03-02 15:58:39', '2023-04-26 14:42:38');
INSERT INTO `oa_department` VALUES (205, '综合部', 0, 0, 0, '综合部', '2023-03-01 15:58:42', '2023-04-18 20:42:26');
INSERT INTO `oa_department` VALUES (400, '未分配', 0, 0, 3, '未分配', '2023-04-18 21:26:29', '2023-06-04 10:18:33');
INSERT INTO `oa_department` VALUES (500, '试用期', 0, 0, 0, '试用期', '2023-03-18 16:37:00', '2023-04-18 20:42:35');
INSERT INTO `oa_department` VALUES (505, '测试', 10, 12, 0, NULL, '2023-04-24 13:12:25', '2023-04-26 14:42:52');

-- ----------------------------
-- Table structure for oa_job
-- ----------------------------
DROP TABLE IF EXISTS `oa_job`;
CREATE TABLE `oa_job`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位id',
  `job_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `count` int NULL DEFAULT 0 COMMENT '该岗位的人数',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 302 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_job
-- ----------------------------
INSERT INTO `oa_job` VALUES (1, 'CEO', NULL, '2023-04-26 17:33:24', 1, NULL);
INSERT INTO `oa_job` VALUES (2, '测试工程师', '2023-03-02 16:06:34', '2023-04-26 17:33:34', 1, NULL);
INSERT INTO `oa_job` VALUES (3, '运维工程师', '2023-03-02 16:06:48', '2023-04-26 17:35:06', 0, NULL);
INSERT INTO `oa_job` VALUES (4, '新媒体', '2023-03-02 16:06:59', '2023-04-26 17:33:45', 0, NULL);
INSERT INTO `oa_job` VALUES (5, '活动策划', '2023-03-02 16:07:23', '2023-03-23 21:35:08', 0, NULL);
INSERT INTO `oa_job` VALUES (6, '内容运营', NULL, '2023-03-23 21:35:08', 0, NULL);
INSERT INTO `oa_job` VALUES (7, '产品运营', NULL, '2023-03-23 21:35:08', 0, NULL);
INSERT INTO `oa_job` VALUES (8, 'UI设计师', NULL, '2023-03-23 21:35:08', 0, NULL);
INSERT INTO `oa_job` VALUES (9, '视觉设计师', NULL, '2023-03-23 21:35:08', 0, NULL);
INSERT INTO `oa_job` VALUES (10, '产品经理', NULL, '2023-03-23 21:35:08', 0, NULL);
INSERT INTO `oa_job` VALUES (11, '商务', NULL, '2023-03-23 21:35:08', 0, NULL);
INSERT INTO `oa_job` VALUES (12, '推广', NULL, '2023-03-23 21:35:08', 0, NULL);
INSERT INTO `oa_job` VALUES (13, '渠道', NULL, '2023-03-23 21:35:08', 0, NULL);
INSERT INTO `oa_job` VALUES (14, '人事部', NULL, '2023-03-23 21:35:08', 0, NULL);
INSERT INTO `oa_job` VALUES (15, '行政部', NULL, '2023-03-23 21:35:08', 0, NULL);
INSERT INTO `oa_job` VALUES (16, '财务部', NULL, '2023-04-26 17:42:58', 0, NULL);
INSERT INTO `oa_job` VALUES (17, '后端工程师', NULL, '2023-03-23 21:35:08', 0, NULL);
INSERT INTO `oa_job` VALUES (18, '前端工程师', '2023-03-02 16:05:40', '2023-03-23 21:35:08', 0, NULL);
INSERT INTO `oa_job` VALUES (200, '试用岗', NULL, '2023-04-26 17:11:47', 0, '');
INSERT INTO `oa_job` VALUES (300, '未分配', NULL, '2023-05-02 15:14:50', 3, '未分配工作');

-- ----------------------------
-- Table structure for oa_menu
-- ----------------------------
DROP TABLE IF EXISTS `oa_menu`;
CREATE TABLE `oa_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单id',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT ' ' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单类型(M 目录,C 菜单,F 按钮)',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 147 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_menu
-- ----------------------------
INSERT INTO `oa_menu` VALUES (1, '系统管理', 'Tools', 0, 1, '/sys', '', 'M', '', NULL, '2023-04-24 12:35:30', NULL);
INSERT INTO `oa_menu` VALUES (2, '业务管理', '#', 0, 2, '/busi', NULL, 'M', NULL, NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (3, '高级管理', '#', 0, 3, '/business', '', 'M', '', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (4, '用户管理', '#', 1, 1, '/sys/user', 'sys/user/index', 'C', 'user:list', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (5, '角色管理', '#', 1, 2, '/sys/role', 'sys/role/index', 'C', 'role:list', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (6, '公告管理', '#', 1, 4, '/sys/notice', 'sys/notice/index', 'C', 'notice:list', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (7, '菜单管理', '#', 1, 3, '/sys/menu', 'sys/menu/index', 'C', 'menu:list', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (8, '部门管理', '#', 2, 1, '/busi/department', 'busi/department/index', 'C', 'deparment:list', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (9, '岗位管理', '#', 2, 2, '/busi/job', 'busi/job/index', 'C', 'job:list', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (10, '日程管理', '#', 2, 3, '/busi/calendar', 'busi/calendar/index', 'C', 'calendar:list', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (11, '考勤管理', '#', 2, 4, '/busi/clock', 'busi/clock/index', 'C', 'clock:list', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (12, '资源管理', '#', 3, 1, '/business/resources', 'business/resources/index', 'C', 'resources:list', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (13, '回收管理', '#', 3, 2, '/business/recover', 'business/recover/index', 'C', 'recover:list', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (14, '用户新增', '#', 4, 1, '', NULL, 'F', 'user:add', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (15, '用户修改', '#', 4, 2, '', NULL, 'F', 'user:update', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (16, '用户删除', '#', 4, 3, ' ', NULL, 'F', 'user:delete', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (17, '分配角色', '#', 4, 4, ' ', NULL, 'F', 'user:part', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (18, '重置密码', '#', 4, 5, ' ', NULL, 'F', 'user:reset', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (19, '角色新增', '#', 5, 1, ' ', NULL, 'F', 'role:add', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (20, '角色修改', '#', 5, 2, ' ', NULL, 'F', 'role:update', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (21, '角色删除', '#', 5, 3, ' ', NULL, 'F', 'role:delete', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (22, '分配权限', '#', 5, 4, ' ', NULL, 'F', 'role:part', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (23, '公告添加', '#', 6, 1, ' ', NULL, 'F', 'notice:add', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (24, '公告删除', '#', 6, 2, ' ', NULL, 'F', 'notice:delete', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (25, '公告修改', '#', 6, 3, ' ', NULL, 'F', 'notice:update', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (26, '菜单添加', '#', 7, 1, ' ', NULL, 'F', 'menu:add', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (27, '菜单删除', '#', 7, 2, ' ', NULL, 'F', 'menu:delete', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (28, '菜单修改', '#', 7, 3, ' ', NULL, 'F', 'menu:update', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (29, '部门添加', '#', 8, 1, '', NULL, 'F', 'department:add', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (30, '部门删除', '#', 8, 2, ' ', NULL, 'F', 'department:delete', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (31, '部门修改', '#', 8, 3, ' ', NULL, 'F', 'department:update', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (32, '岗位添加', '#', 9, 1, ' ', NULL, 'F', 'job:add', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (33, '岗位删除', '#', 9, 2, ' ', NULL, 'F', 'job:delete', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (34, '岗位修改', '#', 9, 3, ' ', NULL, 'F', 'job:update', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (35, '日程添加', '#', 10, 1, ' ', NULL, 'F', 'calendar:add', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (36, '日程删除', '#', 10, 2, ' ', NULL, 'F', 'calendar:delete', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (37, '日程修改', '#', 10, 3, ' ', NULL, 'F', 'calendar:update', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (38, '考勤添加', '#', 11, 1, ' ', NULL, 'F', 'clock:add', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (39, '考勤删除', '#', 11, 2, ' ', NULL, 'F', 'clock:delete', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (40, '考勤修改', '#', 11, 3, ' ', NULL, 'F', 'clock:update', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (41, '资源添加', '#', 12, 1, ' ', NULL, 'F', 'resources:add', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (42, '资源删除', '#', 12, 2, ' ', NULL, 'F', 'resources:delete', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (43, '资源修改', '#', 12, 3, ' ', NULL, 'F', 'resources:update', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (44, '用户信息恢复', '#', 13, 1, '', NULL, 'F', 'recover:add', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (45, '用户信息删除', '#', 13, 2, ' ', NULL, 'F', 'recover:delete', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (46, '用户查询', '#', 4, 6, ' ', NULL, 'F', 'user:query', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (47, '角色查询', '#', 5, 6, ' ', NULL, 'F', 'role:query', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (48, '公告查询', '#', 6, 6, ' ', NULL, 'F', 'notice:query', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (49, '菜单查询', '#', 7, 6, ' ', NULL, 'F', 'menu:query', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (50, '部门查询', '#', 8, 6, ' ', NULL, 'F', 'department:query', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (51, '岗位查询', '#', 9, 6, ' ', NULL, 'F', 'job:query', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (52, '日程查询', '#', 10, 6, ' ', NULL, 'F', 'calendar:query', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (53, '签到查询', '#', 11, 6, ' ', NULL, 'F', 'clock:query', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (54, '资源查询', '#', 12, 6, ' ', NULL, 'F', 'resources:query', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (55, '回收用户查询', '#', 13, 6, ' ', NULL, 'F', 'recover:query', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (126, '申请管理', '#', 3, 3, '/business/request', 'business/request/index', 'C', 'request:list', '2023-04-20 22:15:59', '2023-04-24 22:17:15', '用户申请管理');
INSERT INTO `oa_menu` VALUES (127, '申请管理添加', '#', 126, 1, ' ', NULL, 'F', 'request:add', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (128, '申请管理删除', '#', 126, 2, ' ', NULL, 'F', 'request:delete', NULL, NULL, NULL);
INSERT INTO `oa_menu` VALUES (129, '申请管理修改', '#', 126, 3, ' ', NULL, 'F', 'request:add', '2023-04-24 22:21:51', NULL, NULL);
INSERT INTO `oa_menu` VALUES (130, '申请管理查询', '#', 126, 4, ' ', NULL, 'F', 'request:part', '2023-04-24 22:22:53', NULL, NULL);
INSERT INTO `oa_menu` VALUES (133, '工作管理', '#', 2, 5, '/busi/work', 'busi/work/index', 'C', 'work:list', '2023-04-25 16:20:45', NULL, NULL);
INSERT INTO `oa_menu` VALUES (134, '工作添加', '#', 133, 1, ' ', NULL, 'F', 'work:add', '2023-04-25 16:21:18', NULL, NULL);
INSERT INTO `oa_menu` VALUES (135, '工作删除', '#', 133, 2, ' ', NULL, 'F', 'work:delete', '2023-04-25 16:21:50', NULL, NULL);
INSERT INTO `oa_menu` VALUES (136, '工作修改', '#', 133, 3, ' ', NULL, 'F', 'work:update', '2023-04-25 16:22:22', NULL, NULL);
INSERT INTO `oa_menu` VALUES (137, '工作查询', '#', 133, 4, ' ', NULL, 'F', 'work:part', '2023-04-25 16:23:29', NULL, NULL);
INSERT INTO `oa_menu` VALUES (138, '申请', '#', 141, 0, ' ', NULL, 'F', 'user:request', '2023-04-25 16:24:25', '2023-04-25 16:40:25', NULL);
INSERT INTO `oa_menu` VALUES (139, '工作', '#', 141, 0, ' ', NULL, 'F', 'user:work', '2023-04-25 16:24:50', '2023-04-25 19:07:03', NULL);
INSERT INTO `oa_menu` VALUES (140, '日程', '#', 141, 0, ' ', NULL, 'F', 'user:calendar', '2023-04-25 19:06:48', '2023-04-25 19:11:21', NULL);
INSERT INTO `oa_menu` VALUES (141, '个人', '#', 0, 4, ' ', NULL, 'M', NULL, '2023-04-25 19:10:46', NULL, NULL);
INSERT INTO `oa_menu` VALUES (142, '公司', '#', 141, 4, ' ', NULL, 'F', 'user:company', '2023-04-27 21:02:01', '2023-04-27 21:05:53', NULL);
INSERT INTO `oa_menu` VALUES (143, '公司管理', '#', 3, 4, '/business/company', 'business/company/index', 'C', 'company:list', '2023-04-27 21:08:20', '2023-04-27 21:08:57', NULL);
INSERT INTO `oa_menu` VALUES (144, '公司管理添加', '#', 143, 1, ' ', NULL, 'F', 'company:add', '2023-04-28 21:15:22', NULL, NULL);
INSERT INTO `oa_menu` VALUES (145, '公司管理删除', '#', 143, 2, ' ', NULL, 'F', 'company:delete', '2023-04-28 21:15:57', NULL, NULL);
INSERT INTO `oa_menu` VALUES (146, '公司管理修改', '#', 143, 3, ' ', NULL, 'F', 'company:update', '2023-04-28 21:16:31', NULL, NULL);
INSERT INTO `oa_menu` VALUES (147, '文件', '#', 141, 4, ' ', NULL, 'F', 'user:document', '2023-04-29 17:11:33', NULL, NULL);

-- ----------------------------
-- Table structure for oa_notice
-- ----------------------------
DROP TABLE IF EXISTS `oa_notice`;
CREATE TABLE `oa_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发布人',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '0 未发布， 1 发布',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_notice
-- ----------------------------
INSERT INTO `oa_notice` VALUES (1, '什么是vue', 'Vue，类似于 view) 是一套用于构建用户界面的渐进式JavaScript框架。与其它框架不同的是，Vue被设计为可以自底向上逐层应用。Vue的核心库只关注视图层，方便与第三方库或既有项目整合。另一方面，Vue 完全有能力驱动采用单文件组件和Vue生态系统支持的库开发的复杂单页应用。', '2023-03-18 12:59:37', '2023-04-28 13:20:34', '超级管理员', NULL, '1');
INSERT INTO `oa_notice` VALUES (2, '你了解doker吗', 'Docker是基于Linux内核实现的, Docker最早采用了LXC技术, LXC是Linux原生支持的容器技术, 可以提供轻量级的虚拟化. Docker基于LXC发展, 提供了LXC的高级封装, 标准的配置方法, 在LXC的基础上, Docker提供了一系列更强大的功能. 而虚拟化技术, 比如KVM, 是基于模块实现, 后来Docker改为自己研发并开源的runc技术运行容器\r\n\r\nDocker相比虚拟机的交付速度更快, 资源消耗更低, Docker采用客户端/服务器端架构, 使用远程API来管理和创建容器, 其可以轻松的创建一个轻量级的, 可移植的, 自给自足的容器. Docker的三大理念就是build, ship, run. Docker通过namespace和cgroup来提供容器的资源隔离与安全保障等, 所以Docker容器在运行时, 不需要类似虚拟机的额外资源开销, 因此可以大幅度提供资源利用率', '2023-04-11 13:29:41', '2023-06-02 12:07:40', '管理员', NULL, '0');
INSERT INTO `oa_notice` VALUES (3, '什么是Promise方式', 'Promise 是一种用于管理异步操作的机制，它可以简化异步编程，并且提供了更好的可读性和可维护性。基本思路是通过异步操作返回一个 Promise 对象，然后使用 then 方法或 catch 方法处理该 Promise 对象的状态。\r\n\r\n在使用 Promise 时，我们可以传入一个异步任务函数作为参数，并在其中进行异步操作。当异步操作完成后，我们可以调用 resolve 函数来设置任务成功的结果，或者调用 reject 函数来设置任务失败的原因。Promise 会根据任务执行的结果自动更新自身的状态，并将结果或错误信息传递给 then 方法或 catch 方法。\r\n\r\n使用 Promise 方式可以避免回调地狱（callback hell）问题，并且使异步代码更加清晰易懂。另外，Promise 也提供了链式调用的方式，可以在不同步骤之间传递数据，并且能够简化错误处理流程。', '2023-04-16 17:19:15', '2023-06-02 12:07:50', '管理员', NULL, '1');
INSERT INTO `oa_notice` VALUES (8, '测试', '测试程序', '2023-05-08 15:29:57', NULL, '管理员', NULL, '1');
INSERT INTO `oa_notice` VALUES (9, 'qwe', '123', '2023-06-04 10:23:55', NULL, '1', NULL, '1');

-- ----------------------------
-- Table structure for oa_recover
-- ----------------------------
DROP TABLE IF EXISTS `oa_recover`;
CREATE TABLE `oa_recover`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '工号',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `salary` double NULL DEFAULT 0 COMMENT '薪资',
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '男' COMMENT '性别',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT ' ' COMMENT '邮箱',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT ' ' COMMENT '手机号码',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `is_delete` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '是否已经删除（0 可用，1 删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100028 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oa_recover
-- ----------------------------
INSERT INTO `oa_recover` VALUES (100027, 'ceshi', '$2a$10$oj3bawygoni/ZyRhG9dbj.7f6BXq/4pQPLqQUBPctzbPDwLK0XoRu', '测试', NULL, 0, '女', 42, ' ', '9801-02-28', '131234198010301234', '12345678900', NULL, NULL, '1', '2023-04-26 18:25:30', '2023-04-26 19:50:05', NULL, '1');
INSERT INTO `oa_recover` VALUES (100028, 'ceshi2', '$2a$10$WBfJuiIC2/zG1aHd.k07W.T4RaHDcvZbWZURCRq6LK6SMIq88bQhq', '测试2', NULL, 1, '女', 132, ' ', '8900-12-31', '121123189010101234', '12345654321', NULL, NULL, '0', '2023-05-01 18:05:26', '2023-05-01 18:05:31', NULL, '1');

-- ----------------------------
-- Table structure for oa_request
-- ----------------------------
DROP TABLE IF EXISTS `oa_request`;
CREATE TABLE `oa_request`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `time` datetime NULL DEFAULT NULL COMMENT '申请时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '(0 未申请,1 已撤回,2已申请,3 已批准,4已拒绝）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_request
-- ----------------------------
INSERT INTO `oa_request` VALUES (1, '请求升职加薪', '2023-05-05 19:05:00', '2', '2023-04-24 22:34:11', NULL, NULL);
INSERT INTO `oa_request` VALUES (2, '拒绝加班', '2023-04-25 13:05:45', '3', '2023-04-24 23:38:19', NULL, NULL);
INSERT INTO `oa_request` VALUES (3, '增加年终奖', '2023-05-02 15:56:16', '1', '2023-04-25 13:06:15', NULL, NULL);
INSERT INTO `oa_request` VALUES (4, '尊敬的领导，我因有事，需请假3天，望批准！', '2023-04-25 19:14:46', '2', '2023-04-25 19:14:39', NULL, NULL);
INSERT INTO `oa_request` VALUES (5, '请求加薪', '2023-05-02 15:04:12', '2', '2023-05-02 15:04:01', NULL, '谢谢老板');

-- ----------------------------
-- Table structure for oa_resources
-- ----------------------------
DROP TABLE IF EXISTS `oa_resources`;
CREATE TABLE `oa_resources`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `resources_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件名称',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '(0 不可下载 1 可下载）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件大小(K)',
  `cover` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件地址',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1001009 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_resources
-- ----------------------------
INSERT INTO `oa_resources` VALUES (1001002, '关于Spring Security的权限控制', '0', '2023-03-02 20:12:02', '2023-05-05 17:50:02', 'txt', '0', 'http://localhost:9091/files/56d7fb303c014bf98fc6d69c57d1b13a_Test.txt', NULL);
INSERT INTO `oa_resources` VALUES (1001003, '微服务之前后端分离架构设计', '1', '2023-03-16 20:57:52', '2023-05-05 17:26:58', 'txt', '0', 'http://localhost:9091/files/f83ce5c475684f23aaa942604e682578_Test2.txt', NULL);

-- ----------------------------
-- Table structure for oa_rm
-- ----------------------------
DROP TABLE IF EXISTS `oa_rm`;
CREATE TABLE `oa_rm`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色菜单id',
  `role_id` bigint NOT NULL DEFAULT 703 COMMENT '角色id',
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1234 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_rm
-- ----------------------------
INSERT INTO `oa_rm` VALUES (1067, 701, 1);
INSERT INTO `oa_rm` VALUES (1068, 701, 4);
INSERT INTO `oa_rm` VALUES (1069, 701, 14);
INSERT INTO `oa_rm` VALUES (1070, 701, 15);
INSERT INTO `oa_rm` VALUES (1071, 701, 16);
INSERT INTO `oa_rm` VALUES (1072, 701, 17);
INSERT INTO `oa_rm` VALUES (1073, 701, 18);
INSERT INTO `oa_rm` VALUES (1074, 701, 46);
INSERT INTO `oa_rm` VALUES (1075, 701, 5);
INSERT INTO `oa_rm` VALUES (1076, 701, 19);
INSERT INTO `oa_rm` VALUES (1077, 701, 20);
INSERT INTO `oa_rm` VALUES (1078, 701, 21);
INSERT INTO `oa_rm` VALUES (1079, 701, 22);
INSERT INTO `oa_rm` VALUES (1080, 701, 47);
INSERT INTO `oa_rm` VALUES (1081, 701, 7);
INSERT INTO `oa_rm` VALUES (1082, 701, 26);
INSERT INTO `oa_rm` VALUES (1083, 701, 27);
INSERT INTO `oa_rm` VALUES (1084, 701, 28);
INSERT INTO `oa_rm` VALUES (1085, 701, 49);
INSERT INTO `oa_rm` VALUES (1086, 701, 6);
INSERT INTO `oa_rm` VALUES (1087, 701, 23);
INSERT INTO `oa_rm` VALUES (1088, 701, 24);
INSERT INTO `oa_rm` VALUES (1089, 701, 25);
INSERT INTO `oa_rm` VALUES (1090, 701, 48);
INSERT INTO `oa_rm` VALUES (1091, 701, 2);
INSERT INTO `oa_rm` VALUES (1092, 701, 8);
INSERT INTO `oa_rm` VALUES (1093, 701, 29);
INSERT INTO `oa_rm` VALUES (1094, 701, 30);
INSERT INTO `oa_rm` VALUES (1095, 701, 31);
INSERT INTO `oa_rm` VALUES (1096, 701, 50);
INSERT INTO `oa_rm` VALUES (1097, 701, 9);
INSERT INTO `oa_rm` VALUES (1098, 701, 32);
INSERT INTO `oa_rm` VALUES (1099, 701, 33);
INSERT INTO `oa_rm` VALUES (1100, 701, 34);
INSERT INTO `oa_rm` VALUES (1101, 701, 51);
INSERT INTO `oa_rm` VALUES (1102, 701, 10);
INSERT INTO `oa_rm` VALUES (1103, 701, 35);
INSERT INTO `oa_rm` VALUES (1104, 701, 36);
INSERT INTO `oa_rm` VALUES (1105, 701, 37);
INSERT INTO `oa_rm` VALUES (1106, 701, 52);
INSERT INTO `oa_rm` VALUES (1107, 701, 11);
INSERT INTO `oa_rm` VALUES (1108, 701, 38);
INSERT INTO `oa_rm` VALUES (1109, 701, 39);
INSERT INTO `oa_rm` VALUES (1110, 701, 40);
INSERT INTO `oa_rm` VALUES (1111, 701, 53);
INSERT INTO `oa_rm` VALUES (1112, 701, 133);
INSERT INTO `oa_rm` VALUES (1113, 701, 134);
INSERT INTO `oa_rm` VALUES (1114, 701, 135);
INSERT INTO `oa_rm` VALUES (1115, 701, 136);
INSERT INTO `oa_rm` VALUES (1116, 701, 137);
INSERT INTO `oa_rm` VALUES (1117, 701, 3);
INSERT INTO `oa_rm` VALUES (1118, 701, 12);
INSERT INTO `oa_rm` VALUES (1119, 701, 41);
INSERT INTO `oa_rm` VALUES (1120, 701, 42);
INSERT INTO `oa_rm` VALUES (1121, 701, 43);
INSERT INTO `oa_rm` VALUES (1122, 701, 54);
INSERT INTO `oa_rm` VALUES (1123, 701, 13);
INSERT INTO `oa_rm` VALUES (1124, 701, 44);
INSERT INTO `oa_rm` VALUES (1125, 701, 45);
INSERT INTO `oa_rm` VALUES (1126, 701, 55);
INSERT INTO `oa_rm` VALUES (1127, 701, 126);
INSERT INTO `oa_rm` VALUES (1128, 701, 127);
INSERT INTO `oa_rm` VALUES (1129, 701, 128);
INSERT INTO `oa_rm` VALUES (1130, 701, 129);
INSERT INTO `oa_rm` VALUES (1131, 701, 130);
INSERT INTO `oa_rm` VALUES (1132, 701, 143);
INSERT INTO `oa_rm` VALUES (1133, 701, 144);
INSERT INTO `oa_rm` VALUES (1134, 701, 145);
INSERT INTO `oa_rm` VALUES (1135, 701, 146);
INSERT INTO `oa_rm` VALUES (1136, 701, 140);
INSERT INTO `oa_rm` VALUES (1137, 701, 138);
INSERT INTO `oa_rm` VALUES (1138, 701, 139);
INSERT INTO `oa_rm` VALUES (1139, 701, 142);
INSERT INTO `oa_rm` VALUES (1140, 701, 147);
INSERT INTO `oa_rm` VALUES (1141, 702, 1);
INSERT INTO `oa_rm` VALUES (1142, 702, 4);
INSERT INTO `oa_rm` VALUES (1143, 702, 14);
INSERT INTO `oa_rm` VALUES (1144, 702, 15);
INSERT INTO `oa_rm` VALUES (1145, 702, 16);
INSERT INTO `oa_rm` VALUES (1146, 702, 17);
INSERT INTO `oa_rm` VALUES (1147, 702, 18);
INSERT INTO `oa_rm` VALUES (1148, 702, 46);
INSERT INTO `oa_rm` VALUES (1149, 702, 140);
INSERT INTO `oa_rm` VALUES (1150, 702, 138);
INSERT INTO `oa_rm` VALUES (1151, 702, 139);
INSERT INTO `oa_rm` VALUES (1152, 702, 142);
INSERT INTO `oa_rm` VALUES (1153, 702, 147);
INSERT INTO `oa_rm` VALUES (1154, 703, 3);
INSERT INTO `oa_rm` VALUES (1155, 703, 12);
INSERT INTO `oa_rm` VALUES (1156, 703, 41);
INSERT INTO `oa_rm` VALUES (1157, 703, 42);
INSERT INTO `oa_rm` VALUES (1158, 703, 43);
INSERT INTO `oa_rm` VALUES (1159, 703, 54);
INSERT INTO `oa_rm` VALUES (1160, 703, 140);
INSERT INTO `oa_rm` VALUES (1161, 703, 138);
INSERT INTO `oa_rm` VALUES (1162, 703, 139);
INSERT INTO `oa_rm` VALUES (1163, 703, 142);
INSERT INTO `oa_rm` VALUES (1164, 703, 147);
INSERT INTO `oa_rm` VALUES (1165, 704, 1);
INSERT INTO `oa_rm` VALUES (1166, 704, 5);
INSERT INTO `oa_rm` VALUES (1167, 704, 19);
INSERT INTO `oa_rm` VALUES (1168, 704, 20);
INSERT INTO `oa_rm` VALUES (1169, 704, 21);
INSERT INTO `oa_rm` VALUES (1170, 704, 22);
INSERT INTO `oa_rm` VALUES (1171, 704, 140);
INSERT INTO `oa_rm` VALUES (1172, 704, 138);
INSERT INTO `oa_rm` VALUES (1173, 704, 139);
INSERT INTO `oa_rm` VALUES (1174, 704, 142);
INSERT INTO `oa_rm` VALUES (1175, 704, 147);
INSERT INTO `oa_rm` VALUES (1176, 705, 2);
INSERT INTO `oa_rm` VALUES (1177, 705, 8);
INSERT INTO `oa_rm` VALUES (1178, 705, 29);
INSERT INTO `oa_rm` VALUES (1179, 705, 30);
INSERT INTO `oa_rm` VALUES (1180, 705, 31);
INSERT INTO `oa_rm` VALUES (1181, 705, 50);
INSERT INTO `oa_rm` VALUES (1182, 705, 140);
INSERT INTO `oa_rm` VALUES (1183, 705, 138);
INSERT INTO `oa_rm` VALUES (1184, 705, 139);
INSERT INTO `oa_rm` VALUES (1185, 705, 142);
INSERT INTO `oa_rm` VALUES (1186, 705, 147);
INSERT INTO `oa_rm` VALUES (1187, 706, 2);
INSERT INTO `oa_rm` VALUES (1188, 706, 9);
INSERT INTO `oa_rm` VALUES (1189, 706, 32);
INSERT INTO `oa_rm` VALUES (1190, 706, 33);
INSERT INTO `oa_rm` VALUES (1191, 706, 34);
INSERT INTO `oa_rm` VALUES (1192, 706, 51);
INSERT INTO `oa_rm` VALUES (1193, 706, 140);
INSERT INTO `oa_rm` VALUES (1194, 706, 138);
INSERT INTO `oa_rm` VALUES (1195, 706, 139);
INSERT INTO `oa_rm` VALUES (1196, 706, 142);
INSERT INTO `oa_rm` VALUES (1197, 706, 147);
INSERT INTO `oa_rm` VALUES (1198, 707, 1);
INSERT INTO `oa_rm` VALUES (1199, 707, 7);
INSERT INTO `oa_rm` VALUES (1200, 707, 26);
INSERT INTO `oa_rm` VALUES (1201, 707, 27);
INSERT INTO `oa_rm` VALUES (1202, 707, 28);
INSERT INTO `oa_rm` VALUES (1203, 707, 49);
INSERT INTO `oa_rm` VALUES (1204, 707, 140);
INSERT INTO `oa_rm` VALUES (1205, 707, 138);
INSERT INTO `oa_rm` VALUES (1206, 707, 139);
INSERT INTO `oa_rm` VALUES (1207, 707, 142);
INSERT INTO `oa_rm` VALUES (1208, 707, 147);
INSERT INTO `oa_rm` VALUES (1209, 708, 1);
INSERT INTO `oa_rm` VALUES (1210, 708, 6);
INSERT INTO `oa_rm` VALUES (1211, 708, 23);
INSERT INTO `oa_rm` VALUES (1212, 708, 24);
INSERT INTO `oa_rm` VALUES (1213, 708, 25);
INSERT INTO `oa_rm` VALUES (1214, 708, 48);
INSERT INTO `oa_rm` VALUES (1215, 708, 140);
INSERT INTO `oa_rm` VALUES (1216, 708, 138);
INSERT INTO `oa_rm` VALUES (1217, 708, 139);
INSERT INTO `oa_rm` VALUES (1218, 708, 142);
INSERT INTO `oa_rm` VALUES (1219, 708, 147);
INSERT INTO `oa_rm` VALUES (1220, 709, 3);
INSERT INTO `oa_rm` VALUES (1221, 709, 13);
INSERT INTO `oa_rm` VALUES (1222, 709, 44);
INSERT INTO `oa_rm` VALUES (1223, 709, 45);
INSERT INTO `oa_rm` VALUES (1224, 709, 55);
INSERT INTO `oa_rm` VALUES (1225, 709, 140);
INSERT INTO `oa_rm` VALUES (1226, 709, 138);
INSERT INTO `oa_rm` VALUES (1227, 709, 139);
INSERT INTO `oa_rm` VALUES (1228, 709, 142);
INSERT INTO `oa_rm` VALUES (1229, 709, 147);
INSERT INTO `oa_rm` VALUES (1230, 710, 140);
INSERT INTO `oa_rm` VALUES (1231, 710, 138);
INSERT INTO `oa_rm` VALUES (1232, 710, 139);
INSERT INTO `oa_rm` VALUES (1233, 710, 142);
INSERT INTO `oa_rm` VALUES (1234, 710, 147);

-- ----------------------------
-- Table structure for oa_role
-- ----------------------------
DROP TABLE IF EXISTS `oa_role`;
CREATE TABLE `oa_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限代码',
  `names` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7062 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_role
-- ----------------------------
INSERT INTO `oa_role` VALUES (701, 'ADMIN', '管理员', '2023-03-16 14:41:57', '2023-04-17 19:41:48', '所有权限');
INSERT INTO `oa_role` VALUES (702, 'USERS', '用户管理', '2023-03-16 14:42:10', '2023-04-17 19:41:54', '用户管理');
INSERT INTO `oa_role` VALUES (703, 'RESOURCES', '资源管理', '2023-03-16 14:42:16', '2023-04-17 19:42:03', '资源管理');
INSERT INTO `oa_role` VALUES (704, 'ROLE', '角色管理', '2023-03-16 14:42:18', '2023-04-17 19:42:10', '角色管理');
INSERT INTO `oa_role` VALUES (705, 'DEPARTMENT', '部门管理', '2023-03-23 22:07:07', '2023-04-17 19:42:19', '管理部门');
INSERT INTO `oa_role` VALUES (706, 'JOB', '岗位管理', '2023-03-23 22:08:52', '2023-04-17 19:42:37', '管理岗位');
INSERT INTO `oa_role` VALUES (707, 'MENU', '菜单管理', '2023-03-23 22:09:51', '2023-04-17 19:42:46', '管理菜单');
INSERT INTO `oa_role` VALUES (708, 'NOTICE', '公告管理', '2023-03-23 22:11:31', '2023-04-17 19:42:50', '公告管理');
INSERT INTO `oa_role` VALUES (709, 'RECOVER', '回收管理', '2023-03-23 22:12:50', '2023-04-17 19:43:04', '恢复用户信息');
INSERT INTO `oa_role` VALUES (710, 'USER', '个人', '2023-03-16 14:42:20', '2023-04-17 19:43:15', '个人信息管理权限,最基本的权限');

-- ----------------------------
-- Table structure for oa_ud
-- ----------------------------
DROP TABLE IF EXISTS `oa_ud`;
CREATE TABLE `oa_ud`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户部门ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `department_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_ud
-- ----------------------------
INSERT INTO `oa_ud` VALUES (1, 100001, 100);
INSERT INTO `oa_ud` VALUES (2, 100002, 400);
INSERT INTO `oa_ud` VALUES (8, 100025, 202);
INSERT INTO `oa_ud` VALUES (10, 100027, 400);
INSERT INTO `oa_ud` VALUES (11, 100028, 400);

-- ----------------------------
-- Table structure for oa_uj
-- ----------------------------
DROP TABLE IF EXISTS `oa_uj`;
CREATE TABLE `oa_uj`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户职位ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `job_id` bigint NOT NULL DEFAULT 200 COMMENT '职位ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_uj
-- ----------------------------
INSERT INTO `oa_uj` VALUES (1, 100001, 1);
INSERT INTO `oa_uj` VALUES (2, 100002, 2);
INSERT INTO `oa_uj` VALUES (4, 100025, 300);
INSERT INTO `oa_uj` VALUES (6, 100027, 300);
INSERT INTO `oa_uj` VALUES (7, 100028, 300);

-- ----------------------------
-- Table structure for oa_uq
-- ----------------------------
DROP TABLE IF EXISTS `oa_uq`;
CREATE TABLE `oa_uq`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `request_id` bigint NULL DEFAULT NULL COMMENT '申请表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_uq
-- ----------------------------
INSERT INTO `oa_uq` VALUES (1, 100001, 1);
INSERT INTO `oa_uq` VALUES (2, 100001, 2);
INSERT INTO `oa_uq` VALUES (3, 100001, 3);
INSERT INTO `oa_uq` VALUES (4, 100002, 4);
INSERT INTO `oa_uq` VALUES (5, 100025, 5);

-- ----------------------------
-- Table structure for oa_ur
-- ----------------------------
DROP TABLE IF EXISTS `oa_ur`;
CREATE TABLE `oa_ur`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户角色id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL DEFAULT 710 COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_ur
-- ----------------------------
INSERT INTO `oa_ur` VALUES (1, 100001, 701);
INSERT INTO `oa_ur` VALUES (2, 100002, 702);
INSERT INTO `oa_ur` VALUES (23, 100026, 710);
INSERT INTO `oa_ur` VALUES (24, 100027, 710);
INSERT INTO `oa_ur` VALUES (25, 100028, 710);
INSERT INTO `oa_ur` VALUES (27, 100025, 710);

-- ----------------------------
-- Table structure for oa_user
-- ----------------------------
DROP TABLE IF EXISTS `oa_user`;
CREATE TABLE `oa_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '工号',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `salary` double NULL DEFAULT 0 COMMENT '薪资',
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '男' COMMENT '性别',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT ' ' COMMENT '邮箱',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT ' ' COMMENT '手机号码',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `is_delete` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否已经删除（0 可用，1 删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100028 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oa_user
-- ----------------------------
INSERT INTO `oa_user` VALUES (100001, 'pinellia', '$2a$10$YQgnpO2G1ESAT78bACTtgOwlPOUZgnhMRHyvy7qgaG2JRHwCdM1rC', '超级管理员', 'http://localhost:9091/avatar/c7903487f41c4a41a53be1866ab7db28_9.jpg', 100000, '男', 23, 'pinellia.pine.cn', '2000-01-01', '131345200001123456', '12345678900', NULL, '2023-06-06 19:07:37', '0', '2023-03-01 16:46:30', '2023-05-04 13:20:46', NULL, '0');
INSERT INTO `oa_user` VALUES (100002, 'zhongli', '$2a$10$su0oUMnKwN1n7hNDTapi2.26ilTozpdhfT2sG1QwvtFrUB/27fb6i', '管理员', 'http://localhost:9091/avatar/2cbf3979044744e2862adbee7a405bb5_66.jpg', 20, '男', 23, ' 456@pine.com', '2000-01-01', '131345200001123456', '12345678901', '璃月', '2023-06-02 13:34:02', '0', '2023-03-01 16:46:35', '2023-06-04 10:21:11', NULL, '0');
INSERT INTO `oa_user` VALUES (100025, 'ganyu', '$2a$10$HoP2Sj8eJ3t0Zps6r8BJEeogNsxgM.qvKffapmvTPPcoXR1zi.FQu', '用户', 'http://localhost:9091/avatar/8b26dfaecb614e2bab12bbb262bcecb9_74.jpg', 50, '女', 22, '123', '0010-10-01', '121345200101123456', '12112123451', '璃月港', '2023-06-04 09:56:08', '0', '2023-04-22 18:45:03', '2023-05-16 21:59:34', '璃月秘书', '0');

-- ----------------------------
-- Table structure for oa_user_download
-- ----------------------------
DROP TABLE IF EXISTS `oa_user_download`;
CREATE TABLE `oa_user_download`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `resources_id` bigint NULL DEFAULT NULL COMMENT '资源id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_user_download
-- ----------------------------
INSERT INTO `oa_user_download` VALUES (4, 100001, 1001002);
INSERT INTO `oa_user_download` VALUES (5, 100002, 1001002);
INSERT INTO `oa_user_download` VALUES (6, 100001, 1001003);
INSERT INTO `oa_user_download` VALUES (7, 100025, 1001003);
INSERT INTO `oa_user_download` VALUES (8, 100001, 1001006);
INSERT INTO `oa_user_download` VALUES (9, 100002, 1001006);
INSERT INTO `oa_user_download` VALUES (10, 100025, 1001006);

-- ----------------------------
-- Table structure for oa_user_resources
-- ----------------------------
DROP TABLE IF EXISTS `oa_user_resources`;
CREATE TABLE `oa_user_resources`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `resources` bigint NULL DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_user_resources
-- ----------------------------
INSERT INTO `oa_user_resources` VALUES (1, 100001, 1001002);
INSERT INTO `oa_user_resources` VALUES (2, 100001, 1001003);

-- ----------------------------
-- Table structure for oa_uw
-- ----------------------------
DROP TABLE IF EXISTS `oa_uw`;
CREATE TABLE `oa_uw`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '执行用户的id',
  `work_id` bigint NULL DEFAULT NULL COMMENT '工作id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_uw
-- ----------------------------
INSERT INTO `oa_uw` VALUES (34, 100001, 2);
INSERT INTO `oa_uw` VALUES (35, 100002, 2);
INSERT INTO `oa_uw` VALUES (36, 100025, 2);
INSERT INTO `oa_uw` VALUES (37, 100001, 3);
INSERT INTO `oa_uw` VALUES (38, 100025, 3);
INSERT INTO `oa_uw` VALUES (39, 100001, 4);
INSERT INTO `oa_uw` VALUES (40, 100002, 4);
INSERT INTO `oa_uw` VALUES (41, 100025, 4);
INSERT INTO `oa_uw` VALUES (44, 100001, 5);
INSERT INTO `oa_uw` VALUES (45, 100002, 5);
INSERT INTO `oa_uw` VALUES (46, 100001, 6);
INSERT INTO `oa_uw` VALUES (47, 100025, 6);
INSERT INTO `oa_uw` VALUES (58, 100001, 1);
INSERT INTO `oa_uw` VALUES (59, 100025, 1);

-- ----------------------------
-- Table structure for oa_work
-- ----------------------------
DROP TABLE IF EXISTS `oa_work`;
CREATE TABLE `oa_work`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `begin_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `cover` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '文件',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '(0 已创建，1 已发布，2 超时未提交，3 已提交, 4被打回）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_work
-- ----------------------------
INSERT INTO `oa_work` VALUES (1, '优化界面UI', '2023-04-25 18:28:56', '2023-06-07 17:21:35', '0', '4', NULL, '2023-06-02 13:16:58', NULL);
INSERT INTO `oa_work` VALUES (2, '完成后端权限控制', '2023-02-25 17:18:06', '2024-04-04 00:00:00', '0', '4', '2023-04-25 16:57:11', '2023-04-29 14:10:40', NULL);
INSERT INTO `oa_work` VALUES (3, '完成系统分析', '2023-05-01 14:17:17', '2023-06-05 00:00:00', '0', '1', '2023-05-01 14:17:23', '2023-06-02 13:17:10', NULL);
INSERT INTO `oa_work` VALUES (4, '完成项目测试', '2023-04-01 14:17:36', '2023-04-08 00:00:00', '0', '2', '2023-05-01 14:17:41', '2023-05-02 14:08:32', NULL);
INSERT INTO `oa_work` VALUES (5, '项目部署', '2023-05-01 14:17:53', '2023-05-31 00:00:00', '0', '3', '2023-05-01 14:17:58', NULL, NULL);
INSERT INTO `oa_work` VALUES (6, '项目上线', '2023-05-01 14:18:08', '2023-05-31 00:00:00', '0', '3', '2023-05-01 14:18:13', NULL, NULL);

-- ----------------------------
-- Table structure for oa_wu
-- ----------------------------
DROP TABLE IF EXISTS `oa_wu`;
CREATE TABLE `oa_wu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '工作发布者的用户id',
  `work_id` bigint NULL DEFAULT NULL COMMENT '工作id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_wu
-- ----------------------------
INSERT INTO `oa_wu` VALUES (1, 100001, 1);
INSERT INTO `oa_wu` VALUES (2, 100001, 2);
INSERT INTO `oa_wu` VALUES (3, 100001, 3);
INSERT INTO `oa_wu` VALUES (4, 100001, 4);
INSERT INTO `oa_wu` VALUES (5, 100001, 5);
INSERT INTO `oa_wu` VALUES (6, 100001, 6);

SET FOREIGN_KEY_CHECKS = 1;
