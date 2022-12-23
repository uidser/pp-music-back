package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MediaRankRelation {

    private Long id;
    private Long mediaId;
    private Long rankId;
    private Integer rankDetail;
    private Date lastRankDate;
    private Integer rankFrequency;
    private String mediaName;
    private Integer step;
    private Double growthRate;
    private Long listenQuantity;

}
