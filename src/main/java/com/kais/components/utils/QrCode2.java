package com.kais.components.utils;

import com.kais.app.AppMain;
import com.kais.views.TestView;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 识别二维码工具
 *
 * @author QiuKai
 */
@Component
public class QrCode2 extends AbstractUtilComponent {

    public QrCode2() {

        super();
    }

    @Override
    public void run() {

        AppMain.showAndWait(TestView.class);
    }

    @Override
    protected String getCaption() {
        return "QRCode2";
    }

    @Override
    public UtilType getType() {
        return UtilType.TECH_TRANS;
    }
}
