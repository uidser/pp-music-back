package com.uidser.ppmusic.rank.service;

import com.uidser.ppmusic.common.entity.DaySnapShot;

import java.util.List;

public interface DaySnapShotService {
    void addDayListenQuantity(List<DaySnapShot> daySnapShotList);

    List<DaySnapShot> getDaySnapShotList(Long time, Integer limit);
}
