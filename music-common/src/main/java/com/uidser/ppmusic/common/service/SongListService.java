package com.uidser.ppmusic.common.service;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.SongList;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

import java.util.List;

public interface SongListService {
    PageInfo<SongList> page(QueryVo queryVo);

    void insert(SongList songList, String username);

    void edit(SongList songList);

    void changeShowStatus(Long id, Integer showStatus);

    void batchDelete(List<Long> ids);

    SongList get(Long id);

    List<SongList> getByCategoryId(Long categoryId, QueryVo queryVo);
}
