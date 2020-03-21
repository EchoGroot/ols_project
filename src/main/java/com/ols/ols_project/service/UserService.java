package com.ols.ols_project.service;

import com.ols.ols_project.model.entity.UserEntity;

import java.util.HashMap;

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
    UserEntity getUserInfoById(long id);

    /**
     * 根据用户id查询密码
     * @param id
     * @return
     */
    String getPassWodById(long id);

    /**
     * 根据id修改用户密码
     * @param id
     * @param passWord
     * @return
     */
    int changePassWordById(long id,String passWord);

    /**
     * 根据id查询已接受的任务
     * @param userId
     * @param query
     * @param pageNum
     * @param pageSize
     * @param queryInfo
     * @param searchInfo
     * @return
     */
    HashMap<String,Object> getAcceptTaskByUserId(long userId, String query, Integer pageNum, Integer pageSize, String queryInfo, String searchInfo);

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
    int yesAndNoReviewerSignUp(long userId,String operation);

    /**
     * 根据id查询已发布的任务
     * @param userId
     * @param query
     * @param pageNum
     * @param pageSize
     * @param queryInfo
     * @param searchInfo
     * @return
     */
    HashMap<String, Object> getReleaseTaskByUserId(long userId, String query, Integer pageNum, Integer pageSize, String queryInfo, String searchInfo);
}
