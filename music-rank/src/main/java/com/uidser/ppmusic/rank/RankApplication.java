package com.uidser.ppmusic.rank;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.uidser.ppmusic.common.feign"})
@EnableDiscoveryClient
@EnableAutoDataSourceProxy
@MapperScan(basePackages = {"com.uidser.ppmusic.common.mapper", "com.uidser.ppmusic.rank.mapper"})
public class RankApplication {
    public static void main(String[] args) {
        SpringApplication.run(RankApplication.class, args);
    }
}
