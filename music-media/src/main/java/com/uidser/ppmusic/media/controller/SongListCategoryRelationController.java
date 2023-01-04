package com.uidser.ppmusic.media.controller;

import com.uidser.ppmusic.common.entity.Category;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.service.SongListCategoryRelationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/media/songListCategoryRelation")
public class SongListCategoryRelationController {

    @Resource
    private SongListCategoryRelationService songListCategoryRelationService;

    @GetMapping("/getAll")
    public R<List<Category>> getAll() {
        List<Category> categoryList = songListCategoryRelationService.getAll();
        return new R<List<Category>>().ok(categoryList);
    }

}
