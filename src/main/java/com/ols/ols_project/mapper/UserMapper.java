package com.ols.ols_project.mapper;

import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.TaskEntity;
import com.ols.ols_project.model.UserEntity;

import java.util.List;

/**关于用户操作的mapper
 * @author yuyy
 * @date 20-2-19 下午2:11
 */
public interface UserMapper {

    UserEntity getUserInfoById(int id);

    String getPassWodById(int id);

    int changePassWordById(int id,String passWord);

    List<List<AcceptTask>> getAcceptTaskByUserId(int id, String query, int start, int end);

    List<List<TaskEntity>> getReleaseTaskByUserId(int id, String query, int start, int end);
}
