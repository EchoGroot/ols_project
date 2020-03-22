package com.ols.ols_project.service.Impl;

import com.ols.ols_project.mapper.JudgeMapper;
import com.ols.ols_project.model.MonthAndCount;
import com.ols.ols_project.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuyy
 * @date 20-3-17 下午5:23
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class JudgeServiceImpl implements JudgeService {

    @Autowired
    private JudgeMapper judgeMapper;

    @Override
    public int[][] getHistoryByUserId(long userId,int year) {
        int[][] resultArr=new int[3][];
//        获取审核未通过的审核历史
        List<MonthAndCount> list = judgeMapper.getHistoryByUserId(userId, year, 0);
//        获取审核通过的审核历史
        List<MonthAndCount> list1 = judgeMapper.getHistoryByUserId(userId, year, 1);
//        审核通过
        int[] yes=new int[12];
//        审核未通过
        int[] no=new int[12];
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
}
