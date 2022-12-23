package com.uidser.ppmusic.media.controller;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Singer;
import com.uidser.ppmusic.common.entity.SingerInfo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.service.SingerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/media/singer")
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

    @GetMapping("/get/{id}")
    public R<SingerInfo> get(@PathVariable Long id) {
        SingerInfo singerInfo = singerService.getSingerInfo(id);
        return new R<SingerInfo>().ok(singerInfo);
    }

}
