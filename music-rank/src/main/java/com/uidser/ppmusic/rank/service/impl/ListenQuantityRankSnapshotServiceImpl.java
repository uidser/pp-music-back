package com.uidser.ppmusic.rank.service.impl;

import com.uidser.ppmusic.common.entity.ListenQuantitySnapshot;
import com.uidser.ppmusic.common.entity.Scheduled;
import com.uidser.ppmusic.rank.mapper.ListenQuantityRankSnapshotMapper;
import com.uidser.ppmusic.rank.service.ListenQuantityRankSnapshotService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ListenQuantityRankSnapshotServiceImpl implements ListenQuantityRankSnapshotService {

    @Resource
    private ListenQuantityRankSnapshotMapper listenQuantityRankSnapshotMapper;

    @Override
    public void addPlayQuantity(List<ListenQuantitySnapshot> listenQuantitySnapshotList) {
        listenQuantityRankSnapshotMapper.addPlayQuantity(listenQuantitySnapshotList);
    }

    @Override
    public List<ListenQuantitySnapshot> getByRankIdAndFrequency(Scheduled scheduled) {
        return listenQuantityRankSnapshotMapper.getByRankIdAndFrequency(scheduled);
    }
}
