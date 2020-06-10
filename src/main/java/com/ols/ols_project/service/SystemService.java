package com.ols.ols_project.service;

import com.ols.ols_project.model.entity.SystemEntity;

import java.util.HashMap;
import java.util.List;

public interface SystemService {
    void createSystem(long releaseUID,long acceptUID,String message);
    String setViewed(long id);
    HashMap<String,Object> getAllSystemByAcceptUID(long acceptUID, Integer pageNum, Integer pageSize);
    SystemEntity getSystemById(long acceptId);

    HashMap<String,Object> getAllSystem(Integer pageNum, Integer pageSize);
}
