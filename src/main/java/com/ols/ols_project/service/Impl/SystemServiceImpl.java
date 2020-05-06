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
        return "200";
    }

    @Override
    public List<SystemEntity> getAllSystemByAcceptUID(long acceptUID,Integer pageNum, Integer pageSize) {
        return null;
    }

}
