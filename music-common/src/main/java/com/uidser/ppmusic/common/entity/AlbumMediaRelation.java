package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.util.List;

@Data
public class AlbumMediaRelation {

    private Long albumId;
    private List<Long> mediaIdList;

}
