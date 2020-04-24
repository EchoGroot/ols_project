package com.ols.ols_project.service;

import java.util.HashMap;

public interface AcceptService {
    int[][] getPersonalAcceptByUserId(long userId,int year);
    HashMap<String, Object> getAcceptListByTaskId(Long taskId,Integer pageNum, Integer pageSize);
    String adoptByAcceptId(long acceptId,long taskId);
}
