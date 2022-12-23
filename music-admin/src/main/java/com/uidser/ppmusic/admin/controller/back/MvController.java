package com.uidser.ppmusic.admin.controller.back;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.entity.vo.MvCommitVo;
import com.uidser.ppmusic.common.entity.vo.MvReturnVo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.service.MvService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/back/song/mv")
public class MvController {

    @Resource
    private MvService mvService;

    @GetMapping("/page")
    public R page(QueryVo queryVo) {
        PageInfo<MvReturnVo> mvReturnVoPageInfo = mvService.page(queryVo);
        return new R().ok(mvReturnVoPageInfo);
    }

    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id) {
        MvReturnVo mvReturnVo = mvService.getById(id);
        return new R().ok(mvReturnVo);
    }

    @PutMapping("/insert")
    public R insert(@RequestBody MvCommitVo mvCommitVo) {
        mvService.insert(mvCommitVo);
        return new R().ok();
    }

    @PostMapping("/edit")
    public R edit(@RequestBody MvCommitVo mvCommitVo) {
        mvService.edit(mvCommitVo);
        return new R().ok();
    }

    @PostMapping("/changeShowStatus/{mvId}/{status}")
    public R changeShowStatus(@PathVariable Long mvId,
                              @PathVariable Integer status) {
        mvService.changeShowStatus(mvId, status);
        return new R().ok();
    }

    @DeleteMapping("/batchDelete")
    public R batchDelete(@RequestBody Long[] ids) {
        mvService.batchDelete(ids);
        return new R().ok();
    }

}
