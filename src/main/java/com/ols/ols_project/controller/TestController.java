package com.ols.ols_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yuyy
 * @date 20-2-9 下午8:18
 */
@Controller
public class TestController {
    @RequestMapping("testindex")
    public String showIndex(Model model){
        model.addAttribute("msg","ols");
        return "TestIndex";
    }
}
