package com.ols.ols_project.service;

/**
 * @author yuyy
 * @date 20-3-17 下午5:13
 */
public interface JudgeService {
    int[][] getHistoryByUserId(long userId,int year);
}
