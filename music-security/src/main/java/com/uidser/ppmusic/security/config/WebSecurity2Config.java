package com.uidser.ppmusic.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

@Configuration
public class WebSecurity2Config {

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public UserDetailsService userDetails() {
        return userDetailsService;
    }

}
