package com.uidser.ppmusic.common.entity;

import com.uidser.ppmusic.common.entity.es.Category;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Singer {

    private Long id;
    private String name;
    private String detail;
    private Long fansQuantity;
    private String profilePicture;
    private String title;
    private Integer isDelete;
    private Date createTime;
    private Date updateTime;
    private Integer showStatus;
    private Integer type;
    private List<Long> lastCategoryIdList = new ArrayList<>();
    private List<List<Long>> categoryIdList = new ArrayList<>();
    private List<Category> categoryList;

}
