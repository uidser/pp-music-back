package com.uidser.ppmusic.admin.controller.back;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.SongList;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.jjwt.JJwtUtil;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.service.SongListService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/back/songList")
public class SongListController {

    @Resource
    private SongListService songListService;

    @GetMapping("/page")
    public R<PageInfo<SongList>> page(QueryVo queryVo) {
        PageInfo<SongList> songListPageInfo = songListService.page(queryVo);
        return new R<PageInfo<SongList>>().ok(songListPageInfo);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody SongList songList,
                    HttpServletRequest httpServletRequest) {
        String username = JJwtUtil.getUserByToken(httpServletRequest.getHeader("token"));
        songListService.insert(songList, username);
        return new R().ok();
    }

    @PutMapping("/edit")
    public R edit(@RequestBody SongList songList) {
        songListService.edit(songList);
        return new R().ok();
    }

    @PutMapping("/changeShowStatus/{id}/{showStatus}")
    public R changeShowStatus(@PathVariable Long id,
                              @PathVariable Integer showStatus) {
        songListService.changeShowStatus(id, showStatus);
        return new R().ok();
    }

    @DeleteMapping("/batchDelete")
    public R batchDelete(@RequestBody List<Long> ids) {
        songListService.batchDelete(ids);
        return new R().ok();
    }

    @GetMapping("/get/{id}")
    public R<SongList> get(@PathVariable Long id) {
        SongList songList = songListService.get(id);
        return new R<SongList>().ok(songList);
    }
}
