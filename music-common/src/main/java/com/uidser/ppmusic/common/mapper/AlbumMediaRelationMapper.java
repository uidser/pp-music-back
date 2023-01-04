package com.uidser.ppmusic.common.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumMediaRelationMapper {
    void relation(@Param("albumId") Long albumId, @Param("mediaId") Long id);

    List<Long> getByAlbumId(@Param("albumId") Long albumId);

    void deleteRelation(@Param("albumId") Long albumId);

    void relationList(@Param("albumId") Long albumId, @Param("list") List<Long> mediaIdList);
}
