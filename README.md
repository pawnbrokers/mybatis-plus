# 1. 什么是MyBatis-plus

![image-20200519151120687](https://i.loli.net/2020/05/19/Fvtz5MIe2GAL7gk.png)

## 1.1 特性

- **无侵入**：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
- **损耗小**：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作
- **强大的 CRUD 操作**：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
- **支持 Lambda 形式调用**：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错
- **支持主键自动生成**：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题
- **支持 ActiveRecord 模式**：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作
- **支持自定义全局通用操作**：支持全局通用方法注入（ Write once, use anywhere ）
- **内置代码生成器**：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用
- **内置分页插件**：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询
- **分页插件支持多种数据库**：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer 等多种数据库
- **内置性能分析插件**：可输出 Sql 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询
- **内置全局拦截插件**：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误操作

## 1.2 支持数据库

- mysql 、 mariadb  、  oracle  、  db2  、  h2  、  hsql  、  sqlite  、  postgresql  、  sqlserver
- 达梦数据库  、 虚谷数据库  、  人大金仓数据库



# 2. 快速入门

## 2.1 步骤

>1. 建立数据库表
>2. 新建SpringBoot工程兵并引入依赖
>
>3. 配置application.yml
>4. 在启动类添加注解
>5. 编写实体类
>6. 编写mapper类

## 2.2 数据库表

![image-20200519151812513](https://i.loli.net/2020/05/19/YzhVtWfFrI2REMK.png)

建库建表语句：

```sql
DROP TABLE IF EXISTS USER;

CREATE TABLE USER
(
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	NAME VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	PRIMARY KEY (id)
);

DELETE FROM USER;

INSERT INTO USER (id, NAME, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
`mybatis_plus``user`
```

## 2.3 新建SpringBoot项目

需要的依赖主要有：Spring Boot父工程，Spring Boot启动器，Spring Boot测试，`mybatis-plus-boot-starter`、`lombok`、`Druid`、`mysql驱动` 依赖：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.0.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.yuan</groupId>
    <artifactId>mybatis-plus-demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>mybatis-plus-demo</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.0.5</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.10</version>
        </dependency>
		 <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.20</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

## 2.4 application.yml

```yml
# mysql5与8 8不同。，需要添加时区
# 数据库连接日志
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/mybatisplus?serverTimezone=UTC&userUnicode=true&characyerEncoding=utf-8&userSSL=false
    username: root
    password: root
```

## 2.5 在启动类添加注解

```java

@SpringBootApplication
@MapperScan(basePackages = "com.yuan.mapper")
public class MybatisPlusDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusDemoApplication.class, args);
    }
}
```

## 2.6 编写实体类

```java
/**
 * @author yuan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//开启链式编程
public class User implements Serializable {
    private Long id;
    private String name;
    private int age;
    private String email;
}

```

## 2.7 编写mapper

```java
/**
 * @author yuan
 */
@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {
}
```

## 2.8 测试

```java
@SpringBootTest
class MybatisPlusDemoApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {

        User user = userMapper.selectById(1L);
        System.out.println(user);

    }
}
```

![image-20200519153037406](https://i.loli.net/2020/05/19/6HXnmW8qgIKTib3.png)





# 3. 配置日志

> 在applicaton.yml中开启日志即可

```yml
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

**再次测试：**

![image-20200519153251397](https://i.loli.net/2020/05/19/VMAsBuJnw4IvGpr.png)



# 4. CRUD接口

## 4.1 Insert操作

```java
 @Test
    public void testInsert(){
        User user = new User();
        user
                .setName("yuantangbo")
                .setAge(24)
                .setEmail("yuantb@yeah.net");
        userMapper.insert(user);
    }
```

![image-20200519153854942](https://i.loli.net/2020/05/19/BTkhzqZ35NWx2u1.png)

我们并没有插入id，但是他自动给我们插入了一个id

**数据库插入的id的默认值为全局唯一的id**

### 4.1.1 主键生成策略

> 默认ID_WORKER 全局唯一id

**雪花算法：**
snowflake是Twitter开源的分布式ID生成算法，结果是一个long型的ID。其核心思想是：使用41bit作为
毫秒数，10bit作为机器的ID（5个bit是数据中心，5个bit的机器ID），12bit作为毫秒内的流水号（意味
着每个节点在每毫秒可以产生 4096 个 ID），最后还有一个符号位，永远是0。可以保证几乎全球唯
一！  

> 主键自增

我们需要配置主键自增：

1. 在实体类字段上加上`@TableId(type = IdType.AUTO)  `

2. 数据库的字段一定要是支持自增的

![image-20200519154332747](https://i.loli.net/2020/05/19/LZNSd9se4DBMURx.png)

![image-20200519154446833](https://i.loli.net/2020/05/19/WuOTbC9VjX2cwrG.png)

![image-20200519155152531](https://i.loli.net/2020/05/19/ChapUeLWVTvDirH.png)

成功执行了自增操作

> 其他的主键生成策略

```java
public enum IdType {
    AUTO(0), // 数据库id自增
    NONE(1), // 未设置主键
    INPUT(2), // 手动输入
    ID_WORKER(3), // 默认的全局唯一id
    UUID(4), // 全局唯一id uuid
    ID_WORKER_STR(5); //ID_WORKER 字符串表示法
}
```



## 4.2 Update操作

![image-20200519155321150](https://i.loli.net/2020/05/19/eaJzLBrflOGFW6K.png)

我们先把数据库表修改回6，7这样的自增

```java
@Test
    public void testUpdate(){
        User user = new User();
        user
                .setId(6L)
                .setAge(99)
                .setName("pawnbrokers");
        int i = userMapper.updateById(user);
        System.out.println(i);
    }
```

![image-20200519155601197](https://i.loli.net/2020/05/19/IhoHCpDjv4EzT6n.png)

所有的SQL都是动态帮助我们配置完成的。



## 4.3 自动填充

**创建时间、修改时间！**这些个操作一遍都是自动化完成的，我们不希望手动更新！
阿里巴巴开发手册：所有的数据库表：gmt_create、gmt_modified几乎所有的表都要配置上！而且需
要自动化！  

> 1. 数据库新增字段
> 2. 实体类新增字段
> 3. 实体类字段添加注解
> 4. 编辑处理器来处理这个注解

1. 数据库添加字段![image-20200519155924885](https://i.loli.net/2020/05/19/bTsCqBgh1fRed7S.png)

2. 实体类新增字段并添加注解

```java
 @TableField(fill = FieldFill.INSERT)
    private Date create_time;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date update_time;
```

3.编辑处理器来处理这个注解

```java
package com.yuan.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component//别忘了加到容器中
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时候的填充策略
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ............");
        // setFieldValByName(String fieldName, Object fieldVal, MetaObjectmetaObject
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);

    }

    /**
     * 更新时候的填充策略
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ............");
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}
```

4. 插入测试

![image-20200519160722034](https://i.loli.net/2020/05/19/jD8psJEmFNwe5ad.png)

![image-20200519161139125](https://i.loli.net/2020/05/19/yAmnzwcVFti8LJp.png)

**实现了主键自增和创建时间更新时间的自动填充**

5. 更新测试

![image-20200519161308257](https://i.loli.net/2020/05/19/EPhq8V1dJgRLObF.png)

![image-20200519161317725](https://i.loli.net/2020/05/19/ukqzJVcpIe2rGxU.png)

**实现了更新时间的自动填充。**



## 4.4 乐观锁

在面试过程中，我们经常会被问道乐观锁，悲观锁！这个其实非常简单！

> 在面试过程中，我们经常会被问道乐观锁，悲观锁！这个其实非常简单！
> 乐观锁 : 顾名思义十分乐观，它总是认为不会出现问题，无论干什么不去上锁！如果出现了问题，
> 再次更新值测试
> 悲观锁：顾名思义十分悲观，它总是认为总是出现问题，无论干什么都会上锁！再去操作！

我们这里主要讲解 乐观锁机制！
乐观锁实现方式：**（一定要先查询）**

+ **取出记录时，获取当前 version**
+ 更新时，带上这个version
+ 执行更新时， set version = newVersion where version = oldVersion
+ 如果version不对，就更新失败  

```sql
乐观锁：1、先查询，获得版本号 version = 1
-- A
update user set name = "kuangshen", version = version + 1
where id = 2 and version = 1
-- B 线程抢先完成，这个时候 version = 2，会导致 A 修改失败！
update user set name = "kuangshen", version = version + 1
where id = 2 and version = 1
```

> 1. 数据库新增字段
> 2. 实体类新增字段并添加注解
> 3. 注册组件
> 4. 测试

1. 数据库新增字段![image-20200519161726329](https://i.loli.net/2020/05/19/6wAND5nZi2oIYvg.png)

2. 实体类新增字段并添加注解

```java
@Version
private int version;
```

3. 注册组件

```java
/**
 * @author yuan
 */
@Configuration
@EnableTransactionManagement
public class MyBatisPlusConfig {
    
    @Bean//注册乐观锁组件
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }
}
```

4. 正常测试

![image-20200519162422129](https://i.loli.net/2020/05/19/fX8avlKMsDgjJR2.png)

![image-20200519162438091](https://i.loli.net/2020/05/19/Ed41M7LYvp6ubFI.png)**版本号变成了2**

4. 模拟其他线程打扰

```java
@Test
    public void testOptimisticLocker2(){
        User user = userMapper.selectById(8L);
        user
                .setName("faker")
                .setAge(23)
                .setEmail("skt@skt.com");
        //此时还未进行修改，却被其他线程插入
        User user1 = userMapper.selectById(8L);
        user1
                .setName("faker111")
                .setAge(111)
                .setEmail("111111@skt.com");
        //此时先执行了后续线程的更新操作
        userMapper.updateById(user1);

        userMapper.updateById(user);
    }
```



![image-20200519162820733](https://i.loli.net/2020/05/19/NTmMECvxX7P48Le.png)

我们可以看到两次更新只有第一次是执行成功的**，第二次的更新由于版本号的存在并未成功。**

![image-20200519162923837](https://i.loli.net/2020/05/19/sT4UaX1flodMc79.png)



## 4.5 查询操作

### 4.5.1 单个查询

![image-20200519163105714](https://i.loli.net/2020/05/19/Pw2vsoAQ9OayY8n.png)

### 4.5.2 全部查询

![image-20200519163242165](https://i.loli.net/2020/05/19/WakI4lCrYQegbXR.png)

### 4.5.3 批量查询

![image-20200519163434991](https://i.loli.net/2020/05/19/aftGdQ8IriuNgKp.png)

自动拼接了`where id in (?,?,?,?)`

### 4.5.4 Map查询（字段查询）

![image-20200519163846688](https://i.loli.net/2020/05/19/QYVUsewJSAOua8j.png)

**相当于执行了动态SQL的自动拼接**



## 4.6 分页查询

MyBatis-Plus内置了分页插件

> 1. 配置拦截器逐渐
> 2. 直接使用page对象

1. 注册分页组件

```java
 @Bean//注册分页组件
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
```

2. 直接使用page对象

```java
 @Test
    public void testPage() {
        /**
         * 参数1： 当前是第一页
         * 参数2：一页显示多少条记录
         */
        Page<User> page = new Page<>(2, 5);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }
```

![image-20200519164451325](https://i.loli.net/2020/05/19/oDzEmZ13AVkcR2B.png)

我们可以看到每次他都是先使用`count(1)`查询出全部的记录数量，然后同样使用limit5，5来进行输出



## 4.7 删除操作

删除操作同样有

+ 根据id
+ 批量删除
+ 根据map删除



## 4.8 逻辑删除

> **物理删除 ：**从数据库中直接移除
> **逻辑删除 ：**再数据库中没有被移除，而是通过一个变量来让他失效！ deleted = 0 => deleted = 1  

**步骤：**

> 1. 数据库添加字段
> 2. 实体类添加属性并添加注解
> 3. 配置逻辑删除组件，并在yaml中配置逻辑删除
> 4. 测试

1. 数据库添加字段，设置默认值为0，表示未删除

![image-20200519164917460](https://i.loli.net/2020/05/19/bNvS3puQ5TfdnqC.png)

2. 实体类配置属性并添加注解

```java
@TableLogic
private int deleted;
```

3. 配置逻辑删除组件并在yml中配置

```java
@Bean //配置逻辑删除组件
    public ISqlInjector iSqlInjector() {
        return new LogicSqlInjector();
    }
```

**application.yml中配置未删除为0，删除为1**

![image-20200519165207705](https://i.loli.net/2020/05/19/19xQadmNfhnucHM.png)

4. 测试删除

![image-20200519165354850](https://i.loli.net/2020/05/19/d9Df1s42FPu63yX.png)

我们发现此时的删除走的是**更新操作**。

![image-20200519165454021](https://i.loli.net/2020/05/19/xi2vhO41kqBTZWn.png)



这个时候我们**再次测试查询：**

![image-20200519165529064](https://i.loli.net/2020/05/19/2RS46BAE7agCVkp.png)

这个时候是查询不到的，**因为他会选择deleted=0的字段进行查询**。



# 5. 性能分析插件

我们在平时的开发中，会遇到一些慢sql。测试！ druid,,,,,
作用：性能分析拦截器，用于输出每条 SQL 语句及其执行时间
MP也提供性能分析插件，如果超过这个时间就停止运行。  

使用步骤：

> 1. 导入插件
> 2. 测试使用

1. 导入插件

```java
/**
 * SQL执行效率插件
 */
@Bean
//@Profile({"dev", "test"})// 设置 dev test 环境开启，保证我们的效率
public PerformanceInterceptor performanceInterceptor() {
    PerformanceInterceptor performanceInterceptor = new
            PerformanceInterceptor();
    performanceInterceptor.setMaxTime(100); // ms设置sql执行的最大时间，如果超过了则不执行
    performanceInterceptor.setFormat(true); // 是否格式化代码
    return performanceInterceptor;
}
```

2. 测试使用

![image-20200519165916085](https://i.loli.net/2020/05/19/1HSeguEcpAbZrNv.png)

这里就不能查到已经被逻辑删除的记录，并且会输出sql执行的时间。



## 6. 条件构造器

**十分重要：Wrapper**
我们写一些复杂的sql就可以使用它来替代！  

![image-20200519170051198](https://i.loli.net/2020/05/19/MeNwzyERiKoDVnv.png)

## 6.1 test1

```java
/**
     * 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于30
     */
    @Test
    public void test1() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .isNotNull("name")
                .isNotNull("email")
                .ge("age", 90);
        userMapper.selectList(wrapper).forEach(System.out::println);

    }
```

![image-20200519170607803](https://i.loli.net/2020/05/19/Emr6lUyjGcRkJnq.png)

实现了SQL的动态拼接。



## 6.2 类比Map

```java
@Test
    public void testMap() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yuan");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }
```

![image-20200519170842258](https://i.loli.net/2020/05/19/UmOLyz9rZ7HiBkv.png)



## 6.3 测试区间

```java
/**
     * 测试区间
     */
    @Test
    public void testBetween() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age", 18, 25);
        userMapper.selectList(wrapper).forEach(System.out::println);
    }
```

![image-20200519171134949](https://i.loli.net/2020/05/19/7UgBASx8tuFhqNy.png)



## 6.4 模糊查询

```java
  /**
     * 测试模糊查询
     * email中含有dou
     * 名字J%
     */
    @Test
    public void testLike() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .like("email", "dou")
                .likeLeft("name", "J");
        userMapper.selectList(wrapper).forEach(System.out::println);
    }
```

![image-20200519171621408](https://i.loli.net/2020/05/19/wu1UpFhBXeHEa3o.png)



## 6.5 子查询

```java

    @Test
    public void testSQL() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.inSql("id", "select id from user where id <5");
        userMapper.selectList(wrapper).forEach(System.out::println);
    }
```

![image-20200519171933135](https://i.loli.net/2020/05/19/IbmfnFlrpMjaYOU.png)



## 6.6 order by

```java
 @Test
    public void testOrderBy() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        userMapper.selectList(wrapper).forEach(System.out::println);
    }
```

![image-20200519172157527](https://i.loli.net/2020/05/19/vQE15W9sOtNGUwC.png)