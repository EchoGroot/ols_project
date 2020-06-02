package com.ols.ols_project.service.Impl;

import com.baidu.fsg.uid.service.UidGenService;
import com.ols.ols_project.common.Const.FileTypeEnum;
import com.ols.ols_project.common.Const.IsPassedEnum;
import com.ols.ols_project.common.Const.TaskStateEnum;
import com.ols.ols_project.mapper.RewardAndPunishmentMapper;
import com.ols.ols_project.model.*;
import com.ols.ols_project.model.entity.JudgeEntity;
import com.ols.ols_project.model.entity.RewardAndPunishmentEnity;
import com.ols.ols_project.model.entity.TaskEntity;
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

    @Override
    public HashMap<String, Object> getRInformationById(long userId,Integer pageNum, Integer pageSize) {
        List<List<RewardAndPunishmentEnity>> list = rewardandpunishmentMapper.getRInformationById(userId,(pageNum - 1) * pageSize, pageSize);
        List<RewardAndPunishmentEnityBo> list1=new ArrayList<>();
        HashMap<String,Object> data=new HashMap<>();
        list.get(0).forEach(
                e->{
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
        data.put("messageList",list1);
        data.put("total",list.get(1).get(0));
        return data;
    }

   @Override
    public HashMap<String, Object> getPInformationById(long userId,Integer pageNum, Integer pageSize) {
        List<List<RewardAndPunishmentEnity>> list = rewardandpunishmentMapper.getPInformationById(userId,(pageNum - 1) * pageSize, pageSize);
        List<RewardAndPunishmentEnityBo> list1=new ArrayList<>();
        HashMap<String,Object> data=new HashMap<>();
        list.get(0).forEach(
                e->{
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
        data.put("messageList",list1);
        data.put("total",list.get(1).get(0));
        return data;
    }

    @Override
    public HashMap<String, Object> getRPInformationById(long userId,Integer pageNum, Integer pageSize) {
        List<List<RewardAndPunishmentEnity>> list = rewardandpunishmentMapper.getRPInformationById(userId,(pageNum - 1) * pageSize, pageSize);
        List<RewardAndPunishmentEnityBo> list1=new ArrayList<>();
        HashMap<String,Object> data=new HashMap<>();
        list.get(0).forEach(
                e->{
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
        data.put("messageList",list1);
        data.put("total",list.get(1).get(0));
        return data;
    }

    @Override
    public int[][] getInformationByUserId(long userId, int year) {
        int[][] resultArr=new int[3][];
//        获取惩罚
        List<MonthAndCount> list = rewardandpunishmentMapper.getInformationByUserId(userId, year, 0);
//        获取奖励
        List<MonthAndCount> list1 = rewardandpunishmentMapper.getInformationByUserId(userId, year, 1);
//        惩罚
        int[] no=new int[12];
//        奖励
        int[] yes=new int[12];
//        总数量
        int[] yesAndNo=new int[12];
        int j=0,k=0;
        for (int i=0;i<12;i++){
            no[i]=0;
            yes[i]=0;
            if(j<list.size()&&Integer.parseInt(list.get(j).getMonth())==(i+1)){
                no[i]=Integer.parseInt(list.get(j++).getCount());
            }
            if(k<list1.size()&&Integer.parseInt(list1.get(k).getMonth())==(i+1)){
                yes[i]=Integer.parseInt(list1.get(k++).getCount());
            }
            yesAndNo[i]=yes[i]+no[i];
        }
        resultArr[0]=yes;
        resultArr[1]=no;
        resultArr[2]=yesAndNo;
        return resultArr;
    }

    @Override
    public int[][] getRAPmessage( int year) {
        int[][] resultArr=new int[3][];
//        获取惩罚
        List<MonthAndCount> list = rewardandpunishmentMapper.getRAPmessage( year, 0);
//        获取奖励
        List<MonthAndCount> list1 = rewardandpunishmentMapper.getRAPmessage(year, 1);
//        惩罚
        int[] no=new int[12];
//        奖励
        int[] yes=new int[12];
//        总数量
        int[] yesAndNo=new int[12];
        int j=0,k=0;
        for (int i=0;i<12;i++){
            no[i]=0;
            yes[i]=0;
            if(j<list.size()&&Integer.parseInt(list.get(j).getMonth())==(i+1)){
                no[i]=Integer.parseInt(list.get(j++).getCount());
            }
            if(k<list1.size()&&Integer.parseInt(list1.get(k).getMonth())==(i+1)){
                yes[i]=Integer.parseInt(list1.get(k++).getCount());
            }
            yesAndNo[i]=yes[i]+no[i];
        }
        resultArr[0]=yes;
        resultArr[1]=no;
        resultArr[2]=yesAndNo;
        return resultArr;
    }
}
