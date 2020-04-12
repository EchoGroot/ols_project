package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baidu.fsg.uid.service.UidGenService;
import com.ols.ols_project.common.utils.Cache;
import com.ols.ols_project.common.utils.SendEmailBy126;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.model.entity.UserEntity;
import com.ols.ols_project.model.entity.UserOperationLogEntity;
import com.ols.ols_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * 用户相关的Controller
 * @author yuyy
 * @date 20-2-18 下午3:56
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SendEmailBy126 sendEmailBy126;

    @Autowired
    private UidGenService uidGenService;


    /**
     * 获取用户信息
     * userId：用户ID
     *
     * @return
     */

    @GetMapping(value = "/getUserInfo")
    public String getUserInfo(@RequestParam("userId") String userId) {
        String resultStr = null;
        if (userId != null) {
            UserEntity userInfoById = userService.getUserInfoById(
                    Long.parseLong(userId)
            );
            if (userInfoById == null) {
                Result result = new Result("201", "未找到该用户");
                resultStr = JSON.toJSONString(result);
            } else {
                HashMap<String, Object> data = new HashMap<>();
                data.put("userInfo", userInfoById);
                resultStr = JSON.toJSONStringWithDateFormat(
                        new Result(data, "200", "获取用户信息成功"),
                        "yyyy-MM-dd");
            }
        } else {
            Result result = new Result("202", "用户ID不能为空");
            resultStr = JSON.toJSONString(result);
        }
        return resultStr;
    }

    /**
     * 更改密码
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/changePassWord")
    public String changePassWord(@RequestBody HashMap<String, Object> param) {
        String resultStr = null;
        String passWodById = userService.getPassWodById(Integer.parseInt((String) param.get("id")));
        if (passWodById.equals((String) param.get("oldpassword"))) {
            if (1 == userService.changePassWordById(
                    Integer.parseInt((String) param.get("id")),
                    (String) param.get("newpassword"))) {
                resultStr = JSON.toJSONString(
                        new Result("200", "修改密码成功"));
            } else {
                resultStr = JSON.toJSONString(
                        new Result("201", "修改密码失败"));
            }
        } else {
            resultStr = JSON.toJSONString(
                    new Result("202", "修改密码失败，原密码错误"));
        }
        return resultStr;
    }

    /**
     * 获取用户已接受的任务
     *
     * @param userId
     * @param query      'acceptfinish'：已完成的任务，'acceptnotfinish'：未完成的任务
     * @param pageNum
     * @param pageSize   如果等于0，就是全部查询出来，否则就是分页查询
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
            @RequestParam(value = "queryInfo") String queryInfo,
            @RequestParam(value = "searchInfo") String searchInfo
    ) {
        String result = JSON.toJSONStringWithDateFormat(
                new Result(
                        userService.getAcceptTaskByUserId(
                                Long.parseLong(userId), query, pageNum, pageSize, queryInfo, searchInfo)
                        , "0", "获取已接受任务成功")
                , "yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString
        );
        return result;
    }

    /**
     * 获取用户已发布的任务
     *
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
            @RequestParam(value = "queryInfo") String queryInfo,
            @RequestParam(value = "searchInfo") String searchInfo
    ) {
        // layui默认数据表格的status为0才显示数据
        String result = JSON.toJSONStringWithDateFormat(
                new Result(
                        userService.getReleaseTaskByUserId(Long.parseLong(userId), query, pageNum, pageSize, queryInfo, searchInfo)
                        , "0"
                        , "获取已发布任务成功")
                , "yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString
        );
        return result;
    }

    /**
     * 查询审核者注册账号
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/getReviewerSignUp")
    public String getReleaseTaskByUserId(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "page") Integer pageNum,
            @RequestParam(value = "limit") Integer pageSize,
            @RequestParam(value = "queryInfo") String queryInfo,
            @RequestParam(value = "searchInfo") String searchInfo
    ) {
        HashMap<String, Object> data = userService.getReviewerSignUp(queryInfo, searchInfo, pageNum, pageSize);
        // layui默认数据表格的status为0才显示数据
        String result = JSON.toJSONStringWithDateFormat(
                new Result(data, "0", "获取待批准的审核者注册账号成功"),
                "yyyy-MM-dd",
                SerializerFeature.WriteNonStringValueAsString);
        return result;
    }

    /**
     * 管理员同意或不同意审核者账号注册
     * userIdOfSignUp：操作的审核者账号
     * userId：管理员帐号
     * operation：操作（同意注册，不同意注册）
     *
     * @return
     */
    @PostMapping(value = "/yesReviewerSignUp")
    public String yesReleaseTaskByUserId(
            @RequestParam("userId") String userId,
            @RequestParam("operation") String operation,
            @RequestParam("adminUserId") String adminUserId
    ) {
        String resultStr = null;
        if ("yes".equals(operation)) {
            if (1 == userService.yesAndNoReviewerSignUp(Long.parseLong(userId), operation)) {
                //给审核者发送邮件提醒
                UserEntity userInfo = userService.getUserInfoById(Long.parseLong(userId));
                sendEmailBy126.sendEmail(
                        userInfo.getEmail()
                        , "Ols系统通知"
                        , "恭喜您注册的审核者账号通过管理员的批准，可以正常使用了。");
                resultStr = JSON.toJSONString(
                        new Result("200", "同意注册成功"));
            } else {
                resultStr = JSON.toJSONString(
                        new Result("201", "同意注册失败，请刷新页面"));
            }
        } else {
            if (1 == userService.yesAndNoReviewerSignUp(Long.parseLong(userId), operation)) {
                //给审核者发送邮件提醒
                UserEntity userInfo = userService.getUserInfoById(Long.parseLong(userId));
                sendEmailBy126.sendEmail(
                        userInfo.getEmail()
                        , "Ols系统通知"
                        , "Sorry!您注册的审核者账号未能通过管理员的批准，请联系Ols管理员。");

                resultStr = JSON.toJSONString(
                        new Result("200", "不同意注册成功"));
            } else {
                resultStr = JSON.toJSONString(
                        new Result("201", "不同意注册失败，请刷新页面"));
            }
        }
        return resultStr;
    }

    /**
     * 更改邮箱
     *
     * @param userId
     * @param email
     * @return
     */
    @PostMapping(value = "/changeEmail")
    public String changeEmail(@RequestParam("userId") String userId,
                              @RequestParam("email") String email) {
        String resultStr = null;
        try {
            userService.changeEmailById(Long.parseLong(userId), email);
            resultStr = JSON.toJSONString(
                    new Result("200", "修改密码成功！"));
        } catch (Exception e) {
            resultStr = JSON.toJSONString(
                    new Result("201", "修改密码失败！"));

        }
        return resultStr;
    }

    /**
     * 登录
     * @param userName
     * @param passWord
     * @return
     */
    @GetMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("passWord") String passWord,HttpServletRequest request) {
        String resultStr = null;
        Long id = userService.login(userName, passWord);
        if (id == null) {
            Result result = new Result("205", "账号或密码错误！");
            resultStr = JSON.toJSONString(result);
        } else {
            UserEntity userInfoById = userService.getUserInfoById(id);
            String url="/";
            String page=null;
            switch (userInfoById.getRole()){
                case 0:page="Home/Home.html?userId=";break;
                case 1:page="AdminPage/index.html?userId=";break;
                case 2:page="JudgeTaskPage/index.html?page=notCheck&userId=";
            }
            url=url+page+id;
            HashMap<String, Object> data = new HashMap<>();
            data.put("url", url);
            data.put("userId",Long.toString(id));
            request.getSession().setAttribute("userId",Long.toString(id));//session存数据
            //更新操作日志
            UserOperationLogEntity userLog=new UserOperationLogEntity();
            userLog.setId(uidGenService.getUid());
            userLog.setUser_id(id);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=new Date();
            userLog.setTime(Timestamp.valueOf(df.format(date)));
            if(userInfoById.getRole()==0){
                //普通用户
                resultStr = JSON.toJSONString(
                        new Result(data, "200", "登录成功！"));
                userService.userLoginTime(userLog);
            }else{
                if(userInfoById.getRole()==1){
                    //系统管理员
                    resultStr=JSON.toJSONString(
                            new Result(data, "201", "登录成功！"));
                    userService.userLoginTime(userLog);
                }else{
                    //审核者
                    String ext1=userInfoById.getExt1();
                    if(ext1.equals("0")){
                        resultStr=JSON.toJSONString(
                                new Result(data, "203", "账号审核中！"));
                    }else{
                        if(ext1.equals("1")){
                            resultStr=JSON.toJSONString(
                                    new Result(data, "202", "登录成功！"));
                            userService.userLoginTime(userLog);
                        }else{
                            resultStr=JSON.toJSONString(
                                    new Result(data, "204", "该账号未通过审核！"));
                        }
                    }

                }
            }


        }
        return resultStr;
    }

    /**
     * 验证用户名是否重复
     * @param userName
     * @return
     */
    @PostMapping(value = "/checkUserName")
    public String checkUserName(@RequestParam("userName") String userName) {
        String resultStr = null;
        Long id = userService.checkUserName(userName);
        if (id == null) {
            Result result = new Result("200", "用户名不存在！");
            resultStr = JSON.toJSONString(result);
        } else {
            resultStr = JSON.toJSONString(
                    new Result("201", "用户名已存在"));

        }
        return resultStr;
    }

    /**
     * 向注册用户发送验证码
     * @param email
     * @return
     */
    @PostMapping(value = "/sendEmail")
    public String sendEmail(@RequestParam("email")String email) {
        String resultStr = null;
        //给注册用户发送验证码
        try {
            String code = (System.currentTimeMillis() + "").substring(7);
            //将验证码存入缓存
            Cache.put(email, code, 60000);
            sendEmailBy126.sendEmail(
                    email
                    , "Ols系统通知"
                    , "欢迎使用Ols系统！您的验证码为：" + code);

            resultStr = JSON.toJSONString(
                    new Result("200", "已成功发送验证码！"));

        }catch (Exception e){
            resultStr=JSON.toJSONString(
                    new Result("201", "验证码发送异常！"));
        }
        return resultStr;
    }

    /**
     * 检查用户输入的验证码是否正确
     * @param email
     * @param inputcode
     * @return
     */
    @PostMapping(value = "/verifyCode")
    public String verifyCode(@RequestParam("email")String email,@RequestParam("inputcode")String inputcode) {
        String resultStr = null;
        String code = (String) Cache.get(email);
        if (code != null && code.equals(inputcode)) {
            Result result = new Result("200", "验证码正确！");
            resultStr = JSON.toJSONString(result);
        }else {
            resultStr = JSON.toJSONString(
                    new Result("201", "验证码错误！"));

        }
        return resultStr;
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping(value = "/userRegister")
    public String userRegister(UserEntity user){
        String resultStr = null;
        Long id=uidGenService.getUid();
        user.setId(id);
        if(user.getRole()==0){
            user.setExt1(null);
        }else{
            user.setExt1("0");
        }
        if(user!=null&&userService.getUserInfoById(id)==null){
            userService.userRegister(user);
            if(userService.getUserInfoById(id)!=null){
                resultStr = JSON.toJSONString(
                        new Result("200", "注册成功！"));
                UserOperationLogEntity userLog=new UserOperationLogEntity();
                userLog.setId(uidGenService.getUid());
                userLog.setUser_id(user.getId());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date=new Date();
                userLog.setTime(Timestamp.valueOf(df.format(date)));
                userService.userRegisterTime(userLog);
            }else{
                resultStr = JSON.toJSONString(
                        new Result("201", "注册失败！"));
            }
        }else{
            resultStr = JSON.toJSONString(
                    new Result("202", "出错！"));
        }
        return resultStr;
    }

    /**
     * 根据用户名获取邮箱
     * @param userName
     * @return
     */
    @PostMapping(value = "/getEmailByName")
    public String getEmailByName(@RequestParam("userName")String userName){
        String resultStr = null;
        String email = userService.getEmailByName(userName);
        if (email == null) {
            Result result = new Result("201", "用户不存在！");
            resultStr = JSON.toJSONString(result);
        } else {
            HashMap<String, Object> data = new HashMap<>();
            data.put("email",email);
            resultStr = JSON.toJSONString(
                    new Result(data,"200", "获取邮箱成功！"));
        }
        return resultStr;
    }

    /**
     * 忘记密码根据用户名修改密码
     * @param userName
     * @param password
     * @return
     */
    @PostMapping(value = "/changePasswordByName")
    public String changePasswordByName(@RequestParam("userName")String userName,
                                       @RequestParam("password")String password) {
        String resultStr = null;
        try {
            userService.changePasswordByName(userName, password);
            resultStr = JSON.toJSONString(
                    new Result("200", "修改密码成功！"));
        } catch (Exception e) {
            resultStr = JSON.toJSONString(
                    new Result("201", "修改密码失败！"));

        }
        return resultStr;
    }

    /**
     * 判断账号是否登录
     * @param userId
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/judgeLogin")
    public String judgeLogin(@RequestParam("userId") String userId,
                             HttpServletRequest httpServletRequest){
        String userId1 = (String)httpServletRequest.getSession().getAttribute("userId");
        if(userId==null||userId1==null){
            return JSONObject.toJSONString(new Result("201", "当前用户未登陆"));
        }
        if(!userId.equals(userId1)){
            return JSONObject.toJSONString(new Result("201", "当前用户未登陆"));
        }
        return JSONObject.toJSONString(new Result("200", "当前用户已登陆"));
    }

    /**
     * 获取所有用户
     * @param userId
     * @param pageNum
     * @param pageSize
     * @param queryInfo
     * @param searchInfo
     * @return
     */
    @GetMapping(value = "/getUserSignUp")
    public String getUserSignUp(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "page") Integer pageNum,
            @RequestParam(value = "limit") Integer pageSize,
            @RequestParam(value = "queryInfo") String queryInfo,
            @RequestParam(value = "searchInfo") String searchInfo
    ) {
        HashMap<String, Object> data = userService.getUserSignUp(queryInfo, searchInfo, pageNum, pageSize);
        // layui默认数据表格的status为0才显示数据
        String result = JSON.toJSONStringWithDateFormat(
                new Result(data, "0", "获取所有用户账号成功"),
                "yyyy-MM-dd",
                SerializerFeature.WriteNonStringValueAsString);
        return result;
    }

    @GetMapping(value="/cancel")
    public String cancel(HttpServletRequest httpServletRequest){
        String resultStr = null;
        try {
            Enumeration em = httpServletRequest.getSession().getAttributeNames();
            while(em.hasMoreElements()){
                httpServletRequest.getSession().removeAttribute(em.nextElement().toString());
            }
            resultStr = JSON.toJSONString(
                    new Result("200", "清除session成功！"));
        } catch (Exception e) {
            resultStr = JSON.toJSONString(
                    new Result("201", "清除session失败！"));

        }
        return resultStr;
    }


}





