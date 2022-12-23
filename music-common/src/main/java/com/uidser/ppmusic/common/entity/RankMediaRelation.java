package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class RankMediaRelation {

    private Long id;
    private Long mediaId;
    private Long rankId;
    private Integer rankDetail;
    private Date lastRankDate;
    private Integer rankFrequency;
    
}
