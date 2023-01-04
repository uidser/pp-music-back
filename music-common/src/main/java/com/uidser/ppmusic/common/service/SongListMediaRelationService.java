package com.uidser.ppmusic.common.service;

import java.util.List;

public interface SongListMediaRelationService {
    void relation(Long id, List<Long> songIdList);

    void deleteRelation(Long id);

    List<Long> getRelation(Long id);
}
