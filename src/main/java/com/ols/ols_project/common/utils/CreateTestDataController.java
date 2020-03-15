package com.ols.ols_project.common.utils;

import com.ols.ols_project.service.CreateTestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 构建测试数据
 * @author yuyy
 * @date 20-2-17 下午7:29
 */
@Component
public class CreateTestDataController {

    @Autowired
    private CreateTestDataService createTestDataService;

    /**
     * 创建ols_user表和ols_user_operation_log的测试数据
     * @return
     */
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
    public void createTestDataForOlsTask(){
        for (int i=0;i<326;i++){
            createTestDataService.createTestDataForOlsTask();
        }
    }

    /**
     * 创建ols_accept表的测试数据
     * @return
     */
    public void createTestDataForOlsAccepte(){
        createTestDataService.createTestDataForOlsAccepte();
        for (int i=0;i<25;i++){
        }
    }
}
