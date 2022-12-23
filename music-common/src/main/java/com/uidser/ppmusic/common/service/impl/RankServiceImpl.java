package com.uidser.ppmusic.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.uidser.ppmusic.common.entity.*;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.feign.MediaFeignService;
import com.uidser.ppmusic.common.feign.StatisticsFeignService;
import com.uidser.ppmusic.common.mapper.RankMapper;
import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.service.RankMediaRelationService;
import com.uidser.ppmusic.common.service.RankService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RankServiceImpl implements RankService {

    @Resource
    private RankMapper rankMapper;

    @Resource
    private RankMediaRelationService rankMediaRelationService;

    @Resource
    private StatisticsFeignService statisticsFeignService;

    @Resource
    private MediaFeignService mediaFeignService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Rank> index(Integer limit) {
        List<Rank> rankList = rankMapper.getAll(limit);
        List<RankMediaRelation> rankMediaRelationList = rankMediaRelationService.getByRankId(rankList);
        List<Media> mediaList = null;
        Map<Long, Media> mediaMap = new HashMap<>();
        if(rankMediaRelationList.size() > 0) {
            List<Long> mediaIds = rankMediaRelationList.stream().map((rankMediaRelation) -> {
                return rankMediaRelation.getMediaId();
            }).collect(Collectors.toList());
            R<PageInfo<Media>> mediaListR = mediaFeignService.getRankMediaList(mediaIds, new QueryVo());
            if(mediaListR.getCode() == 200) {
                mediaList = mediaListR.getData().getList();
                Set<Long> mediaIdSet = new HashSet<>();
                for (Media media: mediaList) {
                    if(mediaIdSet.add(media.getId())) {
                        mediaMap.put(media.getId(), media);
                    }
                }
            }
        }
        Integer rankCount = 0;
        for (Rank rank: rankList) {
            rankCount++;
            if(rankCount > 2) {
                break;
            }
            Integer count = 0;
            List<Long> rankMediaIdList = rank.getRankMediaIdList();
            for(Long mediaId: rankMediaIdList) {
                count++;
                rank.getMediaList().add(mediaMap.get(mediaId));
                if(count > 2) {
                    break;
                }
            }
        }
        return rankList;
    }

    @Override
    public PageInfo<Rank> page(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getCurrent(), queryVo.getLimit());
        List<Rank> rankList = rankMapper.page(queryVo);
        return new PageInfo<>(rankList);
    }

    @Override
    public void changeShowStatus(Long rankId, Integer status) {
        rankMapper.changeShowStatus(rankId, status);
    }

    @Override
    public void batchDelete(Long[] ids) {
        rankMapper.batchDelete(ids);
    }

    @Override
    public Rank getRankDetailMediaList(Long rankId, Integer frequency) {
        return rankMapper.getRankDetailMediaList(rankId, frequency);
    }

    @Override
    public void insert(Rank rank) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(servletRequestAttributes, true);
        Date date = new Date();
        rank.setCreateTime(date);
        rank.setProfilePicture("");
        rank.setRankFrequency(0);
        rankMapper.insert(rank);
        Scheduled scheduled = new Scheduled();
        scheduled.setRankId(rank.getId());
        scheduled.setType(rank.getType());
        scheduled.setShowLength(rank.getShowLength());
        scheduled.setTime(rank.getRankTime() * 60 * 60);
        scheduled.setMediaType(rank.getType());
        statisticsFeignService.executeTask(scheduled);
    }

    @Override
    public Rank get(Long rankId) {
        return rankMapper.get(rankId);
    }

    @Override
    public void edit(Rank rank) {
        Date date = new Date();
        rank.setUpdateTime(date);
        rank.setProfilePicture("");
        rank.setType(0);
        rankMapper.edit(rank);
    }

    @Override
    public void addFrequency(Long rankId, Date date) {
        rankMapper.addFrequency(rankId, date);
    }

    @Override
    public Rank getRankDetail(Long rankId, Integer frequency, QueryVo queryVo) {
        Rank rank = rankMapper.getRankDetailMediaList(rankId, frequency);
        R<PageInfo<Media>> mediaListR = mediaFeignService.getRankMediaList(rank.getRankMediaIdList(), queryVo);
        if(mediaListR.getCode() == 200) {
            PageInfo<Media> mediaPageInfo = mediaListR.getData();
            if(!ObjectUtils.isEmpty(mediaPageInfo)) {
                rank.setMediaList(mediaPageInfo.getList());
            }
        }
        return rank;
    }

    @Override
    public List<Rank> getAll() {
        String jsonString = redisTemplate.opsForValue().get("page:rankList:index");
        Gson gson = new Gson();
        if(!ObjectUtils.isEmpty(jsonString)) {
            List<Rank> list = gson.fromJson(jsonString, List.class);
            return list;
        } else {
            List<Rank> rankList = rankMapper.getAll(null);
            List<Long> mediaIdList = new ArrayList<>();
            for (Rank rank: rankList) {
                List<Long> rankMediaIdList = rank.getRankMediaIdList();
                for (Long mediaId: rankMediaIdList) {
                    mediaIdList.add(mediaId);
                }
            }
            List<Rank> rankList1 = rankMapper.getListenQuantity();
            Map<Long, Rank> rankMap = rankList1.stream().collect(Collectors.toMap(rank -> rank.getId(), rank -> rank));
            R<PageInfo<Media>> mediaListR = mediaFeignService.getRankMediaList(mediaIdList, new QueryVo());
            if(mediaListR.getCode() == 200) {
                if(!ObjectUtils.isEmpty(mediaListR.getData())) {
                    PageInfo<Media> mediaPageInfo = mediaListR.getData();
                    List<Media> mediaList = mediaPageInfo.getList();
                    Set<Long> mediaIdSet = new HashSet<>();
                    Map<Long, Media> mediaMap = new HashMap<>();
                    for (Media media: mediaList) {
                        if(mediaIdSet.add(media.getId())) {
                            mediaMap.put(media.getId(), media);
                        }
                    }
                    for (Rank rank: rankList) {
                        rank.setRankListenQuantity(rankMap.get(rank.getId()).getRankListenQuantity());
                        Integer count = 0;
                        List<Long> rankMediaIdList = rank.getRankMediaIdList();
                        for (Long mediaId: rankMediaIdList) {
                            count++;
                            rank.getMediaList().add(mediaMap.get(mediaId));
                            if(count > 2) {
                                break;
                            }
                        }
                    }
                }
            }
            String json = gson.toJson(rankList);
            redisTemplate.opsForValue().set("page:rankList:index", json, 3600, TimeUnit.SECONDS);
            return rankList;
        }
    }
}
