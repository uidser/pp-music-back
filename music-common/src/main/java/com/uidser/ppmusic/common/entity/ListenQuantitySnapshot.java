package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ListenQuantitySnapshot {

    private Long id;
    private Long rankId;
    private Long mediaId;
    private Integer rankFrequency;
    private Date createTime;
    private Date snapshotTime;
    private Long listenQuantity;
    private Integer mediaType;
    
}
