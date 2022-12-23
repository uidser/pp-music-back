package com.uidser.ppmusic.rank.service;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.ListenQuantitySnapshot;
import com.uidser.ppmusic.common.entity.Scheduled;
import com.uidser.ppmusic.common.entity.vo.QueryVo;

import java.util.List;

public interface ListenQuantityRankSnapshotService {
    void addPlayQuantity(List<ListenQuantitySnapshot> message);

    List<ListenQuantitySnapshot> getByRankIdAndFrequency(Scheduled scheduled);
}
