package com.uidser.ppmusic.common.service;


import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Album;
import com.uidser.ppmusic.common.entity.AlbumMediaRelation;
import com.uidser.ppmusic.common.entity.Media;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

import java.util.List;

public interface AlbumService {
    List<Album> query(QueryVo queryVo);

    void insert(Album album);

    void edit(Album album);

    void batchDelete(List<Long> albumIds);

    void changeShowStatus(Long albumId, Integer showStatus);

    PageInfo<Album> page(QueryVo queryVo);

    Album get(Long albumId);

    List<Album> getByIdsAndLimit(List<Long> albumIdList, int limit);

    Album getAlbumAndSongList(Long albumId);

    void editRelation(AlbumMediaRelation albumMediaRelation);
}
