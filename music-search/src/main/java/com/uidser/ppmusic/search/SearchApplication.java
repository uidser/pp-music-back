package com.uidser.ppmusic.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.uidser.ppmusic.security.realconfig", "com.uidser.ppmusic.search"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.uidser.ppmusic.common.service.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.uidser.ppmusic.common.mapper.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.uidser.ppmusic.security.config.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.uidser.ppmusic.security.mapper.*"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.uidser.ppmusic.security.service.*"),
        })
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}
