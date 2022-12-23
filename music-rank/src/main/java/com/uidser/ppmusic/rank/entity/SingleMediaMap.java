package com.uidser.ppmusic.rank.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SingleMediaMap {

    private String mediaName;
    private List<Date> rankDateList = new ArrayList<>();
    private List<Long> listenQuantityList = new ArrayList<>();

}
