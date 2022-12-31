package com.uidser.ppmusic.media.controller;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.ListenQuantitySnapshot;
import com.uidser.ppmusic.common.entity.Media;
import com.uidser.ppmusic.common.entity.Singer;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.service.MediaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Resource
    private MediaService mediaService;

    @PostMapping("/getRankMediaList")
    public R<PageInfo<Media>> getRankMediaList(@RequestBody List<Long> ids,
                                              QueryVo queryVo) {
        if(ids.size() > 0) {
            PageInfo<Media> rankPageInfo = mediaService.getRankMediaList(ids, queryVo);
            return new R<PageInfo<Media>>().ok(rankPageInfo);
        }
        return new R<>().ok();
    }

    @PostMapping("/addPlayQuantity")
    public R addPlayQuantity(@RequestBody ListenQuantitySnapshot listenQuantitySnapshot) {
        mediaService.addPlayQuantity(listenQuantitySnapshot);
        return new R().ok();
    }

    @GetMapping("/getAuthor/{mediaId}")
    public R<List<Singer>> getAuthor(@PathVariable Long mediaId) {
        List<Singer> singerList = mediaService.getAuthor(mediaId);
        return new R<List<Singer>>().ok(singerList);
    }

}
