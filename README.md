# shizy-springboot-demo

shizy-springboot-demo

## quick start

* <a>http://127.0.0.1/swagger-ui.html</a>

### 启动出现问题

* IDEA的Active profiles处，添加`dev`
* 查看`application-dev.yml`，检查MySQL、MongoDB、Redis

### MySQL建库建表

```
CREATE DATABASE test;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_account` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('d78b3c1604514a33870c319a31043e6e', 'string', 'string');
INSERT INTO `user` VALUES ('e8acb754f423475eb166981a60c9c037', 'string', 'string');

SET FOREIGN_KEY_CHECKS = 1;
```

### 打包部署

* Maven打包命令
```
mvn clean package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Pdev
```
* jar包运行命令
```
java -server -Xmx1024m -Xms256m -XX:+UseParallelGC -XX:ParallelGCThreads=20  -jar Shizy-SpringBoot-Demo-1.0-SNAPSHOT.jar &
```