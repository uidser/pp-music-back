package com.uidser.ppmusic.security.mapper;

import com.uidser.ppmusic.common.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface SecurityMapper {
    User login(User user);

    User getInfo(String username);

    void logout(@Param("username") String token, @Param("time") Date date);
}
