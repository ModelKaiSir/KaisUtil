package com.kais.core.task.quartz;

import com.kais.app.AppNotify;
import javafx.application.Platform;
import org.apache.log4j.Logger;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 消息提醒定时任务
 *
 * @author QiuKai
 */
public class AppNotifySchedulerTask extends BaseQuartzSchedulerTask {

    static final Logger LOGGER = Logger.getLogger(AppNotify.class);

    static final String GROUP = "APP_NOTIFY";

    private boolean popup;
    private boolean repeat;

    private ScheduleBuilder builder;
    private Date startAt;

    private String message;
    private String title;

    private TimeUnit timeUnit;
    private long time;

    private boolean build;

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

        if (isPopupRemind()) {

            Platform.runLater(() -> {
                AppNotify.toppingMessage(title, message);
            });
        } else {

            LOGGER.info(message);
        }
    }

    @Override
    public boolean isPopupRemind() {
        return popup;
    }

    @Override
    public Date startAt() {

        if (!build) {
            build();
        }
        return startAt;
    }

    public AppNotifySchedulerTask popup() {

        popup = true;
        return this;
    }

    public AppNotifySchedulerTask popup(Supplier<Boolean> supplier) {

        popup = supplier.get();
        return this;
    }

    public AppNotifySchedulerTask withRepeat() {

        repeat = true;
        return this;
    }

    public AppNotifySchedulerTask withRepeat(Supplier<Boolean> supplier) {
        repeat = supplier.get();
        return this;
    }

    public AppNotifySchedulerTask withUnit(TimeUnit unit) {

        timeUnit = unit;
        return this;
    }

    /**
     * @return
     */
    @Override
    public ScheduleBuilder getCronExpression() {

        if (!build) {
            build();
        }
        return builder;
    }

    public void build() {

        LocalDateTime dateTime = LocalDateTime.now();

        SimpleScheduleBuilder b = SimpleScheduleBuilder.simpleSchedule();

        if (repeat) {

            b.repeatForever();
        }

        switch (timeUnit) {

            default:
            case SECONDS:

                builder = b.withIntervalInSeconds((int) time);
                dateTime = dateTime.plusSeconds(time);
                break;
            case MINUTES:

                builder = b.withIntervalInMinutes((int) time);
                dateTime = dateTime.plusMinutes(time);
                break;
            case HOURS:

                builder = b.withIntervalInHours((int) time);
                dateTime = dateTime.plusHours(time);
                break;
            case DAYS:

                builder = b.withIntervalInHours((int) TimeUnit.HOURS.convert(time, timeUnit));
                dateTime = dateTime.plusDays(time);
                break;
        }

        startAt = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        build = true;
    }

    public static AppNotifySchedulerTask of(long time, String title, String message) {

        AppNotifySchedulerTask result = new AppNotifySchedulerTask();

        result.title = title;
        result.message = message;
        result.time = time;
        return result;
    }
}
