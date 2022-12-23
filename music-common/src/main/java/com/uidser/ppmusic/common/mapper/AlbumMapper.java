package com.uidser.ppmusic.common.mapper;


import com.uidser.ppmusic.common.entity.Album;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

import java.util.List;

public interface AlbumMapper {
    List<Album> query(QueryVo queryVo);
}
