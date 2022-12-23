package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.AttributeGroupAttributeRelation;
import com.uidser.ppmusic.common.entity.vo.AttributeCommitVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface AttributeGroupAttributeRelationMapper {
    List<Long> getRelation(Long id);

    void deleteRelationByAttributeGroupId(Long id);

    void insert(AttributeCommitVo attributeCommitVo);

    List<Long> getByAttributeGroupIds(@Param("ids") List<Long> attributeGroupIds);

    Long getRelationByAttributeId(Long id);

    void deleteRelationByAttributeId(Long id);

    List<AttributeGroupAttributeRelation> getRelationByAttributeGroupIds(@Param("ids") Set<Long> attributeGroupIds);
}
