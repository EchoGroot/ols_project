package com.ols.ols_project.mapper;

import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.UserOperationLog;
import com.ols.ols_project.model.entity.TaskEntity;
import com.ols.ols_project.model.entity.UserEntity;
import com.ols.ols_project.model.UserSignUp;
import com.ols.ols_project.model.entity.UserOperationLogEntity;
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

    List<List<AcceptTask>> getAcceptTaskByUserId(long id, String query, int start, int end, String queryInfo,String searchInfo,String taskType);

    List<List<TaskEntity>> getReleaseTaskByUserId(long id, String query, int start, int end, String queryInfo, String searchInfo,String taskType);

    List<List<UserSignUp>> getReviewerSignUp(String queryInfo, String searchInfo , int start, Integer end);

    int yesAndNoReviewerSignUp(long userId,String operation);

    int changeEmailById(long userId,String email);

    Long login(String userName,String passWord);

    Long checkUserName(String userName);

    int userLoginTime(UserOperationLogEntity userLog);

    int userRegister(UserEntity user);

    int userRegisterTime(UserOperationLogEntity userLog);

    String getEmailByName(String userName);

    int changePasswordByName(String userName,String password);

    List<List<UserSignUp>> getUserSignUp(String queryInfo, String searchInfo , int start, Integer end);

    int getPoints(long userId);

    int setPoints(int points,long userId);

    int insmessage();

    List<List<UserOperationLog>> getUserOperationLog(int start, Integer end,String user_id);

    int deleteUser(String userId);

    List<UserEntity> getPointsRank();

}
