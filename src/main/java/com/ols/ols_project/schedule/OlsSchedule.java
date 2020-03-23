package com.ols.ols_project.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * @author yuyy
 * @date 20-3-23 下午2:14
 */
@Slf4j
@Component
public class OlsSchedule {

    /**
     * 设置定时任务方法
     * 生日送积分
     * 每天00:00:00执行
     * cron表达式
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleMethod(){
        log.info("执行定时任务：生日送积分");
//        查所有的用户
    }
}
