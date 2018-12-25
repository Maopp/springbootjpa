package com.catpp.springbootjpa.base;

import lombok.Data;

import java.io.Serializable;

/**
 * com.catpp.springbootjpa.base
 *
 * @Author cat_pp
 * @Date 2018/12/25
 * @Description
 */
@Data
public class BaseEntity implements Serializable {

    /**
     * 分页页码，默认页码为1
     */
    private Integer page = 1;

    /**
     * 分页每页数量，默认20条
     */
    private Integer size = 20;

    /**
     * 排序列名称，默认为id
     */
    private String sidx = "id";

    /**
     * 排序方式，默认逆序排序
     */
    private String sort = "desc";
}
