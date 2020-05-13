package com.ols.ols_project.service.Impl;

import com.baidu.fsg.uid.service.UidGenService;
import com.ols.ols_project.mapper.MessageMapper;
import com.ols.ols_project.model.MessageEnityBo;
import com.ols.ols_project.model.MonthAndCount;
import com.ols.ols_project.model.entity.MessageEntity;
import com.ols.ols_project.service.MessageService;
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
public class MessageServicelmpl implements MessageService {
    //发布举报信息
    @Resource
    private MessageMapper messageMapper;

    @Autowired
    private UidGenService uidGenService;
    @Override
    public String createMessage(long userId, long taskId,String Message,int type) {
        MessageEntity messageEntity = new MessageEntity();
        long messageId = uidGenService.getUid();
        messageEntity.setId(messageId);
        messageEntity.setUser_id(userId);
        messageEntity.setTask_id(taskId);
        messageEntity.setMessage(Message);
        messageEntity.setIshandled(0);
        messageEntity.setIsfirst(1);
        messageEntity.setType(type);
        messageEntity.setExt2(null);
        messageEntity.setExt3(0);
        messageEntity.setCreate_time(new Timestamp(System.currentTimeMillis()));
        messageMapper.CreateMessage(messageEntity);
        return Long.toString(messageId);
    }

    @Override
    public HashMap<String, Object> getAllMessage(String queryInfo,String searchInfo ,Integer pageNum, Integer pageSize) {
        List<List<MessageEntity>> list = messageMapper.getAllMessage(queryInfo, searchInfo , (pageNum - 1) * pageSize, pageSize);
        List<MessageEnityBo> list1=new ArrayList<>();
        HashMap<String,Object> data=new HashMap<>();
        list.get(0).forEach(
                e->{
                    list1.add(MessageEnityBo.builder()
                            .id(e.getId())
                            .user_id(e.getUser_id())
                            .task_id(e.getTask_id())
                            .message(e.getMessage())
                            .ishandled(e.getIshandled())
                            .isfirst(e.getIsfirst())
                            .response(e.getResponse())
                            .type(e.getType())
                            .ext2(e.getExt2())
                            .ext3(e.getExt3())
                            .create_time(e.getCreate_time())
                            .build());
                }
        );
        data.put("messageList",list1);
        data.put("total",list.get(1).get(0));
        return data;
    }
    //回复举报信息
    @Override
    public String replyMessage(String Message) {
        MessageEntity messageEntity = new MessageEntity();
        long messageId = uidGenService.getUid();
        messageEntity.setId(messageId);
        messageEntity.setUser_id(null);
        messageEntity.setTask_id(null);
        messageEntity.setMessage(Message);
        messageEntity.setIshandled(1);
        messageEntity.setIsfirst(0);
        messageEntity.setType(0);
        messageEntity.setExt2(null);
        messageEntity.setExt3(0);
        messageEntity.setCreate_time(new Timestamp(System.currentTimeMillis()));
        messageMapper.replyMessage(messageEntity);
        return Long.toString(messageId);
    }
//举报可视化
    @Override
    public int[][] getmessage( int year) {
        int[][] resultArr=new int[3][];
//        获取举报文档
        List<MonthAndCount> list = messageMapper.getmessage( year, 0);
//        获取举报图片
        List<MonthAndCount> list1 = messageMapper.getmessage( year, 1);
//        图片
        int[] no=new int[12];
//        文档
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

