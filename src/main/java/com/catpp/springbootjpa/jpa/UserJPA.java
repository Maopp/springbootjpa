package com.catpp.springbootjpa.jpa;

import com.catpp.springbootjpa.base.BaseRepository;
import com.catpp.springbootjpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * com.catpp.springbootjpa.jpa
 *
 * @Author cat_pp
 * @Date 2018/12/24
 * @Description
 */
public interface UserJPA extends BaseRepository<UserEntity, Long>, Serializable {

    @Query(value = "select * from user where age > ?1", nativeQuery = true)
    List<UserEntity> customList(int age);

    @Modifying
    @Query(value = "delete from user where nickname = ?1", nativeQuery = true)
    @Transactional
    void deleteByQuery(String nickname);
}
