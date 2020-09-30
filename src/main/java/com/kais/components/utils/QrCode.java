package com.kais.components.utils;

import com.kais.app.AppMain;
import com.kais.views.AbstractFxmlView;
import com.kais.views.TestView;
import com.kais.views.util.qrcode.QrCodeView;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 识别二维码工具
 *
 * @author QiuKai
 */
@Component
public class QrCode extends AbstractUtilComponent{

    public QrCode() {

        super();
    }

    @Override
    public void run() {

        AppMain.showView(QrCodeView.class);
    }

    @Override
    protected String getCaption() {
        return "QRCode工具";
    }

    @Override
    public UtilType getType() {
        return UtilType.MAIN;
    }
}
