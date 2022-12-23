package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.vo.AttributeAttributeValueVo;
import com.uidser.ppmusic.common.mapper.AttributeAttributeValueRelationMapper;
import com.uidser.ppmusic.common.service.AttributeAttributeValueRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AttributeAttributeValueRelationServiceImpl implements AttributeAttributeValueRelationService {

    @Resource
    private AttributeAttributeValueRelationMapper attributeAttributeValueRelationMapper;

    @Override
    public List<Long> getAttributeAttributeValueRelation(List<Long> ids) {
        return attributeAttributeValueRelationMapper.getAttributeAttributeValueRelation(ids);
    }

    @Override
    public void relation(List<AttributeAttributeValueVo> attributeAttributeValueVoList, Long id) {
        attributeAttributeValueRelationMapper.relation(attributeAttributeValueVoList, id);
    }
}
