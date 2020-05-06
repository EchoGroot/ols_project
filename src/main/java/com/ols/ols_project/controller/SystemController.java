package com.ols.ols_project.controller;

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
}
