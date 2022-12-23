package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.vo.AttributeAttributeValueVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttributeValueMapper {
    void insert(@Param("list") List<AttributeAttributeValueVo> attributeAttributeValueVoList);

    void update(@Param("list") List<AttributeAttributeValueVo> attributeAttributeValueVoList);
}
