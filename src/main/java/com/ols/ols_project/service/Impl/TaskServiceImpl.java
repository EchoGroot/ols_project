package com.ols.ols_project.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baidu.fsg.uid.service.UidGenService;
import com.ols.ols_project.common.Const.FileTypeEnum;
import com.ols.ols_project.common.Const.IsPassedEnum;
import com.ols.ols_project.common.Const.TaskStateEnum;
import com.ols.ols_project.common.utils.XmlUtil;
import com.ols.ols_project.mapper.TaskMapper;
import com.ols.ols_project.mapper.UserMapper;
import com.ols.ols_project.model.*;
import com.ols.ols_project.model.entity.AccepteEntity;
import com.ols.ols_project.model.entity.JudgeEntity;
import com.ols.ols_project.model.entity.TaskEntity;
import com.ols.ols_project.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 任务相关的service实现类
 * @author yuyy
 * @date 20-2-24 下午7:13
 */
@Service
@Slf4j
@Transactional(rollbackFor=Exception.class)
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;
    @Resource
    private UserMapper userMapper;

    @Autowired
    private UidGenService uidGenService;

    @Override
    public String getImageListByTaskId(long taskId) {
        return taskMapper.getImageListByTaskId(taskId);
    }

    @Override
    public AcceptTaskForTaskInfo getAccepteImageListByAccepteId(long acceptId) { return taskMapper.getAccepteImageListByAccepteId(acceptId);
    }

    @Override
    public int storeImageLabelInfo(long acceptTaskId, List<LabelInfo> labelInfos, String imageUrlParam) {
        AccepteEntity acceptEntity = taskMapper.getAccepteTaskInfoByAcceptId(acceptTaskId);
        AccepteImageUrl acceptImageUrl = JSON.parseObject(acceptEntity.getUrl(), new TypeReference<AccepteImageUrl>() {});
        acceptImageUrl.getTaskImage().stream().forEach(e->{
            if(e.getOriginalImage().equals(imageUrlParam)){
                e.setLabeledInfo(labelInfos);
                e.setIsLabeled(true);
                e.setIsExample(false);
                return;
            }
        });
        //转xml测试
        log.debug(XmlUtil.convertToXml(acceptImageUrl));
        return taskMapper.storeImageLabelInfoByAccepteId(acceptTaskId,JSON.toJSONString(acceptImageUrl));
    }

    @Override
    public List<TaskEntityBo> getNotCheckedTask(long userId) {
        List<TaskEntity> entityList = taskMapper.getNotCheckedTask(userId);
        List<TaskEntityBo> entryListBo=new ArrayList<>();
        entityList.stream().forEach(
                taskEntity -> {
                    entryListBo.add(
                            TaskEntityBo.builder()
                                    .id(taskEntity.getId())
                                    .name(taskEntity.getName())
                                    .url(taskEntity.getUrl())
                                    .information(taskEntity.getInformation())
                                    .points(taskEntity.getPoints())
                                    .state(TaskStateEnum.CHECK.getName())
                                    .type(FileTypeEnum.getNameByCode(taskEntity.getType()))
                                    .release_time(taskEntity.getRelease_time())
                                    .release_user_id(taskEntity.getRelease_user_id())
                                    .ext1(taskEntity.getExt1())
                                    .ext2(taskEntity.getExt2())
                                    .ext3(taskEntity.getExt3())
                                    .build()
                    );
                }
        );
        return entryListBo;
    }

    @Override
    public int setNotCheckedTaskForUser(long userId, int count) {
        return taskMapper.setNotCheckedTaskForUser(userId,count);
    }

    @Override
    public TaskEntityBo getTaskInfoByTaskId(long taskId) {
        TaskEntity taskEntity = taskMapper.getTaskInfoByTaskId(taskId);
        return TaskEntityBo.builder()
                .id(taskEntity.getId())
                .name(taskEntity.getName())
                .url(taskEntity.getUrl())
                .information(taskEntity.getInformation())
                .points(taskEntity.getPoints())
                .state(TaskStateEnum.getNameByCode(taskEntity.getState()))
                .type(TaskStateEnum.getNameByCode(taskEntity.getType()))
                .release_time(taskEntity.getRelease_time())
                .release_user_id(taskEntity.getRelease_user_id())
                .ext1(taskEntity.getExt1())
                .ext2(taskEntity.getExt2())
                .ext3(taskEntity.getExt3())
                .build();
    }

    @Override
    public int acceptTask(long userId, long taskId) {
        int result=0;
        //查询任务信息
        TaskEntityBo taskInfoByTaskId = getTaskInfoByTaskId(taskId);
        //写入任务接受表
        result+=taskMapper.insAcceptTask(AccepteEntity.builder()
                .id(uidGenService.getUid())
                .task_id(taskInfoByTaskId.getId())
                .user_id(userId)
                .accept_time(new Timestamp(System.currentTimeMillis()))
                .state(0)
                .url(taskInfoByTaskId.getUrl())
                .build());
        //获取该任务的接受人数
        int acceptNum = taskMapper.selAcceptNumOfTask(taskId);
        //重新写入该任务的接收人数
        result+=taskMapper.updAcceptNumOfTask(taskId,++acceptNum);
        return result;
    }

    @Override
    public int taskPassOrNotPassAudits(long userId, long taskId, String operation,String message) {
        int count=taskMapper.taskPassOrNotPassAudits(taskId,operation);
        int ispassed=operation.equals("yes")?1:0;
        if(count==1){
            count=taskMapper.insJudge(JudgeEntity.builder()
                    .id(uidGenService.getUid())
                    .user_id(userId)
                    .task_id(taskId)
                    .ispassed(ispassed)
                    .isfirst(1)
                    .message(message)
                    .judge_time(new Timestamp(System.currentTimeMillis()))
                    .build());
        }
        return count;
    }

    @Override
    public HashMap<String, Object> getFinishCheckTaskByUserId(long userId,String queryInfo, String searchInfo, int pageNum, int pageSize) {
        List<List<FinishCheckTask>> list = taskMapper.selFinishCheckTaskByUserId(userId,queryInfo, searchInfo, (pageNum - 1) * pageSize, pageSize);
        List<FinishCheckTaskBo> boList=new ArrayList<>();
        HashMap<String,Object> resultMap=new HashMap<>();
        list.get(0).forEach(e->{
            boList.add(FinishCheckTaskBo.builder()
                    .taskId(e.getTaskId())
                    .taskName(e.getTaskName())
                    .releaseTime(e.getReleaseTime())
                    .releaseUserName(e.getReleaseUserName())
                    .judgeId(e.getJudgeId())
                    .isPassed(IsPassedEnum.getNameByCode(e.getIsPassed()))
                    .message(e.getMessage())
                    .judgeTime(e.getJudgeTime())
                    .build());
        });
        resultMap.put("total",list.get(1).get(0));
        resultMap.put("taskList",boList);
        return resultMap;
    }

    @Override
    public int submitAcceptTask(long acceptId, long taskId) {
        return taskMapper.updExt1(taskId,Integer.parseInt(taskMapper.getTaskInfoByTaskId(taskId).getExt1())+1)
                +taskMapper.updAcceptState(acceptId);
    }


    /**
     * @author wjp
     * @date 2020/3/21 下午22:06
     */
    //String taskUrl, int state,Timestamp releaseTime, Timestamp finishTime,int acceptNum, Long adoptAcceptId
    @Override
    public String creatTask(String taskName,String taskUrl, String taskInfo, int rewardPoints, int type,
                          Long releaseUserId) {
        TaskEntity taskEntity = new TaskEntity();
        long taskId = uidGenService.getUid();
        taskEntity.setId(taskId);//"taskId自动生成"
        taskEntity.setName(taskName);
        taskEntity.setUrl(taskUrl);
        taskEntity.setInformation(taskInfo);
        taskEntity.setPoints(rewardPoints);
        taskEntity.setState(5);//默认类型5 已发布
        taskEntity.setType(type);
        taskEntity.setRelease_time(new Timestamp(System.currentTimeMillis()));
        taskEntity.setFinish_time(null);
        taskEntity.setRelease_user_id(releaseUserId);
        taskEntity.setAccept_num(0);
        taskEntity.setAdopt_accept_id(null);
        taskEntity.setExt1("0");
        taskEntity.setExt2("0");
        taskEntity.setExt3(0);
        taskMapper.creatTask(taskEntity);
        return Long.toString(taskId);
    }

    @Override
    public int deductRewardPoints(int rewardPoints,long releaseUserId){
        if(rewardPoints<=userMapper.getPoints(releaseUserId)){
            int resultPoints = userMapper.getPoints(releaseUserId)-rewardPoints;
            userMapper.setPoints(resultPoints,releaseUserId);
            return 1;
        }else {
            return 0;
        }
    }
    @Override
    public int awardPoints(int rewardPoints,long releaseUserId){
        int resultPoints = userMapper.getPoints(releaseUserId)+rewardPoints;
        userMapper.setPoints(resultPoints,releaseUserId);
        return 0;
    }

    @Override
    public String creatTaskUrl(String labelName,String originalImage) {
        System.out.println("/*************创建URL******************/");

        String [] labelName1= labelName.split(",");
        String [] originalImage1 = originalImage.split(",");

        JSONObject taskUrl = new JSONObject();
        taskUrl.put("labelName",labelName1);
        JSONArray taskImage = new JSONArray();
        for (int i = 0; i < originalImage1.length; i++) {
            JSONObject imgInfo = new JSONObject();
            imgInfo.put("isExample",false);
            imgInfo.put("isLabeled",false);
            JSONArray labelInfo =new JSONArray();//labelInfo也遍历添加数据
            //for (int j = 0; j < labelName1.length; j++)
            imgInfo.put("labelInfo",labelInfo);
            imgInfo.put("originalImage",originalImage1[i]);
            taskImage.add(imgInfo);//遍历添加
        }
        taskUrl.put("taskImage",taskImage);
        return JSON.toJSONString(taskUrl,SerializerFeature.WriteNonStringValueAsString);
    }

    @Override
    public HashMap<String, Object> getAllTask(String query, Integer pageNum, Integer pageSize,
                                              String queryInfo, String searchType,String searchInfo,String field,String order) {
        List<List<TaskEntity>> list = taskMapper.getAllTask(query, (pageNum - 1) * pageSize, pageSize,queryInfo,searchType,searchInfo,field,order);
        List<TaskEntityBo> list1=new ArrayList<>();
        HashMap<String,Object> data=new HashMap<>();
        list.get(0).forEach(
                e->{
                    list1.add(TaskEntityBo.builder()
                            .id(e.getId())
                            .name(e.getName())
                            .information(e.getInformation())
                            .points(e.getPoints())
                            .state(TaskStateEnum.getNameByCode(e.getState()))
                            .type(FileTypeEnum.getNameByCode(e.getType()))
                            .release_time(e.getRelease_time())
                            .finish_time(e.getFinish_time())
                            .release_user_id(e.getRelease_user_id())
                            .accept_num(e.getAccept_num())
                            .adopt_accept_id(e.getAdopt_accept_id())
                            .ext1(e.getExt1())
                            .ext2(e.getExt2())
                            .ext3(e.getExt3())
                            .build());
                }
        );
        data.put("taskList",list1);
        data.put("total",list.get(1).get(0));
        return data;
    }
    @Override
    public void clickNumPlus(long taskId){
        taskMapper.clickNumPlus(taskId);
    }
    @Override
    public List<TaskEntity> getClickNum(){
        List<TaskEntity> list = taskMapper.getClickNum();
        return list;
    }
    @Override
    public JSONArray getFileNameByTaskId(long taskId){
        JSONArray fileNameArray = new JSONArray();//定义要返回的ImageName数组
        String url = taskMapper.getImageListByTaskId(taskId);//获取图片url
        //从url中截取图片名,返回数组
        JSONObject urlJson = JSONObject.parseObject(url);//将url转json对象
        JSONArray taskFileArray = JSONArray.parseArray(urlJson.getString("taskImage"));//获取taskImage数组
        for(int i=0;i<taskFileArray.size();i++)
        {
            JSONObject taskImage = JSONObject.parseObject(taskFileArray.get(i).toString());//获取taskImage数组的每个对象
            fileNameArray.add(taskImage.getString("originalImage"));//获取taskImage[i]对象的originalImage值，并赋值到新数组内
        }
        return fileNameArray;
    }
    //管理员删除任务文件时使用    ！！慎用 会使任务获取不到源图片文件
    @Override
    public void delImgFileByTaskId(long taskId){
        //删文件
        JSONArray fileNameArray = getFileNameByTaskId(taskId);
        String path = "G:\\images\\";
        for(int i=0;i<fileNameArray.size();i++){
            File fromFile = new File(path+fileNameArray.get(i));//找到文件
            if (fromFile.exists()) {
                fromFile.delete();//遍历删除文件夹及其子内容
            }
        }
    }
}
