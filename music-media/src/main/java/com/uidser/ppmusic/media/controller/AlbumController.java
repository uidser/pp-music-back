package com.uidser.ppmusic.media.controller;

import com.uidser.ppmusic.common.entity.Album;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.service.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/media/song/album")
public class AlbumController {

    @Resource
    private AlbumService albumService;

    @GetMapping("/query")
    public R query(QueryVo queryVo) {
        List<Album> albumList = albumService.query(queryVo);
        return new R().ok(albumList);
    }

}
