package com.uidser.ppmusic.user.controller.front;

import com.uidser.ppmusic.common.r.R;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("hello")
    public R hello() {
        return new R().ok("hello");
    }

}
