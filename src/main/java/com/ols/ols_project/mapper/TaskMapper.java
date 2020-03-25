package com.ols.ols_project.mapper;

import com.ols.ols_project.model.*;
import com.ols.ols_project.model.entity.AccepteEntity;
import com.ols.ols_project.model.entity.JudgeEntity;
import com.ols.ols_project.model.entity.TaskEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * 关于任务的Mapper
 * @author yuyy
 * @date 20-2-24 下午7:10
 */
public interface TaskMapper {
    String getImageListByTaskId(long taskId);

    AcceptTaskForTaskInfo getAccepteImageListByAccepteId(long acceptId);

    AccepteEntity getAccepteTaskInfoByAcceptId(long acceptId);

    int storeImageLabelInfoByAccepteId(long acceptId,String url);

    List<TaskEntity> getNotCheckedTask(long userId);

    int setNotCheckedTaskForUser(long userId, int count);

    TaskEntity getTaskInfoByTaskId(long taskId);

    int insAcceptTask(AccepteEntity build);

    int selAcceptNumOfTask(long taskId);

    int updAcceptNumOfTask(long taskId,int acceptNum);

    int taskPassOrNotPassAudits(long taskId, String operation);

    int insJudge(JudgeEntity judgeEntity);

    List<List<FinishCheckTask>> selFinishCheckTaskByUserId(long userId,String queryInfo, String searchInfo, int start, int end);

    int updExt1(long taskId, int ext1);

    int updAcceptState(long acceptId);

    /**
     * @author wjp
     * @date 2020/3/21 下午9:32
     */
    void creatTask(TaskEntity taskEntity);
    List<List<TaskEntity>> getAllTask(String query, int start, int end, String queryInfo,String searchInfo);
}
