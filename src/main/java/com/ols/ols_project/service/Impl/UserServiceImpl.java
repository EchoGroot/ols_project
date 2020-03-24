package com.ols.ols_project.service.Impl;

import com.ols.ols_project.common.Const.*;
import com.ols.ols_project.mapper.UserMapper;
import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.AcceptTaskBo;
import com.ols.ols_project.model.TaskEntityBo;
import com.ols.ols_project.model.UserSignUp;
import com.ols.ols_project.model.entity.TaskEntity;
import com.ols.ols_project.model.entity.UserEntity;
import com.ols.ols_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用户相关的Service实现类
 * @author yuyy
 * @date 20-2-19 下午2:20
 */

/**
 *修改邮箱
 * @author sf
 * @date 20-3-23
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity getUserInfoById(long id) {
        return userMapper.getUserInfoById(id);
    }

    @Override
    public String getPassWodById(long id) {
        return userMapper.getPassWodById(id);
    }

    @Override
    public int changePassWordById(long id, String passWord) {
        return userMapper.changePassWordById(id,passWord);
    }

    @Override
    public HashMap<String,Object> getAcceptTaskByUserId(long userId, String query, Integer pageNum, Integer pageSize, String queryInfo, String searchInfo) {
        List<List<AcceptTask>> list = userMapper.getAcceptTaskByUserId(userId, query, (pageNum - 1) * pageSize, pageSize,queryInfo, searchInfo);
        List<AcceptTaskBo> list1=new ArrayList<>();
        HashMap<String,Object> data=new HashMap<>();
        list.get(0).forEach(
                e->{
                    list1.add(AcceptTaskBo.builder()
                            .taskId(e.getTaskId())
                            .acceptId(e.getAcceptId())
                            .taskName(e.getTaskName())
                            .points(e.getPoints())
                            .taskState(TaskStateEnum.getNameByCode(e.getTaskState()))
                            .type(FileTypeEnum.getNameByCode(e.getType()))
                            .releaseTime(e.getReleaseTime())
                            .releaseName(e.getReleaseName())
                            .acceptNum(e.getAcceptNum())
                            .acceptTime(e.getAcceptTime())
                            .finishTime(e.getFinishTime())
                            .acceptState(AcceptStateEnum.getNameByCode(e.getAcceptState()))
                            .build());
                }
        );
        data.put("taskList",list1);
        data.put("total",list.get(1).get(0));
        return data;
    }

    @Override
    public HashMap<String, Object> getReleaseTaskByUserId(long userId, String query, Integer pageNum, Integer pageSize, String queryInfo, String searchInfo) {
        List<List<TaskEntity>> list = userMapper.getReleaseTaskByUserId(userId, query, (pageNum - 1) * pageSize, pageSize,queryInfo, searchInfo);
        List<TaskEntityBo> list1=new ArrayList<>();
        HashMap<String,Object> data=new HashMap<>();
        list.get(0).forEach(
                e->{
                    list1.add(TaskEntityBo.builder()
                            .id(e.getId())
                            .name(e.getName())
                            .information(e.getInformation())
                            .points(e.getPoints())
                            .state(TaskStateEnum.getNameByCode(e.getState()))
                            .type(FileTypeEnum.getNameByCode(e.getType()))
                            .release_time(e.getRelease_time())
                            .finish_time(e.getFinish_time())
                            .accept_num(e.getAccept_num())
                            .adopt_accept_id(e.getAdopt_accept_id())
                            .ext1(e.getExt1())
                            .ext2(e.getExt2())
                            .build());
                }
        );
        data.put("taskList",list1);
        data.put("total",list.get(1).get(0));
        return data;
    }

    @Override
    public HashMap<String, Object> getReviewerSignUp(String queryInfo,String searchInfo ,Integer pageNum, Integer pageSize) {
        List<List<UserSignUp>> list = userMapper.getReviewerSignUp( queryInfo, searchInfo ,(pageNum - 1) * pageSize, pageSize);
        HashMap<String,Object> result=new HashMap<>();
        List<Object> listBo=new ArrayList<>();
        list.get(0).stream().forEach(userEntity -> {
            listBo.add(UserSignUp.builder()
                    .id(userEntity.getId())
                    .name(userEntity.getName())
                    .birthday(userEntity.getBirthday())
                    .sex(userEntity.getSex())
                    .email(userEntity.getEmail())
                    .role(RoleEnum.REVIEWER.getName())
                    .ext1(ReviewerSignUpEnum.getNameByCode(Integer.parseInt(userEntity.getExt1())))
                    .signUpTime(userEntity.getSignUpTime())
                    .build());
        });
        result.put("total",list.get(1).get(0));
        result.put("userList",listBo);
        return result;
    }

    @Override
    public int yesAndNoReviewerSignUp(long userId, String operation) {
        return userMapper.yesAndNoReviewerSignUp(userId,operation);
    }


    @Override
    public int changeEmailById(long userId, String email) {
        return userMapper.changeEmailById(userId,email);
    }



}
