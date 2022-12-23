package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.Type;
import com.uidser.ppmusic.common.mapper.TypeMapper;
import com.uidser.ppmusic.common.service.TypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeMapper typeMapper;

    @Override
    public List<Type> getAll() {
        return typeMapper.getAll();
    }
}
