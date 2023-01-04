package com.uidser.ppmusic.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.AttributeGroup;
import com.uidser.ppmusic.common.entity.Category;
import com.uidser.ppmusic.common.entity.CategorySingerRelation;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.mapper.CategoryMapper;
import com.uidser.ppmusic.common.service.AttributeGroupService;
import com.uidser.ppmusic.common.service.CategoryAttributeGroupRelationService;
import com.uidser.ppmusic.common.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private AttributeGroupService attributeGroupService;

    @Resource
    private CategoryAttributeGroupRelationService attributeGroupRelationService;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public List<Category> getAll() {
        return categoryMapper.getAll();
    }

    @Override
    public List<Category> getByIds(Set<Long> categoryIds) {
        return categoryMapper.getByIds(categoryIds);
    }

    @Override
    public List<Category> getByIds(List<Long> categoryIds) {
        return categoryMapper.getByIds(categoryIds);
    }

    @Override
    public List<AttributeGroup> getAttributeGroupAndAttributeByCategoryId(Long id) {
        return attributeGroupService.getAttributeGroupAndAttributeListByCategoryId(id);
    }

    @Override
    public PageInfo<Category> page(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getCurrent(), queryVo.getLimit());
        List<Category> categoryList = categoryMapper.getAllTerms(queryVo);
        return new PageInfo<>(categoryList);
    }

    @Override
    public void changeShowStatus(Long categoryId, Integer status) {
        Date date = new Date();
        categoryMapper.changeShowStatus(categoryId, status, date);
    }

    @Override
    @Transactional
    public void batchDelete(Long[] ids) {
        if(ids != null && ids.length > 0) {
            CompletableFuture.runAsync(() -> {
                categoryMapper.batchDelete(ids);
            }, threadPoolExecutor);
            CompletableFuture.runAsync(() -> {
                attributeGroupRelationService.deleteRelationByCategoryIds(ids);
            }, threadPoolExecutor);
        }
    }

    @Override
    public void insert(Category category) {
        category.setCreateTime(new Date());
        if(category.getParentId() == null) {
            category.setParentId(0L);
        }
        categoryMapper.insert(category);
    }

    @Override
    public void edit(Category category) {
        category.setParentId(0L);
        category.setUpdateTime(new Date());
        categoryMapper.edit(category);
    }

    @Override
    public List<Category> getMoreLevelCategory() {
        List<Category> categoryList = categoryMapper.getAll();
        List<Category> finalCategory = new ArrayList<>();
        for (Category category: categoryList) {
            if(category.getParentId() == 0) {
                finalCategory.add(category);
                this.packageMoreLevelCategory(category, categoryList, finalCategory);
            }
        }
        return finalCategory;
    }

    public void packageMoreLevelCategory(Category category, List<Category> categoryList, List<Category> finalCategory) {
        List<Category> categoryList1 = new ArrayList<>();
        for (Category category1: categoryList) {
            if(category1.getParentId() == category.getId()) {
                categoryList1.add(category1);
                this.packageMoreLevelCategory(category1, categoryList, finalCategory);
            }
            category.setCategoryChildrenList(categoryList1);
        }
    }

    @Override
    public Map<Long, List<List<Long>>> getThreeLevelCategory(List<CategorySingerRelation> categorySingerRelationList) {
        List<Category> allCategoryList = categoryMapper.getAll();
        Map<Long, List<List<Long>>> map = new HashMap<>();
        List<Category> finaCategoryList = new ArrayList<>();
        for (CategorySingerRelation categorySingerRelation: categorySingerRelationList) {
            Category category = new Category();
            category.setId(categorySingerRelation.getCategoryId());
            packageMoreLevelCategory(category, allCategoryList, finaCategoryList);
        }
        for (Category category: allCategoryList) {
            if(category.getParentId() == 0) {
                this.packageMoreLevelCategory(category, allCategoryList, finaCategoryList);
                finaCategoryList.add(category);
            }
        }
        Map<Long, Category> categoryMap = new HashMap<>();
        for (Category category: allCategoryList) {
            categoryMap.put(category.getId(), category);
            if(category.getCategoryChildrenList().size() > 0) {
                packageCategoryMap(category.getCategoryChildrenList(), categoryMap);
            }
        }
        List<List<Long>> listList = new ArrayList<>();
        for (CategorySingerRelation categorySingerRelation: categorySingerRelationList) {
            List<Long> longList = new ArrayList<>();
            Long categoryId = categorySingerRelation.getCategoryId();
            longList.add(categoryId);
            findPreviousCategoryId(categoryId, categoryMap, longList);
            Collections.reverse(longList);
            listList.add(longList);
            map.put(categorySingerRelation.getSingerId(), listList);
        }
        return map;
    }

    @Override
    public List<Category> getByIdsAndShowStatus(List<Long> longList) {
        return categoryMapper.getByIdsAndShowStatus(longList);
    }

    private void findPreviousCategoryId(Long categoryId, Map<Long, Category> categoryMap, List<Long> longList) {
        if(categoryMap.get(categoryId).getParentId() != 0) {
            longList.add(categoryMap.get(categoryId).getParentId());
            findPreviousCategoryId(categoryMap.get(categoryId).getParentId(), categoryMap, longList);
        }
    }

    private void packageCategoryMap(List<Category> categoryChildrenList, Map<Long, Category> categoryMap) {
        for (Category category: categoryChildrenList) {
            categoryMap.put(category.getId(), category);
            if(category.getCategoryChildrenList().size() > 0) {
                packageCategoryMap(category.getCategoryChildrenList(), categoryMap);
            }
        }
    }
}
