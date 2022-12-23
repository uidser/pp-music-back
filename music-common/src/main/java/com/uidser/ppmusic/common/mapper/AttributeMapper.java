package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.Attribute;
import com.uidser.ppmusic.common.entity.vo.AttributeCommitVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttributeMapper {
    List<Attribute> getByIds(@Param("ids") List<Long> ids);

    void insert(AttributeCommitVo attributeCommitVo);

    void edit(Attribute attribute);

    void delete(Long id);
}
