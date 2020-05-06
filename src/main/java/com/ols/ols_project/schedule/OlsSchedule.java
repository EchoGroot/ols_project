package com.ols.ols_project.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    /**
     * 定时生成备份文件
     *每天凌晨1点执行一次
     * @throws Exception
     */
    @Scheduled(cron = "0 0 1 * * ?") //每天凌晨1点执行一次
    public void backup2() throws Exception {
        System.out.println("############生成备份文件");
        backup();
    }
    private String backup() {
        String hostIP = "106.15.225.159";
        String userName = "olsAdmin";
        String password = "6789@jkl";
        String savePath = "D:\\backUp\\";
        String fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date().getTime());
        String databaseName = "ols";
        fileName += ".sql";
        String bb ="";
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }
        //拼接命令行的命令
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mysqldump").append(" -h").append(hostIP);
        stringBuilder.append(" -u").append(userName).append(" -p").append(password).append(" " + databaseName);
        stringBuilder.append(" >").append(savePath + fileName);
        System.out.println(stringBuilder);
        try {
            //调用外部执行exe文件的javaAPI
            Process process = Runtime.getRuntime().exec("cmd /c" + stringBuilder.toString());
            if (process.waitFor() == 0) {// 0 表示线程正常终止。
                bb="1";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bb;
    }
}
