package com.ols.ols_project.service;

import java.util.HashMap;

public interface DataBackupManagementService {
    //数据库备份
    boolean backup(String hostIP, String userName, String password,
                   String savePath, String fileName, String databaseName);
    //数据库还原
    boolean recover(String filepath,String ip,
                    String database, String userName,String password);

}
