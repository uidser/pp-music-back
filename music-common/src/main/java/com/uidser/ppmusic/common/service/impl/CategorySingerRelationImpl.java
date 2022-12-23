package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.Category;
import com.uidser.ppmusic.common.entity.CategorySingerRelation;
import com.uidser.ppmusic.common.entity.Singer;
import com.uidser.ppmusic.common.mapper.CategorySingerRelationMapper;
import com.uidser.ppmusic.common.service.CategorySingerRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class CategorySingerRelationImpl implements CategorySingerRelationService {

    @Resource
    private CategorySingerRelationMapper categorySingerRelationMapper;

    @Override
    public void insert(List<Long> categoryIdList, Long id) {
        categorySingerRelationMapper.insert(categoryIdList, id);
    }

    @Override
    public List<Category> getAll() {
        return categorySingerRelationMapper.getAll();
    }

    @Override
    public void deleteRelation(Singer singer) {
        categorySingerRelationMapper.deleteRelation(singer);
    }

    @Override
    public List<CategorySingerRelation> getRelationBySingerIds(Set<Long> singerIds) {
        return categorySingerRelationMapper.getRelationBySingerIds(singerIds);
    }

    @Override
    public List<Singer> getRelationByCategoryIds(List<Long> categoryIdList) {
        return categorySingerRelationMapper.getRelationByCategoryIds(categoryIdList);
    }
}
