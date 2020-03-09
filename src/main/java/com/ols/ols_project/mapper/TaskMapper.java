package com.ols.ols_project.mapper;

import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.AccepteEntity;
import com.ols.ols_project.model.TaskEntity;

import java.util.List;

/**
 * 关于任务的Mapper
 * @author yuyy
 * @date 20-2-24 下午7:10
 */
public interface TaskMapper {
    String getImageListByTaskId(int taskId);

    AcceptTask getAccepteImageListByAccepteId(int accepteId);

    AccepteEntity getAccepteTaskInfoByAcceptId(int accepteId);

    int storeImageLabelInfoByAccepteId(int accepteId,String url);

    List<TaskEntity> getNotCheckedTask(Integer userId);

    int setNotCheckedTaskForUser(Integer userId, int count);
}
