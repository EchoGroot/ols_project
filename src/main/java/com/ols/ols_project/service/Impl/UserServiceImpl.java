package com.ols.ols_project.service.Impl;

import com.ols.ols_project.mapper.UserMapper;
import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.TaskEntity;
import com.ols.ols_project.model.UserEntity;
import com.ols.ols_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuyy
 * @date 20-2-19 下午2:20
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity getUserInfoById(int id) {
        return userMapper.getUserInfoById(id);
    }

    @Override
    public String getPassWodById(int id) {
        return userMapper.getPassWodById(id);
    }

    @Override
    public int changePassWordById(int id, String passWord) {
        return userMapper.changePassWordById(id,passWord);
    }

    @Override
    public List<List<AcceptTask>> getAcceptTaskByUserId(int id, String query, int pageNum, int pageSize) {
        return userMapper.getAcceptTaskByUserId(id, query, (pageNum-1)*pageSize, pageSize);
    }

    @Override
    public List<List<TaskEntity>> getReleaseTaskByUserId(int id, String query, int pageNum, int pageSize) {
        return userMapper.getReleaseTaskByUserId(id, query, (pageNum-1)*pageSize, pageSize);
    }
}
