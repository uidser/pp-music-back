package com.uidser.ppmusic.common.entity;

import lombok.Data;

@Data
public class AlbumSingerRelation {

    private Long id;
    private Long albumId;
    private Long singerId;

}
