package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.Rank;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RankMapper {
    List<Rank> index(Integer limit);

    List<Rank> page(QueryVo queryVo);

    void changeShowStatus(@Param("rankId") Long rankId, @Param("status") Integer status);

    void batchDelete(@Param("ids") Long[] ids);

    Rank getRankDetailMediaList(@Param("rankId") Long rankId, @Param("frequency") Integer frequency);

    void insert(Rank rank);

    Rank get(Long rankId);

    void edit(Rank rank);

    void addFrequency(@Param("rankId") Long rankId, @Param("date") Date date);

    List<Rank> getAll(Integer limit);

    List<Rank> getListenQuantity();
}
