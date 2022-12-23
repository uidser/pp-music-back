package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.vo.AttributeAttributeValueVo;
import com.uidser.ppmusic.common.mapper.AttributeValueMapper;
import com.uidser.ppmusic.common.service.AttributeValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AttributeValueServiceImpl implements AttributeValueService {

    @Resource
    private AttributeValueMapper attributeValueMapper;

    @Override
    public void insert(List<AttributeAttributeValueVo> attributeAttributeValueVoList) {
        attributeValueMapper.insert(attributeAttributeValueVoList);
    }

    @Override
    public void update(List<AttributeAttributeValueVo> attributeAttributeValueVoList) {
        attributeValueMapper.update(attributeAttributeValueVoList);
    }
}
