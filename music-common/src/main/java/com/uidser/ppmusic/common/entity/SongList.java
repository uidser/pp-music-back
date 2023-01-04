package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SongList {

    private Long id;
    private String name;
    private Long ownerId;
    private String introduction;
    private String profilePicture;
    private Long forwardQuantity;
    private Long commentQuantity;
    private Long favoriteQuantity;
    private Long playQuantity;
    private Long isDelete;
    private Date createTime;
    private Date updateTime;
    private List<Media> songList;
    private List<Long> lastCategoryIdList;
    private List<List<Long>> categoryIdList = new ArrayList<>();
    private List<Long> songIdList;
    private Integer showStatus;
    private User owner;
    
}
