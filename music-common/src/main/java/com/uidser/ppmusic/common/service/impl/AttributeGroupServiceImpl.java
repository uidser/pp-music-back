package com.uidser.ppmusic.common.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.mapper.AttributeGroupMapper;
import com.uidser.ppmusic.common.entity.*;
import com.uidser.ppmusic.common.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class AttributeGroupServiceImpl implements AttributeGroupService {

    @Resource
    private AttributeGroupMapper attributeGroupMapper;

    @Resource
    private AttributeGroupAttributeRelationService attributeGroupAttributeRelationService;

    @Resource
    private AttributeService attributeService;

    @Resource
    private CategoryAttributeGroupRelationService categoryAttributeGroupRelationService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private ComponentInputService componentInputService;

    @Override
    public PageInfo<AttributeGroup> page(QueryVo queryVo) {
        Page<Object> page = PageHelper.startPage(queryVo.getCurrent(), queryVo.getLimit());
        List<AttributeGroup> attributeGroupList = attributeGroupMapper.getNote(queryVo);
        List<Long> attributeGroupIdList = new ArrayList<>();
        for (AttributeGroup attributeGroup: attributeGroupList) {
            attributeGroupIdList.add(attributeGroup.getId());
        }
        List<Long> categoryIds = new ArrayList<>();
        Map<Long, AttributeGroup> groupMap = attributeGroupList.stream().collect(Collectors.toMap(attributeGroup -> attributeGroup.getId(), attributeGroup -> attributeGroup));
        if(groupMap.size() > 0) {
            List<CategoryAttributeGroupRelation> attributeGroupRelationList = categoryAttributeGroupRelationService.getRelation(attributeGroupIdList);
            for (CategoryAttributeGroupRelation categoryAttributeGroupRelation1 :attributeGroupRelationList) {
                categoryIds.add(categoryAttributeGroupRelation1.getCategoryId());
            }
            List<Category> categoryList = categoryService.getByIds(categoryIds);
            Map<Long, Category> categoryMap = categoryList.stream().collect(Collectors.toMap(category -> category.getId(), category -> category));
            for (CategoryAttributeGroupRelation categoryAttributeGroupRelation1:attributeGroupRelationList) {
                if(attributeGroupIdList.contains(categoryAttributeGroupRelation1.getAttributeGroupId())) {
                    groupMap.get(categoryAttributeGroupRelation1.getAttributeGroupId()).getCategoryAttributeGroupRelationArrayList().add(categoryAttributeGroupRelation1);
                    Set<Long> categoryKeySet = categoryMap.keySet();
                    if(categoryKeySet.contains(categoryAttributeGroupRelation1.getCategoryId())) {
                        groupMap.get(categoryAttributeGroupRelation1.getAttributeGroupId()).getCategoryList().add(categoryMap.get(categoryAttributeGroupRelation1.getCategoryId()));
                    }
                }
            }
            List<AttributeGroup> attributeGroupList2 = new ArrayList<>();
            Set<Long> longSet = groupMap.keySet();
            for (Long id: longSet) {
                AttributeGroup attributeGroup = groupMap.get(id);
                attributeGroupList2.add(attributeGroup);
            }
            PageInfo<AttributeGroup> attributeGroupPageInfo = new PageInfo<>(attributeGroupList2);
            BeanUtils.copyProperties(page, attributeGroupPageInfo);
            return attributeGroupPageInfo;
        }
        return null;
    }

    @Override
    public List<Attribute> getAttributeByAttributeGroupId(Long id) {
        List<Long> ids = attributeGroupAttributeRelationService.getRelation(id);
        if(ids != null && ids.size() > 0) {
            return attributeService.getByIds(ids);
        }
        return null;
    }

    @Override
    public AttributeGroup getById(Long id) throws ExecutionException, InterruptedException {
        AttributeGroup attributeGroup = new AttributeGroup();
        CompletableFuture<Void> getBase = CompletableFuture.runAsync(() -> {
            AttributeGroup attributeGroup1 = attributeGroupMapper.getById(id);
            BeanUtils.copyProperties(attributeGroup1, attributeGroup);
        }, threadPoolExecutor);
        CompletableFuture<List<Long>> listCompletableFuture = CompletableFuture.supplyAsync(() -> {
            List<Long> ids = new ArrayList<>();
            List<CategoryAttributeGroupRelation> relation = categoryAttributeGroupRelationService.getRelation(Arrays.asList(id));
            for (CategoryAttributeGroupRelation categoryAttributeGroupRelation: relation) {
                ids.add(categoryAttributeGroupRelation.getCategoryId());
            }
            return ids;
        }, threadPoolExecutor);
        CompletableFuture<Void> getCategory = listCompletableFuture.thenAcceptAsync((idList) -> {
            if(idList != null && idList.size() > 0) {
                List<Category> categoryList = categoryService.getByIds(idList);
                attributeGroup.setCategoryList(categoryList);
                List<Long> ids = new ArrayList<>();
                for (Category category:categoryList) {
                    ids.add(category.getId());
                }
                attributeGroup.setCategoryIdList(ids);
            }
        }, threadPoolExecutor);
        CompletableFuture<Void> allOf = CompletableFuture.allOf(getBase, getCategory);
        allOf.get();
        return attributeGroup;
    }

    @Override
    @Transactional
    public void insert(AttributeGroup attributeGroup) {
        attributeGroup.setCreateTime(new Date());
        attributeGroupMapper.insert(attributeGroup);
        categoryAttributeGroupRelationService.insertAttributeGroupCategoryRelation(attributeGroup);
    }

    @Override
    @Transactional
    public void edit(AttributeGroup attributeGroup) {
        attributeGroup.setUpdateTime(new Date());
        attributeGroupMapper.edit(attributeGroup);
        categoryAttributeGroupRelationService.deleteRelation(attributeGroup);
        categoryAttributeGroupRelationService.insertAttributeGroupCategoryRelation(attributeGroup);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        attributeGroupMapper.delete(id);
        AttributeGroup attributeGroup = new AttributeGroup();
        attributeGroup.setId(id);
        categoryAttributeGroupRelationService.deleteRelation(attributeGroup);
        attributeGroupAttributeRelationService.deleteRelationByAttributeGroupId(id);
    }

    @Override
    public List<AttributeGroup> getAttributeGroupAndAttributeListByCategoryId(Long id) {
        List<Long> relationIds = categoryAttributeGroupRelationService.getByCategoryId(id);
        if(relationIds != null && relationIds.size() > 0) {
            List<AttributeGroup> attributeGroupList = attributeGroupMapper.getByIds(relationIds);
            Map<Long, AttributeGroup> attributeGroupMap = attributeGroupList.stream().collect(Collectors.toMap(attributeGroup -> attributeGroup.getId(), attributeGroup -> attributeGroup));
            Set<Long> attributeGroupIds = attributeGroupMap.keySet();
            List<AttributeGroupAttributeRelation> attributeGroupAttributeRelationList = attributeGroupAttributeRelationService.getByAttributeGroupIds(attributeGroupIds);
            if(attributeGroupAttributeRelationList != null && attributeGroupAttributeRelationList.size() > 0) {
                List<Long> attributeIds = new ArrayList<>();
                for (AttributeGroupAttributeRelation attributeGroupAttributeRelation: attributeGroupAttributeRelationList) {
                    attributeIds.add(attributeGroupAttributeRelation.getAttributeId());
                }
                if(attributeIds != null && attributeIds.size() > 0) {
                    List<Attribute> attributeList = attributeService.getByIds(attributeIds);

                    if(attributeList != null && attributeList.size() > 0) {
                        List<Long> componentInputIds = attributeList.stream().map(attribute -> attribute.getComponentInputId()).collect(Collectors.toList());
                        List<ComponentInput> componentInputList = componentInputService.getByIds(componentInputIds);
                        Map<Long, ComponentInput> componentInputMap = componentInputList.stream().collect(Collectors.toMap(componentInput -> componentInput.getId(), componentInput -> componentInput));
                        Map<Long, Attribute> attributeMap = attributeList.stream().collect(Collectors.toMap(attribute -> attribute.getId(), attribute -> attribute));
                        for (Attribute attribute: attributeList) {
                            attribute.setInputName(componentInputMap.get(attribute.getComponentInputId()).getInputName());
                        }
                        for (AttributeGroupAttributeRelation attributeGroupAttributeRelation: attributeGroupAttributeRelationList) {
                            Attribute attribute = attributeMap.get(attributeGroupAttributeRelation.getAttributeId());
                            attributeGroupMap.get(attributeGroupAttributeRelation.getAttributeGroupId()).getAttributeList().add(attribute);
                        }
                        return attributeGroupList;
                    }
                }
            }
        }
        return null;
    }

}
