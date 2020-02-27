package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.ols.ols_project.model.LabelInfo;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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

    /**
     * 根据任务ID获取图片信息（ols_task表）
     * @param taskId
     * @return
     */
    @RequestMapping("/getImageListByTaskId")
    @ResponseBody
    public String getImageListByTaskId(@RequestParam("taskId") int taskId){
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskImage",taskService.getImageListByTaskId(taskId));
        return JSON.toJSONString(new Result(data,"200","获取任务图片数据成功"));
    }

    /**
     * 根据接受任务ID获取图片信息（ols_accepte表）
     * @param accepteId
     * @return
     */
    @RequestMapping("/getAccepteImageListByAccepteId")
    @ResponseBody
    public String getAccepteImageListByAccepteId(@RequestParam("accepteId") int accepteId){
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskImage",taskService.getAccepteImageListByAccepteId(accepteId));
        return JSON.toJSONStringWithDateFormat(new Result(data,"200","获取接受任务图片数据成功"),"yyyy-mm-dd hh:mm:ss");
    }

    /**
     * 存储图片标注信息
     * @param param
     * @return
     */
    @RequestMapping(value = "/storeImageLabelInfo",method = RequestMethod.POST)
    @ResponseBody
    public String storeImageLabelInfo(@RequestBody HashMap<String,Object> param){
        List<LabelInfo> labelInfoList= (List<LabelInfo>)param.get("labelInfo");
        if(taskService.storeImageLabelInfo(
                (Integer)param.get("accepteTaskId"),
                labelInfoList,
                (String) param.get("imageUrlParam"))==1){
            return JSON.toJSONString(new Result("200","存储图片标注数据成功"));
        }
        return JSON.toJSONString(new Result("201","存储图片标注数据失败"));
    }
}
