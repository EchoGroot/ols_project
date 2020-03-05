package com.ols.ols_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yuyy
 * @date 20-3-5 下午4:33
 */


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    /**
     *SpringBoot 2.x要重写该方法，不然css、js、image 等静态资源路径无法访问
     *@param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
    }
}

