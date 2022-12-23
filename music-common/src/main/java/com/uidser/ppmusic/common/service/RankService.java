package com.uidser.ppmusic.common.service;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Rank;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

import java.util.Date;
import java.util.List;

public interface RankService {
    List<Rank> index(Integer limit);

    PageInfo<Rank> page(QueryVo queryVo);

    void changeShowStatus(Long rankId, Integer status);

    void batchDelete(Long[] ids);

    Rank getRankDetailMediaList(Long rankId, Integer frequency);

    void insert(Rank rank);

    Rank get(Long rankId);

    void edit(Rank rank);

    void addFrequency(Long rankId, Date date);

    Rank getRankDetail(Long rankId, Integer frequency, QueryVo queryVo);

    List<Rank> getAll();
}
