package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.baidu.fsg.uid.service.UidGenService;
import com.ols.ols_project.model.TestUser;
import com.ols.ols_project.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 测试
 * @author yuyy
 * @date 20-2-9 下午8:18
 */
@Controller
public class TestController {

    @Autowired
    private UidGenService uidGenService;

    @Autowired
    private TestService testService;

    /**
     * 测试项目基础功能（连接MySQL，redis）
     * @param model
     * @return
     */
    @GetMapping("testindex")
    public String showIndex(Model model){
        model.addAttribute("msg","21313dsadas");
        List<TestUser> users = testService.selAll();
        model.addAttribute("list",users);
        System.out.println(users);
        return "TestIndex";
    }

    /**
     * 测试500错误页面
     * @return
     */
    @GetMapping("create500")
    @ResponseBody
    public String create500(){
        int n=0/5;
        System.out.println(n);
        return "ok";
    }

    /**
     * 测试生成uuid
     * @return
     */
    @GetMapping("uid")
    @ResponseBody
    public String genUid() {
        return String.valueOf("本次生成的唯一ID号为："+uidGenService.getUid());
    }

    @GetMapping("setSession")
    @ResponseBody
    public String login(@RequestParam("id") String id,
                        @RequestParam("userInfo") String userInfo,
                        HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().setAttribute(id, userInfo);
        return "ok";
    }

    @GetMapping("getSession")
    @ResponseBody
    public String login(@RequestParam("id") String id,
                        HttpServletRequest httpServletRequest){
        return JSON.toJSONString(httpServletRequest.getSession().getAttribute(id));
    }
}
