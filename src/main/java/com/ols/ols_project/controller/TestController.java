package com.ols.ols_project.controller;

import com.ols.ols_project.config.RedisConfig;
import com.ols.ols_project.model.TestUser;
import com.ols.ols_project.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 测试项目基础功能（连接MySQL，redis）
 * @author yuyy
 * @date 20-2-9 下午8:18
 */
@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private RedisConfig redisConfig;

    @RequestMapping("testindex")
    public String showIndex(Model model){
        model.addAttribute("msg","ols");
        List<TestUser> users = testService.selAll();
        model.addAttribute("list",users);
        System.out.println(users);
        //Redis测试
        RedisTemplate<Object, Object> redisTemplate = redisConfig.getRedisTemplate();
        //配置value的Json格式存储
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(TestUser.class));
        redisTemplate.opsForValue().set("testkey",new TestUser());
        redisTemplate.opsForValue().set("dd","fdsfdsfsdfds");
        TestUser value = (TestUser)redisTemplate.opsForValue().get("testkey");
        System.out.println(value);
        return "TestIndex";
    }
}
