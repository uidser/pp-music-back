package com.uidser.ppmusic.common.feign;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Media;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.r.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("pp-music-media")
public interface MediaFeignService {

    @PostMapping("/media/getRankMediaList")
    public R<PageInfo<Media>> getRankMediaList(@RequestBody List<Long> ids,
                                               @SpringQueryMap QueryVo queryVo);

}
