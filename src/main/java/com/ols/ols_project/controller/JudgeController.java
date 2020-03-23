package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 审核信息的控制器
 * @author yuyy
 * @date 20-3-17 下午5:08
 */
@RestController
@RequestMapping("judge")
public class JudgeController {

    @Autowired
    private JudgeService judgeService;

    /**
     * 获取历史审核信息
     * @param year
     * @param userId
     * @return
     */
    @GetMapping("/getHistoryByUserId")
    public String getHistoryByUserId(
            @RequestParam("year") String year,
            @RequestParam("userId") String userId
            ){
        HashMap<String, Object> data = new HashMap<>();
        data.put("historyList",
                judgeService.getHistoryByUserId(Long.parseLong(userId),Integer.parseInt(year)));
        String result= JSON.toJSONStringWithDateFormat(
                new Result(data,"200","获取历史审核信息成功"),
                "yyyy-MM-dd");
        return result;
    }
}
