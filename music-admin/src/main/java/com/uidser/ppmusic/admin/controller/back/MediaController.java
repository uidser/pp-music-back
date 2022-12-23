package com.uidser.ppmusic.admin.controller.back;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Media;
import com.uidser.ppmusic.common.entity.vo.MediaCommitVo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.feign.MediaFeignService;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.service.MediaService;
import com.uidser.ppmusic.common.validate.Insert;
import com.uidser.ppmusic.common.validate.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/back/media")
public class MediaController {

    @Resource
    private MediaService mediaService;

    @Resource
    private MediaFeignService mediaFeignService;

    @PostMapping("/insert")
    public R insert(@Validated({Insert.class}) @RequestBody MediaCommitVo mediaCommitVo){
        Long id = mediaService.insert(mediaCommitVo);
        return new R().ok(id);
    }

    @GetMapping("/getMediaByPage")
    public R page(QueryVo queryVo) {
        PageInfo<Media> mediaPageInfo =  mediaService.page(queryVo);
        return new R().ok(mediaPageInfo);
    }

    @PutMapping("/update")
    public R update(@Validated({Update.class}) @RequestBody MediaCommitVo mediaCommitVo) {
        mediaService.update(mediaCommitVo);
        return new R().ok();
    }

    @PutMapping("/changeShowStatus/{mediaId}/{status}")
    public R changeShowStatus(@PathVariable Long mediaId,
                              @PathVariable Integer status) {
        mediaService.changeShowStatus(mediaId, status);
        return new R().ok();
    }

    @DeleteMapping("/batchDelete")
    public R batchDelete(@RequestBody Long[] ids) {
        if(ids.length > 0) {
            mediaService.batchDelete(ids);
        }
        return new R().ok();
    }

    @PostMapping("/getRankMediaList")
    public R<PageInfo<Media>> getRankMediaList(@RequestBody List<Long> ids,
                              QueryVo queryVo) {
        return mediaFeignService.getRankMediaList(ids, queryVo);
    }

}
