package com.kais.core.task.quartz;

import com.kais.app.AppNotify;
import com.kais.core.task.SchedulerTask;
import javafx.application.Platform;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 科传上班打卡
 *
 * @author QiuKai
 */
public class TechTransClockWork extends BaseQuartzSchedulerTask {

    private Date clockDate;

    @Override
    public String getGroup() {
        return "TECH-TRANS";
    }

    @Override
    public String getTitle() {
        return "上班打卡";
    }

    @Override
    public void run() {

        //下班后，弹出记录工作内容的窗口
        if (isPopupRemind()) {

            //
            Platform.runLater(() -> {
                AppNotify.toppingMessage("下班啦!");
            });
        } else {

            //
            System.out.println("下班啦!");
        }
    }

    @Override
    public boolean isPopupRemind() {
        return true;
    }

    @Override
    public Date startAt() {

        //执行时间，即下班时间
        return clockDate;
    }

    @Override
    public ScheduleBuilder getCronExpression() {
        return SimpleScheduleBuilder.simpleSchedule();
    }

    public static SchedulerTask of(int workHours, LocalDateTime workDate) {

        TechTransClockWork task = new TechTransClockWork();

        LocalDateTime clockDateTime = workDate.plusHours(workHours);
        task.clockDate = Date.from(clockDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return task;
    }
}
