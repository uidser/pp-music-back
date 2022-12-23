package com.uidser.ppmusic.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Song;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.mapper.SongMapper;
import com.uidser.ppmusic.common.service.SongService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Resource
    private SongMapper songMapper;

    @Override
    public void insert(Song song) {
        songMapper.insert(song);
    }

    @Override
    public PageInfo<Song> page(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getCurrent(), queryVo.getLimit());
        List<Song> songList = songMapper.getAll(queryVo);
        return new PageInfo<Song>(songList);
    }

    @Override
    public void changeShowStatus(Long songId, Integer status) {
        songMapper.changeShowStatus(songId, status);
    }

    @Override
    public void batchDelete(Long[] ids) {
        songMapper.batchDelete(ids);
    }

    @Override
    public List<Song> getByIds(List<Long> songIds) {
        return songMapper.getByIds(songIds);
    }
}
