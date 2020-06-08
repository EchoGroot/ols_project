package com.ols.ols_project.schedule;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author yuyy
 * @date 2020/6/8 14:08
 */
@Slf4j
@Configuration
public class RestartWdcpWebServiceJob implements Job {

    @Value("${restartWdcpWebServiceJob.bash}")
    public static String restartWdcpWebServiceJobBash;

    @Value("${restartWdcpWebServiceJob.path}")
    public static String restartWdcpWebServiceJobPath;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String[] cmdScript = new String[]{restartWdcpWebServiceJobBash, restartWdcpWebServiceJobPath}; 	//脚本位置及类型
        try {
            Process procScript = Runtime.getRuntime().exec(cmdScript);	//执行脚本
        } catch (IOException e) {
            log.error("执行定时任务（重启wdcp的web服务）异常",e);
        }
        log.info("执行定时任务（重启wdcp的web服务）成功");
    }
}
