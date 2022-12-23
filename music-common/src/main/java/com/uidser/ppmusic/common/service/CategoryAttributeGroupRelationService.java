package com.uidser.ppmusic.common.service;


import com.uidser.ppmusic.common.entity.AttributeGroup;
import com.uidser.ppmusic.common.entity.CategoryAttributeGroupRelation;

import java.util.List;

public interface CategoryAttributeGroupRelationService {
    List<CategoryAttributeGroupRelation> getRelation(List<Long> attributeGroupIdList);

    void insertAttributeGroupCategoryRelation(AttributeGroup attributeGroup);

    void deleteRelation(AttributeGroup attributeGroup);

    List<Long> getByCategoryId(Long id);

    void deleteRelationByCategoryIds(Long[] ids);
}
