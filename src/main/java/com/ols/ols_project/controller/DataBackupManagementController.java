package com.ols.ols_project.controller;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

//备份与还原数据库文件
@RestController
@RequestMapping("data")
public class DataBackupManagementController {

    /**
     * 定时生成备份文件
     * @throws Exception
     */
    @Scheduled(cron="0 0 1 * * ?") //每天凌晨1点执行一次
    public void backup2() throws  Exception{
        System.out.println("############生成备份文件");
        backup();
    }

    //备份数据库
    @GetMapping("/backup")
    public static boolean backup() {
        String hostIP="106.15.225.159";
        String userName="olsAdmin";
        String password="6789@jkl";
        String savePath="D:\\backUp\\";
        String fileName=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date().getTime());
        String databaseName="ols";
        fileName +=".sql";
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
        stringBuilder.append(" -u").append(userName).append(" -p").append(password).append(" "+databaseName);
        stringBuilder.append(" >").append(savePath + fileName);
        System.out.println(stringBuilder);
        try {
            //调用外部执行exe文件的javaAPI
            Process process = Runtime.getRuntime().exec("cmd /c"+stringBuilder.toString());
            if (process.waitFor() == 0) {// 0 表示线程正常终止。
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


//还原数据库
    /**
     * @param filepath 数据库备份的脚本路径
     * @return
     */
    @GetMapping("/recover")
    public static boolean recover( @RequestParam("filepath") String filepath
                                   //@RequestParam("ip") String ip,
                                   //@RequestParam("database") String database,
                                  // @RequestParam("userName") String userName,
                                   //@RequestParam("password") String password
                                   ) {

        String ip="106.15.225.159";
        String userName="olsAdmin";
        String password="6789@jkl";
        String database="ols";
        String stmt1 = "mysqladmin -h "+ip+" -u "+userName+" -p"+password+" create "+database;

        String stmt2 = "mysql -h "+ip+" -u "+userName+" -p "+password+" "+database+" < " + filepath;

        String[] cmd = { "cmd", "/c", stmt2 };

        try {
            Runtime.getRuntime().exec(stmt1);
            Runtime.getRuntime().exec(cmd);
            System.out.println("数据已从 " + filepath + " 导入到数据库中");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 定时生成备份文件
     * @throws Exception
     */
    @Scheduled(cron="0 0 1 * * ?") //每天凌晨1点执行一次
    public void backup1() throws  Exception{
        System.out.println("############生成备份文件");
        doBackup();
    }
    /**
     * 执行生成备份
     */
    public static void doBackup(){
        System.out.println("现在时间是"+new Date());
        Runtime runtime = Runtime.getRuntime();  //获取Runtime实例
        String database1 = "ols"; // 需要备份的数据库名
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String sdfDate = (String) sdf.format(currentDate);
        String filepath = "d:\\databack\\time_" + sdfDate + ".sql"; // 备份的路径地址
        //执行命令
        String stmt = "d:\\mysqldump  --defaults-extra-file=d:\\my.ini "+database1+" > "+filepath;
        //String stmt = "mysqldump  -h localhost -u "+olsAdmin+" -p"+password+" --databases "+ols+"> "+filepath;
        System.out.println(stmt);
        try {
            String[] command = { "cmd", "/c", stmt};
            Process process = runtime.exec(command);
            InputStream input = process.getInputStream();
            System.out.println(IOUtils.toString(input, "UTF-8"));
            //若有错误信息则输出
            InputStream errorStream = process.getErrorStream();
            System.out.println(IOUtils.toString(errorStream, "gbk"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
