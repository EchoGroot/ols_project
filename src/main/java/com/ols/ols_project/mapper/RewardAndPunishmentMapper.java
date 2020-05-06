package com.ols.ols_project.mapper;

import com.ols.ols_project.model.FinishCheckTask;
import com.ols.ols_project.model.MonthAndCount;
import com.ols.ols_project.model.entity.RewardAndPunishmentEnity;

import java.util.List;

/**
 * 奖惩信息的Mapper
 * @author zs
 * @date 20-4-18
 */
public interface RewardAndPunishmentMapper {
    //发布奖惩信息
   void CreateMessage(RewardAndPunishmentEnity rewardandpunishmentEntity);
   //查询所有奖惩信息
   List<List<RewardAndPunishmentEnity>> getAllMessage( int start, int end);
    void clickNumPlus(long messageId);
    List<RewardAndPunishmentEnity> getClickNum();
    int subpunishment(long userId, String operation);
    List<List<RewardAndPunishmentEnity>> selRAPinformationByType(int type,String queryInfo, String searchInfo, int start, int end);
    //查询所有奖励信息
   List<List<RewardAndPunishmentEnity>> getRInformationById( long id,int start, int end);
    //查询所有惩罚信息
    List<List<RewardAndPunishmentEnity>> getPInformationById( long id,int start, int end);
    //查询所有奖励惩罚信息
    List<List<RewardAndPunishmentEnity>> getRPInformationById( long id,int start, int end);
    //奖励惩罚可视化
    List<MonthAndCount> getInformationByUserId(long userId, int year, int type);
}
