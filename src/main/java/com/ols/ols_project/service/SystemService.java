package com.ols.ols_project.service;

import java.util.HashMap;

public interface SystemService {
    void createSystem(long releaseUID,long acceptUID,String message);
    String setViewed(long id);
    HashMap<String,Object> getAllSystemByAcceptUID(long acceptUID, Integer pageNum, Integer pageSize);
}
