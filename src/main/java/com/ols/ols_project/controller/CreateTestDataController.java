package com.ols.ols_project.controller;

import com.ols.ols_project.service.CreateTestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 构建测试数据
 * @author yuyy
 * @date 20-2-17 下午7:29
 */
@Controller
public class CreateTestDataController {

    @Autowired
    private CreateTestDataService createTestDataService;

    /**
     * 创建ols_user表的测试数据
     * @return
     */
    @RequestMapping("createTestDataForOlsUser")
    @ResponseBody
    public String createTestDataForOlsUser(){
        for (int i=0;i<100;i++){
            createTestDataService.createTestDataForOlsUser();
        }
        return "ok";
    }

    /**
     * 创建ols_task表的测试数据
     * @return
     */
    @RequestMapping("createTestDataForOlsTask")
    @ResponseBody
    public String createTestDataForOlsTask(){
        for (int i=0;i<1;i++){
            createTestDataService.createTestDataForOlsTask();
        }
        return "ok";
    }

    /**
     * 创建ols_accepte表的测试数据
     * @return
     */
    @RequestMapping("createTestDataForOlsAccepte")
    @ResponseBody
    public String createTestDataForOlsAccepte(){
        createTestDataService.createTestDataForOlsAccepte();
        for (int i=0;i<25;i++){
        }
        return "ok";
    }
}
