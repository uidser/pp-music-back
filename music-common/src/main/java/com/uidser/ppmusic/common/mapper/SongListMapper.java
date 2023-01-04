package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.SongList;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SongListMapper {
    List<SongList> list(QueryVo queryVo);

    void insert(SongList songList);

    void edit(SongList songList);

    void changeShowStatus(@Param("id") Long id, @Param("showStatus") Integer showStatus);

    void batchDelete(@Param("list") List<Long> ids);

    SongList get(Long id);

    List<SongList> listBySongListIdList(@Param("list") List<Long> songListIdList);
}
