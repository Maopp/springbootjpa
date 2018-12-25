package com.catpp.springbootjpa.controller;

import com.catpp.springbootjpa.entity.UserEntity;
import com.catpp.springbootjpa.jpa.UserJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public void saveOrUpdate() {
        for (int i = 10; i < 15; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("zhangsan");
            userEntity.setPassword("123qwe");
            userEntity.setNickname("张三" + i);
            userEntity.setAge(10 + i);
            userJPA.save(userEntity);
        }
    }

    @RequestMapping("/delete/{id}")
    public List<UserEntity> delete(@PathVariable Long id) {
        userJPA.deleteById(id);
        return userJPA.findAll();
    }

    @RequestMapping("/customList/{age}")
    public List<UserEntity> customList(@PathVariable int age) {
        List<UserEntity> userEntities = userJPA.customList(age);
        return userEntities;
    }

    @RequestMapping("/deleteByQuery/{nickname}")
    public void deleteByQuery(@PathVariable String nickname) {
        userJPA.deleteByQuery(nickname);
    }

    @RequestMapping("/page/{page}")
    public List<UserEntity> page(@PathVariable int page) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPage(page);
        userEntity.setSize(10);
        userEntity.setSidx("age");

        // 获取排序对象
        Sort.Direction sort_direction = Sort.Direction.ASC.toString().equalsIgnoreCase(userEntity.getSort())
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        // 设置排序对象参数
        Sort sort = new Sort(sort_direction, userEntity.getSidx());
        // 创建分页对象
        PageRequest pageRequest = new PageRequest(userEntity.getPage() - 1, userEntity.getSize(), sort);
        // 需要引入queryDsl才可以使用哦
//        QPageRequest pageRequest = new QPageRequest(userEntity.getPage() - 1, userEntity.getSize());
        // 执行分页查询
        return userJPA.findAll(pageRequest).getContent();
    }
}
