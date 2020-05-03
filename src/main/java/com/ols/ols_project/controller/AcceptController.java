package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.service.AcceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("accept")
public class AcceptController {
    @Autowired
    private AcceptService acceptService;

    @GetMapping("/getPersonalAcceptByUserId")
    public String getPersonalAcceptByUserId(
            @RequestParam("year") String year,
            @RequestParam("userId") String userId
    ){
        HashMap<String, Object> data = new HashMap<>();
        data.put("acceptList",
                acceptService.getPersonalAcceptByUserId(Long.parseLong(userId),Integer.parseInt(year)));
        String result= JSON.toJSONStringWithDateFormat(
                new Result(data,"200","获取个人已接受成功"),
                "yyyy-MM-dd");
        return result;
    }
    @GetMapping("/getAcceptListByTaskId")
    public String getAcceptListByTaskId(@RequestParam(value = "taskId") Long taskId,
                             @RequestParam(value = "page") Integer pageNum,
                             @RequestParam(value = "limit") Integer pageSize){
        String result= JSON.toJSONStringWithDateFormat(
                new Result(
                        acceptService.getAcceptListByTaskId(taskId, pageNum, pageSize)
                        ,"0"
                        ,"获取接受任务列表成功")
                ,"yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString);
        return result;
    }
    @PostMapping("/adoptByAcceptId")
    public String adoptByAcceptId(@RequestParam(value = "acceptId") Long acceptId,
                                  @RequestParam(value = "taskId") Long taskId) {
        String str;
        if (acceptService.adoptByAcceptId(acceptId, taskId) == "200") {
            str = "采纳成功！";
        } else {
            str = "未知错误！";
        }
        return str;
    }

    //余游洋做了？
    @GetMapping("/createAccept")
    public String createAccept(@RequestParam("taskId") long taskId, @RequestParam("userId") long userId){

        acceptService.createAccept(taskId);
        String resultStr = JSON.toJSONString(new Result("200","1"));
        return resultStr;
    }

    @GetMapping("/getAcceptTaskByTaskId")
    public String getAcceptTaskByTaskId(@RequestParam("taskId") String taskId
            ,@RequestParam("page") String pageNum
            ,@RequestParam("limit") String pageSize
    ){
        HashMap<String,Object> data= acceptService.getAcceptByTaskId(Long.parseLong(taskId)
                ,Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        String resultStr = JSON.toJSONStringWithDateFormat(new Result(data,"0","查询接受任务成功")
                ,"yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString
        );
        return resultStr;
    }
}
