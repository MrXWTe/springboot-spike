## 项目技术要点介绍

- 前端：Thymeleaf；Bootstrap；jQuery
- 后端：Spring Boot；JSR303；MyBatis
- 中间件：RabbitMQ；Redis；Druid
- 开发工具：IEDA，Maven



## 秒杀系统内容简介

- 分布式会话
- 商品列表页
- 商品详情页
- 订单详情页
- 系统压测
- 缓存优化
- 消息队列
- 接口安全



## 第一章：项目框架搭建

#### 1、Spring Boot 环境搭建

本次项目使用Intellij IDEA搭建 SpringBoot 工程，在New Project中直接选择Spring Initializr进行工程创建，起初只要选择web模块就OK，之后需要其他模块可以直接在pom文件中导入依赖即可。如果没有选择web模块则在pom文件中添加以下依赖：

```xml
<!--web工程相关依赖-->
<dependency>
	<groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

接着在启动类目录下创建一个controller测试工程是否成功搭建：

```java
@Controller
@RequestMapping("/demo")
public class SampleController {
    @RequestMapping("/")
    @ResponseBody
    public String home(){
        return "Hello World";
    }
}
```



#### 2、集成Thymeleaf、Restful结果封装

集成Thymeleaf只需要在pom工程中导入如下依赖：

```xml
<!--网页模板引擎-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

在application.properties（或者application.yaml）文件下添加相关配置：在这里需要注意prefix与suffix这两个参数，设置了这两个参数，我们便能够指定某些文件称为模板文件

```properties
# THYMELEAF (ThymeleafAutoConfiguration)
spring.thymeleaf.cache=false	//是否开启模板引擎缓存
spring.thymeleaf.enabled=true	
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.mode=HTML
spring.thymeleaf.prefix=classpath:/templates/       //将该文件夹下的代码转化成模板代码
spring.thymeleaf.suffix=.html						//将该后缀的代码转化成模板代码
```

最后在需要模板化的html文件的头部添加如下代码：

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org">
```



#### 3、集成Restful结果封装

controller的结果返回一般有两种：1）、String字符串，这种返回的一般是html的文件名，即直接返回网页进行渲染；2）、Json字符串，这种返回一般是对象进行Json化后的字符串。

由于前后端，上述第二种返回结果的使用逐渐增加，本次封装便是基于此结果进行的。

首先我们将返回结果定义为如下形式的Json串形式：并提前定义错误代码与错误信息。比如500100对应服务端异常。

```json
{
    "code":"如果code=0，则取出data的数据，如果code!=0，则取出对应的错误信息",
    "msg":"对应code的错误信息",
    "data":"当code=0时，被取出"
}
```

将该Json串封装成Java类，在类中添加两个类方法`success()`，`error()`。每次controller成功返回调用`success()`方法，失败返回`error()`方法。

```java
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;	
	/**
     * 成功时调用
     * @param data 返回数据
     * @param <T> 类型
     * @return Result结果
     */
    public static <T> Result<T> success(T data){
        return new Result<>(data);
    }
	private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    /**
     * 失败时调用
     * @param cm CodeMsg对象，存储错误信息和错误代码
     * @param <T> 类型
     * @return Result对象
     */
    public static <T> Result<T> error(CodeMsg cm) {
        return new Result<T>(cm);
    }
	private Result(CodeMsg cm){
        if(cm == null) return;

        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }
    get&set。。。
}
```

失败方法中调用的`CodeMsg`对象**封装了错误代码和错误信息**，将restful结果对象与错误代码对象分离，十分便于管理。比如以后要添加登录模块错误信息只需要在`CodeMsg`对象中添加一个`public static final CodeMsg`变量即可。

```java
package cn.mrxu.spike.result;

public class CodeMsg {
    private int code;
    private String msg;
    // 通用异常
    public static final CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");

    // 登录模块 5002XX
    // 商品模块 5003XX
    // 订单模块 5004XX
    // 秒杀模块 5005XX
    /********************************constructor******************************************/
    /********************************get&set******************************************/

}
```

controller调用过程，返回结果保持一致，都是`Result`对象，前端获取该对象json化后的字符串只需要判断code代码便可以执行相对应的操作

```java
	@RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        return Result.success("hello, mrxu");
    }


    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }
```

#### 4、集成MyBatis与Druid

添加依赖

```xml
<!--mybatis 相关依赖-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.0.1</version>
</dependency>

<!--mysql数据库依赖-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.13</version>
</dependency>

<!--druid数据库连接池-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.0.5</version>
</dependency>
```

添加配置文件

```properties
# MYBATIS
mybatis.type-aliases-package=cn.mrxu.spike.domain
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.configuration.default-fetch-size=100
mybatis.configuration.default-statement-timeout=3000
mybatis.mapper-locations=classpath:cn/mrxu/spike/dao/*xml


# DRUID
spring.datasource.url=jdbc:mysql://localhost:3306/spike?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.filters=stat
spring.datasource.maxActive=2
spring.datasource.initialSize=1
spring.datasource.maxWait=60000
spring.datasource.minIdle=1
spring.datasource.timeBetweenEvictionRunsMillis=60000
spring.datasource.minEvictableIdleTimeMillis=300000
spring.datasource.validationQuery=select 1 from user
spring.datasource.testWhileIdle=true
spring.datasource.testOnBorrow=false
spring.datasource.testOnReturn=false
spring.datasource.poolPreparedStatements=true
spring.datasource.maxOpenPreparedStatements=20
```

#### 5、集成jedis+redis，封装通用缓存key



