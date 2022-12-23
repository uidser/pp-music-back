package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.AttributeGroup;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttributeGroupMapper {
    List<AttributeGroup> getNote(QueryVo queryVo);

    AttributeGroup getById(Long id);

    void insert(AttributeGroup attributeGroup);

    void edit(AttributeGroup attributeGroup);

    void delete(Long id);

    List<AttributeGroup> getByIds(@Param("ids") List<Long> relationIds);
}
