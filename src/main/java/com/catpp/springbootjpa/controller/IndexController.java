package com.catpp.springbootjpa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.catpp.springbootjpa.controller
 *
 * @Author cat_pp
 * @Date 2018/12/27
 * @Description
 */
@RestController
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "get index success";
    }
}
