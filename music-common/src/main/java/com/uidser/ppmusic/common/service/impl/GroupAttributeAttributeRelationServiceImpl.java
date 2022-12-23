package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.AttributeGroupAttributeRelation;
import com.uidser.ppmusic.common.entity.vo.AttributeCommitVo;
import com.uidser.ppmusic.common.mapper.AttributeGroupAttributeRelationMapper;
import com.uidser.ppmusic.common.service.AttributeAttributeValueRelationService;
import com.uidser.ppmusic.common.service.AttributeGroupAttributeRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class GroupAttributeAttributeRelationServiceImpl implements AttributeGroupAttributeRelationService {

    @Resource
    private AttributeGroupAttributeRelationMapper attributeGroupAttributeRelationMapper;

    @Resource
    private AttributeAttributeValueRelationService attributeAttributeValueRelationService;

    @Override
    public List<Long> getRelation(Long id) {
        if(id != null && id > 0) {
            return attributeGroupAttributeRelationMapper.getRelation(id);
//            if(ids.size() > 0) {
//                return attributeAttributeValueRelationService.getAttributeAttributeValueRelation(ids);
//            }
        }
        return null;
    }

    @Override
    public void deleteRelationByAttributeGroupId(Long id) {
        attributeGroupAttributeRelationMapper.deleteRelationByAttributeGroupId(id);
    }

    @Override
    public void insert(AttributeCommitVo attributeCommitVo) {
        attributeGroupAttributeRelationMapper.insert(attributeCommitVo);
    }

    @Override
    public List<Long> getByAttributeGroupIds(List<Long> attributeGroupIds) {
        if(attributeGroupIds != null && attributeGroupIds.size() > 0) {
            return attributeGroupAttributeRelationMapper.getByAttributeGroupIds(attributeGroupIds);
        }
        return null;
    }

    @Override
    public Long getRelationByAttributeId(Long id) {
        return attributeGroupAttributeRelationMapper.getRelationByAttributeId(id);
    }

    @Override
    public void deleteRelationByAttributeId(Long id) {
        attributeGroupAttributeRelationMapper.deleteRelationByAttributeId(id);
    }

    @Override
    public List<AttributeGroupAttributeRelation> getByAttributeGroupIds(Set<Long> attributeGroupIds) {
        if(attributeGroupIds != null && attributeGroupIds.size() > 0) {
            return attributeGroupAttributeRelationMapper.getRelationByAttributeGroupIds(attributeGroupIds);
        }
        return null;
    }
}
