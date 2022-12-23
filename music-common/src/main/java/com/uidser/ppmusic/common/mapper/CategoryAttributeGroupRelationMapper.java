package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.AttributeGroup;
import com.uidser.ppmusic.common.entity.CategoryAttributeGroupRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryAttributeGroupRelationMapper {
    List<CategoryAttributeGroupRelation> getRelation(@Param("ids") List<Long> attributeGroupIdList);

    void insertAttributeGroupCategoryRelation(@Param("attributeGroup") AttributeGroup attributeGroup);

    void deleteRelation(AttributeGroup attributeGroup);

    List<Long> getByCategoryId(Long id);

    void deleteRelationByCategoryIds(Long[] ids);
}
