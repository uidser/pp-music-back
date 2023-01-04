package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.mapper.SongListMediaRelationMapper;
import com.uidser.ppmusic.common.service.SongListMediaRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SongListMediaRelationServiceImpl implements SongListMediaRelationService {

    @Resource
    private SongListMediaRelationMapper songListMediaRelationMapper;

    @Override
    public void relation(Long id, List<Long> songIdList) {
        songListMediaRelationMapper.relation(id, songIdList);
    }

    @Override
    public void deleteRelation(Long id) {
        songListMediaRelationMapper.deleteRelation(id);
    }

    @Override
    public List<Long> getRelation(Long id) {
        return songListMediaRelationMapper.getRelation(id);
    }
}
