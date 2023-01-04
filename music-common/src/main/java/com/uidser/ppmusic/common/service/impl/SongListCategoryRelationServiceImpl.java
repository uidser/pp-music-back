package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.Category;
import com.uidser.ppmusic.common.mapper.SongListCategoryRelationMapper;
import com.uidser.ppmusic.common.service.CategoryService;
import com.uidser.ppmusic.common.service.SongListCategoryRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SongListCategoryRelationServiceImpl implements SongListCategoryRelationService {

    @Resource
    private SongListCategoryRelationMapper songListCategoryRelationMapper;

    @Resource
    private CategoryService categoryService;

    @Override
    public void relation(Long id, List<Long> categoryIdList) {
        songListCategoryRelationMapper.relation(id, categoryIdList);
    }

    @Override
    public void deleteRelation(Long id) {
        songListCategoryRelationMapper.deleteRelation(id);
    }

    @Override
    public List<Long> getRelation(Long id) {
        return songListCategoryRelationMapper.getRelation(id);
    }

    @Override
    public List<Category> getAll() {
        List<Long> longList = songListCategoryRelationMapper.getAll();
        return categoryService.getByIdsAndShowStatus(longList);
    }

    @Override
    public List<Long> getByCategoryId(Long categoryId) {
        return songListCategoryRelationMapper.getByCategoryId(categoryId);
    }
}
