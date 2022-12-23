package com.uidser.ppmusic.search.service;

import com.uidser.ppmusic.common.entity.Singer;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.search.entity.QueryReturnVo;

import java.util.List;
import java.util.Map;

public interface SearchService {
    QueryReturnVo query(QueryVo queryVo);

    void insertSinger(Singer singer);

    List<Singer> queryByCategory(Map<String, String> categoryIdMap);
}
