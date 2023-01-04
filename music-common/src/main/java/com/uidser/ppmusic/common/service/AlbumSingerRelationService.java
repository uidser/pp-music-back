package com.uidser.ppmusic.common.service;

import com.uidser.ppmusic.common.entity.AlbumSingerRelation;

import java.util.List;

public interface AlbumSingerRelationService {
    void relation(Long albumId, List<Long> singerIdList1);

    List<AlbumSingerRelation> getRelationByAlbumId(Long albumId);

    List<AlbumSingerRelation> getRelationBySingerId(Long id, int limit);
}
