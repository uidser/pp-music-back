package com.uidser.ppmusic.common.service;


import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Singer;
import com.uidser.ppmusic.common.entity.SingerInfo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

import java.util.List;

public interface SingerService {
    List<Singer> query(String queryText);

    List<Singer> getByIds(List<Long> singerIdList);

    PageInfo<Singer> page(QueryVo queryVo);

    void insert(Singer singer);

    SingerInfo getSingerInfo(Long id);

    void edit(Singer singer);

    List<Singer> queryByCategory(List<Long> categoryIdList);

    void changeShowStatus(Long singerId, Integer showStatus);

    void batchDelete(List<Long> singerIdList);
}
