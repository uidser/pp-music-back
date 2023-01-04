package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.AlbumSingerRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumSingerRelationMapper {
    void relation(@Param("albumId") Long albumId, @Param("list") List<Long> singerIdList1);

    List<AlbumSingerRelation> getRelationByAlbumId(@Param("albumId") Long albumId);

    List<AlbumSingerRelation> getRelationBySingerId(@Param("singerId") Long id, @Param("limit") int i);
}
