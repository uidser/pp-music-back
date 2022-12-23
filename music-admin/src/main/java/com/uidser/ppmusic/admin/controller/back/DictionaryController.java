package com.uidser.ppmusic.admin.controller.back;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.entity.Dictionary;
import com.uidser.ppmusic.common.entity.vo.DictionaryVo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.service.DictionaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/back/song/dictionary")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @GetMapping("/page")
    public R page(QueryVo queryVo) {
        PageInfo<DictionaryVo> dictionaryVoPageInfo = dictionaryService.page(queryVo);
        return new R().ok(dictionaryVoPageInfo);
    }

    @PutMapping("/insert")
    public R insert(@RequestBody Dictionary dictionary){
        dictionaryService.insert(dictionary);
        return new R().ok();
    }

    @PostMapping("/edit")
    public R edit(@RequestBody Dictionary dictionary) {
        dictionaryService.edit(dictionary);
        return new R().ok();
    }

    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id) {
        Dictionary dictionary = dictionaryService.getById(id);
        return new R().ok(dictionary);
    }

}
