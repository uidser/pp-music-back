package com.uidser.ppmusic.admin.controller.back;

import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.entity.ComponentInput;
import com.uidser.ppmusic.common.service.ComponentInputService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/back/song/componentInput")
public class ComponentInputController {

    @Resource
    private ComponentInputService componentInputService;

    @GetMapping("/getAll")
    public R getAll() {
        List<ComponentInput> componentInputList = componentInputService.getAll();
        return new R().ok(componentInputList);
    }

}
