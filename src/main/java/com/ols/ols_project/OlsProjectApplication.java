package com.ols.ols_project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * springboot启动类
 * 继承SpringBootServletInitializer是为了打包
 */
@EnableScheduling   //启用定时器
@EnableAsync //支持并发
@EnableTransactionManagement //支持事务
//扫描mapper 前面个是生产uuid的第三方jar包的mapper
@MapperScan({"com.baidu.fsg.uid.worker.dao","com.ols.ols_project.mapper"})
//解决一个关于freemark的异常
@SpringBootApplication(exclude = { FreeMarkerAutoConfiguration.class })
public class OlsProjectApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(OlsProjectApplication.class, args);
    }

    /**
     * 打包需要
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OlsProjectApplication.class);
    }
}
