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


------------------------------------------------------------------------------------------------------------------------
# 使用SpringSecurity让SpringBoot项目更安全：
- SpringSecurity是专门针对基于Spring项目的安全框架，充分利用了依赖注入和AOP来实现安全管控。在很多大型企业级系统中权限是
最核心的部分，一个系统的好与坏全都在于权限管控是否灵活，是否颗粒化。在早期的SpringSecurity版本中我们需要大量的xml来进行
配置，而基于SpringBoot整合SpringSecurity框架相对而言简直是重生了，简单到不可思议的地步。

- SpringSecurity框架有两个概念**认证**和**授权**，认证可以访问系统的用户，而授权则是用户可以访问的资源

## 学习目标：在SpringBoot项目中使用SpringSecurity安全框架实现用户认证以及授权访问。

## 实现UserDetails接口：
```
    public class UserEntity extends BaseEntity implements Serializable, UserDetails
    ...
```
- UserDetails是SpringSecurity验证框架内部提供的用户验证接口，需要用到UserEntity来完成自定义用户认证功能，需要实现
getAuthorities方法内容，将定义的角色列表添加到授权的列表内。

## 自定义SpringSecurity用户认证
- 实现SpringSecurity内的UserDetailsService接口来完成自定义查询用户的逻辑
```
    public class UserService implements UserDetailsService {

        @Autowired
        private UserJPA userJPA;
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            UserEntity user = userJPA.findByUsername(username);
            if (null == user) {
                throw new UsernameNotFoundException("未查询到用户：" + username + "信息");
            }
            return user;
        }
    }
```

## 配置SpringSecurity
```
    @Configuration
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        /**
         * 自定义认证实体注入
         */
        @Bean
        UserDetailsService userService() {
            return new UserService();
        }
        @Override
        public void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.csrf().disable()
                    .authorizeRequests()
                    .anyRequest().authenticated()// 所有请求必须登陆后才能访问
                    .and()
                        .formLogin()
                        .loginPage("/login")
                        .failureUrl("login?error")
                        .permitAll()// 登陆页面、错误页面可以直接访问
                    .and()
                    .logout()
                    .permitAll();// 注销请求可以直接访问
        }
    }
```
- springSecurity4.0后，默认开启了CSRD拦截，如果需要配置请在form表单添加：
```
    <input type="hidden" name="${csrf.parameterName}" value="${_csrf.token}" />
```

## bug
- @ManyToMany(fetch = FetchType.EAGER)，@ManyToMany注解不能使用默认的加载方式（Lazy）
- Spring security 5.0中新增了多种加密方式，也改变了密码的格式。需要设置密码加密方式：
    ```
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService()).passwordEncoder(new BCryptPasswordEncoder());
        }
    ```
    在用户注册时就使用BCrypt编码将用户密码加密处理后存储在数据库中

## 角色判断：
- SpringSecurity不支持中文比对，所以不能直接使用角色中文名称作为判断条件
- 前端不能使用hasRole("roleCode")，要使用hasAuthority("roleCode")
- SpringSecurity将用户数据、角色数据都缓存到框架内，所以要重启项目页面数据才会更新，重新登陆

## 总结：
主要学习了SpringBoot项目中如何使用SpringSecurity来作为安全框架，并通过SpringSecurity提供的JSTL标签库来判断界面的输出，
还有如果修改了用户的权限不会实时生效，需要退出用户后再次登录方可生效。