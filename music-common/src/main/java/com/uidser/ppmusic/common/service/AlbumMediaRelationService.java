package com.uidser.ppmusic.common.service;

import com.uidser.ppmusic.common.entity.Album;
import com.uidser.ppmusic.common.entity.AlbumMediaRelation;

import java.util.List;

public interface AlbumMediaRelationService {
    void relation(Long albumId, Long id);

    List<Long> getByAlbumId(Long albumId);

    void deleteRelation(AlbumMediaRelation albumMediaRelation);

    void relation(Long albumId, List<Long> mediaIdList);
}
