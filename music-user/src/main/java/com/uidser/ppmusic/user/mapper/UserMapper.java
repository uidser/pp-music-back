package com.uidser.ppmusic.user.mapper;

import com.uidser.ppmusic.common.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserMapper {
    User login(User user);

    User getInfo(String username);

    void logout(@Param("username") String token, @Param("time") Date date);
}
