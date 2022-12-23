package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.Category;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface CategoryMapper {
    List<Category> getAll();

    List<Category> getByIds(@Param("ids") List<Long> categoryIds);

    List<Category> getAllTerms(QueryVo queryVo);

    void changeShowStatus(@Param("categoryId") Long categoryId, @Param("status") Integer status, Date date);

    void batchDelete(Long[] ids);

    void insert(Category category);

    void edit(Category category);

    List<Category> getByIds(@Param("ids") Set<Long> categoryIds);
}
