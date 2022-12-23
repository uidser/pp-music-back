package com.uidser.ppmusic.statistics.service.impl;

import com.uidser.ppmusic.common.entity.Scheduled;
import com.uidser.ppmusic.common.exception.MusicException;
import com.uidser.ppmusic.statistics.job.RankJob;
import com.uidser.ppmusic.statistics.service.ScheduledTaskService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;


@Service
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

    @Resource
    private Scheduler scheduler;

    @Override
    public void executeTask(Scheduled scheduled) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = null;
        if(requestAttributes == null) {
            servletRequestAttributes = new ServletRequestAttributes(new MockHttpServletRequest());
        }
        RequestContextHolder.setRequestAttributes(servletRequestAttributes, true);
        try {
            scheduler.getContext().put("rankId", scheduled.getRankId());
            scheduler.getContext().put("type", scheduled.getType());
            scheduler.getContext().put("showLength", scheduled.getShowLength());
            scheduler.getContext().put("mediaType", scheduled.getMediaType());
            scheduler.getContext().put("timeFrequency", Integer.valueOf(scheduled.getTime().toString().split("\\.")[0]));
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(Integer.valueOf(scheduled.getTime().toString().split("\\.")[0])).repeatForever())
                    .build();
            JobDetail jobDetail = JobBuilder.newJob(RankJob.class).withIdentity(scheduled.getRankId().toString(), "rankGroup").build();
            System.out.println("本次定时任务周期是" + Integer.valueOf(scheduled.getTime().toString().split("\\.")[0]));
            scheduler.scheduleJob(jobDetail, trigger);
//            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MusicException("设置定时任务失败", 201);
        }

    }

    @Override
    public void pause(Scheduled scheduled) {
        JobKey jobKey = JobKey.jobKey(scheduled.getRankId().toString(), "rankGroup");
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MusicException("暂停定时任务失败", 201);
        }
    }

    @Override
    public void resume(Scheduled scheduled) {
        JobKey jobKey = JobKey.jobKey(scheduled.getRankId().toString(), "rankGroup");
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MusicException("恢复定时任务失败", 201);
        }
    }

    @Override
    public void delete(Scheduled scheduled) {
        JobKey jobKey = JobKey.jobKey(scheduled.getRankId().toString(), "rankGroup");
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MusicException("删除定时任务失败", 201);
        }
    }
}
