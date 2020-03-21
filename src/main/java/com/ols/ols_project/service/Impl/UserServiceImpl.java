package com.ols.ols_project.service.Impl;

import com.ols.ols_project.mapper.UserMapper;
import com.ols.ols_project.model.*;
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
    public HashMap<String,Object> getAcceptTaskByUserId(Integer userId, String query, Integer pageNum, Integer pageSize, String queryInfo, String searchInfo) {
        List<List<AcceptTask>> list = userMapper.getAcceptTaskByUserId(userId, query, (pageNum - 1) * pageSize, pageSize,queryInfo, searchInfo);
        List<AcceptTaskBo> list1=new ArrayList<>();
        HashMap<String,Object> data=new HashMap<>();
        list.get(0).forEach(
                e->{
                    String state=null;
                    String acceptState=null;
                    String type=null;
                    switch (e.getType()){
                        case 0:type ="文档";break;
                        case 1:type="图片";break;
                        default :type="";break;
                    }
                    switch (e.getTaskState()){
                        case 1:state="已完成";break;
                        case 2:state="已删除";break;
                        case 3:state="已失效";break;
                        case 4:state="审核中";break;
                        case 5:state="已发布";break;
                        case 6:state="未通过审核";break;
                        default :state="";break;
                    }
                    switch (e.getAcceptState()){
                        case 0:acceptState="未完成";break;
                        case 1:acceptState="已提交";break;
                        case 2:acceptState="已采纳";break;
                        case 3:acceptState="未采纳";break;
                        case 4:acceptState="已失效";break;
                        default :acceptState="";break;
                    }
                    list1.add(AcceptTaskBo.builder()
                            .taskId(e.getTaskId())
                            .taskName(e.getTaskName())
                            .points(e.getPoints())
                            .taskState(state)
                            .type(type)
                            .releaseTime(e.getReleaseTime())
                            .releaseName(e.getReleaseName())
                            .acceptNum(e.getAcceptNum())
                            .acceptTime(e.getAcceptTime())
                            .finishTime(e.getFinishTime())
                            .acceptState(acceptState)
                            .build());
                }
        );
        data.put("taskList",list1);
        data.put("total",list.get(1).get(0));
        return data;
    }

    @Override
    public HashMap<String, Object> getReleaseTaskByUserId(Integer userId, String query, Integer pageNum, Integer pageSize, String queryInfo, String searchInfo) {
        List<List<TaskEntity>> list = userMapper.getReleaseTaskByUserId(userId, query, (pageNum - 1) * pageSize, pageSize,queryInfo, searchInfo);
        List<TaskEntityBo> list1=new ArrayList<>();
        HashMap<String,Object> data=new HashMap<>();
        list.get(0).forEach(
                e->{
                    String state=null;
                    String type=null;
                    switch (e.getType()){
                        case 0:type ="文档";break;
                        case 1:type="图片";break;
                        default :type="";break;
                    }
                    switch (e.getState()){
                        case 1:state="已完成";break;
                        case 2:state="已删除";break;
                        case 3:state="已失效";break;
                        case 4:state="审核中";break;
                        case 5:state="已发布";break;
                        case 6:state="未通过审核";break;
                        default :state="";break;
                    }
                    list1.add(TaskEntityBo.builder()
                            .id(e.getId())
                            .name(e.getName())
                            .information(e.getInformation())
                            .points(e.getPoints())
                            .state(state)
                            .type(type)
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
