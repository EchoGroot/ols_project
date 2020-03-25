package com.ols.ols_project.mapper;

import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.entity.TaskEntity;
import com.ols.ols_project.model.entity.UserEntity;
import com.ols.ols_project.model.UserSignUp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**关于用户操作的mapper
 * @author yuyy
 * @date 20-2-19 下午2:11
 */
public interface UserMapper {

    UserEntity getUserInfoById(long id);

    String getPassWodById(long id);

    int changePassWordById(long id,String passWord);

    List<List<AcceptTask>> getAcceptTaskByUserId(long id, String query, int start, int end, String queryInfo,String searchInfo);

    List<List<TaskEntity>> getReleaseTaskByUserId(long id, String query, int start, int end, String queryInfo, String searchInfo);

    List<List<UserSignUp>> getReviewerSignUp(String queryInfo, String searchInfo , int start, Integer end);

    int yesAndNoReviewerSignUp(long userId,String operation);

    int changeEmailById(long userId,String email);

    Long login(String userName,String passWord);

}
