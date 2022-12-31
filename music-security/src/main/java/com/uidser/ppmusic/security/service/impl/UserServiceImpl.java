package com.uidser.ppmusic.security.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.User;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.security.mapper.UserMapper;
import com.uidser.ppmusic.security.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public PageInfo<User> page(QueryVo queryVo) {
        if(!ObjectUtils.isEmpty(queryVo)) {
            if(!ObjectUtils.isEmpty(queryVo.getCurrent()) && !ObjectUtils.isEmpty(queryVo.getLimit())) {
                PageHelper.startPage(queryVo.getCurrent(), queryVo.getLimit());
            }
        }
        List<User> userList = userMapper.page(queryVo);
        return new PageInfo<>(userList);
    }

    @Override
    public void changeEnableStatus(Long userId, Integer status) {
        userMapper.changeEnableStatus(userId, status);
    }

    @Override
    public void edit(User user) {
        user.setUpdateTime(new Date());
        if(!ObjectUtils.isEmpty(user.getPassword())) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encode = passwordEncoder.encode(user.getPassword());
            user.setPassword(encode);
        }
        userMapper.edit(user);
    }

    @Override
    public User get(Long id) {
        return userMapper.get(id);
    }

    @Override
    public void insert(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        userMapper.insert(user);
    }
}
