package com.ols.ols_project.mapper;

import com.ols.ols_project.model.entity.SystemEntity;

import java.util.List;

public interface SystemMapper {
    void createSystem(SystemEntity systemEntity);
    List<List<SystemEntity>> getAllSystemByAcceptUID(long acceptUID, Integer pageNum, Integer pageSize);
    int setViewed(long id);
}