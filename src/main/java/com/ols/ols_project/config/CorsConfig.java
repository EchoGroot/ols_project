package com.ols.ols_project.config;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author yuyy
 * @date 20-2-19 下午3:39
 */
//@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }*/

}