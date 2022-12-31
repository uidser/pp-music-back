package com.uidser.ppmusic.search.controller;

import com.uidser.ppmusic.common.entity.Media;
import com.uidser.ppmusic.common.entity.Singer;
import com.uidser.ppmusic.common.entity.Song;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.search.entity.QueryReturnVo;
import com.uidser.ppmusic.search.service.SearchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;

    @GetMapping("/query")
    public R<QueryReturnVo> query(QueryVo queryVo) {
        QueryReturnVo queryReturnVo = searchService.query(queryVo);
        return new R<QueryReturnVo>().ok(queryReturnVo);
    }

    @PostMapping("/insertSinger")
    public R insertSinger(@RequestBody Singer singer) {
        searchService.insertSinger(singer);
        return new R().ok();
    }

    @GetMapping("/querySingerByCategory")
    public R<List<Singer>> queryByCategory(@RequestParam Map<String, String> categoryIdList) {
        List<Singer> singerList = searchService.queryByCategory(categoryIdList);
        return new R<List<Singer>>().ok(singerList);
    }

    @GetMapping("/searchSingleSingerSong/{singerId}")
    public R<List<Media>> searchSingleSingerSong(@PathVariable Long singerId,
                                                 QueryVo queryVo) {
        List<Media> songList = searchService.searchSingleSingerSong(singerId, queryVo);
        return new R<List<Media>>().ok(songList);
    }

    @PostMapping("/insertMedia")
    public R insertMedia(@RequestBody Media media) {
        searchService.insertMedia(media);
        return new R().ok();
    }

    @PutMapping("/updateMediaUrl/{mediaId}")
    public R updateMediaUrl(@PathVariable Long mediaId,
                            @RequestBody String path) {
        searchService.updateMediaUrl(mediaId, path);
        return new R().ok();
    }

}
