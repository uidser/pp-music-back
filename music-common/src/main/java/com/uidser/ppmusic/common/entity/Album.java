package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Album {

    private Long id;
    private String name;
    private Long singerId;
    private String profilePicture;
    private Date publish_date;
    private String detail;
    private Long favoriteQuantity;
    private Integer isDelete;
    private Integer showStatus;
    private Date createTime;
    private Date updateTime;

}
