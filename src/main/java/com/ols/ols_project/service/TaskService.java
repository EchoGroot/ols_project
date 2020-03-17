package com.ols.ols_project.service;

import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.LabelInfo;
import com.ols.ols_project.model.TaskEntityBo;

import java.util.HashMap;
import java.util.List;

/**
 * @author yuyy
 * @date 20-2-24 下午7:05
 */
public interface TaskService {

    String getImageListByTaskId(int taskId);

    AcceptTask getAccepteImageListByAccepteId(int acceptId);

    int storeImageLabelInfo(int acceptTaskId, List<LabelInfo> labelInfos, String imageUrlParam);

    List<TaskEntityBo> getNotCheckedTask(Integer userId);

    int setNotCheckedTaskForUser(Integer userId, int count);

    TaskEntityBo getTaskInfoByTaskId(int taskId);

    int acceptTask(int userId, int taskId);

    int taskPassOrNotPassAudits(int userId, int taskId, String operation,String message);

    HashMap<String, Object> getFinishCheckTaskByUserId(int userId,String queryInfo, String searchInfo, int pageNum, int pageSize);
}
