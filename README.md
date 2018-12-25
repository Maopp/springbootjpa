# springbootjpa

    <!--spring data jpa-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!--mysql-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>

# SpringBoot使用SpringDataJPA完成CRUD

## 学习目标:学习并且使用SpringBoot访问MySQL数据库，并且结合SpringDataJPA完成CRUD（Create,Read,Update,Delete）简单操作。

## 配置数据源以及JPA：
    ```
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/springdata_jpa?characterEncoding=utf8
        driverClassName: com.mysql.jdbc.Driver
        username: root
        password: admin
    jpa:
      database: MySQL
      show-sql: true
      hibernate:
        naming_strategy: org.hibernate.cfg.ImprovedNamingStrategy
    ```

### 创建JPA：
    ```
    public interface UserJPA extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity>, Serializable {
    }
    ```
    UserJPA继承了JpaRepository接口（SpringDataJPA提供的简单数据操作接口）、
    JpaSpecificationExecutor（SpringDataJPA提供的复杂查询接口）、Serializable（序列化接口）

    SpringDataJPA内部使用了类代理的方式让继承了它接口的子接口都以spring管理的Bean的形式存在，也就是说我们可以直接使用
    @Autowired注解在spring管理bean使用

## 总结：
    userJPA.save方法可以执行添加也可以执行更新，如果需要执行持久化的实体存在主键值则更新数据，如果不存在则添加数据。


------------------------------------------------------------------------------------------------------------------------
# SpringBoot实战SpringDataJPA：
SpringDataJPA是Spring Data的一个子项目，通过提供基于JPA的Repository极大的减少了JPA作为数据访问方案的代码量，仅仅需要编写
一个接口集成下SpringDataJPA内部定义的接口即可完成简单的CRUD操作

## 使用Druid数据源：
    ```
    spring:
      datasource:
        type: com.alibaba.druid.pool.DruidDataSource
        url: jdbc:mysql://localhost:3306/springdata_jpa?characterEncoding=utf8
        driverClassName: com.mysql.jdbc.Driver
        username: root
        password: admin
        # 最大活跃数
        maxActive: 20
        # 初始化数量
        initialSize: 1
        # 最大链接等待超时时间
        maxWait: 60000
        # 打开PSCache，并且指定每个连接PSCache的大小
        poolPreparedStatements: true
        maxPoolPreparedStatementPerConnectionSize: 20
        # 通过connectionProperties属性来打开mergeSql功能；慢sql记录
        # connectionProperties：druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
        minldel: 1
        timeBetweenEvictionRunsMillis: 60000
        minEvictableIdleTimeMillis: 300000
        validationQuery: select 1 from dual
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
        # 配置监控统计拦截的filters，去掉后监控界面sql将无法统计，“wall”用于防火墙
        filters: stat,wall,log4j
    jpa:
      properties:
        hibernate:
          show_sql: true
          format_sql: true
    ```

## @Query注解自定义SQL：
- @Query(value = "select * from user where age > ?1", nativeQuery = true)
    @Query是用来配置自定义SQL的注解，后面参数nativeQuery = true才是表明了使用原生的sql，如果不配置，默认是false，则使用
    HQL查询方式

- @Query配合@Modifying：
    从名字上可以看到@Query注解好像只是用来查询的，但是如果配合@Modifying注解一共使用，则可以完成数据的删除、添加、更新操作。
    ```
    @Modifying
    @Query(value = "delete from user where nickname = ?1", nativeQuery = true)
    @Transactional
    ```

## 自定义BaseRepository：
    @NoRepositoryBean：这个注解如果配置在继承了JpaRepository接口以及其他SpringDataJpa内部的接口的子接口时，子接口不被作
    为一个Repository创建代理实现类。

## 分页查询、排序：