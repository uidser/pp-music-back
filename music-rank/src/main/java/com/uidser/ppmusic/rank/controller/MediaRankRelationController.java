package com.uidser.ppmusic.rank.controller;

import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.entity.MediaRankRelation;
import com.uidser.ppmusic.rank.entity.SingleMediaMap;
import com.uidser.ppmusic.rank.service.MediaRankRelationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rank/mediaRankRelation")
public class MediaRankRelationController {

    @Resource
    private MediaRankRelationService mediaRankRelationService;

    @PostMapping("/insert/{date}")
    public R insert(@RequestBody List<MediaRankRelation> mediaRankRelationList,
                    @PathVariable Date date) {
        mediaRankRelationService.insert(mediaRankRelationList, date);
        return new R().ok();
    }

    @GetMapping("/getStepByOrder/{rankId}/{frequency}")
    public R<List<MediaRankRelation>> getByOrder(@PathVariable Long rankId,
                                                 @PathVariable Integer frequency) {
        List<MediaRankRelation> mediaRankRelationList = mediaRankRelationService.getByOrder(rankId, frequency);
        return new R<List<MediaRankRelation>>().ok(mediaRankRelationList);
    }

    @GetMapping("/getSingleMediaMap/{mediaId}/{rankId}")
    public R<SingleMediaMap> getMap(@PathVariable Long mediaId,
                                    @PathVariable Long rankId) {
        SingleMediaMap singleMediaMap = mediaRankRelationService.getMap(mediaId, rankId);
        return new R<SingleMediaMap>().ok(singleMediaMap);
    }

}
