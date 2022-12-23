package com.uidser.ppmusic.common.mapper;


import com.uidser.ppmusic.common.entity.Dictionary;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

import java.util.List;

public interface DictionaryMapper {
    List<Dictionary> getAll(QueryVo queryVo);

    void insert(Dictionary dictionary);

    void edit(Dictionary dictionary);

    Dictionary getById(Long id);
}
