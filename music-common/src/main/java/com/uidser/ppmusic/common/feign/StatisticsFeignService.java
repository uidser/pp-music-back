package com.uidser.ppmusic.common.feign;

import com.uidser.ppmusic.common.entity.Scheduled;
import com.uidser.ppmusic.common.r.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("pp-music-statistics")
public interface StatisticsFeignService {

    @PostMapping("/schedule/executeTask")
    public R executeTask(@RequestBody Scheduled scheduled);

}
