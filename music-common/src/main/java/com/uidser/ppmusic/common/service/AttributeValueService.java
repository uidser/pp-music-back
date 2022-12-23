package com.uidser.ppmusic.common.service;

import com.uidser.ppmusic.common.entity.vo.AttributeAttributeValueVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttributeValueService {
    void insert(@Param("list") List<AttributeAttributeValueVo> attributeAttributeValueVoList);

    void update(List<AttributeAttributeValueVo> attributeAttributeValueVoList);
}
