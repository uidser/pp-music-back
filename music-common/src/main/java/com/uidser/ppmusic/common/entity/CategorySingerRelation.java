package com.uidser.ppmusic.common.entity;

import lombok.Data;

@Data
public class CategorySingerRelation {

    private Long id;
    private Long categoryId;
    private Long singerId;

}
