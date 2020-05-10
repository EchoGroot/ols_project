package com.ols.ols_project.mapper;

import com.ols.ols_project.model.entity.MessageEntity;
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
    void replyMessage(MessageEntity messageEntity);
}
