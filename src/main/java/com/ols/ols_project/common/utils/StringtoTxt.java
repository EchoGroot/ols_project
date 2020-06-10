package com.ols.ols_project.common.utils;

import java.io.FileOutputStream;

public class StringtoTxt {
    public void WriteStringToFile(String fileName,String file) {
        String filePath = "D:\\docTask\\"+fileName+"label.txt";
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(file.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
