package com.uidser.ppmusic.common.service;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.ListenQuantitySnapshot;
import com.uidser.ppmusic.common.entity.Media;
import com.uidser.ppmusic.common.entity.Singer;
import com.uidser.ppmusic.common.entity.SingerListPage;
import com.uidser.ppmusic.common.entity.vo.IndexVo;
import com.uidser.ppmusic.common.entity.vo.MediaCommitVo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

import java.util.List;

public interface MediaService {
    Long insert(MediaCommitVo media);

    void editMediaUrl(Long mediaId, String url, String column);

    IndexVo play();

    PageInfo<Media> page(QueryVo queryVo);

    void update(MediaCommitVo mediaCommitVo);

    void changeShowStatus(Long mediaId, Integer status);

    void batchDelete(Long[] ids);

    PageInfo<Media> getRankMediaList(List<Long> ids, QueryVo queryVo);

    void addPlayQuantity(ListenQuantitySnapshot listenQuantitySnapshot);

    void addPlayQuantitySelf(List<ListenQuantitySnapshot> listenQuantitySnapshotList1);

    IndexVo musicBuild();

    SingerListPage singerList();

    List<Media> getByIds(List<Long> mediaIdList, Integer type, Integer limit);

    List<Singer> getAuthor(Long mediaId);

    List<Media> getByIds(List<Long> songIdList);
}