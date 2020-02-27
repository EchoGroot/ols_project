package com.ols.ols_project.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ols.ols_project.mapper.TaskMapper;
import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.AccepteEntity;
import com.ols.ols_project.model.AccepteImageUrl;
import com.ols.ols_project.model.LabelInfo;
import com.ols.ols_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 任务相关的service实现类
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

    @Override
    public int storeImageLabelInfo(int accepteTaskId, List<LabelInfo> labelInfos, String imageUrlParam) {
        AccepteEntity accepteEntity = taskMapper.getAccepteTaskInfoByAcceptId(accepteTaskId);
        AccepteImageUrl accepteImageUrl = JSON.parseObject(accepteEntity.getUrl(), new TypeReference<AccepteImageUrl>() {});
        accepteImageUrl.getTaskImage().stream().forEach(e->{
            if(e.getOriginalImage().equals(imageUrlParam)){
                e.setLabeledInfo(labelInfos);
                e.setIsLabeled(true);
                return;
            }
        });
        return taskMapper.storeImageLabelInfoByAccepteId(accepteTaskId,JSON.toJSONString(accepteImageUrl));
    }
}
