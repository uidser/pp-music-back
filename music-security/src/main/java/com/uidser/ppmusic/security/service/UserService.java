package com.uidser.ppmusic.security.service;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.User;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

public interface UserService {
    PageInfo<User> page(QueryVo queryVo);

    void changeEnableStatus(Long userId, Integer status);

    void edit(User user);

    User get(Long id);

    void insert(User user);
}
