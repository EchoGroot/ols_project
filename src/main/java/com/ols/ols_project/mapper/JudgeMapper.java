package com.ols.ols_project.mapper;

import com.ols.ols_project.model.MonthAndCount;

import java.util.List;

/**
 * @author yuyy
 * @date 20-3-17 下午5:23
 */
public interface JudgeMapper {
    List<MonthAndCount>getHistoryByUserId(int userId,int year,int isPassed);
}
