package com.catpp.springbootjpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * com.catpp.springbootjpa.config
 *
 * @Author cat_pp
 * @Date 2018/12/27
 * @Description 重写addViewControllers()方法添加路径访问，可以通过Get形式的/login访问到我们的login.jsp
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer{


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/main").setViewName("main");
    }
}
