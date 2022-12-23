package com.uidser.ppmusic.rank.service.impl;

import com.uidser.ppmusic.common.entity.MediaRankRelation;
import com.uidser.ppmusic.rank.entity.SingleMediaMap;
import com.uidser.ppmusic.rank.mapper.MediaRankRelationMapper;
import com.uidser.ppmusic.rank.service.MediaRankRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MediaRankRelationServiceImpl implements MediaRankRelationService {

    @Resource
    private MediaRankRelationMapper mediaRankRelationMapper;

    @Override
    public void insert(List<MediaRankRelation> mediaRankRelationList, Date date) {
        mediaRankRelationMapper.insert(mediaRankRelationList, date);
    }

    @Override
    public List<MediaRankRelation> getByOrder(Long rankId, Integer frequency) {
        return mediaRankRelationMapper.getByOrder(rankId, frequency);
    }

    @Override
    public SingleMediaMap getMap(Long mediaId, Long rankId) {
        SingleMediaMap singleMediaMap = new SingleMediaMap();
        List<MediaRankRelation> mediaRankRelationList = mediaRankRelationMapper.getMap(mediaId, rankId);
        for (MediaRankRelation mediaRankRelation1: mediaRankRelationList) {
            singleMediaMap.setMediaName(mediaRankRelation1.getMediaName());
            singleMediaMap.getRankDateList().add(mediaRankRelation1.getLastRankDate());
            singleMediaMap.getListenQuantityList().add(mediaRankRelation1.getListenQuantity());
        }
        return singleMediaMap;
    }
}
