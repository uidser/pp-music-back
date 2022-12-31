package com.uidser.ppmusic.common.feign;

import com.uidser.ppmusic.common.entity.Media;
import com.uidser.ppmusic.common.entity.Singer;
import com.uidser.ppmusic.common.r.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("pp-music-search")
public interface SearchFeignService {

    @PostMapping("/search/insertSinger")
    public R insertSinger(@RequestBody Singer singer);

    @PostMapping("/search/insertMedia")
    public R insertMedia(@RequestBody Media media);

    @PutMapping("/search/updateMediaUrl/{mediaId}")
    public R updateMediaUrl(@PathVariable("mediaId") Long mediaId,
                            @RequestBody String path);

}
