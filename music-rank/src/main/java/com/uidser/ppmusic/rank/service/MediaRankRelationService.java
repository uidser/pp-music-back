package com.uidser.ppmusic.rank.service;

import com.uidser.ppmusic.common.entity.MediaRankRelation;
import com.uidser.ppmusic.rank.entity.SingleMediaMap;

import java.util.Date;
import java.util.List;

public interface MediaRankRelationService {
    void insert(List<MediaRankRelation> mediaRankRelationList, Date date);

    List<MediaRankRelation> getByOrder(Long rankId, Integer frequency);

    SingleMediaMap getMap(Long mediaId, Long rankId);
}
