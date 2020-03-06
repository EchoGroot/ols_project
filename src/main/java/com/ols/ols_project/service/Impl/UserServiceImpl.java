package com.ols.ols_project.service.Impl;

import com.ols.ols_project.mapper.UserMapper;
import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.TaskEntity;
import com.ols.ols_project.model.UserEntity;
import com.ols.ols_project.model.UserEntityBo;
import com.ols.ols_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用户相关的Service实现类
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

    @Override
    public HashMap<String, Object> getReviewerSignUp(Integer pageNum, Integer pageSize) {
        List<List<UserEntity>> list = userMapper.getReviewerSignUp((pageNum - 1) * pageSize, pageSize);
        HashMap<String,Object> result=new HashMap<>();
        List<Object> listBo=new ArrayList<>();
        list.get(0).stream().forEach(userEntity -> {
            UserEntityBo userEntityBo = new UserEntityBo();
            userEntityBo.setId(userEntity.getId());
            userEntityBo.setName(userEntity.getName());
            userEntityBo.setBirthday((userEntity.getBirthday()));
            userEntityBo.setSex(userEntity.getSex());
            userEntityBo.setEmail(userEntity.getEmail());
            userEntityBo.setRole("审核者");
            switch (userEntity.getExt1()){
                case "0":userEntityBo.setExt1("待处理");break;
                case "1":userEntityBo.setExt1("通过");break;
                case "2":userEntityBo.setExt1("不通过");break;
                default:userEntityBo.setExt1("");break;
            }
            listBo.add(userEntityBo);
        });
        result.put("total",list.get(1).get(0));
        result.put("userList",listBo);
        return result;
    }
}
