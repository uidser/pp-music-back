package com.uidser.ppmusic.statistics.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpServletRequest httpServletRequest = requestAttributes.getRequest();
                String token = httpServletRequest.getHeader("token");
                requestTemplate.header("token", token);
                String xid = RootContext.getXID();
                requestTemplate.header(RootContext.KEY_XID, xid);
            }
        };
    }
}
