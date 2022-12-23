package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.MediaRankRelation;
import com.uidser.ppmusic.common.entity.Rank;
import com.uidser.ppmusic.common.entity.RankMediaRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RankMediaRelationMapper {
    List<RankMediaRelation> getByRankId(@Param("list") List<Rank> rankList);

    List<MediaRankRelation> getOrderDetail(@Param("list") List<Long> ids);
}
