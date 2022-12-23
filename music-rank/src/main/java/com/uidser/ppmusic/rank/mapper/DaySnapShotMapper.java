package com.uidser.ppmusic.rank.mapper;

import com.uidser.ppmusic.common.entity.DaySnapShot;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DaySnapShotMapper {
    void addDayListenQuantity(@Param("list") List<DaySnapShot> daySnapShotList);

    List<DaySnapShot> getDaySnapShotList(@Param("date") Date date, @Param("limit") Integer limit);
}
