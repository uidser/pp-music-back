package com.uidser.ppmusic.admin.controller.back;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.entity.Song;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.service.SongService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/back/song")
public class SongController {

    @Resource
    private SongService songService;

    @PutMapping("/insert")
    public R insert(@RequestBody Song song) {
        songService.insert(song);
        return new R().ok();
    }

    @GetMapping("/page")
    public R page(QueryVo queryVo) {
        PageInfo<Song> songPageInfo = songService.page(queryVo);
        return new R().ok(songPageInfo);
    }

    @PostMapping("/changeShowStatus/{songId}/{status}")
    public R changeShowStatus(@PathVariable Long songId,
                              @PathVariable Integer status) {
        songService.changeShowStatus(songId, status);
        return new R().ok();
    }

    @DeleteMapping("/batchDelete")
    public R batchDelete(@RequestBody Long[] ids) {
        songService.batchDelete(ids);
        return new R().ok();
    }
}
