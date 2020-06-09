package com.ols.ols_project.mapper;

import com.ols.ols_project.model.MonthAndCount;
import com.ols.ols_project.model.entity.MessageEntity;
import com.ols.ols_project.model.entity.RewardAndPunishmentEnity;

import java.util.List;

/**
 * 举报信息的Mapper
 * @author zs
 * @date 20-4-10
 */
public interface MessageMapper {
    //发布举报信息
   void CreateMessage(MessageEntity messageEntity);
   //查询所有举报信息
   List<List<MessageEntity>> getAllMessage( String queryInfo, String searchInfo ,int start, int end);
    //回复举报信息
    int replyMessage(String response,Long id);
    //奖励惩罚可视化
    List<MonthAndCount> getmessage( int year, int type);

    //查询举报回复信息
    List<List<MessageEntity>> getcomplainById(long id, int start, int end);
}
