package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.Singer;
import com.uidser.ppmusic.common.mapper.SingerMediaRelationMapper;
import com.uidser.ppmusic.common.service.CategorySingerRelationService;
import com.uidser.ppmusic.common.service.SingerMediaRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SingerMediaRelationServiceImpl implements SingerMediaRelationService {

    @Resource
    private SingerMediaRelationMapper singerMediaRelationMapper;

    @Override
    public void relation(Long id, List<Long> singerIdList) {
        singerMediaRelationMapper.relation(id, singerIdList);
    }

    @Override
    public List<Long> get(Long id) {
        return singerMediaRelationMapper.get(id);
    }
}
