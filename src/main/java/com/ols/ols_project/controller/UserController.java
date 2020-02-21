package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.Result;
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

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public String getUserInfo(@RequestBody HashMap<String,Object> param){
        System.out.println(param.get("id"));
        UserEntity userInfoById = userService.getUserInfoById(Integer.parseInt((String) param.get("id")));
        if(userInfoById == null){
            Result result = new Result("201", "未找到该用户");
            System.out.println("result:"+result);
            System.out.println("res"+JSON.toJSONString(result));
            return JSON.toJSONString(result);
        }
        HashMap<String,Object> data=new HashMap<>();
        data.put("userInfo",userInfoById);
        return JSON.toJSONString(new Result(data,"200","获取用户信息成功"));
    }

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

    @RequestMapping(value = "/getAcceptTaskByUserId",method = RequestMethod.GET)
    @ResponseBody
    public String getAcceptTaskByUserId(
            @RequestParam(value = "id",required=false) Integer id,
            @RequestParam(value = "query",required=false) String query,
            @RequestParam(value = "pagenum",required=false) Integer pagenum,
            @RequestParam(value = "pagesize",required=false) Integer pagesize
    ){
        System.out.println("id:"+id);
        System.out.println("query:"+query);
        System.out.println("pagenum:"+pagenum);
        System.out.println("pagesize:"+pagesize);
        List<AcceptTask> acceptTaskByUserId = userService.getAcceptTaskByUserId(id, query, pagenum, pagesize
        );
        System.out.println(acceptTaskByUserId);
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskList",acceptTaskByUserId);
        return JSON.toJSONStringWithDateFormat(new Result(data,"200","获取已接受任务成功"),"yyyy-mm-dd hh:mm:ss");
    }


}
