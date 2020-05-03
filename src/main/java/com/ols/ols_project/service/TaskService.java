package com.ols.ols_project.service;

import com.alibaba.fastjson.JSONArray;
import com.ols.ols_project.model.AcceptTaskForTaskInfoVO;
import com.ols.ols_project.model.LabelInfo;
import com.ols.ols_project.model.TaskEntityBo;
import com.ols.ols_project.model.entity.TaskEntity;

import java.util.HashMap;
import java.util.List;

/**
 * @author yuyy
 * @date 20-2-24 下午7:05
 */
public interface TaskService {

    String getImageListByTaskId(long taskId);

    AcceptTaskForTaskInfoVO getAccepteImageListByAccepteId(long acceptId);

    int storeImageLabelInfo(String pageType,long tempTaskId, List<LabelInfo> labelInfos, String imageUrlParam);

    int setTaskStateByTaskId(long taskId);

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
    String creatTask( String taskName,String taskUrl,  String taskInfo, int rewardPoints, int type,
                    Long releaseUserId);
    int deductRewardPoints(int rewardPoints,long releaseUserId);

    int awardPoints(int rewardPoints,long releaseUserId);
    String creatTaskUrl(String labelName, String originalImage);
    String creatDocTaskUrl(String labelName,String originalDoc);
    HashMap<String, Object> getAllTask(String query, Integer pageNum, Integer pageSize,
                                       String queryInfo,String searchType, String searchInfo,String field,String order);

    void clickNumPlus(long taskId);
    List<TaskEntity> getClickNum();
    JSONArray getFileNameByTaskId(long taskId);
    void delImgFileByTaskId(long taskId);
    int[][] getAdminImgChartData(int year);
    int[][] getAllReleaseById(long userId,int year);
    HashMap<String,Object> getAcceptImgTaskByUserId(long userId, String query, Integer pageNum, Integer pageSize, String queryInfo, String searchInfo,String field,String order);

    HashMap<String, Object> getReleaseImgTaskByUserId(long userId, String query, Integer pageNum, Integer pageSize, String queryInfo, String searchInfo,String field,String order);

}
