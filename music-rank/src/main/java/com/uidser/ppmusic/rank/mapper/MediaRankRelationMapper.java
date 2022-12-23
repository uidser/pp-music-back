package com.uidser.ppmusic.rank.mapper;

import com.uidser.ppmusic.common.entity.MediaRankRelation;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MediaRankRelationMapper {
    void insert(@Param("list") List<MediaRankRelation> mediaRankRelationList, @Param("date") Date date);

    List<MediaRankRelation> getByOrder(@Param("rankId") Long rankId, @Param("frequency") Integer frequency);

    List<MediaRankRelation> getMap(@Param("mediaId") Long mediaId, @Param("rankId") Long rankId);
}
