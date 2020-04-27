package com.ols.ols_project.service.Impl;

import com.baidu.fsg.uid.service.UidGenService;
import com.ols.ols_project.common.Const.IsPassedEnum;
import com.ols.ols_project.mapper.RewardAndPunishmentMapper;
import com.ols.ols_project.model.FinishCheckTask;
import com.ols.ols_project.model.FinishCheckTaskBo;
import com.ols.ols_project.model.RewardAndPunishmentEnityBo;
import com.ols.ols_project.model.entity.JudgeEntity;
import com.ols.ols_project.model.entity.RewardAndPunishmentEnity;
import com.ols.ols_project.service.RewardAndPunishmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor=Exception.class)
public class RewardAndPunishmentServiceImpl implements RewardAndPunishmentService {
    //发布奖惩信息
    @Resource
    private RewardAndPunishmentMapper rewardandpunishmentMapper;
    @Autowired
    private UidGenService uidGenService;

    @Override
    public String createRAP(int type,long userId, String information) {
        RewardAndPunishmentEnity rewardandpunishmentEnity = new RewardAndPunishmentEnity();
        long RAPId = uidGenService.getUid();
        rewardandpunishmentEnity.setId(RAPId);
        rewardandpunishmentEnity.setUser_id(userId);
        rewardandpunishmentEnity.setInformation(information);
        rewardandpunishmentEnity.setType(type);
        rewardandpunishmentEnity.setExt1(null);
        rewardandpunishmentEnity.setExt2(null);
        rewardandpunishmentEnity.setCreate_time(new Timestamp(System.currentTimeMillis()));
        rewardandpunishmentMapper.CreateMessage(rewardandpunishmentEnity);
        return Long.toString(RAPId);
    }

    @Override
    public HashMap<String, Object> getAllMessage(Integer pageNum, Integer pageSize) {
        List<List<RewardAndPunishmentEnity>> list = rewardandpunishmentMapper.getAllMessage((pageNum - 1) * pageSize, pageSize);
        List<RewardAndPunishmentEnityBo> list1 = new ArrayList<>();
        HashMap<String, Object> data = new HashMap<>();
        list.get(0).forEach(
                e -> {
                    list1.add(RewardAndPunishmentEnityBo.builder()
                            .id(e.getId())
                            .user_id(e.getUser_id())
                            .information(e.getInformation())
                            .type(e.getType())
                            .ext1(e.getExt1())
                            .ext2(e.getExt2())
                            .create_time(e.getCreate_time())
                            .build());
                }
        );
        data.put("messageList", list1);
        data.put("total", list.get(1).get(0));
        return data;
    }

    @Override
    public HashMap<String, Object> getRAPInformationBytype(int type,String queryInfo, String searchInfo, int pageNum, int pageSize) {
        List<List<RewardAndPunishmentEnity>> list = rewardandpunishmentMapper.selRAPinformationByType(type,queryInfo, searchInfo, (pageNum - 1) * pageSize, pageSize);
        List<RewardAndPunishmentEnityBo> boList=new ArrayList<>();
        HashMap<String,Object> resultMap=new HashMap<>();
        list.get(0).forEach(e->{
            boList.add(RewardAndPunishmentEnityBo.builder()
                    .id(e.getId())
                    .user_id(e.getUser_id())
                    .information(e.getInformation())
                    .type(e.getType())
                    .ext1(e.getExt1())
                    .ext2(e.getExt2())
                    .create_time(e.getCreate_time())
                    .build());
        });
        resultMap.put("total",list.get(1).get(0));
        resultMap.put("messageList",boList);
        return resultMap;
    }




}
