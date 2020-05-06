package com.ols.ols_project.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baidu.fsg.uid.service.UidGenService;
import com.ols.ols_project.common.utils.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//备份与还原数据库文件
@RestController
@RequestMapping("data")
public class DataBackupManagementController {

    private static UidGenService uidGenService;



    //备份数据库
    @GetMapping("/backup")
    public String backup() {
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
/*
*获取指定文件夹下文件名字
 */
@GetMapping("/getAllBackUpName")
public String getAllBackUpName(){
    List<String> backUpNames = FileUtils.getFiles("D:\\backUp\\");
    JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(backUpNames));
    return jsonArray.toJSONString();
}
    /**
     * 导入Mysql数据库
     *
     * @param fileName    要导入的文件
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/importDatabase")
    public String importDatabase(@RequestParam("fileName") String fileName) {
        String hostIP = "127.0.0.1";
        String hostPort = "3306";
        String userName = "root";
        String password = "970919zs";
        String databaseName = "olsbackup";
        String importFilePath="d:\\backUp\\";
        importFilePath+= fileName;
        String bb ="";
        String path="mysql -h" + hostIP + " -P" + hostPort + " -u" + userName + " -p" + password + " " + databaseName + "<" + importFilePath;
        System.out.println(path);
        try {
            Process process = Runtime.getRuntime().exec("cmd /c" + path);
            if (process.waitFor() == 0) {
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
