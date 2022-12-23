package com.uidser.ppmusic.security.config.custom.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uidser.ppmusic.common.entity.User;
import com.uidser.ppmusic.common.jjwt.JJwtUtil;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.resposneutil.ResponseUtil;
import com.uidser.ppmusic.security.config.custom.CustomUser;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

//    @Resource
//    private RedisTemplate redisTemplate;

    @Resource
    private AuthenticationManager authenticationManager;

    public TokenLoginFilter() {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/security/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User sysUser = new ObjectMapper().readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword());
            return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) authResult.getPrincipal();
//        Gson gson = new Gson();
//        redisTemplate.opsForValue().set(customUser.getUsername(), gson.toJson(customUser.getAuthorities()));
        String token = JJwtUtil.createJwtToken(customUser.getUsername());
        ResponseUtil.response(response, new R().ok(token));
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        if(failed.getCause() instanceof RuntimeException) {
            ResponseUtil.response(response, new R().error().setCode(403).setMsg(failed.getMessage()));
        } else {
            ResponseUtil.response(response, new R().error().setCode(403));
        }
    }
}
