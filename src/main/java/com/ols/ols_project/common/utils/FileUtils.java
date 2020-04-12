package com.ols.ols_project.common.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 封装文件夹递归删除类
 *
 * @author wjp
 *
 */
public class FileUtils {
    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isFile()) {
            return file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
            return file.delete();
        }
    }
    public static void saveAsFileWriter(String content,String filePath) {
        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(filePath, true);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
