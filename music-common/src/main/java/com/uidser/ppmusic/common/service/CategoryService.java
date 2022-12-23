package com.uidser.ppmusic.common.service;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.AttributeGroup;
import com.uidser.ppmusic.common.entity.Category;
import com.uidser.ppmusic.common.entity.CategorySingerRelation;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CategoryService {
    List<Category> getAll();

    List<Category> getByIds(Set<Long> categoryIds);

    List<Category> getByIds(List<Long> categoryIds);
    List<AttributeGroup> getAttributeGroupAndAttributeByCategoryId(Long id);

    PageInfo<Category> page(QueryVo queryVo);

    void changeShowStatus(Long categoryId, Integer status);

    void batchDelete(Long[] ids);

    void insert(Category category);

    void edit(Category category);

    List<Category> getMoreLevelCategory();

    public void packageMoreLevelCategory(Category category, List<Category> categoryList, List<Category> finalCategory);

    Map<Long, List<List<Long>>> getThreeLevelCategory(List<CategorySingerRelation> categorySingerRelationList);
}
