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