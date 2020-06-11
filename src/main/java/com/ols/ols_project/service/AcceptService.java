package com.ols.ols_project.service;

import java.util.HashMap;
import java.util.Map;

public interface AcceptService {
    //发布接收信息
    String createAccept(long task_id);
    //查询接收信息
   // HashMap<String, Object> getAllMessage(Integer pageNum, Integer pageSize);
    HashMap<String,Object> getAcceptByTaskId(Long taskId,Integer pageNum, Integer pageSize);
    HashMap<String,Object> getAllImgAcceptList(Integer pageNum, Integer pageSize);
    // authour:wjp
    int[][] getPersonalAcceptByUserId(long userId,int year);
    HashMap<String, Object> getAcceptListByTaskId(Long taskId,Integer pageNum, Integer pageSize);
    String adoptByAcceptId(long acceptId,long taskId);
    long getUserId(long acceptId);

    Object getPersonalAcceptDocByUserId(long parseLong, int parseInt);
}
