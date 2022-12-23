package com.uidser.ppmusic.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@MapperScan(basePackages = "com.uidser.ppmusic.user.mapper")
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.uidser.ppmusic.security", "com.uidser.ppmusic.common"},
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.uidser.ppmusic.common.service.*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.uidser.ppmusic.common.mapper.*")
})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
