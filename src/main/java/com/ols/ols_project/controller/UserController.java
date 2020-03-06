package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.model.TaskEntity;
import com.ols.ols_project.model.UserEntity;
import com.ols.ols_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 用户相关的Controller
 * @author yuyy
 * @date 20-2-18 下午3:56
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     * id：用户ID
     * @param param
     * @return
     */
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public String getUserInfo(@RequestBody HashMap<String,Object> param){
        UserEntity userInfoById = userService.getUserInfoById(
                Integer.parseInt((String) param.get("id"))
        );
        if(userInfoById == null){
            Result result = new Result("201", "未找到该用户");
            return JSON.toJSONString(result);
        }
        HashMap<String,Object> data=new HashMap<>();
        data.put("userInfo",userInfoById);
        return JSON.toJSONString(new Result(data,"200","获取用户信息成功"));
    }

    /**
     * 更改密码
     * @param param
     * @return
     */
    @RequestMapping(value = "/changePassWord",method = RequestMethod.POST)
    @ResponseBody
    public String changePassWord(@RequestBody HashMap<String,Object> param){
        String passWodById = userService.getPassWodById(Integer.parseInt((String) param.get("id")));
        if(passWodById.equals((String) param.get("oldpassword"))){
            if(1==userService.changePassWordById(
                    Integer.parseInt((String) param.get("id")),
                    (String)param.get("newpassword"))){
                return JSON.toJSONString(new Result("200","修改密码成功"));
            }
            return JSON.toJSONString(new Result("201","修改密码失败"));
        }
        return JSON.toJSONString(new Result("202","修改密码失败，原密码错误"));
    }

    /**
     * 获取用户已接受的任务
     * @param id
     * @param query
     * @param pagenum
     * @param pagesize
     * @return
     */
    @RequestMapping(value = "/getAcceptTaskByUserId",method = RequestMethod.GET)
    @ResponseBody
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
    @ResponseBody
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
     * 查询待批准的审核者注册账号
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getReviewerSignUp",method = RequestMethod.GET)
    @ResponseBody
    public String getReleaseTaskByUserId(
            @RequestParam(value = "page") Integer pageNum,
            @RequestParam(value = "limit") Integer pageSize
    ){
        HashMap<String, Object> data = userService.getReviewerSignUp(pageNum, pageSize);
        // layui默认数据表格的status为0才显示数据
        return JSON.toJSONStringWithDateFormat(new Result(data,"0","获取待批准的审核者注册账号成功"),"yyyy-mm-dd");
    }




}
