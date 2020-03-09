package com.ols.ols_project.service.Impl;

import com.ols.ols_project.common.utils.GenerateNameOfPerson;
import com.ols.ols_project.common.utils.GenerateString;
import com.ols.ols_project.mapper.CreateTestDataMapper;
import com.ols.ols_project.model.AccepteEntity;
import com.ols.ols_project.model.TaskEntity;
import com.ols.ols_project.model.UserEntity;
import com.ols.ols_project.model.UserOperationLogEntity;
import com.ols.ols_project.service.CreateTestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Random;

/**
 * 向数据库添加测试数据的实现类
 * @author yuyy
 * @date 20-2-17 下午4:22
 */
@Service
public class CreateTestDataServiceImpl implements CreateTestDataService {

    @Autowired
    private CreateTestDataMapper createTestDataMapper;

    @Override
    public void createTestDataForOlsUser(int userIdStart) {
        UserEntity userEntity=new UserEntity();
        Random random = new Random();
        userEntity.setName(GenerateNameOfPerson.randomName(true,3));
        userEntity.setBirthday(new Date(
                90+(int)(Math.random()*10),
                1+(int)(Math.random()*(12)),
                1+(int)(Math.random()*28)));
        userEntity.setPassword(GenerateString.generateString(8));
        userEntity.setSex(Math.random()>0.5?"男":"女");
        //方便测试邮箱通知功能
        userEntity.setEmail("326018984@qq.com");
        //2：审核者,0：普通用户
        userEntity.setRole(0);
        userEntity.setPoints(200);
        createTestDataMapper.createTestDataForOlsUser(userEntity);
        createTestDataMapper.createTestDataForOlsUserOperationLog(
                UserOperationLogEntity.builder()
                        .user_id(userIdStart)
                        //0：注册
                        .type(0)
                        .time(new Timestamp(
                        118,
                        random.nextInt(6)+1,
                        random.nextInt(28)+1,
                        random.nextInt(24),
                        random.nextInt(60),
                        random.nextInt(60),
                        random.nextInt(10000000)
                )).build()
        );
    }

    @Override
    public void createTestDataForOlsTask() {
        TaskEntity taskEntity = new TaskEntity();
        Random random = new Random();
        taskEntity.setName("测试数据名字-"+GenerateString.generateNumber(4));
        taskEntity.setUrl("{\n" +
                "  \"taskImage\": [\n" +
                "    {\n" +
                "      \"isExample\": false,\n" +
                "      \"isLabeled\": false,\n" +
                "      \"labeledInfo\": [],\n" +
                "      \"originalImage\": \"1812_XF100251.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"isExample\": false,\n" +
                "      \"isLabeled\": false,\n" +
                "      \"labeledInfo\": [],\n" +
                "      \"originalImage\": \"1465846.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"isExample\": false,\n" +
                "      \"isLabeled\": false,\n" +
                "      \"labeledInfo\": [],\n" +
                "      \"originalImage\": \"bridge_blue.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"isExample\": false,\n" +
                "      \"isLabeled\": false,\n" +
                "      \"labeledInfo\": [],\n" +
                "      \"originalImage\": \"ee94f7814ddd4d03ad44bc876b6564ed.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"isExample\": false,\n" +
                "      \"isLabeled\": false,\n" +
                "      \"labeledInfo\": [],\n" +
                "      \"originalImage\": \"F122_5592.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"isExample\": false,\n" +
                "      \"isLabeled\": false,\n" +
                "      \"labeledInfo\": [],\n" +
                "      \"originalImage\": \"Highway-Bridge-Under-Construction.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"isExample\": false,\n" +
                "      \"isLabeled\": false,\n" +
                "      \"labeledInfo\": [],\n" +
                "      \"originalImage\": \"IMG_1630.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"isExample\": false,\n" +
                "      \"isLabeled\": false,\n" +
                "      \"labeledInfo\": [],\n" +
                "      \"originalImage\": \"IMG_20160421_105909.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"isExample\": false,\n" +
                "      \"isLabeled\": false,\n" +
                "      \"labeledInfo\": [],\n" +
                "      \"originalImage\": \"mural_wide.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"isExample\": false,\n" +
                "      \"isLabeled\": false,\n" +
                "      \"labeledInfo\": [],\n" +
                "      \"originalImage\": \"B9318726335Z.1_20150904163724_000_G61BQVVEC.1-0.jpg\"\n" +
                "    }\n" +
                "  ]\n" +
                "}");
        taskEntity.setInformation("测试数据的详细信息-"+GenerateString.generateString(8));
        taskEntity.setPoints(random.nextInt(20)+10);
        //1：已完成，2：已删除，3：已失效,4：审核中，5：已发布:6：未通过审核
        taskEntity.setState(4);
        //0：文档，1：图片
        taskEntity.setType(1);
        taskEntity.setRelease_time(new Timestamp(
                119,
                random.nextInt(6)+1,
                random.nextInt(28)+1,
                random.nextInt(24),
                random.nextInt(60),
                random.nextInt(60),
                random.nextInt(10000000)
        ));
        taskEntity.setRelease_user_id(12567);
        createTestDataMapper.createTestDataForOlsTask(taskEntity);
    }

    @Override
    public void createTestDataForOlsAccepte() {
        AccepteEntity accepteEntity=new AccepteEntity();
        Random random = new Random();
        for (int i = 0; i < 1; i++) {
            accepteEntity.setUser_id(10000);
            accepteEntity.setTask_id(10128);
            accepteEntity.setAccept_time(new Timestamp(
                    119,
                    random.nextInt(1)+8,
                    random.nextInt(28)+1,
                    random.nextInt(24),
                    random.nextInt(60),
                    random.nextInt(60),
                    random.nextInt(10000000)
            ));
        accepteEntity.setFinish_time(new Timestamp(
                119,
                random.nextInt(1)+10,
                random.nextInt(28)+1,
                random.nextInt(24),
                random.nextInt(60),
                random.nextInt(60),
                random.nextInt(10000000)
        ));
            accepteEntity.setState(2);
            createTestDataMapper.createTestDataForOlsAccepte(accepteEntity);
        }

    }


}
