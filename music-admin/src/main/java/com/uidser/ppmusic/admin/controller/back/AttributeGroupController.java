package com.uidser.ppmusic.admin.controller.back;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.entity.Attribute;
import com.uidser.ppmusic.common.entity.AttributeGroup;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.service.AttributeGroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/back/song/attributeGroup")
public class AttributeGroupController {

    @Resource
    private AttributeGroupService attributeGroupService;

    @GetMapping("/page")
    public R page(QueryVo queryVo) {
        PageInfo<AttributeGroup> attributeGroupPageInfo = attributeGroupService.page(queryVo);
        return new R().ok(attributeGroupPageInfo);
    }

    @GetMapping("/getAttributeByAttributeGroupId/{id}")
    private R getAttributeByAttributeGroupId(@PathVariable Long id) {
        List<Attribute> attributeList = attributeGroupService.getAttributeByAttributeGroupId(id);
        return new R().ok(attributeList);
    }

    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id) {
        AttributeGroup attributeGroup = null;
        try {
            attributeGroup = attributeGroupService.getById(id);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new R().ok(attributeGroup);
    }

    @PutMapping("/insert")
    public R insert(@RequestBody AttributeGroup attributeGroup) {
        attributeGroupService.insert(attributeGroup);
        return new R().ok();
    }

    @PostMapping("/edit")
    public R edit(@RequestBody AttributeGroup attributeGroup) {
        attributeGroupService.edit(attributeGroup);
        return new R().ok();
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        attributeGroupService.delete(id);
        return new R().ok();
    }
    
}
