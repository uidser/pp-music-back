package com.uidser.ppmusic.admin.controller.back;


import com.uidser.ppmusic.common.entity.Attribute;
import com.uidser.ppmusic.common.entity.vo.AttributeCommitVo;
import com.uidser.ppmusic.common.entity.vo.AttributeReturnVo;
import com.uidser.ppmusic.common.service.AttributeService;
import com.uidser.ppmusic.common.r.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/back/song/attribute")
public class AttributeController {

    @Resource
    private AttributeService attributeService;

    @PutMapping("/insert")
    public R insert(@RequestBody AttributeCommitVo attributeCommitVo) {
        attributeService.insert(attributeCommitVo);
        return new R().ok();
    }

    @GetMapping("/getAttributeByCategoryId/{id}")
    public R getAttributeByCategoryId(@PathVariable Long id) {
        List<Attribute> attributeList = attributeService.getAttributeByCategoryId(id);
        return new R().ok(attributeList);
    }

    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id) {
        AttributeReturnVo attributeReturnVo = null;
        try {
            attributeReturnVo = attributeService.getAttributeReturnVoById(id);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new R().ok(attributeReturnVo);
    }

    @PostMapping("/edit")
    public R edit(@RequestBody Attribute attribute) {
        attributeService.edit(attribute);
        return new R().ok();
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        attributeService.delete(id);
        return new R().ok();
    }
}
