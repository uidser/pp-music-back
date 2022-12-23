package com.uidser.ppmusic.rank.controller;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Rank;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.service.RankService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rank")
public class RankController {

    @Resource
    private RankService rankService;

    @GetMapping("/index/{limit}")
    public R<List<Rank>> index(@PathVariable Integer limit) {
        List<Rank> rankList = rankService.index(limit);
        return new R<List<Rank>>().ok(rankList);
    }

    @GetMapping("/page")
    public R<PageInfo<Rank>> page(QueryVo queryVo) {
        PageInfo<Rank> rankPageInfo = rankService.page(queryVo);
        return new R<PageInfo<Rank>>().ok(rankPageInfo);
    }

    @PutMapping("/changeShowStatus/{rankId}/{status}")
    public R changeShowStatus(@PathVariable Long rankId,
                              @PathVariable Integer status) {
        rankService.changeShowStatus(rankId, status);
        return new R().ok();
    }

    @DeleteMapping("/batchDelete")
    public R batchDelete(@RequestBody Long[] ids) {
        if(ids.length > 0) {
            rankService.batchDelete(ids);
        }
        return new R().ok();
    }

    @GetMapping("/getRankDetailMediaList/{rankId}/{frequency}")
    public R<Rank> getRankDetailMediaList(@PathVariable Long rankId,
                                          @PathVariable Integer frequency) {
        Rank rank = rankService.getRankDetailMediaList(rankId, frequency);
        return new R<Rank>().ok(rank);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody Rank rank) {
        rankService.insert(rank);
        return new R().ok();
    }

    @GetMapping("/get/{rankId}")
    public R<Rank> get(@PathVariable Long rankId) {
        Rank rank = rankService.get(rankId);
        return new R<Rank>().ok(rank);
    }

    @PutMapping("/edit")
    public R edit(@RequestBody Rank rank) {
        rankService.edit(rank);
        return new R().ok();
    }

    @PutMapping("/addFrequency/{rankId}")
    public R addFrequency(@PathVariable Long rankId,
                          @RequestBody Date date) {
        rankService.addFrequency(rankId, date);
        return new R().ok();
    }

    @GetMapping("/getRankDetailAndMediaList/{rankId}/{frequency}")
    public R<Rank> getRankDetail(@PathVariable Long rankId,
                                       @PathVariable Integer frequency,
                                       QueryVo queryVo) {
        Rank rankDetail = rankService.getRankDetail(rankId, frequency, queryVo);
        return new R<Rank>().ok(rankDetail);
    }

    @GetMapping("/getAll")
    public R<List<Rank>> getAll() {
        List<Rank> rankList = rankService.getAll();
        return new R<List<Rank>>().ok(rankList);
    }
}
