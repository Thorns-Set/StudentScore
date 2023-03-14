/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : student_score

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 14/03/2023 14:48:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `admin_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `admin_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('adminOne', '1');

-- ----------------------------
-- Table structure for t_class
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class`  (
  `class_id` int(0) NOT NULL COMMENT '班级编号',
  `class_name` int(0) NOT NULL,
  PRIMARY KEY (`class_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_class
-- ----------------------------
INSERT INTO `t_class` VALUES (1, 1401);
INSERT INTO `t_class` VALUES (2, 1402);
INSERT INTO `t_class` VALUES (3, 1403);
INSERT INTO `t_class` VALUES (33, 23);
INSERT INTO `t_class` VALUES (98, 23);

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course`  (
  `course_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '课程编号',
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程名称',
  `course_field` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程字段',
  PRIMARY KEY (`course_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES (1, '语文', 'languageScore');
INSERT INTO `t_course` VALUES (2, '数学', 'mathScore');
INSERT INTO `t_course` VALUES (3, '英语', 'englishScore');
INSERT INTO `t_course` VALUES (4, '政治', 'politicsScore');
INSERT INTO `t_course` VALUES (5, '历史', 'historyScore');
INSERT INTO `t_course` VALUES (6, '地理', 'geogScore');
INSERT INTO `t_course` VALUES (13, '物理', 'physicsScore');
INSERT INTO `t_course` VALUES (14, '化学', 'chemistryScore');
INSERT INTO `t_course` VALUES (15, '生物', 'biologyScore');

-- ----------------------------
-- Table structure for t_exam
-- ----------------------------
DROP TABLE IF EXISTS `t_exam`;
CREATE TABLE `t_exam`  (
  `exam_id` int(0) NOT NULL COMMENT '考试编号',
  `exam_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '考试名称',
  PRIMARY KEY (`exam_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_exam
-- ----------------------------
INSERT INTO `t_exam` VALUES (1, '2020年第一次月考');
INSERT INTO `t_exam` VALUES (2, '2020年第二次月考');

-- ----------------------------
-- Table structure for t_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_relation`;
CREATE TABLE `t_relation`  (
  `rel_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `class_id` int(0) NOT NULL,
  `tea_id` int(0) NOT NULL,
  `course_id` int(0) NOT NULL,
  PRIMARY KEY (`rel_id`) USING BTREE,
  INDEX `班级编号`(`class_id`) USING BTREE,
  INDEX `老师id`(`tea_id`) USING BTREE,
  INDEX `课程编号`(`course_id`) USING BTREE,
  CONSTRAINT `班级编号` FOREIGN KEY (`class_id`) REFERENCES `t_class` (`class_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `老师id` FOREIGN KEY (`tea_id`) REFERENCES `t_teacher` (`tea_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `课程编号` FOREIGN KEY (`course_id`) REFERENCES `t_course` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_relation
-- ----------------------------
INSERT INTO `t_relation` VALUES (1, 1, 1, 1);
INSERT INTO `t_relation` VALUES (2, 1, 2, 2);
INSERT INTO `t_relation` VALUES (3, 1, 3, 3);
INSERT INTO `t_relation` VALUES (4, 1, 4, 4);
INSERT INTO `t_relation` VALUES (5, 1, 5, 5);
INSERT INTO `t_relation` VALUES (6, 1, 6, 6);
INSERT INTO `t_relation` VALUES (7, 2, 1, 1);
INSERT INTO `t_relation` VALUES (8, 2, 2, 2);
INSERT INTO `t_relation` VALUES (9, 2, 3, 3);
INSERT INTO `t_relation` VALUES (10, 2, 7, 4);
INSERT INTO `t_relation` VALUES (11, 2, 8, 5);
INSERT INTO `t_relation` VALUES (12, 2, 9, 6);
INSERT INTO `t_relation` VALUES (13, 3, 1, 1);

-- ----------------------------
-- Table structure for t_score
-- ----------------------------
DROP TABLE IF EXISTS `t_score`;
CREATE TABLE `t_score`  (
  `score_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '成绩编号',
  `stu_id` int(0) NOT NULL COMMENT '学生编号',
  `exam_id` int(0) NOT NULL COMMENT '考试编号',
  `class_id` int(0) NOT NULL COMMENT '班级编号',
  `language_score` int(0) NULL DEFAULT NULL COMMENT '语文成绩',
  `math_score` int(0) NULL DEFAULT NULL COMMENT '数学成绩',
  `english_score` int(0) NULL DEFAULT NULL COMMENT '英语成绩',
  `politics_score` int(0) NULL DEFAULT NULL COMMENT '政治成绩',
  `history_score` int(0) NULL DEFAULT NULL COMMENT '历史成绩',
  `geog_score` int(0) NULL DEFAULT NULL COMMENT '地理成绩',
  PRIMARY KEY (`score_id`) USING BTREE,
  INDEX `学生学号2`(`stu_id`) USING BTREE,
  INDEX `考试编号`(`exam_id`) USING BTREE,
  INDEX `班级编号1`(`class_id`) USING BTREE,
  CONSTRAINT `班级编号1` FOREIGN KEY (`class_id`) REFERENCES `t_class` (`class_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `考试编号1` FOREIGN KEY (`exam_id`) REFERENCES `t_exam` (`exam_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `学生编号1` FOREIGN KEY (`stu_id`) REFERENCES `t_student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 151 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_score
-- ----------------------------
INSERT INTO `t_score` VALUES (1, 20171401, 1, 1, 98, 61, 66, 87, 75, 62);
INSERT INTO `t_score` VALUES (2, 20171402, 1, 1, 100, 50, 87, 62, 85, 71);
INSERT INTO `t_score` VALUES (3, 20171403, 1, 1, 100, 92, 62, 65, 38, 59);
INSERT INTO `t_score` VALUES (4, 20171404, 1, 1, 1, 13, 57, 22, 11, 62);
INSERT INTO `t_score` VALUES (5, 20171405, 1, 1, 96, 82, 78, 34, 94, 71);
INSERT INTO `t_score` VALUES (6, 20171406, 1, 1, 66, 86, 78, 94, 43, 86);
INSERT INTO `t_score` VALUES (7, 20171407, 1, 1, 100, 11, 64, 73, 38, 20);
INSERT INTO `t_score` VALUES (8, 20171408, 1, 1, 7, 45, 52, 31, 13, 87);
INSERT INTO `t_score` VALUES (9, 20171409, 1, 1, 34, 92, 92, 64, 62, 24);
INSERT INTO `t_score` VALUES (10, 20171410, 1, 1, 13, 30, 24, 53, 50, 34);
INSERT INTO `t_score` VALUES (11, 20171411, 1, 1, 3, 54, 44, 73, 15, 56);
INSERT INTO `t_score` VALUES (12, 20171412, 1, 1, 39, 1, 45, 34, 88, 72);
INSERT INTO `t_score` VALUES (13, 20171413, 1, 1, 49, 44, 49, 43, 36, 1);
INSERT INTO `t_score` VALUES (14, 20171414, 1, 1, 49, 16, 83, 3, 75, 54);
INSERT INTO `t_score` VALUES (15, 20171415, 1, 1, 73, 68, 59, 41, 43, 40);
INSERT INTO `t_score` VALUES (16, 20171416, 1, 2, 66, 33, 20, 69, 38, 57);
INSERT INTO `t_score` VALUES (17, 20171417, 1, 2, 95, 24, 11, 75, 67, 90);
INSERT INTO `t_score` VALUES (18, 20171418, 1, 2, 66, 100, 19, 83, 30, 75);
INSERT INTO `t_score` VALUES (19, 20171419, 1, 2, 66, 72, 63, 27, 91, 62);
INSERT INTO `t_score` VALUES (20, 20171420, 1, 2, 66, 98, 92, 23, 60, 50);
INSERT INTO `t_score` VALUES (21, 20171421, 1, 2, 66, 1, 85, 30, 67, 35);
INSERT INTO `t_score` VALUES (22, 20171422, 1, 2, 79, 72, 15, 7, 84, 4);
INSERT INTO `t_score` VALUES (23, 20171423, 1, 2, 66, 32, 28, 94, 27, 61);
INSERT INTO `t_score` VALUES (24, 20171424, 1, 2, 66, 38, 73, 15, 58, 25);
INSERT INTO `t_score` VALUES (25, 20171425, 1, 2, 66, 63, 71, 97, 55, 52);
INSERT INTO `t_score` VALUES (26, 20171426, 1, 2, 67, 46, 39, 61, 9, 98);
INSERT INTO `t_score` VALUES (27, 20171427, 1, 2, 66, 14, 54, 94, 35, 17);
INSERT INTO `t_score` VALUES (28, 20171428, 1, 2, 66, 25, 43, 7, 60, 75);
INSERT INTO `t_score` VALUES (29, 20171429, 1, 2, 66, 71, 13, 75, 85, 51);
INSERT INTO `t_score` VALUES (30, 20171430, 1, 2, 66, 30, 25, 47, 34, 99);
INSERT INTO `t_score` VALUES (31, 20171401, 2, 1, 77, 46, 61, 11, 71, 23);
INSERT INTO `t_score` VALUES (32, 20171402, 2, 1, 42, 36, 98, 47, 6, 5);
INSERT INTO `t_score` VALUES (33, 20171403, 2, 1, 87, 57, 94, 96, 40, 21);
INSERT INTO `t_score` VALUES (34, 20171404, 2, 1, 82, 30, 36, 55, 53, 65);
INSERT INTO `t_score` VALUES (35, 20171405, 2, 1, 34, 26, 62, 65, 57, 77);
INSERT INTO `t_score` VALUES (36, 20171406, 2, 1, 63, 62, 76, 58, 18, 62);
INSERT INTO `t_score` VALUES (37, 20171407, 2, 1, 47, 4, 85, 16, 95, 91);
INSERT INTO `t_score` VALUES (38, 20171408, 2, 1, 85, 77, 52, 22, 7, 38);
INSERT INTO `t_score` VALUES (39, 20171409, 2, 1, 51, 20, 15, 24, 69, 15);
INSERT INTO `t_score` VALUES (40, 20171410, 2, 1, 67, 59, 12, 30, 68, 17);
INSERT INTO `t_score` VALUES (41, 20171411, 2, 1, 91, 79, 73, 66, 50, 56);
INSERT INTO `t_score` VALUES (42, 20171412, 2, 1, 43, 25, 61, 76, 93, 44);
INSERT INTO `t_score` VALUES (43, 20171413, 2, 1, 68, 78, 26, 36, 66, 68);
INSERT INTO `t_score` VALUES (44, 20171414, 2, 1, 44, 17, 65, 13, 87, 89);
INSERT INTO `t_score` VALUES (45, 20171415, 2, 1, 89, 34, 21, 45, 15, 84);
INSERT INTO `t_score` VALUES (46, 20171416, 2, 2, 82, 34, 75, 7, 96, 76);
INSERT INTO `t_score` VALUES (47, 20171417, 2, 2, 27, 96, 95, 58, 5, 82);
INSERT INTO `t_score` VALUES (48, 20171418, 2, 2, 38, 54, 88, 5, 73, 100);
INSERT INTO `t_score` VALUES (49, 20171419, 2, 2, 76, 23, 24, 1, 16, 2);
INSERT INTO `t_score` VALUES (50, 20171420, 2, 2, 57, 63, 73, 14, 76, 50);
INSERT INTO `t_score` VALUES (51, 20171421, 2, 2, 6, 82, 50, 21, 80, 57);
INSERT INTO `t_score` VALUES (52, 20171422, 2, 2, 45, 46, 40, 92, 78, 47);
INSERT INTO `t_score` VALUES (53, 20171423, 2, 2, 20, 40, 35, 32, 82, 81);
INSERT INTO `t_score` VALUES (54, 20171424, 2, 2, 79, 82, 6, 66, 34, 24);
INSERT INTO `t_score` VALUES (55, 20171425, 2, 2, 80, 99, 34, 54, 67, 42);
INSERT INTO `t_score` VALUES (56, 20171426, 2, 2, 48, 97, 93, 43, 21, 13);
INSERT INTO `t_score` VALUES (57, 20171427, 2, 2, 2, 59, 18, 16, 67, 11);
INSERT INTO `t_score` VALUES (58, 20171428, 2, 2, 13, 33, 96, 44, 6, 29);
INSERT INTO `t_score` VALUES (59, 20171429, 2, 2, 77, 88, 22, 74, 76, 22);
INSERT INTO `t_score` VALUES (60, 20171430, 2, 2, 13, 86, 78, 65, 2, 37);

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student`  (
  `stu_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '学生学号',
  `stu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生姓名',
  `stu_sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '学生性别',
  `stu_tel` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生电话号码',
  `stu_emial` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生邮箱',
  `stu_identity` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生身份证号码',
  `stu_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '123456' COMMENT '学生密码',
  `class_id` int(0) NOT NULL COMMENT '学生班级',
  PRIMARY KEY (`stu_id`) USING BTREE,
  INDEX `班级id`(`class_id`) USING BTREE,
  CONSTRAINT `班级id` FOREIGN KEY (`class_id`) REFERENCES `t_class` (`class_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES (20171401, '雷文', '女', '17661956607', 'meta.frami@hotmail.com', '71795320980727591x', '12', 1);
INSERT INTO `t_student` VALUES (20171402, '高博超', '女', '15891608438', 'ernie.gaylord@yahoo.com', '46459920371229809x', '123456', 1);
INSERT INTO `t_student` VALUES (20171403, '韦立诚', '男', '17056097617', 'reggie.smitham@hotmail.com', '82945020681008543x', '123456', 1);
INSERT INTO `t_student` VALUES (20171404, '邓胤祥', '女', '13964861747', 'vida.hahn@gmail.com', '49586219070321828x', '123456', 1);
INSERT INTO `t_student` VALUES (20171405, '唐鑫鹏', '女', '15731697368', 'johnnie.hoppe@gmail.com', '60074120881107929x', '12345', 1);
INSERT INTO `t_student` VALUES (20171406, '刘楷瑞', '男', '17096628551', 'wilfredo.lemke@gmail.com', '41983018890620666X', '123456', 1);
INSERT INTO `t_student` VALUES (20171407, '龚胤祥', '男', '14747390901', 'kara.kirlin@yahoo.com', '74764120710330655X', '123456', 1);
INSERT INTO `t_student` VALUES (20171408, '姚涛', '女', '14531496810', 'andrea.ebert@yahoo.com', '16054620530819467x', '123456', 1);
INSERT INTO `t_student` VALUES (20171409, '曹志泽', '女', '15232545132', 'hannah.hoeger@gmail.com', '274524201811300323', '123456', 1);
INSERT INTO `t_student` VALUES (20171410, '邓思源', '男', '14774411073', 'leif.skiles@hotmail.com', '208930196912011927', '123456', 1);
INSERT INTO `t_student` VALUES (20171411, '尹嘉熙', '男', '17892435249', 'cecil.terry@yahoo.com', '656574192902193369', '123456', 1);
INSERT INTO `t_student` VALUES (20171412, '侯锦程', '男', '17677841416', 'magaly.hackett@hotmail.com', '867014200411202496', '123456', 1);
INSERT INTO `t_student` VALUES (20171413, '雷昊天', '男', '15182729964', 'willian.zemlak@hotmail.com', '37239420780508838X', '123456', 1);
INSERT INTO `t_student` VALUES (20171414, '丁展鹏', '女', '15383164418', 'caterina.kozey@hotmail.com', '982317204911313945', '123456', 1);
INSERT INTO `t_student` VALUES (20171415, '赵瑞霖', '女', '18521163079', 'carroll.beier@yahoo.com', '94396420041008780X', '123456', 1);
INSERT INTO `t_student` VALUES (20171416, '覃苑博', '女', '15585118817', 'susanna.wehner@hotmail.com', '759636192111179030', '123456', 2);
INSERT INTO `t_student` VALUES (20171417, '吴鸿煊', '女', '15585162744', 'herb.powlowski@yahoo.com', '26653618970803078x', '123456', 2);
INSERT INTO `t_student` VALUES (20171418, '周彬', '女', '17736461366', 'shenika.simonis@gmail.com', '66808518741206140X', '123456', 2);
INSERT INTO `t_student` VALUES (20171419, '方博文', '男', '18147692009', 'hana.abbott@yahoo.com', '97758218911130817X', '123456', 2);
INSERT INTO `t_student` VALUES (20171420, '蒋振家', '男', '15829044994', 'sandee.wisoky@hotmail.com', '39082818170506214X', '123456', 2);
INSERT INTO `t_student` VALUES (20171421, '梁天宇', '男', '14505149351', 'bradley.bergstrom@gmail.com', '82944820161029278x', '123456', 2);
INSERT INTO `t_student` VALUES (20171422, '马修杰', '男', '15234018633', 'len.rath@yahoo.com', '13645320620620677X', '123456', 2);
INSERT INTO `t_student` VALUES (20171423, '吕旭尧', '男', '17335813528', 'divina.bechtelar@hotmail.com', '20712818691101941X', '123456', 2);
INSERT INTO `t_student` VALUES (20171424, '钟志泽', '男', '17319470941', 'sona.brown@yahoo.com', '75165119370325457X', '123456', 2);
INSERT INTO `t_student` VALUES (20171425, '邱笑愚', '女', '18996522193', 'marvin.pacocha@gmail.com', '394449207801064943', '123456', 2);
INSERT INTO `t_student` VALUES (20171426, '胡语堂', '男', '18529666990', 'hailey.labadie@hotmail.com', '657399208901175466', '123456', 2);
INSERT INTO `t_student` VALUES (20171427, '杜鹏煊', '女', '14794314921', 'rosario.jacobs@hotmail.com', '83805018700209987x', '123456', 2);
INSERT INTO `t_student` VALUES (20171428, '蔡笑愚', '女', '13177832901', 'anne.kertzmann@hotmail.com', '73409418621030447x', '123456', 2);
INSERT INTO `t_student` VALUES (20171429, '赵熠彤', '女', '15276141670', 'julienne.abshire@gmail.com', '72639219450304051X', '123456', 2);
INSERT INTO `t_student` VALUES (20171430, '马懿轩', '女', '14592031227', 'krishna.dooley@gmail.com', '25304219360331122X', '123456', 2);
INSERT INTO `t_student` VALUES (20171431, '张三', '男', '18719273866', 'merrill.mills@hotmail.com', '430482200012128975', '1234', 3);

-- ----------------------------
-- Table structure for t_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher`  (
  `tea_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '教师编号',
  `tea_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '教师姓名',
  `tea_tel` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教师电话号码',
  `tea_emial` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教师邮件',
  `tea_identity` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '教师身份证号码',
  `tea_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '123456' COMMENT '教师密码',
  PRIMARY KEY (`tea_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_teacher
-- ----------------------------
INSERT INTO `t_teacher` VALUES (1, '魏鹏煊', '18719273866', 'merrill.mills@hotmail.com', '19345920641105986X', '1234');
INSERT INTO `t_teacher` VALUES (2, '袁荣轩', '14570202427', 'meghan.harvey@hotmail.com', '32269920571005151x', '123456');
INSERT INTO `t_teacher` VALUES (3, '姜天磊', '15962448115', 'caleb.hintz@hotmail.com', '102187206010034853', '123456');
INSERT INTO `t_teacher` VALUES (4, '郭志泽', '15783319816', 'dudley.roberts@hotmail.com', '219274204607307877', '123456');
INSERT INTO `t_teacher` VALUES (5, '谭雪松', '15154031833', 'evangeline.lang@hotmail.com', '313944182910305556', '123456');
INSERT INTO `t_teacher` VALUES (6, '熊致远', '17180236147', 'haywood.hilpert@hotmail.com', '32444718341104202x', '123456');
INSERT INTO `t_teacher` VALUES (7, '潘彬', '15230453968', 'bud.macejkovic@hotmail.com', '947774207105308729', '123456');
INSERT INTO `t_teacher` VALUES (8, '宋驰', '17003812534', 'jamar.hamill@yahoo.com', '64337520351017806x', '123456');
INSERT INTO `t_teacher` VALUES (9, '孟瑞霖', '17629786329', 'sherly.abshire@gmail.com', '734158182612314026', '123456');
INSERT INTO `t_teacher` VALUES (10, '测试修改', '15302954886', '测试修改', '152407198010303837', '1');

SET FOREIGN_KEY_CHECKS = 1;
