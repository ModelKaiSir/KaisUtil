package com.kais.components.utils;

import com.kais.app.AppMain;
import com.kais.views.util.scheduler.RemindView;
import org.springframework.stereotype.Component;

/**
 * 识别二维码工具
 *
 * @author QiuKai
 */
@Component
public class Remind extends AbstractUtilComponent {

    @Override
    protected String getCaption() {
        return "设置定时任务";
    }

    @Override
    public void run() {

        AppMain.showView(RemindView.class);
    }

    @Override
    public UtilType getType() {
        return UtilType.MAIN;
    }
}
