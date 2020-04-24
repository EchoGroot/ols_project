package com.ols.ols_project.service.Impl;

import com.ols.ols_project.common.Const.AcceptStateEnum;
import com.ols.ols_project.common.Const.FileTypeEnum;
import com.ols.ols_project.common.Const.TaskStateEnum;
import com.ols.ols_project.mapper.AcceptMapper;
import com.ols.ols_project.mapper.TaskMapper;
import com.ols.ols_project.mapper.UserMapper;
import com.ols.ols_project.model.AcceptEntityBo;
import com.ols.ols_project.model.AcceptTaskBo;
import com.ols.ols_project.model.MonthAndCount;
import com.ols.ols_project.model.entity.AcceptEntity;
import com.ols.ols_project.model.entity.TaskEntity;
import com.ols.ols_project.model.entity.UserEntity;
import com.ols.ols_project.service.AcceptService;
import com.ols.ols_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(rollbackFor=Exception.class)
public class AcceptServiceImpl implements AcceptService {
    @Autowired
    AcceptMapper acceptMapper;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public int[][] getPersonalAcceptByUserId(long userId, int year) {
        int[][] resultArr=new int[3][];
//        获取接受未完成
        List<MonthAndCount> list = acceptMapper.getPersonalAcceptByUserId(userId, year, 0);
//        获取接受并完成
        List<MonthAndCount> list1 = acceptMapper.getPersonalAcceptByUserId(userId, year, 1);
//        未完成
        int[] no=new int[12];
//        已完成
        int[] yes=new int[12];
//        总数量
        int[] yesAndNo=new int[12];
        int j=0,k=0;
        for (int i=0;i<12;i++){
            no[i]=0;
            yes[i]=0;
            if(j<list.size()&&Integer.parseInt(list.get(j).getMonth())==(i+1)){
                no[i]=Integer.parseInt(list.get(j++).getCount());
            }
            if(k<list1.size()&&Integer.parseInt(list1.get(k).getMonth())==(i+1)){
                yes[i]=Integer.parseInt(list1.get(k++).getCount());
            }
            yesAndNo[i]=yes[i]+no[i];
        }
        resultArr[0]=yes;
        resultArr[1]=no;
        resultArr[2]=yesAndNo;
        return resultArr;
    }

    @Override
    public HashMap<String, Object> getAcceptListByTaskId(Long taskId, Integer pageNum, Integer pageSize) {
        List<List<AcceptEntity>> list = acceptMapper.getAcceptListByTaskId(taskId, (pageNum - 1) * pageSize, pageSize);
        List<AcceptEntityBo> list1=new ArrayList<>();
        HashMap<String,Object> data=new HashMap<>();
        list.get(0).forEach(
                e->{
                    list1.add(AcceptEntityBo.builder()
                            .acceptId(e.getId())
                            .acceptUserId(e.getUser_id())
                            .acceptUserName(userMapper.getUserInfoById(e.getUser_id()).getName())
                            .acceptTime(e.getAccept_time())
                            .finishTime(e.getFinish_time())
                            .state(AcceptStateEnum.getNameByCode(e.getState()))
                            .url(e.getUrl())
                            .build());
                }
        );
        data.put("acceptList",list1);
        data.put("total",list.get(1).get(0));
        return data;
    }

    @Override
    public String adoptByAcceptId(long acceptId, long taskId) {
        acceptMapper.setAdoptState(acceptId);
        acceptMapper.setNotAdoptState(taskId);

        long userId=acceptMapper.getUserId(acceptId);
        //奖励积分
        int resultPoints = userMapper.getPoints(userId)+taskMapper.getPoints(taskId);
        userMapper.setPoints(resultPoints,userId);
        //任务状态已完成
        taskMapper.setFinishById(taskId,acceptId);
        return "200";
    }
}
