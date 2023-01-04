package com.uidser.ppmusic.media.controller.page;

import com.uidser.ppmusic.common.entity.SingerListPage;
import com.uidser.ppmusic.common.entity.vo.IndexVo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.service.MediaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/media/page")
public class PageController {

    @Resource
    private MediaService mediaService;

    @GetMapping("/play")
    public R index() {
        IndexVo indexVo = mediaService.play();
        return new R().ok(indexVo);
    }

    @GetMapping("/musicBuild")
    public R<IndexVo> musicBuild() {
        IndexVo indexVo = mediaService.musicBuild();
        return new R<IndexVo>().ok(indexVo);
    }

    @GetMapping("/singerList")
    public R<SingerListPage> singerList() {
        SingerListPage singerListPage = mediaService.singerList();
        return new R<SingerListPage>().ok(singerListPage);
    }
}
