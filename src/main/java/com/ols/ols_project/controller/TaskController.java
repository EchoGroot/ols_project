package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.ols.ols_project.model.LabelInfo;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 关于任务的Controller
 * @author yuyy
 * @date 20-2-24 下午7:03
 */

@Controller
@RequestMapping("task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/getImageListByTaskId")
    @ResponseBody
    public String getImageListByTaskId(@RequestParam("taskId") int taskId){
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskImage",taskService.getImageListByTaskId(taskId));
        return JSON.toJSONString(new Result(data,"200","获取任务图片数据成功"));
    }

    @RequestMapping("/getAccepteImageListByAccepteId")
    @ResponseBody
    public String getAccepteImageListByAccepteId(@RequestParam("accepteId") int accepteId){
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskImage",taskService.getAccepteImageListByAccepteId(accepteId));
        return JSON.toJSONStringWithDateFormat(new Result(data,"200","获取接受任务图片数据成功"),"yyyy-mm-dd hh:mm:ss");
    }

    @RequestMapping("/storeImageLabelInfo")
    @ResponseBody
    public String storeImageLabelInfo(
            @RequestParam("accepteTaskId") int accepteTaskId,
            @RequestParam("labelInfo")LabelInfo[] labelInfos,
            @RequestParam("imageUrlParam") String imageUrlParam
            ){
        System.out.println("accepteTaskId:"+accepteTaskId
                +" labelInfo:"+labelInfos
                +" imageUrlParam:"+imageUrlParam);
        return JSON.toJSONString(new Result("200","存储图片标注数据成功"));
    }
}
