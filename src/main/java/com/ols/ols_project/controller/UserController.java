package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.ols.ols_project.common.utils.SendEmailBy126;
import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.model.TaskEntity;
import com.ols.ols_project.model.UserEntity;
import com.ols.ols_project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 用户相关的Controller
 * @author yuyy
 * @date 20-2-18 下午3:56
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SendEmailBy126 sendEmailBy126;

    /**
     * 获取用户信息
     * userId：用户ID
     * @param param
     * @return
     */

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public String getUserInfo(@RequestBody HashMap<String,Object> param){
        log.info("用户ID：{}，获取用户信息",(String) param.get("userId"));
        String resultStr=null;
        UserEntity userInfoById = userService.getUserInfoById(
                Integer.parseInt((String)param.get("userId"))
        );
        if(userInfoById == null){
            Result result = new Result("201", "未找到该用户");
            resultStr= JSON.toJSONString(result);
        }else{
            HashMap<String,Object> data=new HashMap<>();
            data.put("userInfo",userInfoById);
            resultStr= JSON.toJSONStringWithDateFormat(new Result(data,"200","获取用户信息成功"),"yyyy-MM-dd");
        }
        log.info("用户ID：{}，获取用户信息，result：{}",(String) param.get("userId"),resultStr);
        return resultStr;
    }

    /**
     * 更改密码
     * @param param
     * @return
     */
    @RequestMapping(value = "/changePassWord",method = RequestMethod.POST)
    public String changePassWord(@RequestBody HashMap<String,Object> param){
        log.info("用户ID：{}，更改密码",(String) param.get("id"));
        String resultStr = null;
        String passWodById = userService.getPassWodById(Integer.parseInt((String) param.get("id")));
        if(passWodById.equals((String) param.get("oldpassword"))){
            if(1==userService.changePassWordById(
                    Integer.parseInt((String) param.get("id")),
                    (String)param.get("newpassword"))){
                resultStr= JSON.toJSONString(new Result("200","修改密码成功"));
            }else{
                resultStr= JSON.toJSONString(new Result("201","修改密码失败"));
            }
        }else {
            resultStr=JSON.toJSONString(new Result("202","修改密码失败，原密码错误"));
        }
        log.info("用户ID：{}，更改密码,result:{}",(String) param.get("id"),resultStr);
        return resultStr;
    }

    /**
     * 获取用户已接受的任务
     * @param id
     * @param query 'acceptfinish'：已完成的任务，'acceptnotfinish'：未完成的任务
     * @param pagenum
     * @param pagesize 如果等于0，就是全部查询出来，否则就是分页查询
     * @return
     */
    @RequestMapping(value = "/getAcceptTaskByUserId",method = RequestMethod.GET)
    public String getAcceptTaskByUserId(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "query") String query,
            @RequestParam(value = "pagenum") Integer pagenum,
            @RequestParam(value = "pagesize") Integer pagesize
    ){
        List<List<AcceptTask>> acceptTaskByUserId = userService.getAcceptTaskByUserId(id, query, pagenum, pagesize
        );
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskList",acceptTaskByUserId.get(0));
        data.put("total",acceptTaskByUserId.get(1).get(0));
        return JSON.toJSONStringWithDateFormat(new Result(data,"200","获取已接受任务成功"),"yyyy-mm-dd hh:mm:ss");
    }

    /**
     * 获取用户已发布的任务
     * @param id
     * @param query
     * @param pagenum
     * @param pagesize
     * @return
     */
    @RequestMapping(value = "/getReleaseTaskByUserId",method = RequestMethod.GET)
    public String getReleaseTaskByUserId(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "query") String query,
            @RequestParam(value = "pagenum") Integer pagenum,
            @RequestParam(value = "pagesize") Integer pagesize
    ){
        List<List<TaskEntity>> releaseTaskByUserId = userService.getReleaseTaskByUserId(id, query, pagenum, pagesize);
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskList",releaseTaskByUserId.get(0));
        data.put("total",releaseTaskByUserId.get(1).get(0));
        return JSON.toJSONStringWithDateFormat(new Result(data,"200","获取已发布任务成功"),"yyyy-mm-dd hh:mm:ss");
    }

    /**
     * 查询审核者注册账号
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getReviewerSignUp",method = RequestMethod.GET)
    public String getReleaseTaskByUserId(
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "page") Integer pageNum,
            @RequestParam(value = "limit") Integer pageSize,
            @RequestParam(value = "queryInfo") String  queryInfo,
            @RequestParam(value = "searchInfo") String  searchInfo
    ){
        log.info("管理员ID：{}，查询审核者注册账号，pageNum:{},pageSize:{},queryInfo:{},searchInfo:{}",userId,pageNum,pageSize,queryInfo,searchInfo);
        HashMap<String, Object> data = userService.getReviewerSignUp(queryInfo,searchInfo,pageNum, pageSize);
        // layui默认数据表格的status为0才显示数据
        String result=JSON.toJSONStringWithDateFormat(new Result(data,"0","获取待批准的审核者注册账号成功"),"yyyy-MM-dd");
        log.info("管理员ID：{}，查询审核者注册账号，result:{}",userId,result);
        return result;
    }

    /**
     * 管理员同意或不同意审核者账号注册
     * userIdOfSignUp：操作的审核者账号
     * userId：管理员帐号
     * operation：操作（同意注册，不同意注册）
     * @param param
     * @return
     */
    @RequestMapping(value = "/yesReviewerSignUp",method = RequestMethod.POST)
    public String yesReleaseTaskByUserId(@RequestBody HashMap<String,Object> param) {
        log.info("管理员ID：{}，操作的审核者账号：{}，操作：{}，管理员同意或不同意审核者账号注册"
                ,(String)param.get("userId")
                ,(Integer)param.get("userIdOfSignUp")
                ,(String) param.get("operation")
        );
        String resultStr=null;
        if("yes".equals((String) param.get("operation"))){
            if(1==userService.yesAndNoReviewerSignUp((Integer)param.get("userIdOfSignUp"),(String) param.get("operation"))){
                //给审核者发送邮件提醒
                UserEntity userInfo = userService.getUserInfoById((Integer) param.get("userIdOfSignUp"));
                sendEmailBy126.sendEmail(
                        userInfo.getEmail()
                        ,"Ols系统通知"
                        ,"恭喜您注册的审核者账号通过管理员的批准，可以正常使用了。");
                resultStr=JSON.toJSONString(new Result("200","同意注册成功"));
            }else{
                resultStr=JSON.toJSONString(new Result("201","同意注册失败，请刷新页面"));
            }
        }else {
            if (1 == userService.yesAndNoReviewerSignUp((Integer) param.get("userIdOfSignUp"), (String) param.get("operation"))) {
                //给审核者发送邮件提醒
                UserEntity userInfo = userService.getUserInfoById((Integer) param.get("userIdOfSignUp"));
                sendEmailBy126.sendEmail(
                        userInfo.getEmail()
                        , "Ols系统通知"
                        , "Sorry!您注册的审核者账号未能通过管理员的批准，请联系Ols管理员。");

                resultStr = JSON.toJSONString(new Result("200", "不同意注册成功"));
            }else{
                resultStr = JSON.toJSONString(new Result("201", "不同意注册失败，请刷新页面"));
            }
        }
        log.info("管理员ID：{}，操作的审核者账号：{}，操作：{}，result:{}"
                , (String) param.get("userId")
                , (Integer) param.get("userIdOfSignUp")
                , (String) param.get("operation")
                , resultStr
        );
        return resultStr;
    }
}
