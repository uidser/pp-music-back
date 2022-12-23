package com.uiidser.ppmusic.oss.controller;

import com.uidser.ppmusic.common.r.R;
import com.uiidser.ppmusic.oss.service.OssService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/oss")
public class OssController {

    @Resource
    private OssService ossService;

    @GetMapping("/getUploadToken")
    public R upload() {
        String token = ossService.getUploadToken();
        return new R().ok(token);
    }

    @PostMapping("/callback")
    public Map callback(HttpServletRequest httpServletRequest,
                        @RequestBody Map map) {
        try {
            ossService.callback(httpServletRequest, map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
