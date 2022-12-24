package com.uidser.ppmusic.admin.controller.back;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.entity.Singer;
import com.uidser.ppmusic.common.service.SingerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/back/singer")
public class SingerController {

    @Resource
    private SingerService singerService;

    @GetMapping("/query/{queryText}")
    public R query(@PathVariable String queryText) {
        List<Singer> singerList = singerService.query(queryText);
        return new R().ok(singerList);
    }

    @GetMapping("/page")
    public R<PageInfo<Singer>> page(QueryVo queryVo) {
        PageInfo<Singer> singerPageInfo = singerService.page(queryVo);
        return new R<PageInfo<Singer>>().ok(singerPageInfo);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody Singer singer) {
        singerService.insert(singer);
        return new R().ok();
    }

    @GetMapping("/get/{id}")
    public R<Singer> get(@PathVariable Long id) {
        List<Singer> singerList = singerService.getByIds(Arrays.asList(id));
        return new R<Singer>().ok(singerList.get(0));
    }

    @PutMapping("/edit")
    public R edit(@RequestBody Singer singer) {
        singerService.edit(singer);
        return new R().ok();
    }

    @PostMapping("/changeShowStatus/{singerId}/{showStatus}")
    public R changeShowStatus(@PathVariable Long singerId,
                              @PathVariable Integer showStatus) {
        singerService.changeShowStatus(singerId, showStatus);
        return new R().ok();
    }

    @DeleteMapping("/batchDelete")
    public R batchDelete(@NotEmpty(message = "数组内容为空") @RequestBody List<Long> singerIdList) {
        singerService.batchDelete(singerIdList);
        return new R().ok();
    }

}
