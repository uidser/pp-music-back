package com.uidser.ppmusic.admin.controller.back;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.entity.AttributeGroup;
import com.uidser.ppmusic.common.entity.Category;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/back/song/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/getAll")
    public R getAll() {
        List<Category> categoryList = categoryService.getAll();
        return new R().ok(categoryList);
    }

    @GetMapping("/getAttributeGroupAndAttributeByCategoryId/{id}")
    public R getAttributeGroupAndAttributeByCategoryId(@PathVariable Long id) {
        List<AttributeGroup> attributeGroupList = categoryService.getAttributeGroupAndAttributeByCategoryId(id);
        return new R().ok(attributeGroupList);
    }

    @GetMapping("/page")
    public R page(QueryVo queryVo) {
        PageInfo<Category> categoryPageInfo = categoryService.page(queryVo);
        return new R().ok(categoryPageInfo);
    }

    @PostMapping("/changeShowStatus/{categoryId}/{status}")
    public R changeShowStatus(@PathVariable Long categoryId,
                              @PathVariable Integer status) {
        categoryService.changeShowStatus(categoryId, status);
        return new R().ok();
    }

    @DeleteMapping("/batchDelete")
    public R batchDelete(@RequestBody Long[] ids) {
        categoryService.batchDelete(ids);
        return new R().ok();
    }

    @PutMapping("/insert")
    public R insert(@RequestBody Category category) {
        categoryService.insert(category);
        return new R().ok();
    }

    @PostMapping("/edit")
    public R edit(@RequestBody Category category) {
        categoryService.edit(category);
        return new R().ok();
    }

    @GetMapping("/getMoreLevelCategory")
    public R<List<Category>> getMoreLevelCategory() {
        List<Category> categoryList = categoryService.getMoreLevelCategory();
        return new R<List<Category>>().ok(categoryList);
    }
}
