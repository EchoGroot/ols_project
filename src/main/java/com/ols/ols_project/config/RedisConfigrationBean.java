package com.ols.ols_project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 * @author yuyy
 * @date 20-2-9 下午11:36
 */
@Configuration
public class RedisConfigrationBean {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Bean
    public RedisTemplate<Object,Object> getRedisTemplate(){
        System.out.println(redisTemplate);
        //配置key的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //配置value的序列化
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
