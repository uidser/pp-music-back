package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.Attribute;
import com.uidser.ppmusic.common.entity.AttributeGroup;
import com.uidser.ppmusic.common.entity.Category;
import com.uidser.ppmusic.common.entity.CategoryAttributeGroupRelation;
import com.uidser.ppmusic.common.entity.vo.AttributeCommitVo;
import com.uidser.ppmusic.common.entity.vo.AttributeReturnVo;
import com.uidser.ppmusic.common.mapper.AttributeMapper;
import com.uidser.ppmusic.common.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Resource
    private AttributeMapper attributeMapper;

    @Resource
    private AttributeGroupAttributeRelationService attributeGroupAttributeRelationService;

    @Resource
    private CategoryAttributeGroupRelationService categoryAttributeGroupRelationService;

    @Resource
    private AttributeGroupService attributeGroupService;

    @Resource
    private CategoryService categoryService;

    @Override
    public List<Attribute> getByIds(List<Long> ids) {
        return attributeMapper.getByIds(ids);
    }

    @Override
    @Transactional
    public void insert(AttributeCommitVo attributeCommitVo) {
        attributeCommitVo.setCreateTime(new Date());
        attributeMapper.insert(attributeCommitVo);
        attributeGroupAttributeRelationService.insert(attributeCommitVo);
    }

    @Override
    public List<Attribute> getAttributeByCategoryId(Long id) {
        List<Long> attributeGroupIds = categoryAttributeGroupRelationService.getByCategoryId(id);
        List<Long> attributeIds = attributeGroupAttributeRelationService.getByAttributeGroupIds(attributeGroupIds);
        if(attributeIds != null && attributeIds.size() > 0) {
            return attributeMapper.getByIds(attributeIds);
        }
        return null;
    }

    @Override
    public AttributeReturnVo getAttributeReturnVoById(Long id) throws ExecutionException, InterruptedException {
        List<Attribute> attributeList = attributeMapper.getByIds(Arrays.asList(id));
        Attribute attribute = attributeList.get(0);
        AttributeReturnVo attributeReturnVo = new AttributeReturnVo();
        BeanUtils.copyProperties(attribute, attributeReturnVo);
        Long attributeGroupId = attributeGroupAttributeRelationService.getRelationByAttributeId(id);
        AttributeGroup attributeGroup = attributeGroupService.getById(attributeGroupId);
        attributeReturnVo.setAttributeGroup(attributeGroup);
        List<CategoryAttributeGroupRelation> categoryAttributeGroupRelationList = categoryAttributeGroupRelationService.getRelation(Arrays.asList(attributeGroup.getId()));
        List<Long> categoryIds = new ArrayList<>();
        for (CategoryAttributeGroupRelation categoryAttributeGroupRelation: categoryAttributeGroupRelationList) {
            categoryIds.add(categoryAttributeGroupRelation.getCategoryId());
        }
        List<Category> categoryList = categoryService.getByIds(categoryIds);
        attributeReturnVo.setCategoryList(categoryList);
        return attributeReturnVo;
    }

    @Override
    public void edit(Attribute attribute) {
        attribute.setUpdateTime(new Date());
        attributeMapper.edit(attribute);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        attributeMapper.delete(id);
        attributeGroupAttributeRelationService.deleteRelationByAttributeId(id);
    }
}
