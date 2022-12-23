package com.uidser.ppmusic.rank.controller;

import com.uidser.ppmusic.common.entity.DaySnapShot;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.rank.service.DaySnapShotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/rank")
public class DaySnapShotController {

    @Resource
    private DaySnapShotService daySnapShotService;

    @GetMapping("/getDaySnapShotList/{time}/{limit}")
    public R<List<DaySnapShot>>  getDaySnapShotList(@PathVariable Long time,
                                                    @PathVariable Integer limit) {
        List<DaySnapShot> daySnapShotList = daySnapShotService.getDaySnapShotList(time, limit);
        return new R<List<DaySnapShot>>().ok(daySnapShotList);
    }

}
