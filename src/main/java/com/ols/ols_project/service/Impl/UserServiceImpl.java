package com.ols.ols_project.service.Impl;

import com.ols.ols_project.mapper.UserMapper;
import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.TaskEntity;
import com.ols.ols_project.model.UserEntity;
import com.ols.ols_project.model.UserSignUp;
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
    public HashMap<String, Object> getReviewerSignUp(String queryInfo,String searchInfo ,Integer pageNum, Integer pageSize) {
        List<List<UserSignUp>> list = userMapper.getReviewerSignUp( queryInfo, searchInfo ,(pageNum - 1) * pageSize, pageSize);
        HashMap<String,Object> result=new HashMap<>();
        List<Object> listBo=new ArrayList<>();
        list.get(0).stream().forEach(userEntity -> {
            UserSignUp userSignUp = new UserSignUp();
            userSignUp.setId(userEntity.getId());
            userSignUp.setName(userEntity.getName());
            userSignUp.setBirthday((userEntity.getBirthday()));
            userSignUp.setSex(userEntity.getSex());
            userSignUp.setEmail(userEntity.getEmail());
            userSignUp.setRole("审核者");
            switch (userEntity.getExt1()){
                case "0":userSignUp.setExt1("待处理");break;
                case "1":userSignUp.setExt1("通过");break;
                case "2":userSignUp.setExt1("不通过");break;
                default:userSignUp.setExt1("");break;
            }
            userSignUp.setSignUpTime(userEntity.getSignUpTime());
            listBo.add(userSignUp);
        });
        result.put("total",list.get(1).get(0));
        result.put("userList",listBo);
        return result;
    }

    @Override
    public int yesAndNoReviewerSignUp(Integer userId, String operation) {
        return userMapper.yesAndNoReviewerSignUp(userId,operation);
    }



}
