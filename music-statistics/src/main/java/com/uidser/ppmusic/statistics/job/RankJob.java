package com.uidser.ppmusic.statistics.job;

import com.github.pagehelper.PageInfo;
import com.uidser.ppmusic.common.entity.*;
import com.uidser.ppmusic.common.entity.vo.QueryVo;
import com.uidser.ppmusic.common.feign.MediaFeignService;
import com.uidser.ppmusic.common.feign.RankFeignService;
import com.uidser.ppmusic.common.r.R;
import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransaction;
import io.seata.tm.api.GlobalTransactionContext;
import org.quartz.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

public class RankJob implements Job {

    @Resource
    private RankFeignService rankFeignService;

    @Resource
    private MediaFeignService mediaFeignService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = null;
        if(requestAttributes == null) {
            servletRequestAttributes = new ServletRequestAttributes(new MockHttpServletRequest());
        }
        RequestContextHolder.setRequestAttributes(servletRequestAttributes, true);
        System.out.println("执行了任务");
        Scheduler scheduler = jobExecutionContext.getScheduler();
        SchedulerContext context = null;
        try {
            context = scheduler.getContext();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        Long rankId = Long.valueOf(context.get("rankId").toString());
        Long timeFrequency = Long.valueOf(context.get("timeFrequency").toString());
        Integer showLength = Integer.valueOf(context.get("showLength").toString());
        Long timeStamp = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        timeStamp -= timeFrequency;
        Boolean havaDayRank = false;
        Set<Long> daySnapShotSet = null;
        List<DaySnapShot> daySnapShotList = null;
        Map<Long, DaySnapShot> daySnapShotMap = new HashMap<>();
        R<List<DaySnapShot>> daySnapShotListR = rankFeignService.getDaySnapShotList(timeStamp, showLength);
        if(daySnapShotListR.getCode() == 200) {
            if(!ObjectUtils.isEmpty(daySnapShotListR.getData())) {
                havaDayRank = true;
                daySnapShotList = daySnapShotListR.getData();
                daySnapShotMap = daySnapShotList.stream().collect(Collectors.toMap(daySnapShot -> daySnapShot.getMediaId(), daySnapShot -> daySnapShot));
                daySnapShotSet = daySnapShotList.stream().map(daySnapShot -> daySnapShot.getMediaId()).collect(Collectors.toSet());
            }
        }
        Integer realFrequency = 0;
        R<Rank> rankR = rankFeignService.get(rankId);
        if(rankR.getCode() == 200) {
            System.out.println("1:200");
            Rank rank = rankR.getData();
            realFrequency = rank.getRankFrequency() + 1;
        }
        Integer type = Integer.valueOf(context.get("type").toString());
        Integer mediaType = Integer.valueOf(context.get("mediaType").toString());
        Scheduled scheduled = new Scheduled();
        scheduled.setRankId(rankId);
        scheduled.setType(type);
        scheduled.setFrequency(realFrequency);
        scheduled.setShowLength(showLength);
        scheduled.setMediaType(mediaType);
        Map<Long, MediaRankRelation> previousMediaMap= null;
        R<List<ListenQuantitySnapshot>> r = rankFeignService.getByRankId(scheduled);
        R<List<MediaRankRelation>> previousMediaListR = rankFeignService.getByOrder(rankId,  realFrequency - 1);
        if(previousMediaListR.getCode() == 200) {
            if(previousMediaListR.getData() != null) {
                List<MediaRankRelation> previousMediaList = previousMediaListR.getData();
                previousMediaMap = previousMediaList.stream().collect(Collectors.toMap(mediaRankRelation -> mediaRankRelation.getMediaId(), mediaRankRelation -> mediaRankRelation));
            }
        }
        if(r.getCode() == 200) {
            System.out.println("2:200");
            Date date = new Date();
            List<ListenQuantitySnapshot> listenQuantitySnapshotList = r.getData();
            List<MediaRankRelation> mediaRankRelationList = new ArrayList<>();
            List<Long> mediaIdList = listenQuantitySnapshotList.stream().map((listenQuantitySnapshot) -> {
                return listenQuantitySnapshot.getMediaId();
            }).collect(Collectors.toList());

            for (DaySnapShot daySnapShot: daySnapShotList) {
                mediaIdList.add(daySnapShot.getMediaId());
            }
            QueryVo queryVo = new QueryVo();
            queryVo.setCurrent(1);
            queryVo.setLimit(mediaIdList.size());
            R<PageInfo<Media>> mediaListR = mediaFeignService.getRankMediaList(mediaIdList, queryVo);
            if(mediaListR.getCode() == 200) {
                System.out.println("3:200");
                PageInfo<Media> mediaPageInfo = mediaListR.getData();
                if(mediaPageInfo != null) {
                    List<Media> mediaList = mediaPageInfo.getList();
                    if(mediaList.size() > 0) {
                        System.out.println("medialList");
                        System.out.println(mediaList);
                        Set<Long> rankMediaSet = mediaList.stream().map(media -> media.getId()).collect(Collectors.toSet());
                        Map<Long, Media> mediaMap = mediaList.stream().collect(Collectors.toMap(media -> media.getId(), media -> media));
                        Integer rankNum = 0;
                        Set<Long> noInRankSet = new HashSet<>();
                        if(havaDayRank) {
                            for (DaySnapShot daySnapShot: daySnapShotList) {
                                if(!rankMediaSet.add(daySnapShot.getMediaId())) {
                                    noInRankSet.add(daySnapShot.getMediaId());
                                }
                            }
                        }
                        for (Long daySnapShotId: noInRankSet) {
                            DaySnapShot daySnapShot = daySnapShotMap.get(daySnapShotId);
                            ListenQuantitySnapshot listenQuantitySnapshot = new ListenQuantitySnapshot();
                            listenQuantitySnapshot.setListenQuantity(daySnapShot.getListenQuantity());
                            listenQuantitySnapshot.setMediaId(daySnapShot.getMediaId());
                            listenQuantitySnapshot.setMediaType(mediaType);
                            listenQuantitySnapshotList.add(listenQuantitySnapshot);
                        }
                        listenQuantitySnapshotList = listenQuantitySnapshotList.stream().sorted((e1, e2) -> {
                            return e1.getListenQuantity().compareTo(e2.getListenQuantity());
                        }).collect(Collectors.toList());
                        Collections.reverse(listenQuantitySnapshotList);
                        for (ListenQuantitySnapshot listenQuantitySnapshot: listenQuantitySnapshotList) {
                            rankNum++;
                            if(rankNum > showLength) {
                                break;
                            }
                            MediaRankRelation mediaRankRelation = new MediaRankRelation();
                            mediaRankRelation.setRankId(listenQuantitySnapshot.getRankId());
                            mediaRankRelation.setRankFrequency(realFrequency);
                            mediaRankRelation.setMediaId(listenQuantitySnapshot.getMediaId());
                            mediaRankRelation.setMediaName(mediaMap.get(listenQuantitySnapshot.getMediaId()).getName());
                            mediaRankRelation.setRankDetail(rankNum);
                            mediaRankRelation.setLastRankDate(date);
                            mediaRankRelation.setRankId(rankId);
                            mediaRankRelation.setListenQuantity(listenQuantitySnapshot.getListenQuantity());
                            if(previousMediaMap.get(listenQuantitySnapshot.getMediaId()) != null) {
                                Integer step = previousMediaMap.get(listenQuantitySnapshot.getMediaId()).getRankDetail() - rankNum;
                                mediaRankRelation.setStep(step);
                                Double current = Double.valueOf(listenQuantitySnapshot.getListenQuantity().toString());
                                Double previous = Double.valueOf(previousMediaMap.get(listenQuantitySnapshot.getMediaId()).getListenQuantity().toString());
                                Double growthRate = ((current - previous) / previous) * 100;
                                mediaRankRelation.setGrowthRate(growthRate);
                            } else {
                                mediaRankRelation.setStep((listenQuantitySnapshotList.size() - rankNum) + 1);
                                mediaRankRelation.setGrowthRate(Double.valueOf(mediaRankRelation.getStep().toString()) * 100);
                            }
                            mediaRankRelationList.add(mediaRankRelation);
                        }
                    }
                }
            }
            if(mediaRankRelationList.size() > 0) {
                GlobalTransaction globalTransaction = GlobalTransactionContext.createNew();
                try {
                    globalTransaction.begin();
                } catch (TransactionException e) {
                    throw new RuntimeException(e);
                }
                String xid = globalTransaction.getXid();
                try{
                    rankFeignService.insertMediaRankRelation(mediaRankRelationList, date);
                    rankFeignService.addFrequency(rankId, date);
                    globalTransaction.commit();
                } catch (Exception e){
                    e.printStackTrace();
                    try {
                        GlobalTransactionContext.reload(xid).rollback();
                    } catch (TransactionException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
}
