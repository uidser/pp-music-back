package com.uidser.ppmusic.common.mapper;


import com.uidser.ppmusic.common.entity.Album;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumMapper {
    List<Album> query(QueryVo queryVo);

    void insert(Album album);

    void edit(Album album);

    void batchDelete(@Param("list") List<Long> albumIds);

    void changeShowStatus(@Param("albumId") Long albumId, @Param("showStatus") Integer showStatus);

     List<Album>page(QueryVo queryVo);

    Album get(Long albumId);
}
