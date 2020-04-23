package com.ols.ols_project.mapper;

import com.ols.ols_project.model.MonthAndCount;

import java.util.List;

public interface AcceptMapper {
    List<MonthAndCount> getAllAcceptById(long userId, int year, int state);
}
