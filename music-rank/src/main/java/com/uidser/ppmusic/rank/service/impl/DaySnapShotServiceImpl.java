package com.uidser.ppmusic.rank.service.impl;

import com.uidser.ppmusic.common.entity.DaySnapShot;
import com.uidser.ppmusic.rank.mapper.DaySnapShotMapper;
import com.uidser.ppmusic.rank.service.DaySnapShotService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class DaySnapShotServiceImpl implements DaySnapShotService {

    @Resource
    private DaySnapShotMapper daySnapShotMapper;

    @Override
    public void addDayListenQuantity(List<DaySnapShot> daySnapShotList) {
        daySnapShotMapper.addDayListenQuantity(daySnapShotList);
    }

    @Override
    public List<DaySnapShot> getDaySnapShotList(Long time, Integer limit) {
        Date date = new Date(time);
        return daySnapShotMapper.getDaySnapShotList(date, limit);
    }
}
