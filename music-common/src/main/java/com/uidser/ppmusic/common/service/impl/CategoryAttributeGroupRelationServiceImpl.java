package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.AttributeGroup;
import com.uidser.ppmusic.common.entity.CategoryAttributeGroupRelation;
import com.uidser.ppmusic.common.mapper.CategoryAttributeGroupRelationMapper;
import com.uidser.ppmusic.common.service.CategoryAttributeGroupRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryAttributeGroupRelationServiceImpl implements CategoryAttributeGroupRelationService {

    @Resource
    private CategoryAttributeGroupRelationMapper categoryAttributeGroupRelationMapper;

    @Override
    public List<CategoryAttributeGroupRelation> getRelation(List<Long> attributeGroupIdList) {
        return categoryAttributeGroupRelationMapper.getRelation(attributeGroupIdList);
    }

    @Override
    public void insertAttributeGroupCategoryRelation(AttributeGroup attributeGroup) {
        categoryAttributeGroupRelationMapper.insertAttributeGroupCategoryRelation(attributeGroup);
    }

    @Override
    public void deleteRelation(AttributeGroup attributeGroup) {
        categoryAttributeGroupRelationMapper.deleteRelation(attributeGroup);
    }

    @Override
    public List<Long> getByCategoryId(Long id) {
        return categoryAttributeGroupRelationMapper.getByCategoryId(id);
    }

    @Override
    public void deleteRelationByCategoryIds(Long[] ids) {
        categoryAttributeGroupRelationMapper.deleteRelationByCategoryIds(ids);
    }
}
