package com.ols.ols_project.controller;

import com.ols.ols_project.service.CreateTestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yuyy
 * @date 20-2-17 下午7:29
 */
@Controller
public class CreateTestDataController {

    @Autowired
    private CreateTestDataService createTestDataService;

    @RequestMapping("createTestDataForOlsUser")
    @ResponseBody
    public String createTestDataForOlsUser(){
        for (int i=0;i<1000;i++){
            createTestDataService.createTestDataForOlsUser();
        }
        return "ok";
    }

    @RequestMapping("createTestDataForOlsTask")
    @ResponseBody
    public String createTestDataForOlsTask(){
        for (int i=0;i<1;i++){
            createTestDataService.createTestDataForOlsTask();
        }
        return "ok";
    }

    @RequestMapping("createTestDataForOlsAccepte")
    @ResponseBody
    public String createTestDataForOlsAccepte(){
        createTestDataService.createTestDataForOlsAccepte();
        for (int i=0;i<25;i++){
        }
        return "ok";
    }
}
