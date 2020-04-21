package com.ols.ols_project.service;

import java.util.HashMap;

public interface RewardAndPunishmentService {
    //发布奖惩信息
    String createRAP(int type,long userId, String information) ;
    //查询奖惩信息
    HashMap<String, Object> getAllMessage( Integer pageNum, Integer pageSize);
    //确认发布惩罚信息
   // int taskPassOrNotPassAudits(long userId,  String operation,String infomation);
}
