package com.ols.ols_project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * springboot启动类
 * 继承SpringBootServletInitializer是为了打包
 */
@EnableAsync
@MapperScan("com.ols.ols_project.mapper")
//解决一个关于freemark的异常
@SpringBootApplication(exclude = { FreeMarkerAutoConfiguration.class })
public class OlsProjectApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(OlsProjectApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OlsProjectApplication.class);
    }
}
