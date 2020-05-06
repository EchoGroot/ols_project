package com.ols.ols_project.service.Impl;

import com.ols.ols_project.mapper.SystemMapper;
import com.ols.ols_project.model.entity.SystemEntity;
import com.ols.ols_project.service.SystemService;
import com.baidu.fsg.uid.service.UidGenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor=Exception.class)
public class SystemServiceImpl implements SystemService {
    @Resource
    SystemMapper systemMapper;

    @Autowired
    private UidGenService uidGenService;

    @Override
    public void createSystem(long releaseUID, long acceptUID, String message) {
        SystemEntity systemEntity = new SystemEntity();
        long Id = uidGenService.getUid();
        systemEntity.setId(Id);
        systemEntity.setRelease_user_id(releaseUID);
        systemEntity.setMessage(message);
        systemEntity.setAccept_user_id(acceptUID);
        systemEntity.setCreate_time(new Timestamp(System.currentTimeMillis()));
        systemEntity.setExt1("0");
        systemEntity.setExt2("0");
        systemEntity.setExt3(0);
        systemMapper.createSystem(systemEntity);
    }

    @Override
    public String setViewed(long id) {
        systemMapper.setViewed(id);
        return "1";
    }

    @Override
    public HashMap<String,Object> getAllSystemByAcceptUID(long acceptUID, Integer pageNum, Integer pageSize) {
        List<List<SystemEntity>> list = systemMapper.getAllSystemByAcceptUID(acceptUID,(pageNum - 1) * pageSize,pageSize);
        List<SystemEntity> list1=new ArrayList<>();
        HashMap<String,Object> data=new HashMap<>();
        list.get(0).forEach(
                e->{
                    //list1.add(e);
                    Collections.addAll(list1,e);
                }
        );
        data.put("sysList",list1);
        data.put("total",list.get(1).get(0));
        return data;
    }

    @Override
    public SystemEntity getSystemById(long acceptId) {
        SystemEntity systemEntity = systemMapper.getSystemById(acceptId);
        return systemEntity;
    }

}
