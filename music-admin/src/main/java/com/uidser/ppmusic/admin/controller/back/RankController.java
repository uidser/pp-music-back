package com.uidser.ppmusic.admin.controller.back;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Rank;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.feign.RankFeignService;
import com.uidser.ppmusic.common.r.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/back/rank")
public class RankController {

    @Resource
    private RankFeignService rankFeignService;

    @GetMapping("/page")
    public R<PageInfo<Rank>> page(QueryVo queryVo) {
        return rankFeignService.page(queryVo);
    }

    @PutMapping("/changeShowStatus/{rankId}/{status}")
    public R changeShowStatus(@PathVariable Long rankId,
                                       @PathVariable Integer status) {
        rankFeignService.changeShowStatus(rankId, status);
        return new R().ok();
    }

    @DeleteMapping("/batchDelete")
    public R batchDelete(@RequestBody Long[] ids){
        rankFeignService.batchDelete(ids);
        return new R().ok();
    }

    @GetMapping("/getRankDetailMediaList/{rankId}/{frequency}")
    public R<Rank> getRankDetailMediaList(@PathVariable Long rankId,
                                          @PathVariable Integer frequency){
        return rankFeignService.getRankDetailMediaList(rankId, frequency);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody Rank rank) {
        rankFeignService.insert(rank);
        return new R().ok();
    }

    @GetMapping("/get/{rankId}")
    public R<Rank> get(@PathVariable Long rankId) {
        return rankFeignService.get(rankId);
    }

    @PutMapping("/edit")
    public R edit(@RequestBody Rank rank) {
        rankFeignService.edit(rank);
        return new R().ok();
    }

}
