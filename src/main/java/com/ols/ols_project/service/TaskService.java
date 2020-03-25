package com.ols.ols_project.service;

import com.alibaba.fastjson.JSONArray;
import com.ols.ols_project.mapper.TaskMapper;
import com.ols.ols_project.model.AcceptTaskForTaskInfo;
import com.ols.ols_project.model.LabelInfo;
import com.ols.ols_project.model.entity.TaskEntity;
import com.ols.ols_project.model.TaskEntityBo;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author yuyy
 * @date 20-2-24 下午7:05
 */
public interface TaskService {

    String getImageListByTaskId(long taskId);

    AcceptTaskForTaskInfo getAccepteImageListByAccepteId(long acceptId);

    int storeImageLabelInfo(long acceptTaskId, List<LabelInfo> labelInfos, String imageUrlParam);

    List<TaskEntityBo> getNotCheckedTask(long userId);

    int setNotCheckedTaskForUser(long userId, int count);

    TaskEntityBo getTaskInfoByTaskId(long taskId);

    int acceptTask(long userId, long taskId);

    int taskPassOrNotPassAudits(long userId, long taskId, String operation,String message);

    HashMap<String, Object> getFinishCheckTaskByUserId(long userId,String queryInfo, String searchInfo, int pageNum, int pageSize);

    int submitAcceptTask(long acceptId, long taskId);

    /**
     * @author wjp
     * @date 2020/3/21 下午9:32
     */
    //String taskUrl, int state,Timestamp releaseTime, Timestamp finishTime,int acceptNum, Long adoptAcceptId
    void creatTask( String taskName,String taskUrl,  String taskInfo, int rewardPoints, int type,
                    Long releaseUserId);

    String creatTaskUrl(String lableName, String originalImage);

    HashMap<String, Object> getAllTask(String query, Integer pageNum, Integer pageSize, String queryInfo, String searchInfo);
}
