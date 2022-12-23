package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.Singer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SingerMediaRelationMapper {
    void relation(@Param("mediaId") Long id, @Param("singerIds") List<Long> singerIdList);

    List<Long> get(Long id);
}
