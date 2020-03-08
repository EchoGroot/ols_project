package com.ols.ols_project.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池配置属性类
 * @author yuyy
 * @date 20-3-8 下午2:18
 */
@ConfigurationProperties(prefix = "task.pool")
@Getter
@Setter
public class TaskThreadPoolConfig {
    private int corePoolSize;

    private int maxPoolSize;

    private int keepAliveSeconds;

    private int queueCapacity;
}