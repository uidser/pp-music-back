package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Mv {

    private Long id;
    private String title;
    private String mvUrl;
    private Date publishDate;
    private Long forwardQuantity;
    private Long songId;
    private Long watchQuantity;
    private Long isDelete;
    private Date createTime;
    private Date updateTime;
    private Integer showStatus;
    private Date prePublishDate;

}
