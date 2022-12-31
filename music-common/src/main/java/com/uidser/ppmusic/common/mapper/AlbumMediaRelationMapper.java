package com.uidser.ppmusic.common.mapper;

import org.apache.ibatis.annotations.Param;

public interface AlbumMediaRelationMapper {
    void relation(@Param("albumId") Long albumId, @Param("mediaId") Long id);
}
