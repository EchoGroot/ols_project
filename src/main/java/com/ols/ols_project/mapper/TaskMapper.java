package com.ols.ols_project.mapper;

import com.ols.ols_project.model.*;

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
}
