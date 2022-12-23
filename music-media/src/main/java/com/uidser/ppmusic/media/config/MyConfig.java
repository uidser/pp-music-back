package com.uidser.ppmusic.media.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.uidser.ppmusic.security", "com.uidser.ppmusic.common"})
public class MyConfig {
}
