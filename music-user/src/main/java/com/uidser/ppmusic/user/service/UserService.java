package com.uidser.ppmusic.user.service;

import com.uidser.ppmusic.common.entity.User;

public interface UserService {
    String login(User user);

    User getInfo(String username);

    void logout(String username);
}
