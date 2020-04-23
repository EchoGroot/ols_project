package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.service.AcceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("accept")
public class AcceptController {
    @Autowired
    private AcceptService acceptService;

    @GetMapping("/getAllAcceptById")
    public String getAllAcceptById(
            @RequestParam("year") String year,
            @RequestParam("userId") String userId
    ){
        HashMap<String, Object> data = new HashMap<>();
        data.put("acceptList",
                acceptService.getAllAcceptById(Long.parseLong(userId),Integer.parseInt(year)));
        String result= JSON.toJSONStringWithDateFormat(
                new Result(data,"200","获取个人已接受成功"),
                "yyyy-MM-dd");
        return result;
    }
}
