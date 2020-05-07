package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baidu.fsg.uid.service.UidGenService;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.model.entity.UserOperationLogEntity;
import com.ols.ols_project.service.MessageService;
import com.ols.ols_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Long.parseLong;

//关于举报信息的controller
@RestController
@RequestMapping("message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private UidGenService uidGenService;
    //发布举报信息
    @GetMapping("/createMessage")
    public String createMessage(@RequestParam("userId") String userId,
                                @RequestParam("taskId") String taskId,
                                @RequestParam("Message") String Message
                                //@RequestParam("type") int type
                                ){
        messageService.createMessage(Long.parseLong(userId),Long.parseLong(taskId),Message);
        String resultStr = JSON.toJSONString(new Result("200","创建举报信息成功"));
        //更新操作日志
        UserOperationLogEntity userLog=new UserOperationLogEntity();
        userLog.setId(uidGenService.getUid());
        userLog.setUser_id(Long.parseLong(userId));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        userLog.setTime(Timestamp.valueOf(df.format(date)));
        userLog.setOperation("举报任务"+taskId+"("+Message+")");
        userService.userOperation(userLog);
        return resultStr;
    }
//查询所有举报信息
    @GetMapping("/getAllMessage")
    public String getAllMessage(
                                @RequestParam(value = "page") Integer pageNum,
                                @RequestParam(value = "limit") Integer pageSize,
                                @RequestParam(value = "queryInfo") String queryInfo,
                                @RequestParam(value = "searchInfo") String searchInfo
                                ){
        String result= JSON.toJSONStringWithDateFormat(
                new Result(
                        messageService.getAllMessage( queryInfo, searchInfo ,pageNum, pageSize)
                        ,"0"
                        ,"获取所有举报信息成功")
                ,"yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString);
        return result;
    }
}
