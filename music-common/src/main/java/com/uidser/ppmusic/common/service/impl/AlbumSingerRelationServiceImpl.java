package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.mapper.AlbumSingerRelationMapper;
import com.uidser.ppmusic.common.service.AlbumSingerRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AlbumSingerRelationServiceImpl implements AlbumSingerRelationService {

    @Resource
    private AlbumSingerRelationMapper albumSingerRelationMapper;

    @Override
    public void relation(Long albumId, List<Long> singerIdList1) {
        albumSingerRelationMapper.relation(albumId, singerIdList1);
    }
}
