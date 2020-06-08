package com.ols.ols_project.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author yuyy
 * @date 2020/6/8 14:18
 */
@Slf4j
@Configuration
public class QuartzConfig {

    @Value("${spring.profiles.active}")
    public static String springProfilesActive;
    /**
     * 创建job对象
     * 做什么
     */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        //关联自定义的job类
        factoryBean.setJobClass(RestartWdcpWebServiceJob.class);
        return factoryBean;
    }

    /**
     * 使用cron表达式
     * 创建Trigger对象
     * 什么时候做
     */
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        //关联jobdetail
        factoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        factoryBean.setCronExpression("0 10 * * * ?");
        return factoryBean;
    }

    /**
     * 创建Schedul对象
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(CronTriggerFactoryBean cronTriggerFactoryBean){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        if("pro".equals(springProfilesActive)){
            factoryBean.setTriggers(cronTriggerFactoryBean.getObject());
            log.info("SpringBoot配置文件为Pro，将定时任务（重启wdcp的web服务）的触发器set到调度器里");
        }
        return factoryBean;
    }
}
