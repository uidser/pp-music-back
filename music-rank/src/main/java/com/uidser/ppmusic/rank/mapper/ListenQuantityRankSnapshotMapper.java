package com.uidser.ppmusic.rank.mapper;

import com.uidser.ppmusic.common.entity.ListenQuantitySnapshot;
import com.uidser.ppmusic.common.entity.Scheduled;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ListenQuantityRankSnapshotMapper {
    void addPlayQuantity(@Param("list") List<ListenQuantitySnapshot> listenQuantitySnapshotList);

    List<ListenQuantitySnapshot> getByRankIdAndFrequency(Scheduled scheduled);
}
