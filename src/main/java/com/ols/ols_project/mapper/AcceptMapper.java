package com.ols.ols_project.mapper;

import com.ols.ols_project.model.MonthAndCount;
import com.ols.ols_project.model.AcceptByTaskId;
import com.ols.ols_project.model.entity.AcceptEntity;

import java.util.List;

public interface AcceptMapper {
    List<MonthAndCount> getPersonalAcceptByUserId(long userId, int year, int state);
    List<List<AcceptEntity>> getAcceptListByTaskId(long taskId, int start, int end);
    int setAdoptState(long acceptId);//采纳并扣除和奖励积分
    int setNotAdoptState(long taskId);//批量更改未采纳状态
    long getUserId(long acceptId);
    AcceptEntity getSystemByAcceptId(long acceptId);
/**
 * 关于接收任务的Mapper
 * @author cc
 * @date 20-4-18
 */

    void createAccept(AcceptEntity acceptEntity);
    void adopt(long id,int toState);
    List<List<AcceptByTaskId>> getAcceptByTaskId(Long taskId,int start,int end);
    List<MonthAndCount> getPersonalAcceptDocByUserId(long userId, int year, int state);
}

