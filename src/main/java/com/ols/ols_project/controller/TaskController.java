package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.ols.ols_project.common.Const.NormalConst;
import com.ols.ols_project.model.AcceptTaskBo;
import com.ols.ols_project.model.LabelInfo;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.model.TaskEntityBo;
import com.ols.ols_project.service.TaskService;
import com.ols.ols_project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 关于任务的Controller
 * @author yuyy
 * @date 20-2-24 下午7:03
 */

@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    /**
     * 不知道这方法还在用没，先注释掉，如果项目正常运行，就删掉这个方法
     * 根据任务ID获取图片信息（ols_task表）
     * @param taskId
     * @return
     */
//    @GetMapping("/getImageListByTaskId")
//    public String getImageListByTaskId(
//            @RequestParam("taskId") int taskId,
//        @RequestParam("userId") int userId
//    ){
//        log.info("用户ID：{}，");
//        HashMap<String,Object> data=new HashMap<>();
//        data.put("taskImage",taskService.getImageListByTaskId(taskId));
//        return JSON.toJSONString(new Result(data,"200","获取任务图片数据成功"));
//    }

    /**
     * 根据接受任务ID获取图片信息（ols_accept表）
     * @param acceptId
     * @return
     */
    @GetMapping("/getAcceptImageListByAcceptId")
    public String getAcceptImageListByAcceptId(
            @RequestParam("acceptId") String acceptId,
            @RequestParam("userId") String userId
    ){
        String resultStr=null;
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskImage",
                taskService.getAccepteImageListByAccepteId(Long.parseLong(acceptId)));
        resultStr= JSON.toJSONStringWithDateFormat(
                new Result(data,"200","获取接受任务图片数据成功"),
                "yyyy-MM-dd hh:mm:ss");
        return resultStr;
    }

    /**
     * 根据任务ID获取任务信息（ols_task表）
     * @param
     * @return
     */
    @GetMapping("/getTaskInfoByTaskId")
    public String getTaskInfoByTaskId(
            @RequestParam("taskId") String taskId,
            @RequestParam("userId") String userId
    ){
        String resultStr=null;
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskInfo",
                taskService.getTaskInfoByTaskId(Long.parseLong(taskId)));
        resultStr= JSON.toJSONStringWithDateFormat(
                new Result(data,"200","获取任务数据成功"),
                "yyyy-MM-dd hh:mm:ss");
        return resultStr;
    }

    /**
     * 存储图片标注信息
     *
     * @return
     */
    @PostMapping(value = "/storeImageLabelInfo")
    public String storeImageLabelInfo(
            @RequestParam("userId") String userId,
            @RequestParam("acceptId") String acceptId,
            @RequestParam("imageUrlParam") String imageUrlParam,
            @RequestParam("labelInfo") String labelInfo

    ){
        String resultStr=null;
        List<LabelInfo> labelInfoList= (List<LabelInfo>)JSON.parse(labelInfo);
        if(1==taskService.storeImageLabelInfo(
                Long.parseLong(acceptId),
                labelInfoList,
                imageUrlParam)){
            resultStr= JSON.toJSONString(
                    new Result("200","存储图片标注数据成功"));
        }else{
            resultStr=JSON.toJSONString(
                    new Result("201","存储图片标注数据失败"));
        }
        return resultStr;
    }

    /**
     * 获取待审核的任务
     * userId：审核者账号
     * @param userId
     * @return
     */
    @PostMapping(value = "/getNotCheckedTask")
    public String getNotCheckedTask(@RequestParam("userId") String userId){
        String resultStr=null;
        //第一次查询，查的是已分配给该审核者的
        List<TaskEntityBo> notCheckedTask = taskService.getNotCheckedTask(Long.parseLong(userId));
        if(notCheckedTask.size() < NormalConst.setNotCheckedTaskForUserCount){
            //分配任务给该审核者
            taskService.setNotCheckedTaskForUser(
                    Long.parseLong(userId),
                    NormalConst.setNotCheckedTaskForUserCount-notCheckedTask.size());
            //第二次查询
            notCheckedTask = taskService.getNotCheckedTask(Long.parseLong(userId));
        }
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskList",notCheckedTask);
        resultStr=JSON.toJSONStringWithDateFormat(
                new Result(data,"0","获取待审核任务成功"),
                "yyyy-MM-dd hh-mm-ss");
        return resultStr;
    }

    /**
     * 接受任务
     * @param userId 用户id
     * @param taskId 任务id（ols_task表）
     * @return
     */
    @PostMapping(value = "/acceptTask")
    public String acceptTask(
            @RequestParam("userId") String userId,
            @RequestParam("taskId") String taskId
                             ){
        String resultStr=null;
        //查询该用户是否已接受此任务
        List<AcceptTaskBo>acceptTaskByUserId=
                (List<AcceptTaskBo>)userService.getAcceptTaskByUserId(Long.parseLong(userId),"",0,0,"","").get("taskList");
        for(AcceptTaskBo item:acceptTaskByUserId){
            if(item.getTaskId()==Long.parseLong(taskId)){
                resultStr=JSON.toJSONString(
                        new Result("201","该用户已接受此任务"));
                break;
            }
        }
        if(resultStr==null){
            if(2==taskService.acceptTask(Long.parseLong(userId),Long.parseLong(taskId))){
                resultStr=JSON.toJSONString(
                        new Result("200","接受任务成功"));
            }else{
                resultStr=JSON.toJSONString(
                        new Result("202","接受任务失败"));
            }
        }
        return resultStr;
    }

    /**
     * 任务是否通过审核
     * @param userId
     * @param taskId
     * @param operation
     * @param message
     * @return
     */
    @PostMapping(value = "/taskPassOrNotPassAudits")
    public String taskPassOrNotPassAudits(
            @RequestParam("userId") String userId,
            @RequestParam("taskId") String taskId,
            @RequestParam("operation") String operation,
            @RequestParam("message") String message
                             ){
        String resultStr=null;
        if(1==taskService.taskPassOrNotPassAudits(
                Long.parseLong(userId),
                Long.parseLong(taskId),
                operation,
                message
                )){
            resultStr=JSON.toJSONString(new Result("200","操作成功"));

        }else{
            resultStr=JSON.toJSONString(new Result("201","操作失败，请刷新再试"));

        }
        return resultStr;
    }

    /**
     * 根据审核者id获取已审核的任务
     * @param pageNum
     * @param pageSize
     * @param userId
     * @param queryInfo 筛选条件
     * @param searchInfo 搜索关键字（任务名）
     * @return
     */
    @GetMapping("/getFinishCheckTaskByUserId")
    public String getFinishCheckTaskByUserId(
            @RequestParam("page") int pageNum,
            @RequestParam("limit") int pageSize,
            @RequestParam("userId") String userId,
            @RequestParam(value = "queryInfo") String  queryInfo,
            @RequestParam(value = "searchInfo") String  searchInfo
    ){
        // layui默认数据表格的status为0才显示数据
        String result=JSON.toJSONStringWithDateFormat(
                new Result(taskService.getFinishCheckTaskByUserId(Long.parseLong(userId),queryInfo,searchInfo,pageNum, pageSize),"0","根据审核者id获取已审核的任务"),
                "yyyy-MM-dd");
        return result;
    }

    /**
     * 提交已接受的任务
     * @param userId
     * @param acceptId
     * @param taskId
     * @return
     */
    @PostMapping(value = "/submitAcceptTask")
    public String submitAcceptTask(
            @RequestParam("userId") String userId,
            @RequestParam("acceptId") String acceptId,
            @RequestParam("taskId") String taskId
    ){
        String resultStr=null;
        if(2==taskService.submitAcceptTask(Long.parseLong(acceptId),Long.parseLong(taskId))){
            resultStr= JSON.toJSONString(
                    new Result("200","提交已接受的任务成功"));
        }else{
            resultStr=JSON.toJSONString(
                    new Result("201","提交已接受的任务失败"));
        }
        return resultStr;
    }
}
