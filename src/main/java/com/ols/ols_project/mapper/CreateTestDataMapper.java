package com.ols.ols_project.mapper;

import com.ols.ols_project.model.TaskEntity;
import com.ols.ols_project.model.UserEntity;

/**
 * 向数据库添加测试数据的Mapper
 * @author yuyy
 * @date 20-2-17 下午4:24
 */
public interface CreateTestDataMapper {
    void createTestDataForOlsUser(UserEntity userEntity);
    void createTestDataForOlsTask(TaskEntity taskEntity);
}
