package com.uidser.ppmusic.common.service;


import com.uidser.ppmusic.common.entity.AttributeGroupAttributeRelation;
import com.uidser.ppmusic.common.entity.vo.AttributeCommitVo;

import java.util.List;
import java.util.Set;

public interface AttributeGroupAttributeRelationService {
    List<Long> getRelation(Long id);

    void deleteRelationByAttributeGroupId(Long id);

    void insert(AttributeCommitVo attributeCommitVo);

    List<Long> getByAttributeGroupIds(List<Long> attributeGroupIds);

    Long getRelationByAttributeId(Long id);

    void deleteRelationByAttributeId(Long id);

    List<AttributeGroupAttributeRelation> getByAttributeGroupIds(Set<Long> attributeGroupIds);
}
