package com.uidser.ppmusic.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(12, 200, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100000), new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
