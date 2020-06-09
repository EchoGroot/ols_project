package com.ols.ols_project.service;

import java.util.HashMap;

public interface MessageService {
    //发布举报信息
    String createMessage( long userId,long taskId,String Message,int type);
    //查询举报信息
    HashMap<String, Object> getAllMessage(String queryInfo,String searchInfo ,Integer pageNum, Integer pageSize);
    //回复举报信息
    int replyMessage(long Id ,String Response);

    //通过ID查询举报回复信息
    HashMap<String, Object> getcomplainById(long userId,Integer pageNum, Integer pageSize);

    int[][] getmessage(int year);
}
