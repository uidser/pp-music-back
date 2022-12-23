package com.uidser.ppmusic.security.service.impl;

import com.uidser.ppmusic.common.entity.User;
import com.uidser.ppmusic.common.exception.MusicException;
import com.uidser.ppmusic.common.jjwt.JJwtUtil;
import com.uidser.ppmusic.security.mapper.SecurityMapper;
import com.uidser.ppmusic.security.service.SecurityService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Resource
    private SecurityMapper securityMapper;

    @Override
    public String login(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user1 = securityMapper.getInfo(user.getUsername());
        if(user1 == null) {
            throw new MusicException("用户名或密码错误", 201);
        }
        boolean matches = bCryptPasswordEncoder.matches(user.getPassword(), user1.getPassword());
        if(matches) {
            String jwtToken = JJwtUtil.createJwtToken(user1);
            return jwtToken;
        }
        return null;
    }

    @Override
    public User getInfo(String username) {
        User user = securityMapper.getInfo(username);
        user.setPassword(null);
        return user;
    }

    @Override
    public void logout(String username) {
        securityMapper.logout(username, new Date());
    }
}
