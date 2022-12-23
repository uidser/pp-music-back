package com.uidser.ppmusic.security.controller.front;

import com.uidser.ppmusic.common.entity.User;
import com.uidser.ppmusic.common.exception.MusicException;
import com.uidser.ppmusic.common.jjwt.JJwtUtil;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.security.service.SecurityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @Resource
    private SecurityService securityService;

    @PostMapping("/login")
    public R login(@RequestBody User user) {
        String token = securityService.login(user);
        if(token == null) {
            throw new MusicException("账号或密码错误", 201);
        }
        return new R().ok(token);
    }

    @GetMapping("/info")
    public R info(HttpServletRequest httpServletRequest) {
        String username = JJwtUtil.getUserByToken(httpServletRequest);
        User user = securityService.getInfo(username);
        return new R().ok(user);
    }

    @PostMapping("/logout")
    public R logout(HttpServletRequest httpServletRequest) {
        String username = JJwtUtil.getUserByToken(httpServletRequest);
        securityService.logout(username);
        return new R().ok();
    }

}
