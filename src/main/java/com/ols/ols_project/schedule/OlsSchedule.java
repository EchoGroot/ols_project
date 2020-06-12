package com.ols.ols_project.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${mysql.address}")
    private  String hostIP;

    @Value("${spring.datasource.username}")
    public  String userName;

    @Value("${dbBackUp.savePath}")
    public  String savePath;

    @Value("${spring.datasource.password}")
    public  String password;

//    每隔5秒执行一次："*/5 * * * * ?"
//
//    每隔1分钟执行一次："0 */1 * * * ?"
//
//    每天23点执行一次："0 0 23 * * ?"
//
//    每天凌晨1点执行一次："0 0 1 * * ?"
//
//    每月1号凌晨1点执行一次："0 0 1 1 * ?"
//
//    每月最后一天23点执行一次："0 0 23 L * ?"
//
//    每周星期天凌晨1点实行一次："0 0 1 ? * L"
//
//    在26分、29分、33分执行一次："0 26,29,33 * * * ?"
//
//    每天的0点、13点、18点、21点都执行一次："0 0 0,13,18,21 * * ?"
//
//    表示在每月的1日的凌晨2点调度任务："0 0 2 1 * ? *"
//
//    表示周一到周五每天上午10：15执行作业："0 15 10 ? * MON-FRI"
    /**
     * 定时生成备份文件
     *每天凌晨1点执行一次
     * @throws Exception
     */
    @Scheduled(cron = "0 0 1 * * ?") //每天凌晨1点执行一次
    public void backup2() throws Exception {
        //log.info("############生成备份文件");
        backup();
    }
//    @Scheduled(cron = "0 */1 * * * ?") //每隔1分钟执行一次
//    public void backup2() throws Exception {
//        //log.info("############生成备份文件");
//        backup();
//    }
    public String backup() {
        String fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date().getTime());
        String databaseName = "ols";
        fileName += ".sql";
        String bb ="";
        String hostIP1=hostIP.substring(0,hostIP.length()-5);
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }
        //拼接命令行的命令
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mysqldump").append(" -h").append(hostIP1);
        stringBuilder.append(" -u").append(userName).append(" -p").append(password).append(" " + databaseName);
        stringBuilder.append(" >").append(savePath + fileName);
        log.info(stringBuilder.toString());
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
