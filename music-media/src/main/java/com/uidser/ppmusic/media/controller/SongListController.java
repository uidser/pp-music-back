package com.uidser.ppmusic.media.controller;

import com.uidser.ppmusic.common.entity.SongList;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.service.SongListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/media/songList")
public class SongListController {

    @Resource
    private SongListService songListService;

    @GetMapping("/getByCategoryId/{categoryId}")
    public R<List<SongList>> getByCategoryId(@PathVariable Long categoryId,
                                             QueryVo queryVo) {
        List<SongList> songListList = songListService.getByCategoryId(categoryId, queryVo);
        return new R<List<SongList>>().ok(songListList);
    }

    @GetMapping("/get/{id}")
    public R<SongList> get(@PathVariable Long id) {
        SongList songList = songListService.get(id);
        return new R<SongList>().ok(songList);
    }

}
