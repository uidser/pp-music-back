package com.uidser.ppmusic.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Album;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.mapper.AlbumMapper;
import com.uidser.ppmusic.common.service.AlbumService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Resource
    private AlbumMapper albumMapper;

    @Override
    public List<Album> query(QueryVo queryVo) {
        return albumMapper.query(queryVo);
    }

    @Override
    public void insert(Album album) {
        album.setCreateTime(new Date());
        album.setSingerId(album.getSingerIds().get(0));
        albumMapper.insert(album);
    }

    @Override
    public void edit(Album album) {
        album.setUpdateTime(new Date());
        albumMapper.edit(album);
    }

    @Override
    public void batchDelete(List<Long> albumIds) {
        albumMapper.batchDelete(albumIds);
    }

    @Override
    public void changeShowStatus(Long albumId, Integer showStatus) {
        albumMapper.changeShowStatus(albumId, showStatus);
    }

    @Override
    public PageInfo<Album> page(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getCurrent(), queryVo.getLimit());
        List<Album> albumList = albumMapper.page(queryVo);
        return new PageInfo<>(albumList);
    }

    @Override
    public Album get(Long albumId) {
        return albumMapper.get(albumId);
    }
}
