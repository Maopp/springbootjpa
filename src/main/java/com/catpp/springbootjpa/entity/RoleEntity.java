package com.catpp.springbootjpa.entity;

import com.catpp.springbootjpa.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * com.catpp.springbootjpa.entity
 *
 * @Author cat_pp
 * @Date 2018/12/27
 * @Description
 */
@Data
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

}
