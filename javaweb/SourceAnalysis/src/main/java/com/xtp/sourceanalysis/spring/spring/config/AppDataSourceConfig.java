package com.xtp.sourceanalysis.spring.spring.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@PropertySource("classpath:dbconfig.properties")

@Configuration
public class AppDataSourceConfig {

    @Bean("dataSource")
    public DataSource newDataSource(Environment environment){
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(environment.getProperty("jdbc.driver"));
        ds.setUrl(environment.getProperty("jdbc.url"));
        ds.setUsername(environment.getProperty("jdbc.username"));
        ds.setPassword(environment.getProperty("jdbc.password"));
        return ds;
    }
}
