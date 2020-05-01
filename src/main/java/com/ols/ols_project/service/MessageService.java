package com.ols.ols_project.service;

import java.util.HashMap;

public interface MessageService {
    //发布举报信息
    String createMessage( long userId,long taskId,String Message);
    //查询举报信息
    HashMap<String, Object> getAllMessage(String queryInfo,String searchInfo ,Integer pageNum, Integer pageSize);
}
