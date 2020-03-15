package com.ols.ols_project.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author yuyy
 * @date 20-3-14 下午11:06
 */
@Configuration
public class DruidDBAutoConfig {
    @Autowired
    private DruidDBConfig config;

    @Bean     //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(config.getDbUrl());
        datasource.setUsername(config.getUsername());
        datasource.setPassword(config.getPassword());
        datasource.setDriverClassName(config.getDriverClassName());

        //configuration
        datasource.setInitialSize(config.getInitialSize());
        datasource.setMinIdle(config.getMinIdle());
        datasource.setMaxActive(config.getMaxActive());
        datasource.setMaxWait(config.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(config.getValidationQuery());
        datasource.setTestWhileIdle(config.isTestWhileIdle());
        datasource.setTestOnBorrow(config.isTestOnBorrow());
        datasource.setTestOnReturn(config.isTestOnReturn());
        /*datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            log.error("druid configuration initialization filter : {0}", e.toString());
        }
        datasource.setConnectionProperties(connectionProperties);*/

        return datasource;
    }
}
