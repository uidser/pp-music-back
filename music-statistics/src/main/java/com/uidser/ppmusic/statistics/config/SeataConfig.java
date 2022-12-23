package com.uidser.ppmusic.statistics.config;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class SeataConfig {

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        HikariDataSource hikariDataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if(StringUtils.hasText(dataSourceProperties.getName())) {
            hikariDataSource.setPoolName(dataSourceProperties.getName());
        }
        return new DataSourceProxy(hikariDataSource);
    }

}
