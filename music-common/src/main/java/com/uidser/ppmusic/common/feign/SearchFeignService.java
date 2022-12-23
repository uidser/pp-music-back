package com.uidser.ppmusic.common.feign;

import com.uidser.ppmusic.common.entity.Singer;
import com.uidser.ppmusic.common.r.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("pp-music-search")
public interface SearchFeignService {

    @PostMapping("/search/insertSinger")
    public R insertSinger(@RequestBody Singer singer);

}
