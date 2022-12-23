package com.uidser.ppmusic.statistics.controller;

import com.uidser.ppmusic.common.r.R;
import com.uidser.ppmusic.common.entity.Scheduled;
import com.uidser.ppmusic.statistics.service.ScheduledTaskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/schedule")
public class ScheduledTaskController {

    @Resource
    private ScheduledTaskService scheduledTaskService;

    @PostMapping("/executeTask")
    public R executeTask(@RequestBody Scheduled scheduled) {
        scheduledTaskService.executeTask(scheduled);
        return new R().ok();
    }

    @PostMapping("/pause")
    public R pause(@RequestBody Scheduled scheduled) {
        scheduledTaskService.pause(scheduled);
        return new R().ok();
    }

    @PostMapping("/resume")
    public R resume(@RequestBody Scheduled scheduled) {
        scheduledTaskService.resume(scheduled);
        return new R().ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Scheduled scheduled) {
        scheduledTaskService.delete(scheduled);
        return new R().ok();
    }
}
