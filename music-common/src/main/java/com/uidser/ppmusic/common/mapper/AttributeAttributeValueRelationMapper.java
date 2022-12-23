package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.vo.AttributeAttributeValueVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttributeAttributeValueRelationMapper {
    List<Long> getAttributeAttributeValueRelation(@Param("ids") List<Long> ids);

    void relation(@Param("list") List<AttributeAttributeValueVo> attributeAttributeValueVoList, @Param("mediaId") Long id);
}
