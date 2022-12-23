package com.uidser.ppmusic.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.Song;
import com.uidser.ppmusic.common.entity.vo.MvCommitVo;
import com.uidser.ppmusic.common.entity.vo.MvReturnVo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.mapper.MvMapper;
import com.uidser.ppmusic.common.service.MvService;
import com.uidser.ppmusic.common.service.SongService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MvServiceImpl implements MvService {

    @Resource
    private MvMapper mvMapper;

    @Resource
    private SongService songService;

    @Override
    public PageInfo<MvReturnVo> page(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getCurrent(), queryVo.getLimit());
        List<MvReturnVo> mvReturnVoList = mvMapper.page(queryVo);
        PageInfo<MvReturnVo> mvReturnVoPageInfo = new PageInfo<>(mvReturnVoList);
        List<MvReturnVo> list = mvReturnVoPageInfo.getList();
        List<Long> songIds = new ArrayList<>();
        for (MvReturnVo mvReturnVo:list) {
            songIds.add(mvReturnVo.getSongId());
        }
        if(songIds.size() > 0) {
            List<Song> songList = songService.getByIds(songIds);
            Map<Long, Song> songMap = new HashMap<>();
            for (Song song: songList) {
                songMap.put(song.getId(), song);
            }
            Set<Long> songIdSet = songMap.keySet();
            for (MvReturnVo mvReturnVo: list) {
                if(songIdSet.contains(mvReturnVo.getSongId())) {
                    mvReturnVo.setSong(songMap.get(mvReturnVo.getSongId()));
                }
            }
        }
        return mvReturnVoPageInfo;
    }

    @Override
    public MvReturnVo getById(Long id) {
        QueryVo queryVo = new QueryVo();
        queryVo.setCurrent(1);
        queryVo.setLimit(1);
        queryVo.setQueryText(id.toString());
        PageInfo<MvReturnVo> page = this.page(queryVo);
        List<MvReturnVo> list = page.getList();
        return list.get(0);
    }

    @Override
    public void insert(MvCommitVo mvCommitVo) {
        mvCommitVo.setCreateTime(new Date());
        mvCommitVo.setForwardQuantity(0L);
        mvCommitVo.setWatchQuantity(0L);
        mvMapper.insert(mvCommitVo);
    }

    @Override
    public void edit(MvCommitVo mvCommitVo) {
        mvMapper.edit(mvCommitVo);
    }

    @Override
    public void changeShowStatus(Long mvId, Integer status) {
        mvMapper.changeShowStatus(mvId, status);
    }

    @Override
    public void batchDelete(Long[] ids) {
        mvMapper.batchDelete(ids);
    }
}
