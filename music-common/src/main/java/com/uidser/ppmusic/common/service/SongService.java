package com.uidser.ppmusic.common.service;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Song;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

import java.util.List;

public interface SongService {
    void insert(Song song);

    PageInfo<Song> page(QueryVo queryVo);

    void changeShowStatus(Long songId, Integer status);

    void batchDelete(Long[] ids);

    List<Song> getByIds(List<Long> songIds);
}
