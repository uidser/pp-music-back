package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.MediaRankRelation;
import com.uidser.ppmusic.common.entity.Rank;
import com.uidser.ppmusic.common.entity.RankMediaRelation;
import com.uidser.ppmusic.common.mapper.RankMediaRelationMapper;
import com.uidser.ppmusic.common.service.RankMediaRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RankMediaRelationImpl implements RankMediaRelationService {

    @Resource
    private RankMediaRelationMapper rankMediaRelationMapper;

    @Override
    public List<RankMediaRelation> getByRankId(List<Rank> rankList) {
        return rankMediaRelationMapper.getByRankId(rankList);
    }

    @Override
    public List<MediaRankRelation> getOrderDetail(List<Long> ids) {
        return rankMediaRelationMapper.getOrderDetail(ids);
    }
}
