package com.uidser.ppmusic.common.service;

import com.uidser.ppmusic.common.entity.vo.AttributeAttributeValueVo;

import java.util.List;

public interface AttributeAttributeValueRelationService {
    List<Long> getAttributeAttributeValueRelation(List<Long> ids);

    void relation(List<AttributeAttributeValueVo> attributeAttributeValueVoList, Long id);
}
