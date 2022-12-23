package com.uidser.ppmusic.common.service;


import com.uidser.ppmusic.common.entity.Attribute;
import com.uidser.ppmusic.common.entity.vo.AttributeCommitVo;
import com.uidser.ppmusic.common.entity.vo.AttributeReturnVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AttributeService {
    List<Attribute> getByIds(List<Long> ids);

    void insert(AttributeCommitVo attributeCommitVo);

    List<Attribute> getAttributeByCategoryId(Long id);

    AttributeReturnVo getAttributeReturnVoById(Long id) throws ExecutionException, InterruptedException;

    void edit(Attribute attribute);

    void delete(Long id);
}
