package com.uidser.ppmusic.statistics;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableAutoDataSourceProxy
@EnableFeignClients(basePackages = {"com.uidser.ppmusic.common.feign"})
@MapperScan(basePackages = "com.uidser.ppmusic.common.mapper")
@ComponentScan(basePackages = {"com.uidser.ppmusic.security", "com.uidser.ppmusic.statistics"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.uidser.ppmusic.common.service.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.uidser.ppmusic.common.mapper.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.uidser.ppmusic.common.config.*")
        })
public class StatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }
}
