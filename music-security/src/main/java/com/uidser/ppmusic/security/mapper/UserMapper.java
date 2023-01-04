package com.uidser.ppmusic.security.mapper;

import com.uidser.ppmusic.common.entity.User;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    List<User> page(@Param("queryVo") QueryVo queryVo);

    void changeEnableStatus(@Param("userId") Long userId, @Param("status") Integer status);

    void edit(User user);

    User get(Long id);

    void insert(User user);

    User getByUserName(@Param("username") String username);
}
