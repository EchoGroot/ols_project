package com.ols.ols_project.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 线程池配置属性类
 * @author yuyy
 * @date 20-3-8 下午2:18
 */
@Component
@ConfigurationProperties(prefix = "task.pool")
@Getter
@Setter
public class TaskThreadPoolConfigrationBean {
    private int corePoolSize;

    private int maxPoolSize;

    private int keepAliveSeconds;

    private int queueCapacity;
}