package com.ols.ols_project;

import com.ols.ols_project.config.TaskThreadPoolConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * springboot启动类
 * 继承SpringBootServletInitializer是为了打包
 */
@EnableAsync
@EnableConfigurationProperties({TaskThreadPoolConfig.class} ) // 开启配置属性支持
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
