package com.kais.core.task.quartz;

import com.kais.app.AppMain;
import com.kais.app.AppNotify;
import com.kais.core.task.SchedulerTask;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.Assert;

/**
 * 定时任务
 *
 * @author QiuKai
 */
public class QuartzJobFactory implements Job {

    public static final String JOB_KEY = "Scheduler_Job";

    static SchedulerFactoryBean SCHEDULER_FACTORY = AppMain.getBean(SchedulerFactoryBean.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

        SchedulerTask task = (SchedulerTask) ctx.getMergedJobDataMap().get(JOB_KEY);
        task.run();
    }

    public static void addTask(BaseQuartzSchedulerTask task) throws SchedulerException {

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(task.getTitle(), task.getGroup())
                .startAt(task.startAt())
                .withSchedule(task.getCronExpression())
                .build();

        JobDetail detail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(task.getTitle(), task.getGroup()).build();
        detail.getJobDataMap().put(QuartzJobFactory.JOB_KEY, task);

        SCHEDULER_FACTORY.getScheduler().scheduleJob(detail, trigger);
    }

    public static void resetTask(TriggerKey triggerKey, Trigger trigger, BaseQuartzSchedulerTask task) throws SchedulerException {

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .startAt(task.startAt())
                .withSchedule(task.getCronExpression()).build();

        //按新的trigger重新设置job执行
        SCHEDULER_FACTORY.getScheduler().rescheduleJob(triggerKey, trigger);
    }

    /**
     * 提交定时任务
     *
     * @param task
     */
    public static void submitTask(BaseQuartzSchedulerTask task) {

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
}
