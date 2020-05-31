package com.ols.ols_project.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页控制器
 * @author yuyy
 * @date 20-4-3 上午10:13
 */
@Controller
public class IndexController {
    @RequestMapping(value = "/")
    public String index(){
        return "forward:/Home/Home.html";
    }

    @RequestMapping("/testMyPage")
    public String testMyPage(){
        return "forward:/Home/test.html";
    }
}