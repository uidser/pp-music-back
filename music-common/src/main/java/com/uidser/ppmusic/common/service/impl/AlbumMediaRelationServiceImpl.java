package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.mapper.AlbumMediaRelationMapper;
import com.uidser.ppmusic.common.service.AlbumMediaRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AlbumMediaRelationServiceImpl implements AlbumMediaRelationService {

    @Resource
    private AlbumMediaRelationMapper albumMediaRelationMapper;

    @Override
    public void relation(Long albumId, Long id) {
        albumMediaRelationMapper.relation(albumId, id);
    }
}
