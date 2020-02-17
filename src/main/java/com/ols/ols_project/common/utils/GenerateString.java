package com.ols.ols_project.common.utils;

import java.util.Random;

/**
 * 产生随机字符串
 * @author yuyy
 * @date 20-2-17 下午6:08
 */
public class GenerateString {

    /**
     * 产生指定长度的字符串
     * @param length
     * @return String
     */
    public static String generateString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random1=new Random();
        //指定字符串长度，拼接字符并toString
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < length; i++) {
            //获取指定长度的字符串中任意一个字符的索引值
            int number=random1.nextInt(str.length());
            //根据索引值获取对应的字符
            char charAt = str.charAt(number);
            sb.append(charAt);
        }
        return sb.toString();
    }

    /**
     * 产生指定长度的数字
     * 非0开头
     * @param length
     * @return
     */
    public static String generateNumber(int length) {
        String str = "0123456789";
        String str1 = "123456789";
        Random random1 = new Random();
        //指定字符串长度，拼接字符并toString
        StringBuffer sb = new StringBuffer();
        //获取指定长度的字符串中任意一个字符的索引值
        int number = random1.nextInt(str1.length());
        //根据索引值获取对应的字符
        char charAt = str1.charAt(number);
        sb.append(charAt);
        for (int i = 0; i < length - 1; i++) {
            //获取指定长度的字符串中任意一个字符的索引值
            number = random1.nextInt(str.length());
            //根据索引值获取对应的字符
            charAt = str.charAt(number);
            sb.append(charAt);
        }
        return sb.toString();
    }
}
