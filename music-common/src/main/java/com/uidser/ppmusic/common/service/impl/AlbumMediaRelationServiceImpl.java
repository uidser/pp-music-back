package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.AlbumMediaRelation;
import com.uidser.ppmusic.common.mapper.AlbumMediaRelationMapper;
import com.uidser.ppmusic.common.service.AlbumMediaRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AlbumMediaRelationServiceImpl implements AlbumMediaRelationService {

    @Resource
    private AlbumMediaRelationMapper albumMediaRelationMapper;

    @Override
    public void relation(Long albumId, Long id) {
        albumMediaRelationMapper.relation(albumId, id);
    }

    @Override
    public List<Long> getByAlbumId(Long albumId) {
        return albumMediaRelationMapper.getByAlbumId(albumId);
    }

    @Override
    public void deleteRelation(AlbumMediaRelation albumMediaRelation) {
        albumMediaRelationMapper.deleteRelation(albumMediaRelation.getAlbumId());
    }

    @Override
    public void relation(Long albumId, List<Long> mediaIdList) {
        albumMediaRelationMapper.relationList(albumId, mediaIdList);
    }
}
