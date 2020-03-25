package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ols.ols_project.common.Const.NormalConst;
import com.ols.ols_project.common.utils.SendEmailBy126;
import com.ols.ols_project.model.*;
import com.ols.ols_project.model.entity.UserEntity;
import com.ols.ols_project.service.TaskService;
import com.ols.ols_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @Autowired
    private SendEmailBy126 sendEmailBy126;

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
            UserEntity userInfo = userService.getUserInfoById(Long.parseLong(userId));
            //发送邮件通知
            sendEmailBy126.sendEmail(
                    userInfo.getEmail()
                    ,"Ols系统通知"
                    ,message);
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

    /**
     * 创建ols_task表的测试数据
     * @author wjp
     * @date 2020/3/21 23.30
     */
    @PostMapping("/createTask")
    public String createTask( String taskName,String taskUrl, String taskInfo, int rewardPoints, int type, Long releaseUserId){
        taskService.creatTask(taskName,taskUrl,taskInfo,rewardPoints, type,releaseUserId);
        return "ok";
    }

    @PostMapping("/creatTaskUrl")
    public String creatTaskUrl(String lableName, String originalImage){
        return taskService.creatTaskUrl(lableName,originalImage);
    }

    @PostMapping("uploadImgs")
    public String uplpadImgs(@RequestParam("file") MultipartFile file) {
        String desFilePath = "";
        String oriName = "";
        try {
            // 1.获取原文件名
            oriName = file.getOriginalFilename();
            // 2.获取原文件图片后缀名extensionName，以最后的.作为截取(.jpg)
            String extName = oriName.substring(oriName.lastIndexOf("."));
            // 3.生成自定义的新文件名，这里以UUID.jpg|png|xxx作为格式（可选操作，也可以不自定义新文件名）
            String uuid = UUID.randomUUID().toString();//生成通用唯一识别码
            String newName = uuid + extName;
            //String realPath = request.getRealPath("http://yuyy.info/image/ols/");
            // 4.保存绝对路径
            desFilePath = "G:\\images\\" + newName;
            File desFile = new File(desFilePath);
            file.transferTo(desFile);
            // 6.返回保存结果信息
            HashMap<String,Object> dataMap=new HashMap<>();
            dataMap.put("src", "static/Home/image" + newName);
            dataMap.put("imgName", newName);
            Result result = new Result(dataMap,"0","上传成功");
            System.out.println(desFilePath+"图片保存成功");
            return JSON.toJSONString(result);
        } catch (IllegalStateException e) {
            Result result = new Result("1","图片保存失败");
            System.out.println(desFilePath + "图片保存失败");
            return JSON.toJSONString(result);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Result result = new Result("1","图片保存失败--IO异常");
            System.out.println(desFilePath + "图片保存失败--IO异常");
            return JSON.toJSONString(result);
        }
    }
    @GetMapping("/getAllTask")
    public String getAllTask(@RequestParam(value = "query") String query,
                             @RequestParam(value = "page") Integer pageNum,
                             @RequestParam(value = "limit") Integer pageSize,
                             @RequestParam(value = "queryInfo") String queryInfo,
                             @RequestParam(value = "searchInfo") String searchInfo){
        String result= JSON.toJSONStringWithDateFormat(
                new Result(
                        taskService.getAllTask(query, pageNum, pageSize,queryInfo,searchInfo)
                        ,"0"
                        ,"获取所有任务成功")
                ,"yyyy-MM-dd hh:mm:ss");
        return result;
    }
}
