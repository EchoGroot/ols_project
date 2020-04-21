package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.model.entity.UserEntity;
import com.ols.ols_project.service.RewardAndPunishmentService;
import com.ols.ols_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("information")
public class RewardAndPunishmentController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private RewardAndPunishmentService rewardAndPunishmentService;
    //发布奖惩信息
    @PostMapping("/createMessage")
    public String createMessage(@RequestParam("userId") long userId,
                                @RequestParam("deductPoints") int deductPoints,
                                @RequestParam("information") String information,
                                @RequestParam("type") int type
                                ){
        if(type==1){
            //奖励
            taskService.awardPoints(deductPoints,userId);
            rewardAndPunishmentService.createRAP(type, userId, information);
            return "奖励成功";
        }else if (type==0){
            if(taskService.deductRewardPoints(deductPoints,userId)==1){
                //已经扣除积分了
                // 新建信息
                rewardAndPunishmentService.createRAP(type, userId, information);
                return "扣除成功";
            }else{
                //积分不够没扣掉
                return "积分不足";
            }
        }
        return "";
    }
    //查询所有奖惩信息
    @GetMapping("/getAllMessage")
    public String getAllMessage(
            @RequestParam(value = "page") Integer pageNum,
            @RequestParam(value = "limit") Integer pageSize
    ){
        String result= JSON.toJSONStringWithDateFormat(
                new Result(
                        rewardAndPunishmentService.getAllMessage( pageNum, pageSize)
                        ,"0"
                        ,"获取所有奖惩信息成功")
                ,"yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString);
        return result;
    }
}
