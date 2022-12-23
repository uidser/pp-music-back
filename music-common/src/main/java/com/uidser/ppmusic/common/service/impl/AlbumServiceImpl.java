package com.uidser.ppmusic.common.service.impl;

import com.uidser.ppmusic.common.entity.Album;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.mapper.AlbumMapper;
import com.uidser.ppmusic.common.service.AlbumService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Resource
    private AlbumMapper albumMapper;

    @Override
    public List<Album> query(QueryVo queryVo) {
        return albumMapper.query(queryVo);
    }
}
