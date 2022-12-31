package com.uidser.ppmusic.security.realconfig;

import com.uidser.ppmusic.security.config.custom.filter.TokenLoginFilter;
import com.uidser.ppmusic.security.realconfig.passwordencode.CustomMd5PasswordEncode;
import com.uidser.ppmusic.security.realconfig.tokenfilter.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {



    @Resource
    private CustomMd5PasswordEncode customMd5PasswordEncode;

//    @Resource
//    private RedisTemplate redisTemplate;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return customMd5PasswordEncode;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web.ignoring().antMatchers("/security/login", "/oss/callback",  "/snapshot/getByRankIdAndFrequency", "/rank/get/**", "/rank/addFrequency/**", "/media/getRankMediaList", "/rank/mediaRankRelation/insert/**", "/rank/mediaRankRelation/getStepByOrder/**", "/rank/getDaySnapShotList/**", "/search/updateMediaUrl/**");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        try {
            httpSecurity.csrf().disable()
                    .cors().and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(new TokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilter(new TokenLoginFilter());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            return httpSecurity.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
