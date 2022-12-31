package com.uidser.ppmusic.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.uidser.ppmusic.common.entity.*;
import com.uidser.ppmusic.common.entity.vo.AttributeAttributeValueVo;
import com.uidser.ppmusic.common.entity.vo.IndexVo;
import com.uidser.ppmusic.common.entity.vo.MediaCommitVo;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.feign.RankFeignService;
import com.uidser.ppmusic.common.feign.SearchFeignService;
import com.uidser.ppmusic.common.mapper.MediaMapper;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.service.*;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class MediaServiceImpl implements MediaService {

    @Resource
    private MediaMapper mediaMapper;

    @Resource
    private SingerService singerService;

    @Resource
    private SingerMediaRelationService singerMediaRelationService;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private AttributeAttributeValueRelationService attributeAttributeValueRelationService;

    @Resource
    private AttributeValueService attributeValueService;

    @Resource
    private RankFeignService rankFeignService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private SearchFeignService searchFeignService;

    @Resource
    private CategorySingerRelationService categorySingerRelationService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private AlbumMediaRelationService albumMediaRelationService;

    @Resource
    private AlbumSingerRelationService albumSingerRelationService;

    @Override
    @GlobalTransactional
    public Long insert(MediaCommitVo media) {
        Date date = new Date();
        Media media1 = new Media();
        BeanUtils.copyProperties(media, media1);
        media1.setCreateTime(date);
        media1.setUpdateTime(date);
        media1.setFavoriteQuantity(0L);
        media1.setMediaUrl(null);
        media1.setMediaProfilePictureImg(null);
        List<Long> singerIdList = media.getSingerIdList();
        List<Singer> singerList = singerService.getByIds(singerIdList);
        List<String> singerNameList = new ArrayList<>();
        for (Singer singer: singerList) {
            singerNameList.add(singer.getName());
        }
        String singerNameJoin = String.join("，", singerNameList);
        List<Long> singerIdList1 = media1.getSingerIdList();
        media1.setSingerIdList(null);
        media1.setIsDelete(0);
        media1.setAuthor(singerNameJoin);
        mediaMapper.insert(media1);
        CompletableFuture<Void> attributeValueRelation = CompletableFuture.runAsync(() -> {
            List<AttributeAttributeValueVo> attributeAttributeValueVoList = media.getAttributeAttributeValueVoList();
            if(attributeAttributeValueVoList != null) {
                if (attributeAttributeValueVoList.size() > 0) {
                    for (AttributeAttributeValueVo attributeAttributeValueVo : attributeAttributeValueVoList) {
                        attributeAttributeValueVo.setCreateTime(new Date());
                    }
                    attributeValueService.insert(attributeAttributeValueVoList);
                    attributeAttributeValueRelationService.relation(attributeAttributeValueVoList, media1.getId());
                }
            }
        }, threadPoolExecutor);
        CompletableFuture<Void> singerMediaRelation = CompletableFuture.runAsync(() -> {
            singerMediaRelationService.relation(media1.getId(), singerIdList1);
        }, threadPoolExecutor);
        CompletableFuture<Void> albumMediaRelation = CompletableFuture.runAsync(() -> {
            albumMediaRelationService.relation(media1.getAlbumId(), media1.getId());
        }, threadPoolExecutor);
        CompletableFuture.runAsync(() -> {
            albumSingerRelationService.relation(media1.getAlbumId(), singerIdList1);
        }, threadPoolExecutor);
        CompletableFuture<Void> allOf = CompletableFuture.allOf(attributeValueRelation, singerMediaRelation, albumMediaRelation);
        media1.setAlbumId(null);
        media1.setPublishDate(null);
        media1.setIsHaveMv(null);
        media1.setCreateTime(null);
        media1.setUpdateTime(null);
        media1.setIsDelete(null);
        media1.setShowStatus(null);
        media1.setMediaUrl("");
        List<Singer> singerList1 = singerService.getByIds(singerIdList1);
        Map<Long, Singer> singerMap = singerList1.stream().collect(Collectors.toMap(singer -> singer.getId(), singer -> singer));
        for (Long id: singerIdList1) {
            MediaSingerESModel mediaSingerESModel = new MediaSingerESModel();
            mediaSingerESModel.setId(id);
            mediaSingerESModel.setName(singerMap.get(id).getName());
            media1.getSinger().add(mediaSingerESModel);
        }
        Gson gson = new Gson();
        String singerList2 = gson.toJson(singerList1);
        redisTemplate.opsForHash().put("media:info:singer", media1.getId().toString(), singerList2);
        searchFeignService.insertMedia(media1);
        try {
            allOf.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return media1.getId();
    }

    @Override
    public void editMediaUrl(Long mediaId, String url, String column) {
        mediaMapper.editMediaUrl(mediaId, url, column);
    }

    @Override
    public IndexVo play() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(servletRequestAttributes, true);
        String json = redisTemplate.opsForValue().get("page:index:play");
        if(!ObjectUtils.isEmpty(json)) {
            Gson gson = new Gson();
            return gson.fromJson(json, IndexVo.class);
        } else {
            IndexVo indexVo = new IndexVo();
            List<Media> songList = mediaMapper.index(11, 100);
            indexVo.setFirstBrowsePlayList(songList);
            Gson gson = new Gson();
            String jsonString = gson.toJson(indexVo);
            redisTemplate.opsForValue().set("page:index:play", jsonString, 3600, TimeUnit.SECONDS);
            return indexVo;
        }
    }

    @Override
    public PageInfo<Media> page(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getCurrent(), queryVo.getLimit());
        List<Media> mediaList = mediaMapper.getByTerm(queryVo);
        return new PageInfo<>(mediaList);
    }

    @Override
    public void update(MediaCommitVo mediaCommitVo) {
        CompletableFuture.runAsync(() -> {
            List<AttributeAttributeValueVo> attributeAttributeValueVoList = mediaCommitVo.getAttributeAttributeValueVoList();
            if (attributeAttributeValueVoList.size() > 0) {
                attributeValueService.update(attributeAttributeValueVoList);
            }
        }, threadPoolExecutor);
        mediaCommitVo.setUpdateTime(new Date());
        mediaMapper.update(mediaCommitVo);
    }

    @Override
    public void changeShowStatus(Long mediaId, Integer status) {
        mediaMapper.changeShowStatus(mediaId, status);
    }

    @Override
    public void batchDelete(Long[] ids) {
        mediaMapper.batchDelete(ids);
    }

    @Override
    public PageInfo<Media> getRankMediaList(List<Long> ids, QueryVo queryVo) {
        if(queryVo != null) {
            if(queryVo.getCurrent() != null && queryVo.getLimit() != null) {
                PageHelper.startPage(queryVo.getCurrent(), queryVo.getLimit());
            }
        }
        List<Media> rankMediaList = mediaMapper.getRankMediaList(ids, queryVo);
        Map<Long, Media> mediaMap = rankMediaList.stream().collect(Collectors.toMap(media -> media.getId(), media -> media));
        List<Media> mediaList = new ArrayList<>();
        for (Long id: ids) {
            mediaList.add(mediaMap.get(id));
        }
        return new PageInfo<>(mediaList);
    }

    @Override
    public void addPlayQuantity(ListenQuantitySnapshot listenQuantitySnapshot) {
        rabbitTemplate.convertAndSend("media-exchange", "rabbit.add.play.quantity", listenQuantitySnapshot);
    }

    @Override
    public void addPlayQuantitySelf(List<ListenQuantitySnapshot> listenQuantitySnapshotList1) {
        mediaMapper.addPlayQuantitySelf(listenQuantitySnapshotList1);
    }

    @Override
    public IndexVo musicBuild() {
        Gson gson = new Gson();
        IndexVo indexVo = new IndexVo();
        String json = redisTemplate.opsForValue().get("page:index:build");
        if(!ObjectUtils.isEmpty(json)) {
            return gson.fromJson(json, IndexVo.class);
        } else {
            R<List<Rank>> rankListR = rankFeignService.index(3);
            if(rankListR.getCode() == 200) {
                indexVo.setRankList(rankListR.getData());
            }
            String jsonString = gson.toJson(indexVo);
            redisTemplate.opsForValue().set("page:index:build", jsonString, 3600, TimeUnit.SECONDS);
            return indexVo;
        }
    }

    @Override
    public SingerListPage singerList() {
        QueryVo queryVo = new QueryVo();
        queryVo.setCurrent(1);
        queryVo.setLimit(10);
        PageInfo<Singer> singerPageInfo = singerService.page(queryVo);
        List<Singer> singerList = singerPageInfo.getList();
        List<Category> categoryList = categorySingerRelationService.getAll();
        List<Long> parentIdList = categoryList.stream().map(category -> category.getParentId()).collect(Collectors.toList());
        List<Category> parentCategory = categoryService.getByIds(parentIdList);
        for (Category category :parentCategory) {
            categoryList.add(category);
        }
        List<Category> finalCategoryList = new ArrayList<>();
        for (Category category: categoryList) {
            this.packageCategory(categoryList, category, finalCategoryList);
        }
        SingerListPage singerListPage = new SingerListPage();
        singerListPage.setCategoryList(finalCategoryList);
        singerListPage.setSingerList(singerList);
        return singerListPage;
    }

    @Override
    public List<Media> getByIds(List<Long> mediaIdList, Integer type, Integer limit) {
        return mediaMapper.getByIds(mediaIdList, type, limit);
    }

    @Override
    public List<Singer> getAuthor(Long mediaId) {
        String jsonString = redisTemplate.opsForHash().get("media:info:singer", mediaId.toString()).toString();
        Gson gson = new Gson();
        List<Singer> singerList = gson.fromJson(jsonString, List.class);
        return singerList;
    }

    private void packageCategory(List<Category> categoryList, Category category, List<Category> finalCategoryList) {
        for (Category category1: categoryList) {
            if(category1.getParentId() == category.getId()) {
                category.setId(category.getId());
                category.setName(category.getName());
                category.getCategoryChildrenList().add(category1);
                this.packageCategory(categoryList, category1, finalCategoryList);
            }
        }
        if(category.getCategoryChildrenList().size() > 0) {
            finalCategoryList.add(category);
        }
    }
}
