package com.kais.core.task;

import com.kais.app.AppMain;
import com.kais.app.AppNotify;
import org.quartz.*;
import org.springframework.context.event.ApplicationListenerMethodAdapter;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务
 *
 * @author QiuKai
 */
public class SchedulerTaskFactory implements Job {

    public static final String JOB_KEY = "Scheduler_Job";

    static SchedulerFactoryBean SCHEDULER_FACTORY = AppMain.getBean(SchedulerFactoryBean.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

        SchedulerTask task = (SchedulerTask) ctx.getMergedJobDataMap().get(JOB_KEY);
        task.run();
    }

    public static void addTask(SchedulerTask task) throws SchedulerException {

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(task.getTitle(), task.getGroup())
                .startAt(task.startAt())
                .withSchedule(task.getCronExpression())
                .build();

        JobDetail detail = JobBuilder.newJob(SchedulerTaskFactory.class).withIdentity(task.getTitle(), task.getGroup()).build();
        detail.getJobDataMap().put(SchedulerTaskFactory.JOB_KEY, task);

        SCHEDULER_FACTORY.getScheduler().scheduleJob(detail, trigger);
    }

    public static void resetTask(TriggerKey triggerKey, Trigger trigger, SchedulerTask task) throws SchedulerException {

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .withSchedule(task.getCronExpression()).build();

        //按新的trigger重新设置job执行
        SCHEDULER_FACTORY.getScheduler().rescheduleJob(triggerKey, trigger);
    }

    /**
     * 提交定时任务
     *
     * @param task
     */
    public static void submitTask(SchedulerTask task) {

        //保存定时任务的信息，在界面上可以看到任务的执行状态
        try {

            Trigger trigger = null;

            try {

                TriggerKey triggerKey = TriggerKey.triggerKey(task.getTitle(), task.getGroup());

                trigger = SCHEDULER_FACTORY.getScheduler().getTrigger(triggerKey);
                Assert.notNull(trigger);
                resetTask(triggerKey, trigger, task);
            } catch (IllegalArgumentException | SchedulerException e) {
                // 没有正在执行的定时任务
                addTask(task);
            }
        } catch (SchedulerException e) {

            AppNotify.error(e);
        }
    }

    public static enum SchedulerMode {

        /**
         * 执行一次
         */
        NORMAL,
        /**
         * 重复执行
         */
        REPEAT,
        /**
         * 重复执行,指定一周的哪几天。
         */
        REPEAT_IN_WEEK;
    }
}
