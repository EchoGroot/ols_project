package com.ols.ols_project.service;

import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.TaskEntity;
import com.ols.ols_project.model.UserEntity;

import java.util.HashMap;
import java.util.List;

/**
 * 关于User的Service
 * @author yuyy
 * @date 20-2-19 下午2:18
 */
public interface UserService {

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    UserEntity getUserInfoById(int id);

    /**
     * 根据用户id查询密码
     * @param id
     * @return
     */
    String getPassWodById(int id);

    /**
     * 根据id修改用户密码
     * @param id
     * @param passWord
     * @return
     */
    int changePassWordById(int id,String passWord);

    /**
     * 根据id查询已接受的任务
     * @param id
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<List<AcceptTask>> getAcceptTaskByUserId(int id, String query, int pageNum, int pageSize);

    /**
     * 根据id查询已发布的任务
     * @param id
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<List<TaskEntity>> getReleaseTaskByUserId(int id, String query, int pageNum, int pageSize);

    /**
     * 查询待批准的审核者注册账号
     * @param pageNum
     * @param pageSize
     * @return
     */
    HashMap<String, Object> getReviewerSignUp(String queryInfo,String searchInfo,Integer pageNum, Integer pageSize);

    /**
     * 管理员同意或不同意审核者账号注册
     */
    int yesAndNoReviewerSignUp(Integer userId,String operation);


}
