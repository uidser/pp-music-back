package com.uidser.ppmusic.common.mapper;


import com.uidser.ppmusic.common.entity.Singer;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SingerMapper {
    List<Singer> query(String queryText);

    List<Singer> getByIds(@Param("ids") List<Long> singerIdList);

    List<Singer> page(QueryVo queryVo);

    void insert(Singer singer);

    Singer get(Long id);

    void edit(Singer singer);

    void changeShowStatus(@Param("singerId") Long singerId, @Param("showStatus") Integer showStatus);

    void batchDelete(@Param("list") List<Long> singerIdList);
}
