package com.uidser.ppmusic.common.service;

import com.uidser.ppmusic.common.entity.Category;

import java.util.List;

public interface SongListCategoryRelationService {
    void relation(Long id, List<Long> categoryIdList);

    void deleteRelation(Long id);

    List<Long> getRelation(Long id);

    List<Category> getAll();

    List<Long> getByCategoryId(Long categoryId);
}
