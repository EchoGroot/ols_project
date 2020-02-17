package com.ols.ols_project.common.utils;

import com.ols.ols_project.OlsProjectApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.stream.Stream;

/**
 * @author yuyy
 * @date 20-2-17 下午4:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {OlsProjectApplication.class})
public class GenerateNameOfPersonTest {

    /**
     * 测试生成人名
     */
    @org.junit.Test
    public void randomName() {
        System.out.println("output name of people");
        Stream<String> generate = Stream.generate(
                ()->GenerateNameOfPerson.randomName(true, 3));
        generate.limit(100).forEach(System.out::println);
    }
}