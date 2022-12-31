package com.uidser.ppmusic.common.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumSingerRelationMapper {
    void relation(@Param("albumId") Long albumId, @Param("list") List<Long> singerIdList1);
}
