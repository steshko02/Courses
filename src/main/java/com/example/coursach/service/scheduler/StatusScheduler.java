package com.example.coursach.service.scheduler;

import com.example.coursach.service.CourseService;
import com.example.coursach.service.LessonService;
import com.example.coursach.service.WorkService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class StatusScheduler {

    private final LessonService lessonService;
    private final WorkService workService;
    private final CourseService courseService;

    @Transactional
    @Scheduled(cron = "${status.cron}")
    public void scheduleFixedDelayTask() {
        log.info("Check course, lessons, task status");
        lessonService.checkAndSwitchStatus();
        courseService.checkAndSwitchStatus();
        workService.checkAndSwitchStatus();
    }
}
