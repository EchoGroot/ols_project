package com.ols.ols_project.mapper;

import com.ols.ols_project.model.*;

import java.util.List;

/**
 * 关于任务的Mapper
 * @author yuyy
 * @date 20-2-24 下午7:10
 */
public interface TaskMapper {
    String getImageListByTaskId(int taskId);

    AcceptTaskForTaskInfo getAccepteImageListByAccepteId(int acceptId);

    AccepteEntity getAccepteTaskInfoByAcceptId(int acceptId);

    int storeImageLabelInfoByAccepteId(int acceptId,String url);

    List<TaskEntity> getNotCheckedTask(Integer userId);

    int setNotCheckedTaskForUser(Integer userId, int count);

    TaskEntity getTaskInfoByTaskId(int taskId);

    int insAcceptTask(AccepteEntity build);

    int selAcceptNumOfTask(int taskId);

    int updAcceptNumOfTask(int taskId,int acceptNum);

    int taskPassOrNotPassAudits(int taskId, String operation);

    int insJudge(JudgeEntity judgeEntity);

    List<List<FinishCheckTask>> selFinishCheckTaskByUserId(int userId,String queryInfo, String searchInfo, int start, int end);
}
