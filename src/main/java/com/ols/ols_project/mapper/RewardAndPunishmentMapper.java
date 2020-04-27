package com.ols.ols_project.mapper;

import com.ols.ols_project.model.FinishCheckTask;
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


}
