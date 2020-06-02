package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ols.ols_project.model.Result;
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
    //管理员查询所有奖惩信息
    @GetMapping("/getAllMessage")
    public String getAllMessage(
            @RequestParam("page") int pageNum,
            @RequestParam("limit") int pageSize
          //  @RequestParam("type") int type,
           // @RequestParam(value = "queryInfo") String  queryInfo,
           // @RequestParam(value = "searchInfo") String  searchInfo
    ){
        String result= JSON.toJSONStringWithDateFormat(
                new Result(
                        rewardAndPunishmentService.getAllMessage(pageNum, pageSize)
                        ,"0"
                        ,"获取所有奖惩信息成功")
                ,"yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString);
        return result;
    }

    /**
     * 根据type查询奖励惩罚信息
     * @param pageNum
     * @param pageSize
     * @param type
     * @param queryInfo 筛选条件
     * @param searchInfo 搜索关键字（任务名）
     * @return
     */
    @GetMapping("/getRAPInformationBytype")
    public String getRAPInformationBytype(
            @RequestParam("page") int pageNum,
            @RequestParam("limit") int pageSize,
            @RequestParam("type") int type,
            @RequestParam(value = "queryInfo") String  queryInfo,
            @RequestParam(value = "searchInfo") String  searchInfo
    ){
        // layui默认数据表格的status为0才显示数据
        String result=JSON.toJSONStringWithDateFormat(
                new Result(rewardAndPunishmentService.getRAPInformationBytype(type,queryInfo,searchInfo,pageNum, pageSize),"0","根据类型获取奖励或惩罚的信息"),
                "yyyy-MM-dd");
        return result;
    }
    //根据用户ID查看奖励信息
    @GetMapping(value = "/getRInformationById")
    public String getRInformationById(
            @RequestParam(value = "userId") String userId,
            @RequestParam("page") int pageNum,
            @RequestParam("limit") int pageSize
    ) {
        System.out.println("1");
        // layui默认数据表格的status为0才显示数据
        String result = JSON.toJSONStringWithDateFormat(
                new Result(
                        rewardAndPunishmentService.getRInformationById(Long.parseLong(userId),pageNum, pageSize)
                        , "0"
                        , "获取奖励信息成功")
                , "yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString
        );
        return result;
    }
    //根据用户ID查看惩罚信息
    @GetMapping(value = "/getPInformationById")
    public String getPInformationById(
            @RequestParam(value = "userId") String userId,
            @RequestParam("page") int pageNum,
            @RequestParam("limit") int pageSize
    ) {
        // layui默认数据表格的status为0才显示数据
        String result = JSON.toJSONStringWithDateFormat(
                new Result(
                        rewardAndPunishmentService.getPInformationById(Long.parseLong(userId),pageNum, pageSize)
                        , "0"
                        , "获取奖励信息成功")
                , "yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString
        );
        return result;
    }
    //根据用户ID查看奖惩信息
    @GetMapping(value = "/getRPInformationById")
    public String getRPInformationById(
            @RequestParam(value = "userId") String userId,
            @RequestParam("page") int pageNum,
            @RequestParam("limit") int pageSize
    ) {
        // layui默认数据表格的status为0才显示数据
        String result = JSON.toJSONStringWithDateFormat(
                new Result(
                        rewardAndPunishmentService.getRPInformationById(Long.parseLong(userId),pageNum, pageSize)
                        , "0"
                        , "获取奖励惩罚信息成功")
                , "yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString
        );
        return result;
    }
    //奖惩可视化用户个人
    @GetMapping("/getInformationByUserId")
    public String getInformationByUserId(
            @RequestParam("year") String year,
            @RequestParam("userId") String userId
    ){
        System.out.println("111111111");
        HashMap<String, Object> data = new HashMap<>();
        data.put("rapList",
                rewardAndPunishmentService.getInformationByUserId(Long.parseLong(userId),Integer.parseInt(year)));
        String result= JSON.toJSONStringWithDateFormat(
                new Result(data,"200","获取个人奖惩成功"),
                "yyyy-MM-dd");
        return result;
    }

    //奖惩信息可视化（管理员）
    @GetMapping("/getRAPmessage")
    public String getRAPmessage(
            @RequestParam("year") String year
    ){
        System.out.println("111111111");
        HashMap<String, Object> data = new HashMap<>();
        data.put("rapList",
                rewardAndPunishmentService.getRAPmessage(Integer.parseInt(year)));
        String result= JSON.toJSONStringWithDateFormat(
                new Result(data,"200","获取奖惩信息成功"),
                "yyyy-MM-dd");
        return result;
    }
}
