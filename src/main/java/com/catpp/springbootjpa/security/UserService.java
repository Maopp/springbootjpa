package com.catpp.springbootjpa.security;

import com.catpp.springbootjpa.entity.UserEntity;
import com.catpp.springbootjpa.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * com.catpp.springbootjpa.security
 *
 * @Author cat_pp
 * @Date 2018/12/27
 * @Description 实现SpringSecurity内的UserDetailsService接口来完成自定义查询用户的逻辑
 */
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
