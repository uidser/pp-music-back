package com.uidser.ppmusic.common.service;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Dictionary;
import com.uidser.ppmusic.common.entity.vo.DictionaryVo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

public interface DictionaryService {
    PageInfo<DictionaryVo> page(QueryVo queryVo);

    void insert(Dictionary dictionary);

    void edit(Dictionary dictionary);

    Dictionary getById(Long id);
}
