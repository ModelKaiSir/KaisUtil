package com.kais.core.task;

import com.kais.app.AppNotify;
import javafx.application.Platform;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 定时提醒任务
 *
 * @author QiuKai
 */
public class SimpleSchedulerTask implements SchedulerTask {

    static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(AppNotify.class);

    static final String GROUP = "NOTIFY";

    private String notifyMessage;
    private String title;

    private boolean notifiable = true;

    private ScheduleBuilder builder;
    private Date startAt;

    protected SimpleSchedulerTask() {

    }

    @Override
    public String getGroup() {
        return GROUP;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void run() {

        if (notifiable()) {

            //
            Platform.runLater(() -> {
                AppNotify.toppingMessage(notifyMessage);
            });
        } else {

            LOGGER.info(notifyMessage);
        }
    }

    @Override
    public boolean notifiable() {
        return notifiable;
    }

    @Override
    public Date startAt() {
        return startAt;
    }

    /**
     * @return
     */
    @Override
    public ScheduleBuilder getCronExpression() {
        return builder;
    }

    public static SchedulerTask of(TimeUnit unit, long time, String title, String message) {

        SimpleSchedulerTask result = new SimpleSchedulerTask();
        result.title = title;
        result.notifyMessage = message;

        LocalDateTime dateTime = LocalDateTime.now();

        switch (unit) {

            default:
            case SECONDS:

                result.builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds((int) time);
                dateTime.plusSeconds(time);

                break;
            case MINUTES:

                result.builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes((int) time);
                dateTime.plusMinutes(time);
                break;
            case HOURS:

                result.builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours((int) time);
                dateTime.plusHours(time);
                break;
            case DAYS:

                result.builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours((int) unit.toHours(time));
                dateTime.plusDays(time);
                break;
        }

        result.startAt = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info(result.startAt);
        return result;
    }
}

