package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.ols.ols_project.common.utils.SendEmailBy126;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.model.entity.UserEntity;
import com.ols.ols_project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
     * @return
     */

    @GetMapping(value = "/getUserInfo")
    public String getUserInfo(@RequestParam("userId") String userId){
        log.info("用户ID：{}，获取用户信息",userId);
        String resultStr=null;
        if(userId != null){
            UserEntity userInfoById = userService.getUserInfoById(
                    Long.parseLong(userId)
            );
            if(userInfoById == null){
                Result result = new Result("201", "未找到该用户");
                resultStr= JSON.toJSONString(result);
            }else{
                HashMap<String,Object> data=new HashMap<>();
                data.put("userInfo",userInfoById);
                resultStr= JSON.toJSONStringWithDateFormat(
                        new Result(data,"200","获取用户信息成功"),
                        "yyyy-MM-dd");
            }
        }else{
            Result result = new Result("202", "用户ID不能为空");
            resultStr= JSON.toJSONString(result);
        }
        log.info("用户ID：{}，获取用户信息，result：{}",userId,resultStr);
        return resultStr;
    }

    /**
     * 更改密码
     * @param param
     * @return
     */
    @PostMapping(value = "/changePassWord")
    public String changePassWord(@RequestBody HashMap<String,Object> param){
        log.info("用户ID：{}，更改密码",(String) param.get("id"));
        String resultStr = null;
        String passWodById = userService.getPassWodById(Integer.parseInt((String) param.get("id")));
        if(passWodById.equals((String) param.get("oldpassword"))){
            if(1==userService.changePassWordById(
                    Integer.parseInt((String) param.get("id")),
                    (String)param.get("newpassword"))){
                resultStr= JSON.toJSONString(
                        new Result("200","修改密码成功"));
            }else{
                resultStr= JSON.toJSONString(
                        new Result("201","修改密码失败"));
            }
        }else {
            resultStr=JSON.toJSONString(
                    new Result("202","修改密码失败，原密码错误"));
        }
        log.info("用户ID：{}，更改密码,result:{}",(String) param.get("id"),resultStr);
        return resultStr;
    }

    /**
     * 获取用户已接受的任务
     * @param userId
     * @param query 'acceptfinish'：已完成的任务，'acceptnotfinish'：未完成的任务
     * @param pageNum
     * @param pageSize 如果等于0，就是全部查询出来，否则就是分页查询
     * @param queryInfo
     * @param searchInfo
     * @return
     */
    @GetMapping(value = "/getAcceptTaskByUserId")
    public String getAcceptTaskByUserId(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "query") String query,
            @RequestParam(value = "page") Integer pageNum,
            @RequestParam(value = "limit") Integer pageSize,
            @RequestParam(value = "queryInfo") String  queryInfo,
            @RequestParam(value = "searchInfo") String  searchInfo
    ){
        log.info("用户ID：{}，获取用户已接受的任务，query：{}，pageNum:{},pageSize:{},queryInfo:{},searchInfo:{}"
                ,userId,query,pageNum,pageSize,queryInfo,searchInfo);
        String result= JSON.toJSONStringWithDateFormat(
                new Result(
                        userService.getAcceptTaskByUserId(
                                Long.parseLong(userId), query, pageNum , pageSize,queryInfo, searchInfo)
                        ,"0","获取已接受任务成功")
                ,"yyyy-MM-dd hh:mm:ss");
        log.info("用户ID：{}，获取用户已接受的任务，query：{}，pageNum:{},pageSize:{},queryInfo:{},searchInfo:{},result:{}"
                ,userId,query,pageNum,pageSize,queryInfo,searchInfo,result);
        return result;
    }

    /**
     * 获取用户已发布的任务
     * @param userId
     * @param query
     * @param pageNum
     * @param pageSize
     * @param queryInfo
     * @param searchInfo
     * @return
     */
    @GetMapping(value = "/getReleaseTaskByUserId")
    public String getReleaseTaskByUserId(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "query") String query,
            @RequestParam(value = "page") Integer pageNum,
            @RequestParam(value = "limit") Integer pageSize,
            @RequestParam(value = "queryInfo") String  queryInfo,
            @RequestParam(value = "searchInfo") String  searchInfo
    ){
        log.info("用户ID：{}，获取用户已发布的任务，query：{}，pageNum:{},pageSize:{},queryInfo:{},searchInfo:{}"
                ,userId,query,pageNum,pageSize,queryInfo,searchInfo);
        // layui默认数据表格的status为0才显示数据
        String result= JSON.toJSONStringWithDateFormat(
                new Result(
                        userService.getReleaseTaskByUserId(Long.parseLong(userId), query, pageNum, pageSize,queryInfo,searchInfo)
                        ,"0"
                        ,"获取已发布任务成功")
                ,"yyyy-MM-dd hh:mm:ss");
        log.info("用户ID：{}，获取用户已发布的任务，query：{}，pageNum:{},pageSize:{},queryInfo:{},searchInfo:{},result:{}"
                ,userId,query,pageNum,pageSize,queryInfo,searchInfo,result);
        return result;
    }

    /**
     * 查询审核者注册账号
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/getReviewerSignUp")
    public String getReleaseTaskByUserId(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "page") Integer pageNum,
            @RequestParam(value = "limit") Integer pageSize,
            @RequestParam(value = "queryInfo") String  queryInfo,
            @RequestParam(value = "searchInfo") String  searchInfo
    ){
        log.info("管理员ID：{}，查询审核者注册账号，pageNum:{},pageSize:{},queryInfo:{},searchInfo:{}",userId,pageNum,pageSize,queryInfo,searchInfo);
        HashMap<String, Object> data = userService.getReviewerSignUp(queryInfo,searchInfo,pageNum, pageSize);
        // layui默认数据表格的status为0才显示数据
        String result=JSON.toJSONStringWithDateFormat(
                new Result(data,"0","获取待批准的审核者注册账号成功"),
                "yyyy-MM-dd");
        log.info("管理员ID：{}，查询审核者注册账号，result:{}",userId,result);
        return result;
    }

    /**
     * 管理员同意或不同意审核者账号注册
     * userIdOfSignUp：操作的审核者账号
     * userId：管理员帐号
     * operation：操作（同意注册，不同意注册）
     * @return
     */
    @PostMapping(value = "/yesReviewerSignUp")
    public String yesReleaseTaskByUserId(
            @RequestParam("userId") String userId,
            @RequestParam("operation") String operation,
            @RequestParam("adminUserId") String adminUserId
    ) {
        log.info("管理员ID：{}，操作的审核者账号：{}，操作：{}，管理员同意或不同意审核者账号注册"
                ,adminUserId
                ,userId
                ,operation
        );
        String resultStr=null;
        if("yes".equals(operation)){
            if(1==userService.yesAndNoReviewerSignUp(Long.parseLong(userId),operation)){
                //给审核者发送邮件提醒
                UserEntity userInfo = userService.getUserInfoById(Long.parseLong(userId));
                sendEmailBy126.sendEmail(
                        userInfo.getEmail()
                        ,"Ols系统通知"
                        ,"恭喜您注册的审核者账号通过管理员的批准，可以正常使用了。");
                resultStr=JSON.toJSONString(
                        new Result("200","同意注册成功"));
            }else{
                resultStr=JSON.toJSONString(
                        new Result("201","同意注册失败，请刷新页面"));
            }
        }else {
            if (1 == userService.yesAndNoReviewerSignUp(Long.parseLong(userId), operation)) {
                //给审核者发送邮件提醒
                UserEntity userInfo = userService.getUserInfoById(Long.parseLong(userId));
                sendEmailBy126.sendEmail(
                        userInfo.getEmail()
                        , "Ols系统通知"
                        , "Sorry!您注册的审核者账号未能通过管理员的批准，请联系Ols管理员。");

                resultStr = JSON.toJSONString(
                        new Result("200", "不同意注册成功"));
            }else{
                resultStr = JSON.toJSONString(
                        new Result("201", "不同意注册失败，请刷新页面"));
            }
        }
        log.info("管理员ID：{}，操作的审核者账号：{}，操作：{}，result:{}"
                ,adminUserId
                ,userId
                ,operation
                , resultStr
        );
        return resultStr;
    }
}
