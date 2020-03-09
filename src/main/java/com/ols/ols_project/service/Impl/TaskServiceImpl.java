package com.ols.ols_project.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ols.ols_project.mapper.TaskMapper;
import com.ols.ols_project.model.*;
import com.ols.ols_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<TaskEntityBo> getNotCheckedTask(Integer userId) {
        List<TaskEntity> entityList = taskMapper.getNotCheckedTask(userId);
        List<TaskEntityBo> entryListBo=new ArrayList<>();
        entityList.stream().forEach(
                taskEntity -> {
                    String type="";
                    switch (taskEntity.getType()){
                        case 0:type ="文档";break;
                        case 1:type="图片";break;
                    }
                    entryListBo.add(
                            TaskEntityBo.builder()
                                    .id(taskEntity.getId())
                                    .name(taskEntity.getName())
                                    .url(taskEntity.getUrl())
                                    .information(taskEntity.getInformation())
                                    .points(taskEntity.getPoints())
                                    .state("审核中")
                                    .type(type)
                                    .release_time(taskEntity.getRelease_time())
                                    .release_user_id(taskEntity.getRelease_user_id())
                                    .ext1(taskEntity.getExt1())
                                    .ext2(taskEntity.getExt2())
                                    .ext3(taskEntity.getExt3())
                                    .build()
                    );
                }
        );
        return entryListBo;
    }

    @Override
    public int setNotCheckedTaskForUser(Integer userId, int count) {
        return taskMapper.setNotCheckedTaskForUser(userId,count);
    }
}
