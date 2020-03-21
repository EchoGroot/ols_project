package com.ols.ols_project.common.utils;

import com.ols.ols_project.service.CreateTestDataService;
import com.ols.ols_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 构建测试数据
 * @author yuyy
 * @date 20-2-17 下午7:29
 */
@RestController
public class CreateTestDataController {

    @Autowired
    private CreateTestDataService createTestDataService;

    @Autowired
    private TaskService taskService;

    /**
     * 创建ols_user表和ols_user_operation_log的测试数据
     * @return
     */
    @GetMapping("createTestDataForOlsUser")
    public void createTestDataForOlsUser(){
        int userIdStart=12344;
        for (int i=0;i<223;i++){
            createTestDataService.createTestDataForOlsUser(++userIdStart);
        }
    }

    /**
     * 创建ols_task表的测试数据
     * @return
     */
    @GetMapping("/createTestDataForOlsTask")
    public String createTestDataForOlsTask(){
        for (int i=0;i<1200;i++){
            createTestDataService.createTestDataForOlsTask();
        }
        return "ok";
    }

    /**
     * 创建ols_task表的测试数据
     * @return
     */
    @GetMapping("/createTestDataForOlsJudge")
    public String createTestDataForOlsJudge(){
        for (int i=0;i<1200;i++){
            createTestDataService.createTestDataForOlsJudge(
                    12040,
                    11655-i
            );
        }
        return "ok";
    }

    /**
     * 创建ols_accept表的测试数据
     * @return
     */
    @GetMapping("createTestDataForOlsAccepte")
    public void createTestDataForOlsAccepte(){
        createTestDataService.createTestDataForOlsAccepte();
        for (int i=0;i<25;i++){
        }
    }
}
