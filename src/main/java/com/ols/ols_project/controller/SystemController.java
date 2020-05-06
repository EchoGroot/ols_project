package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ols.ols_project.model.Result;
import com.ols.ols_project.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("system")
public class SystemController {
    @Autowired
    private SystemService systemService;

    @GetMapping("/getSystemByUID")
    public String getSystemByUID(long userId){
        return null;
    }

    @GetMapping("/getAllSystemByAcceptUID")
    public String getAllSystemByAcceptUID(long acceptUID, Integer pageNum, Integer pageSize){
        String result= JSON.toJSONStringWithDateFormat(
                new Result(
                        systemService.getAllSystemByAcceptUID(acceptUID,pageNum,pageSize)
                        ,"0"
                        ,"获取所有任务成功")
                ,"yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString);
        return result;
    }
}
