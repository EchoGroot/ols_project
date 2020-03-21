package com.ols.ols_project.mapper;

import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.TaskEntity;
import com.ols.ols_project.model.UserEntity;
import com.ols.ols_project.model.UserSignUp;

import java.util.List;

/**关于用户操作的mapper
 * @author yuyy
 * @date 20-2-19 下午2:11
 */
public interface UserMapper {

    UserEntity getUserInfoById(int id);

    String getPassWodById(int id);

    int changePassWordById(int id,String passWord);

    List<List<AcceptTask>> getAcceptTaskByUserId(int id, String query, int start, int end, String queryInfo, String searchInfo);

    List<List<TaskEntity>> getReleaseTaskByUserId(int id, String query, int start, int end, String queryInfo, String searchInfo);

    List<List<UserSignUp>> getReviewerSignUp(String queryInfo, String searchInfo , int start, Integer end);

    int yesAndNoReviewerSignUp(int userId,String operation);

}
