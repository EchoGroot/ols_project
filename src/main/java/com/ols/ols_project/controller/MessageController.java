package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.service.MessageService;
import org.springframework.web.bind.annotation.*;

//关于举报信息的controller
@RestController
@RequestMapping("message")
public class MessageController {
    MessageService messageService;
    //发布举报信息
    @GetMapping("/createMessage")
    public String createMessage(@RequestParam("userId") String userId,@RequestParam("taskId") String taskId,@RequestParam("Message") String Message){
        messageService.createMessage(Long.parseLong(userId),Long.parseLong(taskId),Message);
        String resultStr = JSON.toJSONString(new Result("200","创建举报信息成功"));;
        return resultStr;
    }
//查询所有举报信息
    @GetMapping("/getAllMessage")
    public String getAllMessage(
                                @RequestParam(value = "page") Integer pageNum,
                                @RequestParam(value = "limit") Integer pageSize
                                ){
        String result= JSON.toJSONStringWithDateFormat(
                new Result(
                        messageService.getAllMessage( pageNum, pageSize)
                        ,"0"
                        ,"获取所有举报信息成功")
                ,"yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString);
        return result;
    }
}
