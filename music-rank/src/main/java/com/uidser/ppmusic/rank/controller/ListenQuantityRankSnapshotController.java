package com.uidser.ppmusic.rank.controller;

import com.uidser.ppmusic.common.entity.ListenQuantitySnapshot;
import com.uidser.ppmusic.common.entity.Scheduled;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.rank.service.ListenQuantityRankSnapshotService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/snapshot")
public class ListenQuantityRankSnapshotController {

    @Resource
    private ListenQuantityRankSnapshotService listenQuantityRankSnapshotService;

    @GetMapping("/getByRankIdAndFrequency")
    public R<List<ListenQuantitySnapshot>> getByRankId(Scheduled scheduled) {
        List<ListenQuantitySnapshot> listenQuantitySnapshotList = listenQuantityRankSnapshotService.getByRankIdAndFrequency(scheduled);
        return new R<List<ListenQuantitySnapshot>>().ok(listenQuantitySnapshotList);
    }

}
