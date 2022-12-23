package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.Category;
import com.uidser.ppmusic.common.entity.CategorySingerRelation;
import com.uidser.ppmusic.common.entity.Singer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface CategorySingerRelationMapper {
    void insert(@Param("list") List<Long> categoryIdList, @Param("id") Long id);

    List<Category> getAll();

    void deleteRelation(Singer singer);

    List<CategorySingerRelation> getRelationBySingerIds(@Param("list") Set<Long> singerIds);

    List<Singer> getRelationByCategoryIds(@Param("list") List<Long> categoryIdList);
}
