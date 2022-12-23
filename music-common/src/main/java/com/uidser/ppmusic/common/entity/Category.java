package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Category {

    private Long id;
    private Long parentId;
    private String name;
    private Integer showStatus;
    private Integer isDelete;
    private Date createTime;
    private Date updateTime;
    private List<Category> categoryChildrenList = new ArrayList<>();

}
