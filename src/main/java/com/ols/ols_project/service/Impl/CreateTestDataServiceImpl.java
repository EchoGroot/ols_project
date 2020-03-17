package com.ols.ols_project.service.Impl;

import com.ols.ols_project.common.utils.GenerateNameOfPerson;
import com.ols.ols_project.common.utils.GenerateString;
import com.ols.ols_project.mapper.CreateTestDataMapper;
import com.ols.ols_project.mapper.TaskMapper;
import com.ols.ols_project.model.*;
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

    @Autowired
    private TaskMapper taskMapper;

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
        taskEntity.setUrl("{\"labelName\":[\"渗水\",\"生锈\",\"裂缝\",\"安全隐患\"],\"taskImage\":[{\"isExample\":true,\"isLabeled\":true,\"labeledInfo\":[{\"ex\":578.4375,\"ey\":517.83,\"name\":\"渗水\",\"x\":194.06249999999997,\"y\":126.3},{\"ex\":345.9375,\"ey\":728.33,\"name\":\"生锈\",\"x\":182.8125,\"y\":555.72},{\"ex\":638.4375,\"ey\":326.27500000000003,\"name\":\"生锈\",\"x\":315.9375,\"y\":134.72},{\"ex\":820.3125,\"ey\":446.26000000000005,\"name\":\"裂缝\",\"x\":475.3125,\"y\":216.815},{\"ex\":325.3125,\"ey\":393.63500000000005,\"name\":\"裂缝\",\"x\":173.4375,\"y\":261.02},{\"ex\":1292.8125,\"ey\":458.89000000000004,\"name\":\"安全隐患\",\"x\":1052.8125,\"y\":189.45000000000002}],\"originalImage\":\"1812_XF100251.jpg\"},{\"isExample\":false,\"isLabeled\":false,\"labeledInfo\":[],\"originalImage\":\"1465846.jpg\"},{\"isExample\":false,\"isLabeled\":false,\"labeledInfo\":[],\"originalImage\":\"bridge_blue.jpg\"},{\"isExample\":false,\"isLabeled\":false,\"labeledInfo\":[],\"originalImage\":\"ee94f7814ddd4d03ad44bc876b6564ed.jpg\"},{\"isExample\":false,\"isLabeled\":false,\"labeledInfo\":[],\"originalImage\":\"F122_5592.jpg\"},{\"isExample\":false,\"isLabeled\":false,\"labeledInfo\":[],\"originalImage\":\"Highway-Bridge-Under-Construction.jpg\"},{\"isExample\":false,\"isLabeled\":false,\"labeledInfo\":[],\"originalImage\":\"IMG_1630.jpg\"},{\"isExample\":false,\"isLabeled\":false,\"labeledInfo\":[],\"originalImage\":\"IMG_20160421_105909.jpg\"},{\"isExample\":false,\"isLabeled\":false,\"labeledInfo\":[],\"originalImage\":\"mural_wide.jpg\"},{\"isExample\":false,\"isLabeled\":false,\"labeledInfo\":[],\"originalImage\":\"B9318726335Z.1_20150904163724_000_G61BQVVEC.1-0.jpg\"}]}");
        taskEntity.setInformation("测试数据的详细信息-"+GenerateString.generateString(8));
        taskEntity.setPoints(random.nextInt(20)+10);
        //1：已完成，2：已删除，3：已失效,4：审核中，5：已发布:6：未通过审核
        taskEntity.setState(4);
        //0：文档，1：图片
        taskEntity.setType(1);
        taskEntity.setRelease_time(new Timestamp(
                118,
                random.nextInt(12)+1,
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
        AccepteEntity acceptEntity=new AccepteEntity();
        Random random = new Random();
        for (int i = 0; i < 1; i++) {
            acceptEntity.setUser_id(10000);
            acceptEntity.setTask_id(10128);
            acceptEntity.setAccept_time(new Timestamp(
                    119,
                    random.nextInt(1)+8,
                    random.nextInt(28)+1,
                    random.nextInt(24),
                    random.nextInt(60),
                    random.nextInt(60),
                    random.nextInt(10000000)
            ));
        acceptEntity.setFinish_time(new Timestamp(
                119,
                random.nextInt(1)+10,
                random.nextInt(28)+1,
                random.nextInt(24),
                random.nextInt(60),
                random.nextInt(60),
                random.nextInt(10000000)
        ));
            acceptEntity.setState(2);
            createTestDataMapper.createTestDataForOlsAccepte(acceptEntity);
        }

    }

    @Override
    public void createTestDataForOlsJudge(int userId, int taskId) {
        Random random=new Random();
        String operation =random.nextInt(2)%2==0?"yes":"no";
        taskMapper.taskPassOrNotPassAudits(
                taskId,operation);
        int ispassed=operation.equals("yes")?1:0;
        String message=operation.equals("yes")
                ?"恭喜您！您发布的任务（编号"+taskId+"）已通过审核。"
                :"很抱歉！您发布的任务（编号"+taskId+"）未能通过审核，理由：图片涉嫌违规。";
        taskMapper.insJudge(JudgeEntity.builder()
                    .user_id(userId)
                    .task_id(taskId)
                    .ispassed(ispassed)
                    .isfirst(1)
                    .message(message)
                    .judge_time(
                            new Timestamp(
                                    119,
                                    random.nextInt(12)+1,
                                    random.nextInt(28)+1,
                                    random.nextInt(24),
                                    random.nextInt(60),
                                    random.nextInt(60),
                                    random.nextInt(10000000)
                    ))
                    .build());
    }


}
