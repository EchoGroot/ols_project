package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.ols.ols_project.common.Const.NormalConst;
import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.LabelInfo;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.model.TaskEntityBo;
import com.ols.ols_project.service.TaskService;
import com.ols.ols_project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 关于任务的Controller
 * @author yuyy
 * @date 20-2-24 下午7:03
 */

@Slf4j
@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    /**
     * 根据任务ID获取图片信息（ols_task表）
     * @param taskId
     * @return
     */
    @RequestMapping("/getImageListByTaskId")
    public String getImageListByTaskId(
            @RequestParam("taskId") int taskId,
        @RequestParam("userId") int userId
    ){
        log.info("用户ID：{}，");
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskImage",taskService.getImageListByTaskId(taskId));
        return JSON.toJSONString(new Result(data,"200","获取任务图片数据成功"));
    }

    /**
     * 根据接受任务ID获取图片信息（ols_accept表）
     * @param acceptId
     * @return
     */
    @RequestMapping("/getAcceptImageListByAcceptId")
    public String getAcceptImageListByAcceptId(
            @RequestParam("acceptId") int acceptId,
            @RequestParam("userId") int userId
    ){
        log.info("用户ID：{}，根据接受任务ID获取图片信息（ols_accept表），接受任务ID：{}",userId,acceptId);
        String resultStr=null;
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskImage",taskService.getAccepteImageListByAccepteId(acceptId));
        resultStr= JSON.toJSONStringWithDateFormat(new Result(data,"200","获取接受任务图片数据成功"),"yyyy-mm-dd hh:mm:ss");
        log.info("用户ID：{}，根据接受任务ID获取图片信息（ols_accept表），接受任务ID：{}，result:{}",userId,acceptId,resultStr);
        return resultStr;
    }

    /**
     * 根据任务ID获取任务信息（ols_task表）
     * @param
     * @return
     */
    @RequestMapping("/getTaskInfoByTaskId")
    public String getTaskInfoByTaskId(
            @RequestParam("taskId") String taskId,
            @RequestParam("userId") String userId
    ){
        log.info("用户ID：{}，根据任务ID获取任务信息（ols_task表），任务ID：{}",userId,taskId);
        String resultStr=null;
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskInfo",taskService.getTaskInfoByTaskId(Integer.parseInt(taskId)));
        resultStr= JSON.toJSONStringWithDateFormat(new Result(data,"200","获取任务数据成功"),"yyyy-mm-dd hh:mm:ss");
        log.info("用户ID：{}，根据任务ID获取任务信息（ols_task表），任务ID：{}，result:{}",userId,taskId,resultStr);
        return resultStr;
    }

    /**
     * 存储图片标注信息
     *
     * @return
     */
    @RequestMapping(value = "/storeImageLabelInfo",
            method = RequestMethod.POST)
    public String storeImageLabelInfo(
            @RequestParam("userId") int userId,
            @RequestParam("acceptId") int acceptId,
            @RequestParam("imageUrlParam") String imageUrlParam,
            @RequestParam("labelInfo") String labelInfo

    ){
        log.info("用户ID：{}，存储图片标注信息，接受任务ID：{}，图片名称：{}，标注信息：{}",
                userId,
                acceptId,
                imageUrlParam,
                labelInfo
        );
        String resultStr=null;
        List<LabelInfo> labelInfoList= (List<LabelInfo>)JSON.parse(labelInfo);
        if(taskService.storeImageLabelInfo(
                acceptId,
                labelInfoList,
                imageUrlParam)==1){
            resultStr= JSON.toJSONString(new Result("200","存储图片标注数据成功"));
        }else{
            resultStr=JSON.toJSONString(new Result("201","存储图片标注数据失败"));
        }
        log.info("用户ID：{}，存储图片标注信息，接受任务ID：{}，图片名称：{}，标注信息：{}，result：{}",
                userId,
                acceptId,
                imageUrlParam,
                labelInfo,
                resultStr
        );
        return resultStr;
    }

    /**
     * 获取待审核的任务
     * userId：审核者账号
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getNotCheckedTask",method = RequestMethod.POST)
    public String getNotCheckedTask(@RequestParam("userId") String userId){
        log.info("审核者ID：{}，获取待审核的任务",userId);
        String resultStr=null;
        //第一次查询，查的是已分配给该审核者的
        List<TaskEntityBo> notCheckedTask = taskService.getNotCheckedTask(Integer.parseInt(userId));
        if(notCheckedTask.size() < NormalConst.setNotCheckedTaskForUserCount){
            //分配任务给该审核者
            taskService.setNotCheckedTaskForUser(Integer.parseInt(userId),NormalConst.setNotCheckedTaskForUserCount-notCheckedTask.size());
            //第二次查询
            notCheckedTask = taskService.getNotCheckedTask(Integer.parseInt(userId));
        }
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskList",notCheckedTask);
        resultStr=JSON.toJSONStringWithDateFormat(new Result(data,"0","获取待审核任务成功"),"yyyy-MM-dd hh-mm-ss");
        log.info("审核者ID：{}，获取待审核的任务，result：{}",userId,resultStr);
        return resultStr;
    }

    /**
     * 接受任务
     * @param userId 用户id
     * @param taskId 任务id（ols_task表）
     * @return
     */
    @RequestMapping(value = "/acceptTask",method = RequestMethod.POST)
    public String acceptTask(
            @RequestParam("userId") String userId,
            @RequestParam("taskId") String taskId
                             ){
        log.info("普通用户ID：{}，接受任务，任务ID:{}",userId,taskId);
        String resultStr=null;
        //查询该用户是否已接受此任务
        List<List<AcceptTask>>acceptTaskByUserId=userService.getAcceptTaskByUserId(Integer.parseInt(userId),"",0,0);
        for(AcceptTask item:acceptTaskByUserId.get(0)){
            if(item.getOls_task_id()==Integer.parseInt(taskId)){
                resultStr=JSON.toJSONString(new Result("201","该用户已接受此任务"));
                log.info("普通用户ID：{}，接受任务，任务ID:{}，result：{}",userId,taskId,resultStr);
                break;
            }
        }
        if(resultStr==null){
            if(2==taskService.acceptTask(Integer.parseInt(userId),Integer.parseInt(taskId))){
                resultStr=JSON.toJSONString(new Result("200","接受任务成功"));
                log.info("普通用户ID：{}，接受任务，任务ID:{}，result：{}",userId,taskId,resultStr);
            }else{
                resultStr=JSON.toJSONString(new Result("202","接受任务失败"));
                log.info("普通用户ID：{}，接受任务，任务ID:{}，result：{}",userId,taskId,resultStr);
            }
        }
        return resultStr;
    }
}
