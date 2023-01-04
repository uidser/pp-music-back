package com.uidser.ppmusic.common.feign;

import com.uidser.ppmusic.common.entity.User;
import com.uidser.ppmusic.common.r.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("pp-music-security")
public interface UserFeignService {

    @GetMapping("/security/back/user/getByUserName/{username}")
    public R<User> getByUserName(@PathVariable("username") String username);

    @GetMapping("/security/back/user/get/{id}")
    public R<User> get(@PathVariable("id") Long id);

}
