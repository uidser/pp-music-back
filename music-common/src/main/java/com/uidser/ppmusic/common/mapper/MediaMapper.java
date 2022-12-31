package com.uidser.ppmusic.common.mapper;

import com.uidser.ppmusic.common.entity.ListenQuantitySnapshot;
import com.uidser.ppmusic.common.entity.Media;
import com.uidser.ppmusic.common.entity.vo.MediaCommitVo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MediaMapper {
    void insert(Media media);

    void editMediaUrl(@Param("mediaId") Long mediaId, @Param("url") String url, @Param("column") String column);

    List<Media> index(@Param("type") Integer type, @Param("limit") Integer limit);

    List<Media> getByTerm(QueryVo queryVo);

    void update(MediaCommitVo mediaCommitVo);

    void changeShowStatus(@Param("mediaId") Long mediaId, @Param("showStatus") Integer status);

    void batchDelete(@Param("ids") Long[] ids);

    List<Media> getRankMediaList(@Param("ids") List<Long> ids, @Param("queryVo") QueryVo queryVo);

    void addPlayQuantitySelf(@Param("list") List<ListenQuantitySnapshot> listenQuantitySnapshotList1);

    List<Media> getByIds(@Param("list") List<Long> mediaIdList, @Param("type") Integer type, @Param("limit") Integer limit);
}
