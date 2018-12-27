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

    /**
     * 这个方法其实是SpringDataJPA的一个规则，这样写JPA就会认为我们要根据username这个字段去查询，并自动使用参数索引为0的值
     * @param username
     * @return
     */
    UserEntity findByUsername(String username);
}
