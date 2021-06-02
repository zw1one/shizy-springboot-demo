# shizy-springboot-demo

shizy-springboot-demo

## quick start

* http://127.0.0.1/swagger-ui.html

### 启动出现问题

* IDEA的Active profiles处，添加`dev`
* 查看`application-dev.yml`，检查MySQL、Redis

### MySQL建库建表

```
CREATE DATABASE test;
USE test;
CREATE TABLE `user`  (
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_account` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

INSERT INTO `user` VALUES ('d78b3c1604514a33870c319a31043e6e', 'string', 'string');
INSERT INTO `user` VALUES ('e8acb754f423475eb166981a60c9c037', 'string', 'string');

```

### 打包部署

* Maven打包命令
```
mvn clean package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Pdev
mvn clean package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Prelease
```

打包完idea启动项目报错，Rebuild project或者mvn clean

* jar包运行命令
```
(java -server -Xmx1024m -Xms256m -XX:+UseParallelGC -XX:ParallelGCThreads=20  -jar Shizy-SpringBoot-Demo-1.0-SNAPSHOT.jar &)
```

<br>
<br>
<br>
<br>
<br>
<br>

### todo

- update.xml
  - mybatis-plus 多数据源支持
  
- ? 自定义@QueryParam注解来构建Wrappers查询条件

- 代码生成器 根据表生成
  - × 哪些部分是要生成的？
  
  
- 动态更新xml里面的sql 
http://www.yihaomen.com/article/java/681.htm

