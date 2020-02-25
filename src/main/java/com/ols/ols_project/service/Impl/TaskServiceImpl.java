package com.ols.ols_project.service.Impl;

import com.ols.ols_project.mapper.TaskMapper;
import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yuyy
 * @date 20-2-24 下午7:13
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public String getImageListByTaskId(int taskId) {
        return taskMapper.getImageListByTaskId(taskId);
    }

    @Override
    public AcceptTask getAccepteImageListByAccepteId(int accepteId) {
        return taskMapper.getAccepteImageListByAccepteId(accepteId);
    }
}
