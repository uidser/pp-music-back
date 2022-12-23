package com.uidser.ppmusic.security.config.custom;

import com.uidser.ppmusic.common.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CustomUser(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }

    public User getSysUser() {
        return user;
    }

    public void setSysUser(User sysUser) {
        this.user = sysUser;
    }
}
