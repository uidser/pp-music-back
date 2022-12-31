package com.uidser.ppmusic.common.service;

import java.util.List;

public interface AlbumSingerRelationService {
    void relation(Long albumId, List<Long> singerIdList1);
}
