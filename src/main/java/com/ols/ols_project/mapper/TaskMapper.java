package com.ols.ols_project.mapper;

import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.AcceptTaskForTaskInfo;
import com.ols.ols_project.model.FinishCheckTask;
import com.ols.ols_project.model.MonthAndCount;
import com.ols.ols_project.model.entity.AcceptEntity;
import com.ols.ols_project.model.*;
import com.ols.ols_project.model.entity.AcceptEntity;
import com.ols.ols_project.model.entity.JudgeEntity;
import com.ols.ols_project.model.entity.TaskEntity;

import java.util.List;

/**
 * 关于任务的Mapper
 * @author yuyy
 * @date 20-2-24 下午7:10
 */
public interface TaskMapper {
    String getImageListByTaskId(long taskId);

    AcceptTaskForTaskInfo getAccepteImageListByAccepteId(long acceptId);

    AcceptEntity getAccepteTaskInfoByAcceptId(long acceptId);

    int storeImageLabelInfoByTempTaskId(String pageType,long tempTaskId,String url);

    List<TaskEntity> getNotCheckedTask(long userId);

    int updTaskState(long taskId);

    int setNotCheckedTaskForUser(long userId, int count);

    TaskEntity getTaskInfoByTaskId(long taskId);

    int insAcceptTask(AcceptEntity build);

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
    void setPoints(int points,long release_user_id);
    List<List<TaskEntity>> getAllTask(String query, int start, int end, String queryInfo,String searchType,String searchInfo,String field,String order);
    void clickNumPlus(long taskId);
    List<TaskEntity> getClickNum();
    List<MonthAndCount> getAdminImgChartData(int year, int state);
    List<MonthAndCount> getAdminDocChartData(int year, int state);
    List<MonthAndCount> getAllReleaseById(long userId, int year, int state);
    List<MonthAndCount> getJudgeTimeById(long userId, int year);
    int getPoints(long taskId);
    void setFinishById(long taskId,long acceptId);
    List<List<AcceptTask>> getAcceptImgTaskByUserId(long id, String query, int start, int end, String queryInfo, String searchInfo,String field,String order);

    List<List<TaskEntity>> getReleaseImgTaskByUserId(long id, String query, int start, int end, String queryInfo, String searchInfo,String field,String order);


    List<MonthAndCount> getAllReleaseDocById(long userId, int year, int state);

    List<List<AcceptTask>> getAcceptDocTaskByUserId(long userId, String query, int start, int end, String queryInfo, String searchInfo, String field, String order);

    List<List<TaskEntity>> getReleaseDocTaskByUserId(long userId, String query, int start, int end, String queryInfo, String searchInfo, String field, String order);

    String getDocListByTaskId(long taskId);
}
