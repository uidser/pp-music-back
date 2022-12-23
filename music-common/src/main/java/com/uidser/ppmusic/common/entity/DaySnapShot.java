package com.uidser.ppmusic.common.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DaySnapShot {

    private Long id;
    private Long mediaId;
    private Integer mediaType;
    private Long listenQuantity;
    private LocalDate snapShotTime;

}
