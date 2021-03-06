package com.ols.ols_project.mapper;

import com.ols.ols_project.model.MonthAndCount;

import java.util.List;

/**
 * 审核相关的mapper
 * @author yuyy
 * @date 20-3-17 下午5:23
 */
public interface JudgeMapper {
    List<MonthAndCount>getHistoryByUserId(long userId,int year,int isPassed);
}
