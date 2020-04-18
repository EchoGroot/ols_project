package com.ols.ols_project.service.Impl;

import com.baidu.fsg.uid.service.UidGenService;
import com.ols.ols_project.mapper.MessageMapper;
import com.ols.ols_project.model.MessageEnityBo;
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
    public String createMessage(long userId, long taskId,String Message) {
        MessageEntity messageEntity = new MessageEntity();
        long messageId = uidGenService.getUid();
        messageEntity.setId(messageId);
        messageEntity.setUser_id(userId);
        messageEntity.setTask_id(taskId);
        messageEntity.setMessage(Message);
        messageEntity.setIshandled(0);
        messageEntity.setIsfirst(1);
        messageEntity.setExt1(null);
        messageEntity.setExt2(null);
        messageEntity.setExt3(0);
        messageEntity.setCreate_time(new Timestamp(System.currentTimeMillis()));
        messageMapper.CreateMessage(messageEntity);
        return Long.toString(messageId);
    }

    @Override
    public HashMap<String, Object> getAllMessage(Integer pageNum, Integer pageSize) {
        List<List<MessageEntity>> list = messageMapper.getAllMessage( (pageNum - 1) * pageSize, pageSize);
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
                            .ext1(e.getExt1())
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
}
