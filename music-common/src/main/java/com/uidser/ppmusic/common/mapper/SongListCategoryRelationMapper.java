package com.uidser.ppmusic.common.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SongListCategoryRelationMapper {
    void relation(@Param("songListId") Long id, @Param("categoryIdList") List<Long> categoryIdList);

    void deleteRelation(Long id);

    List<Long> getRelation(Long id);

    List<Long> getAll();

    List<Long> getByCategoryId(Long categoryId);
}
