package com.ols.ols_project.service.Impl;

import com.ols.ols_project.OlsProjectApplication;
import com.ols.ols_project.service.CreateTestDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yuyy
 * @date 20-2-17 下午5:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {OlsProjectApplication.class})
public class CreateTestDataServiceImplTest {
    @Autowired
    private CreateTestDataService createTestDataService;


    @Test
    public void createTestDataForOlsUser() {

    }

    @Test
    public void createTestDataForOlsTask() {
        for(int i=0;i<10;i++){
            createTestDataService.createTestDataForOlsTask();

        }
    }


}