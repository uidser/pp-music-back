package com.uidser.ppmusic.admin.controller.back;

import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.entity.Type;
import com.uidser.ppmusic.common.service.TypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/back/song/type")
public class TypeController {

    @Resource
    private TypeService typeService;

    @GetMapping("/getAll")
    public R getAll() {
        List<Type> typeList = typeService.getAll();
        return new R().ok(typeList);
    }

}
