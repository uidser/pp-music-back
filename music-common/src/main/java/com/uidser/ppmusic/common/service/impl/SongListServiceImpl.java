package com.uidser.ppmusic.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.uidser.ppmusic.common.entity.Category;
import com.uidser.ppmusic.common.entity.Media;
import com.uidser.ppmusic.common.entity.SongList;
import com.uidser.ppmusic.common.entity.User;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.feign.UserFeignService;
import com.uidser.ppmusic.common.mapper.SongListMapper;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.service.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SongListServiceImpl implements SongListService {

    @Resource
    private SongListMapper songListMapper;

    @Resource
    private SongListCategoryRelationService songListCategoryRelationService;

    @Resource
    private SongListMediaRelationService songListMediaRelationService;

    @Resource
    private UserFeignService userFeignService;

    @Resource
    private MediaService mediaService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public PageInfo<SongList> page(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getCurrent(), queryVo.getLimit());
        List<SongList> songListList = songListMapper.list(queryVo);
        return new PageInfo<>(songListList);
    }

    @Override
    @Transactional
    public void insert(SongList songList, String username) {
        List<Long> lastCategoryIdList = songList.getLastCategoryIdList();
        for (Long categoryId: lastCategoryIdList) {
            Set<String> keys = redisTemplate.keys("songList:page:info:" + categoryId + ":" + "*");
            if(keys != null) {
                if(keys.size() > 0) {
                    redisTemplate.delete(keys);
                }
            }
        }
        songList.setCreateTime(new Date());
        R<User> userR = userFeignService.getByUserName(username);
        User user = userR.getData();
        songList.setOwnerId(user.getId());
        songList.setCommentQuantity(0L);
        songList.setFavoriteQuantity(0L);
        songList.setForwardQuantity(0L);
        songListMapper.insert(songList);
        songListCategoryRelationService.relation(songList.getId(), songList.getLastCategoryIdList());
        songListMediaRelationService.relation(songList.getId(), songList.getSongIdList());
    }

    @Override
    @Transactional
    public void edit(SongList songList) {
        songList.setUpdateTime(new Date());
        songListMediaRelationService.deleteRelation(songList.getId());
        songListCategoryRelationService.deleteRelation(songList.getId());
        songListMediaRelationService.relation(songList.getId(), songList.getSongIdList());
        songListCategoryRelationService.relation(songList.getId(), songList.getLastCategoryIdList());
        List<Long> categoryIdList = songListCategoryRelationService.getRelation(songList.getId());
        for (Long categoryId: categoryIdList) {
            Set<String> keys = redisTemplate.keys("songList:page:info:" + categoryId + ":" + "*");
            if(keys != null && keys.size() > 0) {
                redisTemplate.delete(keys);
            }
        }
        songListMapper.edit(songList);
    }

    @Override
    public void changeShowStatus(Long id, Integer showStatus) {
        List<Long> categoryIdList = songListCategoryRelationService.getRelation(id);
        for (Long categoryId: categoryIdList) {
            Set<String> keys = redisTemplate.keys("songList:page:info:" + categoryId + ":" + "*");
            if(keys != null && keys.size() > 0) {
                redisTemplate.delete(keys);
            }
        }
        songListMapper.changeShowStatus(id, showStatus);
    }

    @Override
    @Transactional
    public void batchDelete(List<Long> ids) {
        songListMapper.batchDelete(ids);
    }

    @Override
    public SongList get(Long id) {
        SongList songList = songListMapper.get(id);
        List<Long> categoryIdList = songListCategoryRelationService.getRelation(id);
        List<Long> songIdList = songListMediaRelationService.getRelation(id);
        List<Media> mediaList = mediaService.getByIds(songIdList);
        R<User> userR = userFeignService.get(songList.getOwnerId());
        User user = userR.getData();
        songList.setOwner(user);
        songList.setSongList(mediaList);
        songList.setSongIdList(songIdList);
        List<Category> categoryList = categoryService.getAll();
        for (Long categoryId: categoryIdList) {
            List<Long> finalCategoryIdList = new ArrayList<>();
            findPreviousCategoryId(categoryId, categoryList, finalCategoryIdList);
            Collections.reverse(finalCategoryIdList);
            songList.getCategoryIdList().add(finalCategoryIdList);
        }
        return songList;
    }

    @Override
    public List<SongList> getByCategoryId(Long categoryId, QueryVo queryVo) {
        String jsonString = redisTemplate.opsForValue().get("songList:page:info:" + categoryId + ":" + queryVo.getCurrent() + ":" + queryVo.getLimit());
        Gson gson = new Gson();
        List<SongList> list = gson.fromJson(jsonString, List.class);
        if(!ObjectUtils.isEmpty(list)) {
            return list;
        } else {
            List<Long> songListIdList = songListCategoryRelationService.getByCategoryId(categoryId);
            PageHelper.startPage(queryVo.getCurrent(), queryVo.getLimit());
            List<SongList> songListList = songListMapper.listBySongListIdList(songListIdList);
            String json = gson.toJson(songListList);
            redisTemplate.opsForValue().set("songList:page:info:" + categoryId + ":" + queryVo.getCurrent() + ":" + queryVo.getLimit(), json, 3600, TimeUnit.SECONDS);
            return songListList;
        }
    }

    private void findPreviousCategoryId(Long categoryId, List<Category> categoryList, List<Long> finalCategoryIdList) {
        Map<Long, Category> categoryMap = categoryList.stream().collect(Collectors.toMap(category -> category.getId(), category -> category));
        finalCategoryIdList.add(categoryId);
        Set<Long> longSet = categoryMap.keySet();
        for (Long id: longSet) {
            Category category = categoryMap.get(id);
            if(categoryMap.get(categoryId).getParentId() == category.getId()) {
                findPreviousCategoryId(category.getId(), categoryList, finalCategoryIdList);
            }
        }
    }
}
