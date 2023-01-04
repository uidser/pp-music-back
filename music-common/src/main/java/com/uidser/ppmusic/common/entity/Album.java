package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Album {

    private Long id;
    private String name;
    private Long singerId;
    private String profilePicture;
    private Date publishDate;
    private String detail;
    private Long favoriteQuantity;
    private Integer isDelete;
    private Integer showStatus;
    private Date createTime;
    private Date updateTime;
    private List<Long> singerIds;
    private List<Singer> singerList;
    private List<Media> mediaList;
    private List<Long> mediaIdList;

}
