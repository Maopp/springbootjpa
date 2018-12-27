package com.catpp.springbootjpa.config;

import com.catpp.springbootjpa.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * com.catpp.springbootjpa.config
 *
 * @Author cat_pp
 * @Date 2018/12/27
 * @Description 配置SpringBoot项目支持SpringSecurity安全框架
 */
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
                    .failureUrl("/login?error")
                    .permitAll()// 登陆页面、错误页面可以直接访问
                .and()
                .logout()
                .permitAll();// 注销请求可以直接访问
    }

    /**
     * Spring security 5.0中新增了多种加密方式，也改变了密码的格式。
     *
     * 将前端传过来的密码进行某种方式加密，spring security 官方推荐的是使用bcrypt加密方式。那么如何对密码加密呢，
     * 只需要在configure方法里面指定一下
     *
     * 一般是要在用户注册时就使用BCrypt编码将用户密码加密处理后存储在数据库中
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService()).passwordEncoder(new BCryptPasswordEncoder());
    }
}
