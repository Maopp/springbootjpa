package com.catpp.springbootjpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * com.catpp.springbootjpa.entity
 *
 * @Author cat_pp
 * @Date 2018/12/24
 * @Description
 */
@Data
@Entity
@Table(name = "user")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "age")
    private Integer age;

    @Column(name = "sex")
    private Integer sex;
}
