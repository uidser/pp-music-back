package com.uidser.ppmusic.common.feign;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.*;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.r.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@FeignClient("pp-music-rank")
public interface RankFeignService {

    @GetMapping("/rank/index/{limit}")
    public R<List<Rank>> index(@PathVariable("limit") Integer limit);

    @GetMapping("/rank/page")
    public R<PageInfo<Rank>> page(@SpringQueryMap QueryVo queryVo);

    @PutMapping("/rank/changeShowStatus/{rankId}/{status}")
    void changeShowStatus(@PathVariable("rankId") Long rankId,
                          @PathVariable("status") Integer status);

    @DeleteMapping("/rank/batchDelete")
    public R batchDelete(@RequestBody Long[] ids);

    @GetMapping("/rank/getRankDetailMediaList/{rankId}/{frequency}")
    public R<Rank> getRankDetailMediaList(@PathVariable Long rankId,
                                          @PathVariable Integer frequency);

    @PostMapping("/rank/insert")
    public R insert(@RequestBody Rank rank);

    @GetMapping("/rank/get/{rankId}")
    public R<Rank> get(@PathVariable("rankId") Long rankId);

    @PutMapping("/rank/edit")
    public R edit(@RequestBody Rank rank);

    @PostMapping("/rank/mediaRankRelation/insert/{date}")
    public R insertMediaRankRelation(@RequestBody List<MediaRankRelation> mediaRankRelationList,
                                     @PathVariable Date date);

    @GetMapping("/snapshot/getByRankIdAndFrequency")
    public R<List<ListenQuantitySnapshot>> getByRankId(@SpringQueryMap Scheduled scheduled);

    @PutMapping("/rank/addFrequency/{rankId}")
    public R addFrequency(@PathVariable("rankId") Long rankId,
                          @RequestBody Date date);

    @GetMapping("/rank/mediaRankRelation/getStepByOrder/{rankId}/{frequency}")
    public R<List<MediaRankRelation>> getByOrder(@PathVariable Long rankId,
                                                 @PathVariable Integer frequency);

    @GetMapping("/rank/getDaySnapShotList/{time}/{limit}")
    public R<List<DaySnapShot>>  getDaySnapShotList(@PathVariable("time") Long time,
                                                    @PathVariable("limit") Integer limit);
}
