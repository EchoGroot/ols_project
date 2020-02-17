package com.ols.ols_project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;

@MapperScan("com.ols.ols_project.mapper")
@SpringBootApplication(exclude = { FreeMarkerAutoConfiguration.class })
public class OlsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OlsProjectApplication.class, args);
    }

}
