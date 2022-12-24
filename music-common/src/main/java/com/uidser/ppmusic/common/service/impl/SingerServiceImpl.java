package com.uidser.ppmusic.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.*;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.feign.SearchFeignService;
import com.uidser.ppmusic.common.mapper.SingerMapper;
import com.uidser.ppmusic.common.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class SingerServiceImpl implements SingerService {

    @Resource
    private SingerMapper singerMapper;

    @Resource
    private CategorySingerRelationService categorySingerRelationService;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private SingerMediaRelationService singerMediaRelationService;

    @Resource
    private MediaService mediaService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private SearchFeignService searchFeignService;

    @Override
    public List<Singer> query(String queryText) {
        return singerMapper.query(queryText);
    }

    @Override
    public List<Singer> getByIds(List<Long> singerIdList) {
        List<Singer> singerList = singerMapper.getByIds(singerIdList);
        Set<Long> singerIds = singerList.stream().map(singer -> singer.getId()).collect(Collectors.toSet());
        List<CategorySingerRelation> categorySingerRelationList = categorySingerRelationService.getRelationBySingerIds(singerIds);
        Map<Long, List<List<Long>>> threeLevelCategoryMap = categoryService.getThreeLevelCategory(categorySingerRelationList);
        for (int i = 0; i < singerList.size(); i++) {
            singerList.get(i).setCategoryIdList(threeLevelCategoryMap.get(singerList.get(i).getId()));
        }
        return singerList;
    }

    @Override
    public PageInfo<Singer> page(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getCurrent(), queryVo.getLimit());
        List<Singer> singerList = singerMapper.page(queryVo);
        return new PageInfo<>(singerList);
    }

    @Override
    @Transactional
    public void insert(Singer singer) {
        singer.setCreateTime(new Date());
        singer.setFansQuantity(0L);
        singerMapper.insert(singer);
        categorySingerRelationService.insert(singer.getLastCategoryIdList(), singer.getId());
        List<Category> categoryList = categoryService.getByIds(singer.getLastCategoryIdList());
        List<com.uidser.ppmusic.common.entity.es.Category> categoryList1 = new ArrayList<>();
        for (Category category: categoryList) {
            com.uidser.ppmusic.common.entity.es.Category category1 = new com.uidser.ppmusic.common.entity.es.Category();
            BeanUtils.copyProperties(category, category1);
            categoryList1.add(category1);
        }
        singer.setCategoryList(categoryList1);
        Singer singer1 = new Singer();
        singer1.setId(singer.getId());
        singer1.setName(singer.getName());
        singer1.setCategoryList(singer.getCategoryList());
        singer1.setProfilePicture(singer.getProfilePicture());
        singer1.setLastCategoryIdList(null);
        singer1.setCategoryIdList(null);
        searchFeignService.insertSinger(singer1);
    }

    @Override
    public SingerInfo getSingerInfo(Long id) {
        SingerInfo singerInfo = new SingerInfo();
        CompletableFuture<Void> singerInfoCompletableFuture = CompletableFuture.runAsync(() -> {
            Singer singer = singerMapper.get(id);
            BeanUtils.copyProperties(singer, singerInfo);
        }, threadPoolExecutor);
        CompletableFuture<Void> songListMvListCompletableFuture = CompletableFuture.runAsync(() -> {
            List<Long> mediaIdList = singerMediaRelationService.get(id);
            if(mediaIdList.size() > 0) {
                List<Media> mediaList = mediaService.getByIds(mediaIdList, 11, 10);
                singerInfo.setSongList(mediaList);
            }
        });
        CompletableFuture<Void> all = CompletableFuture.allOf(singerInfoCompletableFuture, songListMvListCompletableFuture);
        try {
            all.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return singerInfo;
    }

    @Override
    @Transactional
    public void edit(Singer singer) {
        singerMapper.edit(singer);
        categorySingerRelationService.deleteRelation(singer);
        categorySingerRelationService.insert(singer.getLastCategoryIdList(), singer.getId());
    }

    @Override
    public List<Singer> queryByCategory(List<Long> categoryIdList) {
        return categorySingerRelationService.getRelationByCategoryIds(categoryIdList);
    }

    @Override
    public void changeShowStatus(Long singerId, Integer showStatus) {
        singerMapper.changeShowStatus(singerId, showStatus);
    }

    @Override
    public void batchDelete(List<Long> singerIdList) {
        singerMapper.batchDelete(singerIdList);
    }
}
