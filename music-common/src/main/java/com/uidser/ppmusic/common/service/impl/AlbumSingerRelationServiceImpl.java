package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.AlbumSingerRelation;
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

    @Override
    public List<AlbumSingerRelation> getRelationByAlbumId(Long albumId) {
        return albumSingerRelationMapper.getRelationByAlbumId(albumId);
    }

    @Override
    public List<AlbumSingerRelation> getRelationBySingerId(Long id, int limit) {
        return albumSingerRelationMapper.getRelationBySingerId(id, limit);
    }
}
