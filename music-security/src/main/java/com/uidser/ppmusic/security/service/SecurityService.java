package com.uidser.ppmusic.security.service;

import com.uidser.ppmusic.common.entity.User;

public interface SecurityService {
    String login(User user);

    User getInfo(String username);

    void logout(String username);
}
