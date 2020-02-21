package com.ols.ols_project.service.Impl;

import com.ols.ols_project.mapper.UserMapper;
import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.AcceptTaskForData;
import com.ols.ols_project.model.UserEntity;
import com.ols.ols_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yuyy
 * @date 20-2-19 下午2:20
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity getUserInfoById(int id) {
        return userMapper.getUserInfoById(id);
    }

    @Override
    public String getPassWodById(int id) {
        return userMapper.getPassWodById(id);
    }

    @Override
    public int changePassWordById(int id, String passWord) {
        return userMapper.changePassWordById(id,passWord);
    }

    @Override
    public List<AcceptTask> getAcceptTaskByUserId(int id, String query, int pageNum, int pageSize) {
        return userMapper.getAcceptTaskByUserId(id, query, pageNum, pageSize);
       /* List<AcceptTask> acceptTaskByUserId = userMapper.getAcceptTaskByUserId(id, query, pageNum, pageSize);
        List<AcceptTaskForData> acceptTaskForDataList=new ArrayList<>();
        for( AcceptTask acceptTask:acceptTaskByUserId){
            Date date = new Date();
            AcceptTaskForData acceptTaskForData = new AcceptTaskForData();
            try {
                date = acceptTask.getOls_task_finish_time();
            } catch (Exception e) {
                System.out.println("时间格式转换异常：TimeStamp to Date");
                e.printStackTrace();
            }
            acceptTaskForData.setOls_accept_finish_time(date);
            try {
                date = acceptTask.getRelease_time();
            } catch (Exception e) {
                System.out.println("时间格式转换异常：TimeStamp to Date");
                e.printStackTrace();
            }
            acceptTaskForData.setRelease_time(date);
            try {
                date = acceptTask.getAccept_time();
            } catch (Exception e) {
                System.out.println("时间格式转换异常：TimeStamp to Date");
                e.printStackTrace();
            }
            acceptTaskForData.setAccept_time(date);
            try {
                date = acceptTask.getOls_accept_finish_time();
            } catch (Exception e) {
                System.out.println("时间格式转换异常：TimeStamp to Date");
                e.printStackTrace();
            }
            acceptTaskForData.setOls_accept_finish_time(date);
            acceptTaskForData.setOls_task_id(acceptTask.getOls_task_id());
            acceptTaskForData.setName(acceptTask.getName());
            acceptTaskForData.setOls_task_url(acceptTask.getOls_task_url());
            acceptTaskForData.setInformation(acceptTask.getInformation());
            acceptTaskForData.setPoints(acceptTask.getPoints());
            acceptTaskForData.setOls_task_state(acceptTask.getOls_task_state());
            acceptTaskForData.setType(acceptTask.getType());
            acceptTaskForData.setRelease_user_id(acceptTask.getRelease_user_id());
            acceptTaskForData.setAccepte_num(acceptTask.getAccepte_num());
            acceptTaskForData.setAdopt_accepte_id(acceptTask.getAdopt_accepte_id());
            acceptTaskForData.setOls_accept_id(acceptTask.getOls_accept_id());
            acceptTaskForData.setUser_id(acceptTask.getUser_id());
            acceptTaskForData.setOls_accepte_state(acceptTask.getOls_accepte_state());
            acceptTaskForData.setOls_accepte_url(acceptTask.getOls_accepte_url());
            acceptTaskForDataList.add(acceptTaskForData);
        }
        return acceptTaskForDataList;*/
    }
}
