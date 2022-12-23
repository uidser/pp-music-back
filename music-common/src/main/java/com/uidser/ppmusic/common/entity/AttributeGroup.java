package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AttributeGroup {

    private Long id;
    private String groupName;
    private Integer showStatus;
    private Integer isDelete;
    private Date createTime;
    private Date updateTime;
    private Boolean hasChildren = true;
    private List<CategoryAttributeGroupRelation> categoryAttributeGroupRelationArrayList = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    private List<Long> categoryIdList;
    private List<Attribute> attributeList = new ArrayList<>();

}
