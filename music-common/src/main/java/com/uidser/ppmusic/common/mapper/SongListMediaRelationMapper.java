package com.uidser.ppmusic.common.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SongListMediaRelationMapper {
    void relation(@Param("songListId") Long id, @Param("songIdList") List<Long> songIdList);

    void deleteRelation(Long id);

    List<Long> getRelation(Long id);
}
