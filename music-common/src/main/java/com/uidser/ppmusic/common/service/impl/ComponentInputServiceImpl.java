package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.ComponentInput;
import com.uidser.ppmusic.common.mapper.ComponentInputMapper;
import com.uidser.ppmusic.common.service.ComponentInputService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ComponentInputServiceImpl implements ComponentInputService {

    @Resource
    private ComponentInputMapper componentInputMapper;

    @Override
    public List<ComponentInput> getAll() {
        return componentInputMapper.getAll();
    }

    @Override
    public List<ComponentInput> getByIds(List<Long> componentInputIds) {
        return componentInputMapper.getByIds(componentInputIds);
    }
}
