package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Song {

    private Long id;
    private String name;
    private String author;
    private String songUrl;
    private Long singerId;
    private String album;
    private Long albumId;
    private Long favoriteQuantity;
    private String songImg;
    private Date publishDate;
    private String songTime;
    private Long mvId;
    private Integer isHaveMv;
    private Date createTime;
    private Date updateTime;
    private String songWord;
    private String songMv;
    private Integer isDelete;
    private Integer showStatus;

}
