package com.kais.core.task;

import org.quartz.ScheduleBuilder;

import java.util.Date;

/**
 * 定时任务
 * @author QiuKai
 */
public interface SchedulerTask {

    /**
     *
     * 定时任务组
     * @return
     */
    String getGroup();

    /**
     *
     * 定时任务标题
     * @return
     */
    String getTitle();

    /**
     *
     * 执行定时任务
     */
    void run();

    /**
     *
     * 是否通知用户
     * @return
     */
    boolean notifiable();

    /**
     *
     * 定时任务开始执行时间
     * @return
     */
    Date startAt();

    /**
     *
     * quartz cron表达式
     * @return
     */
    ScheduleBuilder getCronExpression();
}
