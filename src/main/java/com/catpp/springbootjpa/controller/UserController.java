package com.catpp.springbootjpa.controller;

import com.catpp.springbootjpa.entity.UserEntity;
import com.catpp.springbootjpa.jpa.UserJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.catpp.springbootjpa.controller
 *
 * @Author cat_pp
 * @Date 2018/12/24
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserJPA userJPA;

    @RequestMapping("/list")
    public List<UserEntity> list() {
        return userJPA.findAll();
    }

    @RequestMapping("/save")
    public UserEntity saveOrUpdate(UserEntity userEntity) {
        userEntity.setUsername("zhangsan");
        userEntity.setPassword("123qwe");
        userEntity.setNickname("张三");
        return userJPA.save(userEntity);
    }

    @RequestMapping("/delete/{id}")
    public List<UserEntity> delete(@PathVariable Long id) {
        userJPA.deleteById(id);
        return userJPA.findAll();
    }
}
