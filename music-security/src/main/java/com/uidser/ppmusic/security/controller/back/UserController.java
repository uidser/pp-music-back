package com.uidser.ppmusic.security.controller.back;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.User;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.security.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/security/back/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/page")
    public R<PageInfo<User>> page(QueryVo queryVo) {
        PageInfo<User> userPageInfo = userService.page(queryVo);
        return new R<PageInfo<User>>().ok(userPageInfo);
    }

    @PutMapping("/changeEnableStatus/{userId}/{status}")
    public R changeEnableStatus(@PathVariable Long userId,
                                @PathVariable Integer status) {
        userService.changeEnableStatus(userId, status);
        return new R().ok();
    }

    @PutMapping("/edit")
    public R edit(@RequestBody User user) {
        userService.edit(user);
        return new R().ok();
    }

    @GetMapping("/get/{id}")
    public R<User> get(@PathVariable Long id) {
        User user = userService.get(id);
        return new R<User>().ok(user);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody User user) {
        userService.insert(user);
        return new R().ok();
    }

    @GetMapping("/getByUserName/{username}")
    public R<User> getByUserName(@PathVariable String username) {
        User user = userService.getByUserName(username);
        return new R<User>().ok(user);
    }
}
