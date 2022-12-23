package com.uidser.ppmusic.common.service;


import com.uidser.ppmusic.common.entity.Album;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

import java.util.List;

public interface AlbumService {
    List<Album> query(QueryVo queryVo);
}
