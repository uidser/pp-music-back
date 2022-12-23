package com.uidser.ppmusic.common.service;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Attribute;
import com.uidser.ppmusic.common.entity.AttributeGroup;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AttributeGroupService {
    PageInfo<AttributeGroup> page(QueryVo queryVo);

    List<Attribute> getAttributeByAttributeGroupId(Long id);

    AttributeGroup getById(Long id) throws ExecutionException, InterruptedException;

    void insert(AttributeGroup attributeGroup);

    void edit(AttributeGroup attributeGroup);

    void delete(Long id);

    List<AttributeGroup> getAttributeGroupAndAttributeListByCategoryId(Long id);
}
