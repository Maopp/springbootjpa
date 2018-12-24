package com.catpp.springbootjpa.jpa;

import com.catpp.springbootjpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * com.catpp.springbootjpa.jpa
 *
 * @Author cat_pp
 * @Date 2018/12/24
 * @Description
 */
public interface UserJPA extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity>, Serializable {
}
