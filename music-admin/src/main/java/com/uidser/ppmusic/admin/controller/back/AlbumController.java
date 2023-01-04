package com.uidser.ppmusic.admin.controller.back;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Album;
import com.uidser.ppmusic.common.entity.AlbumMediaRelation;
import com.uidser.ppmusic.common.entity.Media;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.service.AlbumMediaRelationService;
import com.uidser.ppmusic.common.service.AlbumService;
import com.uidser.ppmusic.common.r.R;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/back/album")
public class AlbumController {

    @Resource
    private AlbumService albumService;

    @GetMapping("/query")
    public R query(QueryVo queryVo) {
        List<Album> albumList = albumService.query(queryVo);
        return new R().ok(albumList);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody Album album) {
        albumService.insert(album);
        return new R().ok();
    }

    @PutMapping("/edit")
    public R edit(@RequestBody Album album) {
        albumService.edit(album);
        return new R().ok();
    }

    @DeleteMapping("/batchDelete")
    public R batchDelete(@RequestBody List<Long> albumIds) {
        albumService.batchDelete(albumIds);
        return new R().ok();
    }

    @PutMapping("/changeShowStatus/{albumId}/{showStatus}")
    public R changeShowStatus(@PathVariable Long albumId,
                              @PathVariable Integer showStatus) {
        albumService.changeShowStatus(albumId, showStatus);
        return new R().ok();
    }

    @GetMapping("/page")
    public R<PageInfo<Album>> page(QueryVo queryVo) {
        PageInfo<Album> albumPageInfo = albumService.page(queryVo);
        return new R<PageInfo<Album>>().ok(albumPageInfo);
    }

    @GetMapping("/get/{albumId}")
    public R<Album> get(@PathVariable Long albumId) {
        Album album = albumService.get(albumId);
        return new R<Album>().ok(album);
    }

    @GetMapping("/getMediaByAlbumId/{albumId}")
    public R<Album> getMediaByAlbumId(@PathVariable Long albumId) {
        Album album = albumService.getAlbumAndSongList(albumId);
        return new R<Album>().ok(album);
    }

    @PutMapping("/editRelation")
    public R editRelation(@RequestBody AlbumMediaRelation albumMediaRelation) {
        albumService.editRelation(albumMediaRelation);
        return new R().ok();
    }

}
