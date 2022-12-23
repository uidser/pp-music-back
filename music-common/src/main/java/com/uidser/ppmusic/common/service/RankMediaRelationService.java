package com.uidser.ppmusic.common.service;

import com.uidser.ppmusic.common.entity.MediaRankRelation;
import com.uidser.ppmusic.common.entity.Rank;
import com.uidser.ppmusic.common.entity.RankMediaRelation;

import java.util.List;

public interface RankMediaRelationService {
    List<RankMediaRelation> getByRankId(List<Rank> rankList);

    List<MediaRankRelation> getOrderDetail(List<Long> ids);
}
