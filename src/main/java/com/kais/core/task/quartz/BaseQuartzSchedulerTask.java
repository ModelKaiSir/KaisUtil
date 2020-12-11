package com.kais.core.task.quartz;

import com.kais.core.task.SchedulerTask;
import org.quartz.ScheduleBuilder;

import java.util.Date;

/**
 *
 * Quartz定时任务
 * @author QiuKai
 */
public abstract class BaseQuartzSchedulerTask implements SchedulerTask {

    /**
     * 定时任务组
     *
     * @return
     */
    public abstract String getGroup();

    /**
     * 定时任务标题
     *
     * @return
     */
    public abstract String getTitle();


    /**
     * 是否弹窗提醒用户
     *
     * @return
     */
    public abstract boolean isPopupRemind();

    /**
     * 定时任务开始执行时间
     *
     * @return
     */
    public abstract Date startAt();

    /**
     * quartz cron表达式
     *
     * @return
     */
    public abstract ScheduleBuilder getCronExpression();
}
