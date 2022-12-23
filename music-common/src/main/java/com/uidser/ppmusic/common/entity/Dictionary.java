package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Dictionary {

    private Long id;
    private Long parentId;
    private String name;
    private int type;
    private String redirectUrl;
    private int showStatus;
    private int isDelete;
    private Date createTime;
    private Date updateTime;

}
