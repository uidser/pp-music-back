package com.uidser.ppmusic.security.userdetailservice.impl;

import com.uidser.ppmusic.common.entity.User;
import com.uidser.ppmusic.security.config.custom.CustomUser;
import com.uidser.ppmusic.security.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = securityService.getInfo(username);
        if(user == null) {
            throw new UsernameNotFoundException("用户名未找到到");
        }
        User user1 = securityService.getInfo(user.getUsername());
//        List<String> buttons = sysUser1.getButtons();
//        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
//        for (String button: buttons) {
//            simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(button.trim()));
//        }
        return new CustomUser(user, Collections.emptyList());
    }
}
