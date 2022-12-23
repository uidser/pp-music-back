package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.Song;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SongMapper {
    void insert(Song song);

    List<Song> getAll(QueryVo queryVo);

    void changeShowStatus(@Param("songId") Long songId,@Param("status") Integer status);

    void batchDelete(@Param("ids") Long[] ids);

    List<Song> getByIds(@Param("ids") List<Long> songIds);
}
