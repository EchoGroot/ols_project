package com.ols.ols_project.common.utils;

import com.ols.ols_project.OlsProjectApplication;
import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)

public class WordUtilTest {
    @Test
    public void qwe(){
        WordUtil tt =new WordUtil();
        String aa=tt.readWord("E:\\123.doc");
        System.out.print(aa);
    }
}
