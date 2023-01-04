package com.uidser.ppmusic.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.*;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.mapper.AlbumMapper;
import com.uidser.ppmusic.common.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Resource
    private AlbumMapper albumMapper;

    @Resource
    private AlbumSingerRelationService albumSingerRelationService;

    @Resource
    private SingerService singerService;

    @Resource
    private AlbumMediaRelationService albumMediaRelationService;

    @Resource
    private MediaService mediaService;

    @Override
    public List<Album> query(QueryVo queryVo) {
        return albumMapper.query(queryVo);
    }

    @Override
    @Transactional
    public void insert(Album album) {
        album.setCreateTime(new Date());
        album.setSingerId(album.getSingerIds().get(0));
        album.setFavoriteQuantity(0L);
        albumMapper.insert(album);
        albumSingerRelationService.relation(album.getId(), album.getSingerIds());
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
        Album album = albumMapper.get(albumId);
        List<AlbumSingerRelation> albumSingerRelationList = albumSingerRelationService.getRelationByAlbumId(albumId);
        List<Long> singerIdList = albumSingerRelationList.stream().map(albumSingerRelation -> albumSingerRelation.getSingerId()).collect(Collectors.toList());
        List<Singer> singerList = singerService.getByIds(singerIdList);
        album.setSingerList(singerList);
        album.setSingerIds(singerIdList);
        return album;
    }

    @Override
    public List<Album> getByIdsAndLimit(List<Long> albumIdList, int limit) {
        return albumMapper.getByIdsAndLimit(albumIdList, limit);
    }

    @Override
    public Album getAlbumAndSongList(Long albumId) {
        Album album = albumMapper.get(albumId);
        List<Long> mediaIds = albumMediaRelationService.getByAlbumId(albumId);
        List<Media> mediaList = mediaService.getByIds(mediaIds, 11, 100);
        album.setMediaList(mediaList);
        List<Singer> singerList = singerService.getByIds(Arrays.asList(album.getSingerId()));
        album.setSingerList(singerList);
        album.setMediaIdList(mediaIds);
        return album;
    }

    @Override
    @Transactional
    public void editRelation(AlbumMediaRelation albumMediaRelation) {
        albumMediaRelationService.deleteRelation(albumMediaRelation);
        albumMediaRelationService.relation(albumMediaRelation.getAlbumId(), albumMediaRelation.getMediaIdList());
    }
}
