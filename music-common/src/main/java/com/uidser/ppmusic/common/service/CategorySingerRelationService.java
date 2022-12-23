package com.uidser.ppmusic.common.service;

import com.uidser.ppmusic.common.entity.Category;
import com.uidser.ppmusic.common.entity.CategorySingerRelation;
import com.uidser.ppmusic.common.entity.Singer;

import java.util.List;
import java.util.Set;

public interface CategorySingerRelationService {
    void insert(List<Long> categoryIdList, Long id);

    List<Category> getAll();

    void deleteRelation(Singer singer);

    List<CategorySingerRelation> getRelationBySingerIds(Set<Long> singerIds);

    List<Singer> getRelationByCategoryIds(List<Long> categoryIdList);
}
