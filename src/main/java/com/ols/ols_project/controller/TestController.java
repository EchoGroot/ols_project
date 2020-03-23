package com.ols.ols_project.controller;

import com.baidu.fsg.uid.service.UidGenService;
import com.ols.ols_project.config.RedisConfigrationBean;
import com.ols.ols_project.model.TestUser;
import com.ols.ols_project.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 测试
 * @author yuyy
 * @date 20-2-9 下午8:18
 */
@Controller
public class TestController {

    @Autowired
    private UidGenService uidGenService;

    @Autowired
    private TestService testService;

    @Autowired
    private RedisConfigrationBean redisConfig;

    /**
     * 测试项目基础功能（连接MySQL，redis）
     * @param model
     * @return
     */
    @GetMapping("testindex")
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

    /**
     * 测试500错误页面
     * @return
     */
    @GetMapping("create500")
    @ResponseBody
    public String create500(){
        int n=0/5;
        System.out.println(n);
        return "ok";
    }

    /**
     * 测试生成uuid
     * @return
     */
    @GetMapping("uid")
    @ResponseBody
    public String genUid() {
        return String.valueOf("本次生成的唯一ID号为："+uidGenService.getUid());
    }
}
