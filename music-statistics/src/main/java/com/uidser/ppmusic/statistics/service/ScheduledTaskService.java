package com.uidser.ppmusic.statistics.service;

import com.uidser.ppmusic.common.entity.Scheduled;

import javax.servlet.http.HttpServletRequest;

public interface ScheduledTaskService {
    void executeTask(Scheduled scheduled);

    void pause(Scheduled scheduled);

    void resume(Scheduled scheduled);

    void delete(Scheduled scheduled);
}
