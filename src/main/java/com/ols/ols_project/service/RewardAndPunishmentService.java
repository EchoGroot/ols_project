package com.ols.ols_project.service;

import java.util.HashMap;

public interface RewardAndPunishmentService {
    //发布奖惩信息
    String createRAP(int type,long userId, String information) ;
    //查询奖惩信息
    HashMap<String, Object> getAllMessage( Integer pageNum, Integer pageSize);
    //确认发布惩罚信息
   // int taskPassOrNotPassAudits(long userId,  String operation,String infomation);
   //通过类型查询奖惩信息
    HashMap<String, Object> getRAPInformationBytype(int type,String queryInfo, String searchInfo, int pageNum, int pageSize);
   //通过ID查询奖励信息
    HashMap<String, Object> getRInformationById(long userId,Integer pageNum, Integer pageSize);
    //通过ID查询惩罚信息
    HashMap<String, Object> getPInformationById(long userId,Integer pageNum, Integer pageSize);
    //通过ID查询奖励惩罚信息
    HashMap<String, Object> getRPInformationById(long userId,Integer pageNum, Integer pageSize);
    //用户查询奖惩信息可视化
    int[][] getInformationByUserId(long userId,int year);
    //管理员查看可视化奖惩信息
    int[][] getRAPmessage(int year);
}
