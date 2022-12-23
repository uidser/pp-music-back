package com.uidser.ppmusic.common.entity.vo;

import com.uidser.ppmusic.common.entity.Attribute;
import com.uidser.ppmusic.common.entity.AttributeGroup;
import com.uidser.ppmusic.common.entity.Category;
import lombok.Data;

import java.util.List;

@Data
public class AttributeReturnVo extends Attribute {

    private AttributeGroup attributeGroup;
    private List<Category> categoryList;

}
